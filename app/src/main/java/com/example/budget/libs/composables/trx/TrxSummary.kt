package com.example.budget.libs.composables.trx

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budget.data.TrxWithAccountBudget
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrxSummary(
    trx: TrxWithAccountBudget,
    modifier: Modifier = Modifier,
    onClick: (TrxWithAccountBudget) -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onSecondary,
        onClick = { onClick(trx) }
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(text = trx.account.name)
                        val sdf = SimpleDateFormat("MMMM d")
                        val currentDate = sdf.format(trx.datetime)
                        Text(
                            text = currentDate,
                            style = MaterialTheme.typography.labelMedium,
                        )
                        if (trx.description != null) {
                            Text(
                                text = trx.description,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.surfaceTint,
                            )
                        }
                    }
                }

                Surface(
                    color = if (trx.credit - trx.debit < 0) {
                        MaterialTheme.colorScheme.onErrorContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceTint
                    },
                    modifier = Modifier
                        .width(128.dp)
                        .fillMaxHeight(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Rp. ${trx.credit - trx.debit}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = if (trx.credit - trx.debit < 0) {
                            MaterialTheme.colorScheme.onError
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Balance Before",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(text = "Rp. ${trx.balanceBefore}")
                }

                Column {
                    Text(
                        text = "Balance After",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(text = "Rp. ${trx.balanceAfter}")
                }
            }
        }
    }
}

@Composable
fun GridTrxSummary(
    trxs: List<TrxWithAccountBudget>?,
    onClick: (TrxWithAccountBudget) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier,
        state = listState,
    ) {
        if (trxs != null) {
            items(trxs) {
                    trx -> TrxSummary(trx, modifier = Modifier, onClick)
            }
        }
    }
}