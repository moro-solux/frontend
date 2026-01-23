package com.solux.moro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.data.model.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val feed: StateFlow<List<FeedItem>> =feedRepository.refreshTrigger
        .flatMapLatest {
            feedRepository.getHomeFeed()
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun onLikeClick(feedId: Long) {
        viewModelScope.launch {
            feedRepository.likeFeed(feedId)
            feedRepository.triggerRefresh()
        }
    }
}
