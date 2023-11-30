package com.example.budget.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.account.AddAccountActivity
import com.example.budget.account.DetailAccountActivity
import com.example.budget.data.Account
import com.example.budget.data.CategoryTypeWithBudget
import com.example.budget.libs.composables.account.GridAccountSummary
import com.example.budget.libs.composables.cat_type.GridCatTypesSummary
import com.example.budget.libs.ContainerSection
import com.example.budget.setting.ConfigActivity
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity

class HomeActivity: BudgetApp()  {
    override val navName = R.string.nav_home

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val homeVM = HomeViewModel(super.apiConfig!!)
        var accountsList by remember { mutableStateOf<List<Account>?>(value = null) }
        var categoriesList by remember { mutableStateOf<List<CategoryTypeWithBudget>?>(value = null) }

        LaunchedEffect(Unit, block = {

            accountsList = homeVM.getAccounts()
            categoriesList = homeVM.getCategories()

        })

        if (homeVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, homeVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        Column(modifier = Modifier.fillMaxSize(1F)){
            Column(
                Modifier
                    .fillMaxHeight(0.35f)
                    .padding(all = 8.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    )
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Text(
                        text = stringResource(R.string.home_bal_account),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .paddingFromBaseline(
                                top = 30.dp,
                                bottom = 20.dp
                            )
                            .padding(
                                horizontal = 16.dp
                            )
                    )

                    IconButton(onClick = { change(R.string.nav_add_account) }) {
                        Icon(Icons.Outlined.AddCircleOutline, contentDescription = null)
                    }
                }

                GridAccountSummary(accountsList, onClick = { detail(it) })
            }

            Column(modifier = Modifier.fillMaxHeight(1F)) {
                ContainerSection(
                    title = R.string.home_ava_categories,
                    border = false
                ) {
                    GridCatTypesSummary( categoriesList, onClick = {})
                }
            }
        }
    }

    private fun detail(account: Account) {
        val intent = Intent(this, DetailAccountActivity::class.java)
        intent.putExtra("accountid", account.id)
        startActivity(intent)
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
            R.string.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }

            R.string.nav_add_account -> {
                val intent = Intent(this, AddAccountActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_setting_config -> {
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
            }
        }
    }
}