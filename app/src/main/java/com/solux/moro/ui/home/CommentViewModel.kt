package com.solux.moro.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _postId = MutableStateFlow<String?>(null)

    val comments = _postId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList())
        else commentRepository.observeComments(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setPostId(id: String) {
        _postId.value = id
        viewModelScope.launch {
            commentRepository.loadComments(id)
        }
    }

    fun onAddComment(content: String) {
        val currentId = _postId.value ?: return
        viewModelScope.launch {
            commentRepository.addComment(currentId, content)
        }
    }

    fun onDeleteComment(commentId: String) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId)
        }
    }

}