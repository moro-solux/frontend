package com.solux.moro.data.repository.menurepo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("moro_settings", Context.MODE_PRIVATE)

    // 공개 설정 저장/불러오기
    fun setVisibility(isVisible: Boolean) {
        prefs.edit().putBoolean("is_visible", isVisible).apply()
    }

    fun getVisibility(): Boolean {
        return prefs.getBoolean("is_visible", false) // 기본값 false
    }

    // 알림 설정 저장/불러오기
    fun setNotification(isEnabled: Boolean) {
        prefs.edit().putBoolean("is_noti_enabled", isEnabled).apply()
    }

    fun getNotification(): Boolean {
        return prefs.getBoolean("is_noti_enabled", false) // 기본값 false
    }

    fun clearAuthData() {
        prefs.edit().remove("access_token").apply()
        prefs.edit().remove("is_visible").remove("is_noti_enabled").apply()
    }
}