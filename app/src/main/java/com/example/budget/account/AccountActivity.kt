package com.example.budget.account

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.data.Account
import com.example.budget.home.HomeActivity
import com.example.budget.libs.HeadActivity
import com.example.budget.libs.composables.account.GridAccountSummary
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity

class AccountActivity: BudgetApp() {
    override val navName = R.string.nav_account

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val accountVM = AccountViewModel(super.apiConfig!!)
        var accountsList by remember { mutableStateOf<List<Account>?>(value = null) }

        LaunchedEffect(Unit, block = {

            accountsList = accountVM.getAccounts()

        })

        if (accountVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, accountVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        HeadActivity(
            title = R.string.account_head_title,
            addSection = {
                IconButton(
                    onClick = { change(R.string.nav_add_account) },
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        Icons.Default.AddCircleOutline,
                        contentDescription = null
                    )
                }
            }
        )

        GridAccountSummary(
            accounts = accountsList,
            onClick = { detail(it) }
        )
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
        }
    }
}