package com.solux.moro.ui.mission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.solux.moro.data.dto.request.MissionUploadRequest
import com.solux.moro.data.dto.request.CommentRequest
import com.solux.moro.data.dto.response.CurrentMissionDto
import com.solux.moro.data.dto.response.MissionPostDto
import com.solux.moro.data.repository.mission.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

enum class FeedFilterType {
    GLOBAL, FOLLOWING
}

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val repository: MissionRepository
) : ViewModel() {

    private val _currentMission = MutableStateFlow<CurrentMissionDto?>(null)
    val currentMission: StateFlow<CurrentMissionDto?> = _currentMission.asStateFlow()

    private val _missionFeed = MutableStateFlow<List<MissionPostDto>>(emptyList())
    val missionFeed: StateFlow<List<MissionPostDto>> = _missionFeed.asStateFlow()

    private val _myMissions = MutableStateFlow<List<MissionPostDto>>(emptyList())
    val myMissions: StateFlow<List<MissionPostDto>> = _myMissions.asStateFlow()

    private val _todaySubmission = MutableStateFlow<MissionPostDto?>(null)
    val todaySubmission: StateFlow<MissionPostDto?> = _todaySubmission.asStateFlow()

    private val _currentFilter = MutableStateFlow(FeedFilterType.GLOBAL)
    val currentFilter: StateFlow<FeedFilterType> = _currentFilter.asStateFlow()

    private val _comments = MutableStateFlow<List<com.solux.moro.data.dto.response.MissionCommentDto>>(emptyList())
    val comments = _comments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uploadedPost = MutableStateFlow<MissionPostDto?>(null)
    val uploadedPost = _uploadedPost.asStateFlow()

    private val _analysisScore = MutableStateFlow<Double?>(null)
    val analysisScore = _analysisScore.asStateFlow()

    private val _nickname = MutableStateFlow("colorlover")
    val nickname = _nickname.asStateFlow()

    init {
        loadMissionData()
    }

    fun loadMissionData() {
        viewModelScope.launch {
            try {
                val missionRes = repository.getTodayMission()
                if (missionRes.isSuccessful) _currentMission.value = missionRes.body()?.data
                else if (missionRes.code() == 404) _currentMission.value = null

                val myRes = repository.getMyMissionPosts()
                if (myRes.isSuccessful) {

                    val myList = myRes.body()?.data?.sortedByDescending { it.misPostId } ?: emptyList()
                    _myMissions.value = myList


                    if (myList.isNotEmpty()) {
                        val myName = myList[0].userName
                        _nickname.value = myName
                        Log.d("MissionViewModel", "내 닉네임 감지됨: $myName")
                    }


                    val submission = myList.firstOrNull()

                    if (_uploadedPost.value != null) {
                        _todaySubmission.value = _uploadedPost.value
                    } else {
                        _todaySubmission.value = submission
                    }
                }

                loadFeedByFilter(_currentFilter.value)

            } catch (e: Exception) { e.printStackTrace() }
        }
    }
    fun changeFilter(filterType: FeedFilterType) {
        if (_currentFilter.value == filterType) return
        _currentFilter.value = filterType
        loadFeedByFilter(filterType)
    }

    private fun loadFeedByFilter(filterType: FeedFilterType) {
        viewModelScope.launch {
            try {
                val response = when (filterType) {
                    FeedFilterType.GLOBAL -> repository.getAllMissionPosts()
                    FeedFilterType.FOLLOWING -> repository.getFriendsMissionPosts()
                }
                if (response.isSuccessful) {
                    _missionFeed.value = response.body()?.data ?: emptyList()
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun analyzeImage(context: Context, imageUri: Uri, missionId: Long) {
        viewModelScope.launch {
            try {
                Log.d("MissionAnalyze", "분석 시작 요청: ID $missionId")
                val file = getFileFromUri(context, imageUri)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                val response = repository.analyzeMissionImage(missionId, imagePart)

                if (response.isSuccessful) {
                    val rawScore = response.body()?.data?.score
                    val safeScore = rawScore ?: 0.0
                    _analysisScore.value = safeScore
                    Log.d("MissionAnalyze", "분석 완료: $safeScore")
                } else {
                    Log.e("MissionAnalyze", "분석 실패: ${response.code()}")
                    _analysisScore.value = 0.0
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _analysisScore.value = 0.0
            }
        }
    }

    fun uploadMission(context: Context, imageUri: Uri, content: String, missionId: Long, score: Double) {
        if (_isLoading.value) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val file = getFileFromUri(context, imageUri)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                val jsonRequest = MissionUploadRequest(
                    content = content,
                    missionId = missionId,
                    score = score.toInt()
                )

                val jsonString = Gson().toJson(jsonRequest)
                val dataPart = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

                Log.d("MissionUpload", "Uploading... ID: $missionId, Score: ${score.toInt()}")

                val response = repository.uploadMissionPost(imagePart, dataPart)

                if (response.isSuccessful) {
                    Log.d("MissionUpload", "Success!")
                    val resultPost = response.body()?.data
                    _uploadedPost.value = resultPost

                    _todaySubmission.value = resultPost

                    loadMissionData()
                } else {
                    Log.e("MissionUpload", "Failed: ${response.code()} ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MissionUpload", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMissionPost(misPostId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.deleteMissionPost(misPostId)
                if (response.isSuccessful) {
                    loadMissionData()
                    onSuccess()
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun getShareUrl(context: Context, misPostId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getShareUrl(misPostId)
                if (response.isSuccessful) {
                    val url = response.body()?.data?.shareUrl
                    url?.let { shareLink ->
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareLink)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, null))
                    }
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun loadComments(misPostId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getComments(misPostId)
                if (response.isSuccessful) {
                    val newComments = response.body()?.data?.toList() ?: emptyList()
                    _comments.value = newComments
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun createComment(misPostId: Long, content: String) {
        viewModelScope.launch {
            try {
                val request = CommentRequest(misPostId, content)
                val response = repository.createComment(request)

                if (response.isSuccessful) {
                    delay(100)
                    loadComments(misPostId)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteComment(misPostId: Long, misCommentId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.deleteComment(misCommentId)

                if (response.isSuccessful) {
                    delay(100)
                    loadComments(misPostId)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun editComment(misPostId: Long, misCommentId: Long, content: String) {
        viewModelScope.launch {
            try {
                val request = com.solux.moro.data.dto.request.CommentEditRequest(content)
                val response = repository.editComment(misCommentId, request)

                if (response.isSuccessful) {
                    delay(100)
                    loadComments(misPostId)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
        inputStream?.use { input -> file.outputStream().use { output -> input.copyTo(output) } }
        return file
    }
}