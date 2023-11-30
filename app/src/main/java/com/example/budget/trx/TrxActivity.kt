package com.example.budget.trx

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.window.Dialog
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.account.AccountViewModel
import com.example.budget.data.Account
import com.example.budget.data.CategoryWithTypeBudget
import com.example.budget.data.TrxWithAccountBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.HeadActivity
import com.example.budget.libs.composables.account.AccountsDropdownMenu
import com.example.budget.libs.composables.trx.GridTrxSummary
import com.example.budget.libs.composables.trx_cat.CatsDropMenu
import com.example.budget.setting.SettingActivity
import com.example.budget.trx_cat.TrxCatActivity
import com.example.budget.trx_cat.TrxCatViewModel

class TrxActivity: BudgetApp() {
    override val navName = R.string.nav_trx

    private var accountid: Int? by mutableStateOf(null)
    private var categoryid: Int? by mutableStateOf(null)
    private var accountsList: List<Account>? by mutableStateOf(null)
    private var catsList: List<CategoryWithTypeBudget>? by mutableStateOf(null)

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val trxVM = TrxViewModel(super.apiConfig!!)
        val accountVM = AccountViewModel(super.apiConfig!!)
        val catVM = TrxCatViewModel(super.apiConfig!!)
        var trxsList by remember { mutableStateOf<List<TrxWithAccountBudget>?>(value = null) }

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            accountid = intent.getIntExtra("accountid", 0)
            categoryid = intent.getIntExtra("categoryid", 0)
            accountsList = accountVM.getAccounts()
            catsList = catVM.getCategories(null)
            trxsList = trxVM.getTrxs(accountid, categoryid)

        })

        if (trxVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, trxVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        if (accountVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, accountVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        Column(modifier = Modifier
            .fillMaxSize(1F)
            .padding(bottom = 64.dp)){
            HeadActivity(
                title = R.string.trx_head_title,
                addSection = { PopupWindowDialog() }
            )

            GridTrxSummary(
                trxs = trxsList,
                onClick = { detail(it) }
            )
        }
    }

    @Composable
    fun PopupWindowDialog() {

        val openAlertDialog = remember { mutableStateOf(false) }

        IconButton(onClick = { openAlertDialog.value = !openAlertDialog.value }) {
            Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null)
        }

        if (openAlertDialog.value) {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
            )
        }
    }

    @Composable
    fun AlertDialogExample(
        onDismissRequest: () -> Unit,
    ) {
        Dialog(onDismissRequest = { }) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(288.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text( text = "Filter data transaction." )
                        IconButton(onClick = { onDismissRequest() }) {
                            Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                        }
                    }

                    if (accountsList !== null) {
                        AccountsDropdownMenu(
                            label = "Account",
                            accounts = accountsList!!,
                            initItem = true,
                            changeAccountId = { accountid = it },
                        )
                    }

                    if (catsList !== null) {
                        CatsDropMenu(
                            label = "Categories",
                            catsList = catsList,
                            initItem = true,
                            changeCategoryId = { categoryid = it },
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Button(onClick = { filter(0, 0) }) {
                            Text(text = "Reset Filter")
                        }
                        Button(onClick = { filter(accountid, categoryid) }) {
                            Text(text = "Filter")
                        }
                    }
                }
            }
        }
    }

    private fun filter(accountId: Int?, categoryId: Int?) {
        val intent = intent
        intent.putExtra("accountid", accountId)
        intent.putExtra("categoryid", categoryId)
        startActivity(intent)
    }

    private fun detail(trx: TrxWithAccountBudget) {
        val intent = Intent(this, DetailTrxActivity::class.java)
        intent.putExtra("trxid", trx.id)
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
        }
    }
}