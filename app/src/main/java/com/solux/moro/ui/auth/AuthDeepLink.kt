package com.solux.moro.ui.auth

import android.net.Uri

data class AuthResult(
    val token: String?,
    val email: String?
)

fun parseAuthResult(uri: Uri): AuthResult? {
    val token = extractToken(uri)
    val email = uri.getQueryParameter("email")?.ifBlank { null }
    if (token == null && email == null) {
        return null
    }
    return AuthResult(token = token, email = email)
}

private fun extractToken(uri: Uri): String? {
    val tokenParam = uri.getQueryParameter("token")
        ?: uri.getQueryParameter("accessToken")
    if (tokenParam != null) {
        return tokenParam.normalizeToken()
    }
    val fragment = uri.fragment ?: return null
    val fragmentParams = fragment.split("&")
        .mapNotNull { pair ->
            val parts = pair.split("=")
            if (parts.size == 2) parts[0] to parts[1] else null
        }
        .toMap()
    val fragmentToken = fragmentParams["token"] ?: fragmentParams["accessToken"]
    return fragmentToken?.normalizeToken()
}

private fun String.normalizeToken(): String? {
    return if (equals("null", ignoreCase = true) || isBlank()) {
        null
    } else {
        this
    }
}
