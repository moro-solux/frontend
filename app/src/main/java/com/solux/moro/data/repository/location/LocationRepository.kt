package com.solux.moro.data.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) {
    // 현재 GPS 좌표
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            locationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }

    // 좌표 /주소 변환
    fun getAddressFromLocation(lat: Double, lng: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.KOREA)
            val addresses = geocoder.getFromLocation(lat, lng, 1)

            if (!addresses.isNullOrEmpty()) {
                addresses[0].getAddressLine(0).replace("대한민국 ", "")
            } else {
                "주소를 찾을 수 없음"
            }
        } catch (e: Exception) {
            "위치 변환 실패"
        }
    }
}