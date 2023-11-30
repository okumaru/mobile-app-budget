package com.example.budget.libs.composables.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.data.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSummary(
    modifier: Modifier = Modifier,
    account: Account,
    onClick: (Account) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(account) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy( 8.dp ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (account.star) {Icons.Filled.Star} else {Icons.Outlined.StarBorder},
                    contentDescription = null
                )
                Column {
                    Text(text = account.name)

                    if (account.description != null) {
                        Text(
                            text = account.description,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Text(text = "Rp. ${account.balance}")
        }
    }
}

@Composable
fun GridAccountSummary(
    accounts: List<Account>?,
    onClick: (Account) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = Modifier, state = listState) {
        if (accounts != null) {
            items(accounts) {
                    account -> AccountSummary(modifier = Modifier, account, onClick)
            }
        }
    }
}