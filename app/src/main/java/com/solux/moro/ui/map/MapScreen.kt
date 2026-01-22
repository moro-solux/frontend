package com.solux.moro.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.dto.response.MapPostDetailDto
import com.solux.moro.data.dto.response.MapPostDto
import com.solux.moro.ui.map.component.CircleActionButton
import com.solux.moro.ui.map.component.MapPostBottomSheet
import com.solux.moro.ui.map.component.MapSearchBar
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreenRoute(
    viewModel: MapViewModel,
    navController: NavHostController? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        MapScreen(
            modifier = Modifier.padding(innerPadding),
            posts = uiState.posts,
            selectedPost = uiState.selectedPost,
            keyword = uiState.keyword,
            searchCenter = uiState.searchCenter,
            onKeywordChange = viewModel::onKeywordChange,
            onSearch = viewModel::search,
            onSelectPost = viewModel::selectPost,
            onClearSelection = viewModel::clearSelection,
            onLoadNearby = viewModel::loadNearby,

            hasFineLocationPermission = uiState.hasFineLocationPermission,
            lastKnownLatLng = uiState.lastKnownLatLng,
            onLocationPermissionChanged = viewModel::onLocationPermissionChanged,
            onUpdateLastKnownLocation = viewModel::updateLastKnownLocation,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    posts: List<MapPostDto>,
    selectedPost: MapPostDetailDto?,
    keyword: String,
    searchCenter: LatLng?,
    onKeywordChange: (String) -> Unit,
    onSearch: () -> Unit,
    onSelectPost: (Long) -> Unit,
    onClearSelection: () -> Unit,
    onLoadNearby: (Double, Double) -> Unit,

    hasFineLocationPermission: Boolean,
    lastKnownLatLng: LatLng?,
    onLocationPermissionChanged: (Boolean) -> Unit,
    onUpdateLastKnownLocation: (LatLng?) -> Unit,

    enableAutoLoad: Boolean = true,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    var searchBarBottomPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    val rightControlsTop: Dp = with(density) {
        if (searchBarBottomPx <= 0f) 40.dp else searchBarBottomPx.toDp() + 40.dp
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(37.5665, 126.9780),
            13f
        )
    }

    val fineLocationPermissionState = if (!isPreview) {
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    } else null

    val hasFineLocationPermissionNow = !isPreview &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

    LaunchedEffect(hasFineLocationPermissionNow) {
        if (!isPreview) onLocationPermissionChanged(hasFineLocationPermissionNow)
    }

    val effectiveHasFineLocationPermission =
        hasFineLocationPermission || hasFineLocationPermissionNow

    val fusedLocationClient = if (!isPreview) {
        remember { LocationServices.getFusedLocationProviderClient(context) }
    } else null

    LaunchedEffect(Unit) {
        if (isPreview) return@LaunchedEffect
        if (!effectiveHasFineLocationPermission) {
            fineLocationPermissionState?.launchPermissionRequest()
        }
    }

    fun requestAndMoveToMyLocation() {
        if (isPreview) return
        if (!effectiveHasFineLocationPermission) return

        @SuppressLint("MissingPermission")
        val cts = CancellationTokenSource()

        try {
            fusedLocationClient
                ?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                ?.addOnSuccessListener { loc: Location? ->
                    if (loc != null) {
                        val latLng = LatLng(loc.latitude, loc.longitude)
                        onUpdateLastKnownLocation(latLng)
                        scope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                                durationMs = 600
                            )
                        }
                    } else {
                        try {
                            fusedLocationClient?.lastLocation?.addOnSuccessListener { last: Location? ->
                                if (last != null) {
                                    val latLng = LatLng(last.latitude, last.longitude)
                                    onUpdateLastKnownLocation(latLng)
                                    scope.launch {
                                        cameraPositionState.animate(
                                            CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                                            durationMs = 600
                                        )
                                    }
                                }
                            }
                        } catch (_: SecurityException) {}
                    }
                }
        } catch (_: SecurityException) {}
    }

    LaunchedEffect(effectiveHasFineLocationPermission) {
        if (isPreview) return@LaunchedEffect
        if (effectiveHasFineLocationPermission) {
            requestAndMoveToMyLocation()
        }
    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = effectiveHasFineLocationPermission
    )
    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false,
    )

    LaunchedEffect(searchCenter) {
        if (searchCenter != null) {
            scope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(searchCenter, 15f),
                    durationMs = 600
                )
            }
        }
    }

    LaunchedEffect(selectedPost?.postId) {
        if (selectedPost != null) scope.launch { sheetState.show() }
    }

    Box(modifier.fillMaxSize()) {
        MapLayer(
            isPreview = isPreview,
            enableAutoLoad = enableAutoLoad,
            cameraPositionState = cameraPositionState,
            mapProperties = mapProperties,
            mapUiSettings = mapUiSettings,
            posts = posts,
            onLoadNearby = onLoadNearby,
            onSelectPost = onSelectPost,
        )

        TopOverlay(
            keyword = keyword,
            onKeywordChange = onKeywordChange,
            onSearch = onSearch,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            onSearchBarBottomPxChange = { bottomPx -> searchBarBottomPx = bottomPx }
        )

        RightControls(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp)
                .padding(top = rightControlsTop),
            onMyLocationClick = {
                if (!effectiveHasFineLocationPermission) {
                    if (!isPreview) fineLocationPermissionState?.launchPermissionRequest()
                    return@RightControls
                }

                if (lastKnownLatLng != null) {
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(lastKnownLatLng, 15f),
                            durationMs = 600
                        )
                    }
                } else {
                    requestAndMoveToMyLocation()
                }
            },
            onZoomInClick = {
                scope.launch { cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 300) }
            },
            onZoomOutClick = {
                scope.launch { cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 300) }
            },
        )
    }

    if (selectedPost != null) {
        ModalBottomSheet(
            onDismissRequest = { onClearSelection() },
            sheetState = sheetState,
            containerColor = Color(0xFF121212),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 44.dp, height = 4.dp)
                            .background(Color(0xFF5A5A5A), RoundedCornerShape(999.dp))
                    )
                }
            }
        ) {
            MapPostBottomSheet(
                detail = selectedPost,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 18.dp)
            )
        }
    }
}

