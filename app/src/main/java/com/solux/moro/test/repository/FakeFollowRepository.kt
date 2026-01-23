//package com.solux.moro.test.repository
//
//import com.solux.moro.core.designsystem.theme.MoroPalette
//import com.solux.moro.core.domain.FollowRepository
//import com.solux.moro.data.model.User
//import com.solux.moro.data.model.UserColorPalette
//import com.solux.moro.data.model.UserStats
//import com.solux.moro.ui.followlist.FollowUserInfo
//import jakarta.inject.Inject
//
//class FakeFollowRepository @Inject constructor() : FollowRepository {
//    private val mockUsers = mutableListOf(
//        FollowUserInfo(
//            user = User(
//                id = 1,
//                nickname = "테스트유저1",
//                userColorHex = "#11111",
//                colorPalette = UserColorPalette(
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//        ),
//        FollowUserInfo(
//            user = User(
//                id = 2,
//                nickname = "테스트유저2",
//                userColorHex = "#222222",
//                colorPalette = UserColorPalette(
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//        ),
//        FollowUserInfo(
//            user = User(
//                id = 3,
//                nickname = "테스트유저3",
//                userColorHex = "#33333",
//                colorPalette = UserColorPalette(
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//        )
//    )
//    private val mockRequests = mutableListOf(
//        FollowUserInfo(
//            user = User(
//                id = 4,
//                nickname = "테스트유저4",
//                userColorHex = "#44444",
//                colorPalette = UserColorPalette(
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//        )
//    )
//    override suspend fun getFollowers(): List<FollowUserInfo>{
//        return mockUsers.take(2)
//    }
//    override suspend fun getFollowings(): List<FollowUserInfo>{
//        return mockUsers.filter { it.stats.isFollowing }
//    }
//
//    override suspend fun getFollowRequest(): List<FollowUserInfo>{
//        return mockRequests
//    }
//    override suspend fun acceptFollowRequest(userId: Long){
//        mockRequests.removeAll { it.user.id == userId }
//    }
//    override suspend fun rejectFollowRequest(userId: Long){
//        mockRequests.removeAll { it.user.id == userId }
//    }
//
//
//    override suspend fun unFollow(userId: Long){
//        mockUsers.removeAll { it.user.id == userId }
//    }
//
//    override suspend fun deleteFollower(userId: Long){
//        mockUsers.removeAll { it.user.id == userId }
//    }
//
//
//}