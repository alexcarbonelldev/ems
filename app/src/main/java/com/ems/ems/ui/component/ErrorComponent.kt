package com.ems.ems.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ems.ems.R

@Preview
@Composable
fun ErrorComponent(modifier: Modifier = Modifier, onRetryButtonClick: () -> Unit = {}) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 24.sp,
                text = stringResource(id = R.string.error_occurred),
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier.padding(24.dp),
                onClick = onRetryButtonClick
            ) {
                Text(
                    text = stringResource(id = R.string.retry),
                    fontSize = 18.sp
                )
            }
        }
    }
}
