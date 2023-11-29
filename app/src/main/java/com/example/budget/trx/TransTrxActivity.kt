package com.example.budget.trx

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.account.AccountViewModel
import com.example.budget.data.Account
import com.example.budget.data.AddTransaction
import com.example.budget.data.CategoryWithTypeBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.composables.account.AccountsDropdownMenu
import com.example.budget.libs.composables.trx.DateTimeTextField
import com.example.budget.libs.composables.trx.HeadingAddTrx
import com.example.budget.libs.composables.trx_cat.CatsDropMenu
import com.example.budget.setting.SettingActivity
import com.example.budget.trx_cat.TrxCatActivity
import com.example.budget.trx_cat.TrxCatViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

class TransTrxActivity: BudgetApp() {
    override val navName = R.string.nav_add_trx_transfer

    private var amount: String by mutableStateOf("0")
    private var description: String by mutableStateOf("")
    private var srcAccountId: Int? by mutableStateOf(null)
    private var destAccountId: Int? by mutableStateOf(null)
    private var categoryid: Int? by mutableStateOf(null)
    private var trxDateTime by mutableStateOf(Date(Timestamp(System.currentTimeMillis()).time))
    private var valTrxDateTime by mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val trxVM = TrxViewModel(super.apiConfig!!)
        val accountVM = AccountViewModel(super.apiConfig!!)
        val catVM = TrxCatViewModel(super.apiConfig!!);

        var accountsList by remember { mutableStateOf<List<Account>?>(value = null) }
        var catsList by remember { mutableStateOf<List<CategoryWithTypeBudget>?>(value = null) }
        val coroutineScope = rememberCoroutineScope()

        @SuppressLint("SimpleDateFormat")
        val sdfTrxDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        LaunchedEffect(Unit, block = {

            valTrxDateTime = sdfTrxDateTime.format(trxDateTime)
            accountsList = accountVM.getAccounts()
            catsList = catVM.getCategories("Transfer");

        })

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeadingAddTrx(
                "Transfer Transaction",
                onClickBack = { change(R.string.nav_add) },
                onSubmit = {
                    coroutineScope.launch {
                        val addTrxSrc = trxVM.addTrx(
                            AddTransaction(
                                credit = 0,
                                debit = amount.toInt(),
                                description = description,
                                datetime = valTrxDateTime,
                                accountid = srcAccountId!!,
                                categoryid = categoryid!!,
                            )
                        )

                        val addTrxDest = trxVM.addTrx(
                            AddTransaction(
                                credit = amount.toInt(),
                                debit = 0,
                                description = description,
                                datetime = valTrxDateTime,
                                accountid = destAccountId!!,
                                categoryid = categoryid!!,
                            )
                        )

                        if (addTrxSrc && addTrxDest) {
                            change(R.string.nav_trx)
                        }
                    }
                },
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text( text = "Amount" ) },
                    value = amount,
                    onValueChange = { amount = it },
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle.Default.copy(fontSize = 72.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                if (accountsList !== null) {
                    AccountsDropdownMenu(
                        label = "Account",
                        accounts = accountsList!!,
                        initItem = true,
                        changeAccountId = { srcAccountId = it },
                    )

                    AccountsDropdownMenu(
                        label = "Account",
                        accounts = accountsList!!,
                        initItem = true,
                        changeAccountId = { destAccountId = it },
                    )
                }

                if (catsList !== null) {
                    CatsDropMenu(
                        label = "Categories of type transfer",
                        catsList = catsList,
                        initItem = false,
                        changeCategoryId = { categoryid = it },
                    )
                }

                DateTimeTextField(
                    defaultDT = trxDateTime,
                    updateDT = {
                        trxDateTime = it
                        valTrxDateTime = sdfTrxDateTime.format(it)
                    }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    label = { Text( text = "Description" ) },
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text( text = "e.g. Home decoration souvenir" ) },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
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
            R.string.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}