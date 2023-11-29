package com.example.budget.libs.composables.budget

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budget.data.Account
import com.example.budget.data.CategoryBudget
import com.example.budget.libs.composables.account.AccountSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSummary(
    modifier: Modifier = Modifier,
    budget: CategoryBudget,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.onSecondary,
        onClick = {  }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = budget.periode)
            Row(
                horizontalArrangement = Arrangement.spacedBy( 8.dp )
            ) {
                Column(
                    modifier = Modifier.width(96.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Allocated",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(text = "Rp. ${budget.allocated}")
                }
                Column(
                    modifier = Modifier.width(96.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Spent",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(text = "Rp. ${budget.spent}")
                }

            }

        }
    }
}

@Composable
fun GridBudgetSummary(
    budgets: List<CategoryBudget>?,
//    onClick: (CategoryBudget) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = Modifier, state = listState, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (budgets != null) {
            items(budgets) {
                    budget -> BudgetSummary(modifier = Modifier, budget)
            }
        }
    }
}