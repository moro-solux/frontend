package com.solux.moro.ui.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme

@Composable
fun MapSearchBar(
    keyword: String,
    onKeywordChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    onBottomPxChange: (Float) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White, RoundedCornerShape(25.dp))
            .clickable {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
            .padding(horizontal = 16.dp)
            .onGloballyPositioned { coordinates ->
                val bottom = coordinates.boundsInRoot().bottom
                onBottomPxChange(bottom)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color(0xFFA3A3A3),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        BasicTextField(
            value = keyword,
            onValueChange = onKeywordChange,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            textStyle = TextStyle(
                color = MoroTheme.colors.background,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (keyword.isEmpty()) {
                    Text(
                        text = "Search locations...",
                        color = Color(0xFFADAEBC),
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Filter",
            tint = Color(0xFFA3A3A3),
            modifier = Modifier
                .size(16.dp)
                .clickable { onSearch() }
        )
    }
}