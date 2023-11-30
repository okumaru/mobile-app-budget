package com.example.budget.libs.composables.trx_cat

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
import com.example.budget.data.CategoryWithTypeBudget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySummary(
    modifier: Modifier = Modifier,
    category: CategoryWithTypeBudget,
    onClick: (CategoryWithTypeBudget) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onSecondary,
        onClick = { onClick(category) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding( 16.dp ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(24.dp)
                ) {
                    Icon(
                        imageVector = when (category.type.type) {
                            "Income" -> { Icons.Outlined.SaveAlt }
                            "Saving" -> { Icons.Outlined.EnergySavingsLeaf }
                            "Need" -> { Icons.Outlined.Balance }
                            else -> { Icons.Outlined.Celebration }
                        },
                        contentDescription = null
                    )
                }

                Column {
                    Text(text = category.name)
                    Text(
                        text = category.type.type,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.surfaceTint,
                    )
                }
            }

            if (category.budget !== null) {
                Column(
                    modifier = Modifier.width(128.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text =
                        if (category.type.type == "Income" || category.type.type == "Saving")
                        { "Target Budget" }
                        else
                        { "Available Budget" },
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = "Rp. ${category.budget.available}",
                        textAlign = TextAlign.Center,
                    )
                }
            }

        }
    }
}

@Composable
fun GridCategories(
    categories: List<CategoryWithTypeBudget>?,
    onClick: (CategoryWithTypeBudget) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier,
        state = listState,
    ) {
        if (categories != null) {
            items(categories) {
                    category -> CategorySummary(modifier = Modifier, category, onClick)
            }
        }
    }
}