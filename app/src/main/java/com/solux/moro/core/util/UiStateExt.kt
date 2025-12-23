package com.solux.moro.core.util

import com.solux.moro.core.state.UiState

inline fun <T> UiState<T>.onSuccess(block: (T) -> Unit) {
    if (this is UiState.Success) {
        block(data)
    }
}