@Composable
private fun MapLayer(
    isPreview: Boolean,
    enableAutoLoad: Boolean,
    cameraPositionState: com.google.maps.android.compose.CameraPositionState,
    mapProperties: MapProperties,
    mapUiSettings: MapUiSettings,
    posts: List<MapPostDto>,
    onLoadNearby: (Double, Double) -> Unit,
    onSelectPost: (Long) -> Unit,
) {
    val context = LocalContext.current
    var markerIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

    if (isPreview) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDEDED)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Map Preview Placeholder", color = Color.DarkGray)
        }
        return
    }

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
        MapEffect(Unit) {
            markerIcon = createMarkerIcon(context = context, resId = R.drawable.ic_map_pin)
        }

        posts.forEach { post ->
            Marker(
                state = MarkerState(LatLng(post.lat, post.lng)),
                icon = markerIcon,
                onClick = {
                    onSelectPost(post.postId)
                    true
                }
            )
        }
    }
}

private fun createMarkerIcon(context: android.content.Context, resId: Int): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, resId) ?: return null
    val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 48
    val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 48
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
private fun TopOverlay(
    keyword: String,
    onKeywordChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    onSearchBarBottomPxChange: (Float) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(showBell = false)
        Spacer(modifier = Modifier.height(13.dp))
        MapSearchBar(
            keyword = keyword,
            onKeywordChange = onKeywordChange,
            onSearch = onSearch,
            modifier = Modifier.padding(horizontal = 16.dp),
            onBottomPxChange = onSearchBarBottomPxChange,
        )
    }
}

@Composable
private fun RightControls(
    modifier: Modifier = Modifier,
    onMyLocationClick: () -> Unit,
    onZoomInClick: () -> Unit,
    onZoomOutClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CircleActionButton(iconRes = R.drawable.ic_location, onClick = onMyLocationClick)
        CircleActionButton(iconRes = R.drawable.ic_zoom_in, onClick = onZoomInClick)
        CircleActionButton(iconRes = R.drawable.ic_zoom_out, onClick = onZoomOutClick)
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
                title = "Sookmyung women's univ",
                thumbnailUrl = null
            ),
            MapPostDto(
                postId = 2L,
                lat = 37.5888,
                lng = 126.9955,
                title = "Nearby post",
                thumbnailUrl = null
            ),
            MapPostDto(
                postId = 3L,
                lat = 37.5879,
                lng = 126.9982,
                title = "Mock cafe",
                thumbnailUrl = null
            )
        )

        val dummyDetail = MapPostDetailDto(
            postId = 1L,
            createdAt = "25.11.03.mon",
            placeName = "Sookmyung women's univ",
            addressKo = "123 Main Street, City",
            addressEn = "123 Main Street, City",
            hexCode1 = "#FF6025",
            hexCode2 = "#FF6025",
            hexCode3 = "#FF6025",
            hexCode4 = "#FF6025",
            imageUrl = null
        )

        MapScreen(
            posts = dummyPosts,
            selectedPost = dummyDetail,
            keyword = "sookmyung",
            searchCenter = null,
            onKeywordChange = {},
            onSearch = {},
            onSelectPost = {},
            onClearSelection = {},
            onLoadNearby = { _, _ -> },

            hasFineLocationPermission = false,
            lastKnownLatLng = null,
            onLocationPermissionChanged = {},
            onUpdateLastKnownLocation = {},

            enableAutoLoad = false
        )
    }
}
