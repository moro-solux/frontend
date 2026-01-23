package com.solux.moro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.data.model.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _postId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val postDetail: StateFlow<FeedItem?> = combine(
        feedRepository.refreshTrigger,
        _postId
    ) { _, id -> id }
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else feedRepository.getPosts(id)
        }
        .map { it.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun loadPost(id: Long) {
        _postId.value = id
    }
}