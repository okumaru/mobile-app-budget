package com.example.budget.libs.composables.trx

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeadingAddTrx(
    addType: String,
    onClickBack: () -> Unit,
    onSubmit: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onClickBack() },
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Localized description"
                )
            }

            Text(
                text = "Add $addType",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        IconButton(
            onClick = { onSubmit() },
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Localized description"
            )
        }
    }
}