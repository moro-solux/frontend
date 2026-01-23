package com.solux.moro.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.model.SearchUser
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUser(
    val id: Long,
    val nickname: String,
)

data class SearchUiState(
    val searchQuery: String = "",
    val filteredSearchRequests: List<SearchUser> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _searchResults = MutableStateFlow<List<SearchUser>>(emptyList())
    val searchResults = _searchResults.asStateFlow()
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }

        searchJob?.cancel()

        if (newQuery.isBlank()) {
            _uiState.update { it.copy(filteredSearchRequests = emptyList(), isLoading = false) }
        } else {
            searchJob = viewModelScope.launch {
                delay(300L)
                performSearch(newQuery)
            }
        }
    }


    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            userRepository.searchUsers(query).onSuccess { response ->
                val domainUsers = response.users

                _uiState.update { it.copy(
                    filteredSearchRequests = domainUsers,
                    isLoading = false
                )}
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

}