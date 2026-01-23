package com.solux.moro.test.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserTestViewModel @Inject constructor(
    private val userRepository: UserRepository // 주입 확인
) : ViewModel() {

    init {
        testSearch()
    }

    private fun testSearch() {
        viewModelScope.launch {
            val result = userRepository.searchUsers("test_query", 0)
            result.onSuccess { users ->
                Log.d("TEST_LOG", "검색 성공: ")
            }.onFailure {
                Log.e("TEST_LOG", "에러 발생: ${it.message}")
            }
        }
    }


}