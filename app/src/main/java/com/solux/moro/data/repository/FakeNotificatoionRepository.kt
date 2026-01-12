package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.data.model.NotificationUiModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeNotificationRepository @Inject constructor() : NotificationRepository {
    val mockGroupedNotifications = mapOf(
        "오늘" to listOf(
            NotificationUiModel.Comment(
                id = 1L,
                userName = "김철수",
                postId = 101,
                content = "와! 이 사진 정말 잘 나왔네요. 어디서 찍으신 건가요?",
                createdAt = "오전 01:54",
                isRead = false
            ),
            NotificationUiModel.Liked(
                id = 2L,
                userName = "이영희",
                postId = 102,
                imageUrl = "https://example.com/thumb1.png",
                createdAt = "오전 01:20",
                isRead = true
            )
        ),
        "어제" to listOf(
            NotificationUiModel.ColorUnlocked(
                id = 3L,
                createdAt = "오후 10:30",
                isRead = false
            ),
            NotificationUiModel.Following(
                id = 4L,
                userName = "박지성",
                createdAt = "오후 06:15",
                isRead = false
            )
        ),
        "이번 주" to listOf(   //   ---->
            NotificationUiModel.Mission(
                id = 5L,
                content = "새로운 주간 미션: 사진 3장 업로드하기",
                createdAt = "1월 7일",
                isRead = true
            )
        )
    )
    // 1. 가짜 데이터를 담고 있는 상태 (MutableStateFlow)
    // 초기값으로 아까 만든 mockGroupedNotifications를 넣습니다.
    private val _notifications = MutableStateFlow(mockGroupedNotifications)

    private val TAG = "FakeRepo" // 로그 필터링용 태그

    init {
        val totalCount = mockGroupedNotifications.values.sumOf { it.size }
        Log.d(TAG, "초기 데이터 로드 완료: 총 ${totalCount}개의 알림이 있습니다.")

        // 데이터 내용을 상세히 보고 싶다면?
        mockGroupedNotifications.forEach { (header, list) ->
            Log.d(TAG, "[$header] 그룹 아이템 수: ${list.size}")
        }
    }

    // 2. 외부에서는 이 Flow를 관찰합니다.
    override fun getNotifications(): Flow<Map<String, List<NotificationUiModel>>> {
        return _notifications
    }

    // 3. 읽음 처리 로직 (핵심!)
    override fun markAsRead(notificationId: Long) {
        _notifications.update { currentMap ->
            currentMap.mapValues { entry ->
                entry.value.map { item ->
                    if (item.id == notificationId) {
                        // sealed class 내부에 만든 markAsRead() 호출
                        item.markAsRead()
                    } else item
                }
            }
        }
    }

    // 4. 클릭 시 동작 (보통 markAsRead 호출 및 기타 로깅)
    override fun onNotificationClick(notificationId: Long) {
        println("FakeRepo: 알림 $notificationId 클릭됨")
        markAsRead(notificationId)
    }
}