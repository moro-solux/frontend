package com.solux.moro.ui.profileEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.Gray20
import com.solux.moro.core.designsystem.theme.Gray60
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.profile.component.ProfileHeader

@Composable
fun ProfileEditScreen(
    navController: NavController,
    viewModel: ProfileEditViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.bodyRegular20,
) {
    val user by viewModel.user.collectAsState()

    Scaffold(
        bottomBar = { BottomBar() },
        topBar = {
            BackNavigationTopAppBar(
                "프로필 편집",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF121212))
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            ProfileHeader(user?.nickname ?:"@colorhunter" ,
                user?.colorPalette?.userColor?.value.toString(),)

            Column(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding( start=20.dp,end=5.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(
                    text = "닉네임 변경",
                    color = color,
                    style = style,
                )
                var text by remember { mutableStateOf("") }
                TextField(
                    value = viewModel.nicknameInput,
                    onValueChange = viewModel::onNicknameChange,
                    placeholder = {
                        Text(
                            text = "새 닉네임을 입력하세요",
                            color = Gray20,
                            style =style
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Gray20,
                        unfocusedTextColor = Gray20,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Gray20,
                        focusedContainerColor = Gray60,
                        unfocusedContainerColor = Gray60
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end=15.dp)
                )

                Row(Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "대표색상 변경",
                        color = color,
                        style = style,
                    )
                    IconButton(
                        onClick = {},
                        Modifier.size(50.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            tint = Color.White,
                            modifier = Modifier.size(35.dp),
                            contentDescription = "KeyboardArrowRight icon button"
                        )
                    }
                }
                Button(
                    onClick = {
                        viewModel::onSaveNickname
                    },
                    Modifier
                        .height(55.dp)
                        .padding(end = 15.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                    )
                ) {
                    Text(
                        text = "변경 사항 저장",
                        color = Color.Black,
                        style = style
                    )
                }

            }
        }

    }
}
@Preview(device = Devices.PIXEL_4A)
@Composable
fun ProfileEditScreenPreview(){
    //ProfileEditScreen()
}
