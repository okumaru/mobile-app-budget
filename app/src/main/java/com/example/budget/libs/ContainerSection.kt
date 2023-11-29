package com.example.budget.libs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ContainerSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    border: Boolean?,
    content: @Composable () -> Unit
) {
    Column(
      modifier = modifier  
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .drawBehind {
                    if (border == true) {
                        val borderSize = 1.dp.toPx()
                        val y = size.height - borderSize / 2
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = borderSize
                        )
                    }
                }
                .paddingFromBaseline(
                    top = 30.dp,
                    bottom = 20.dp
                )
                .padding(
                    horizontal = 16.dp
                )
                .fillMaxWidth()
        )
        content()
    }
}