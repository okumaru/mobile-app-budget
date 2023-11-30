package com.example.budget.libs.composables.cat_type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budget.data.CategoryTypeWithBudget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatTypeSummary(
    modifier: Modifier = Modifier,
    type: CategoryTypeWithBudget,
    onClick: (CategoryTypeWithBudget) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(type) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(24.dp)
                ) {
                    Icon(
                        imageVector = when (type.type) {
                            "Income" -> { Icons.Outlined.SaveAlt }
                            "Saving" -> { Icons.Outlined.EnergySavingsLeaf }
                            "Need" -> { Icons.Outlined.Balance }
                            else -> { Icons.Outlined.Celebration }
                        },
                        contentDescription = null
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(text = type.type)

                    type.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            modifier = Modifier.width(144.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.width(128.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text =
                    if (type.type == "Income" || type.type == "Saving")
                        { "Target" }
                    else
                        { "Available" },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = "Rp. ${type.available}",
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}

@Composable
fun GridCatTypesSummary(
    types: List<CategoryTypeWithBudget>?,
    onClick: (CategoryTypeWithBudget) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = Modifier, state = listState) {
        if (types != null) {
            items(types) {
                    type -> CatTypeSummary(modifier = Modifier, type, onClick)
            }
        }
    }
}