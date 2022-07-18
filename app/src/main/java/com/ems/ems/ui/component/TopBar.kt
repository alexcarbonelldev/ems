package com.ems.ems.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    text: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {}
) {
    TopAppBar(modifier = modifier) {
        if (showBackButton) {
            IconButton(onClick = onBackButtonClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, null)
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            fontSize = 24.sp
        )
    }
}
