package com.example.budget.budget

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.data.CategoryBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.composables.budget.GridBudgetSummary
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.DetailTrxCatActivity
import com.example.budget.trx_cat.TrxCatActivity

class HistoriesBudgetActivity: BudgetApp() {
    override val navName = R.string.nav_history_budget
    private var catId: Int by mutableStateOf(0)

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val budgetVM = BudgetViewModel(super.apiConfig!!)
        var budgetsList by remember { mutableStateOf<List<CategoryBudget>?>(value = null) }

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            catId = intent.getIntExtra("catid", 0)
            budgetsList = budgetVM.getBudget(catId)

        })

        if (budgetVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, budgetVM.errorMessage, Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier
                .fillMaxSize(1F)
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        onClick = { change(R.string.nav_detail_cat) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Histories budget category",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            GridBudgetSummary(budgets = budgetsList)
        }
    }

    override fun change(activity: Int) {
        when(activity) {
            R.string.nav_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_trx -> {
                val intent = Intent(this, TrxActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_add -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_trx_cat -> {
                val intent = Intent(this, TrxCatActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_detail_cat -> {
                val intent = Intent(this, DetailTrxCatActivity::class.java)
                intent.putExtra("catid", catId)
                startActivity(intent)
            }
            R.string.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}