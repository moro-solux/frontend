package com.solux.moro.ui.map
import android.location.Location
import com.google.android.gms.location.LocationServices

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.dto.response.MapPostDetailDto
import com.solux.moro.data.dto.response.MapPostDto
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreenRoute(
    viewModel: MapViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    MapScreen(
        posts = uiState.posts,
        selectedPost = uiState.selectedPost,
        keyword = uiState.keyword,
        onKeywordChange = viewModel::onKeywordChange,
        onSearch = viewModel::search,
        onSelectPost = { viewModel.selectPost(it) },
        onClearSelection = viewModel::clearSelection,
        onLoadNearby = { lat, lng -> viewModel.loadNearby(lat, lng) },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    posts: List<MapPostDto>,
    selectedPost: MapPostDetailDto?,
    keyword: String,
    onKeywordChange: (String) -> Unit,
    onSearch: () -> Unit,
    onSelectPost: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onLoadNearby: (Double, Double) -> Unit,
    enableAutoLoad: Boolean = true,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var lastKnownLatLng by remember { mutableStateOf<LatLng?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // 권한/위치 획득 시 사용자 현재 위치로 이동
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(37.5665, 126.9780),
            13f
        )
    }

    val fineLocationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(Unit) {
        // 첫 진입 시 권한 요청
        if (!fineLocationPermissionState.status.isGranted) {
            fineLocationPermissionState.launchPermissionRequest()
        }
    }

    // 현재 위치를 가져와 카메라 이동
    fun requestAndMoveToMyLocation() {
        if (!fineLocationPermissionState.status.isGranted) return

        @SuppressLint("MissingPermission")
        val cts = CancellationTokenSource()

        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
            .addOnSuccessListener { loc: Location? ->
                if (loc != null) {
                    val latLng = LatLng(loc.latitude, loc.longitude)
                    lastKnownLatLng = latLng
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                            durationMs = 600
                        )
                    }
                } else {
                    // fallback: 마지막 캐시 위치
                    fusedLocationClient.lastLocation.addOnSuccessListener { last: Location? ->
                        if (last != null) {
                            val latLng = LatLng(last.latitude, last.longitude)
                            lastKnownLatLng = latLng
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                                    durationMs = 600
                                )
                            }
                        }
                    }
                }
            }
    }

    // 권한이 허용되면 현재 위치로 카메라 이동
    LaunchedEffect(fineLocationPermissionState.status.isGranted) {
        if (fineLocationPermissionState.status.isGranted) {
            requestAndMoveToMyLocation()
        }
    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = fineLocationPermissionState.status.isGranted
    )
    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false, // 기본 줌인/줌아웃 버튼 숨김
    )

    val isPreview = LocalInspectionMode.current

    // 상세가 생기면 바텀시트 열기
    LaunchedEffect(selectedPost?.postId) {
        if (selectedPost != null) {
            scope.launch { sheetState.show() }
        }
    }

    Box(Modifier.fillMaxSize()) {

        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEDEDED)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Map Preview Placeholder", color = Color.DarkGray)
            }
        } else {
            if (enableAutoLoad) {
                LaunchedEffect(cameraPositionState) {
                    snapshotFlow { cameraPositionState.isMoving }
                        .distinctUntilChanged()
                        .collect { moving ->
                            if (!moving) {
                                val c = cameraPositionState.position.target
                                onLoadNearby(c.latitude, c.longitude)
                            }
                        }
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = mapUiSettings,
            ) {
                posts.forEach { post ->
                    Marker(
                        state = MarkerState(LatLng(post.lat, post.lng)),
                        onClick = {
                            onSelectPost(post.postId)
                            true
                        }
                    )
                }
            }
        }

        // 상단 검색바
        MapSearchBar(
            keyword = keyword,
            onKeywordChange = onKeywordChange,
            onSearch = onSearch,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        )

        // 우측 버튼들(예: 내 위치/줌)
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircleActionButton(text = "◎") {
                // 내 위치로 이동
                if (!fineLocationPermissionState.status.isGranted) {
                    fineLocationPermissionState.launchPermissionRequest()
                    return@CircleActionButton
                }

                // 캐시가 있으면 바로 이동, 없으면 현재 위치를 다시 요청
                if (lastKnownLatLng != null) {
                    val latLng = lastKnownLatLng!!
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                            durationMs = 600
                        )
                    }
                } else {
                    requestAndMoveToMyLocation()
                }
            }
            CircleActionButton(text = "+") {
                // 줌 인
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.zoomIn(),
                        durationMs = 300
                    )
                }
            }
            CircleActionButton(text = "–") {
                // 줌 아웃
                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.zoomOut(),
                        durationMs = 300
                    )
                }
            }
        }
    }

    // 바텀시트 (선택된 포스트가 있을 때)
    if (selectedPost != null) {
        ModalBottomSheet(
            onDismissRequest = { onClearSelection() },
            sheetState = sheetState,
            containerColor = Color(0xFF0B0B0B),
        ) {
            MapPostBottomSheetContent(
                detail = selectedPost,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun MapSearchBar(
    keyword: String,
    onKeywordChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "🔍", modifier = Modifier.padding(end = 8.dp))
        BasicTextField(
            value = keyword,
            onValueChange = onKeywordChange,
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "검색",
            modifier = Modifier
                .clickable { onSearch() }
                .padding(6.dp)
        )
    }
}

@Composable
private fun CircleActionButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.White, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.Black)
    }
}

@Composable
private fun MapPostBottomSheetContent(
    detail: MapPostDetailDto,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = detail.title, color = Color.White)
        Spacer(Modifier.height(6.dp))
        Text(text = detail.address, color = Color.White.copy(alpha = 0.7f))
        Spacer(Modifier.height(10.dp))
        Text(text = detail.date, color = Color.White.copy(alpha = 0.7f))
        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            detail.colors.forEach { hex ->
                Box(
                    Modifier
                        .size(18.dp)
                        .background(Color(hex.toColorInt()), CircleShape)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
    }
}

@Preview(showBackground = true, name = "MapScreen")
@Composable
private fun MapScreenPreview() {
    MoroTheme {
        val dummyPosts = listOf(
            MapPostDto(
                postId = 1L,
                lat = 37.5909,
                lng = 126.9970,
                title = "Sookmyung women’s univ",
                thumbnailUrl = null
            ),
            MapPostDto(
                postId = 2L,
                lat = 37.5888,
                lng = 126.9955,
                title = "Nearby post",
                thumbnailUrl = null
            )
        )

        val dummyDetail = MapPostDetailDto(
            postId = 1L,
            title = "Sookmyung women’s univ",
            address = "123 Main Street, City",
            date = "25.11.03.mon",
            colors = listOf("#FF6025", "#FF6025", "#FF6025", "#FF6025"),
            imageUrl = null
        )

        MapScreen(
            posts = dummyPosts,
            selectedPost = dummyDetail,
            keyword = "sookmyung",
            onKeywordChange = {},
            onSearch = {},
            onSelectPost = {},
            onClearSelection = {},
            onLoadNearby = { _, _ -> },
            enableAutoLoad = false
        )
    }
}