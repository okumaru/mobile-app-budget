package com.example.budget.trx

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.data.Account
import com.example.budget.data.Category
import com.example.budget.data.UpdateTransaction
import com.example.budget.home.HomeActivity
import com.example.budget.libs.composables.trx.DateTimeTextField
import com.example.budget.setting.SettingActivity
import com.example.budget.trx_cat.TrxCatActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailTrxActivity: BudgetApp() {
    override val navName = R.string.nav_detail_trx
    private var trxId: Int by mutableStateOf(0)

    private var credit: Int by mutableStateOf(0)
    private var debit: Int by mutableStateOf(0)
    private var amount: String by mutableStateOf("0")
    private var description: String by mutableStateOf("")
    private var trxDateTime: Date? by mutableStateOf(null)
    private var valTrxDateTime by mutableStateOf("")
    private var account: Account? by mutableStateOf(null)
    private var category: Category? by mutableStateOf(null)

    @SuppressLint("SimpleDateFormat")
    private val sdfTrxDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val trxVM = TrxViewModel(super.apiConfig!!)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            trxId = intent.getIntExtra("trxid", 0)

            val detail = trxVM.detailTrx(trxId)
            if (detail != null) {
                credit = detail.credit
                debit = detail.debit
                amount = if (credit > debit) {credit.toString()} else {debit.toString()}
                description = detail.description.toString()
                account = detail.account
                category = detail.category
                trxDateTime = Date(detail.datetime.time)
                valTrxDateTime = sdfTrxDateTime.format(trxDateTime)
            }

        })

        if (trxVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, trxVM.errorMessage, Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
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
                        onClick = { change(R.string.nav_trx) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }

                    Text(
                        text = "Detail of Transaction",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val resSubmit = trxVM.deleteTrx(trxId)
                                if (resSubmit) {
                                    change(R.string.nav_trx)
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Outlined.DeleteOutline,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val resSubmit = trxVM.updateTrx(
                                    trxId,
                                    UpdateTransaction(
                                        credit = if (credit > debit) {amount.toInt()} else {credit},
                                        debit = if (debit > credit) { amount.toInt() } else {debit},
                                        description = description,
                                        datetime = valTrxDateTime,
                                    )
                                )

                                if (resSubmit) {
                                    refresh()
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }

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

                if (account !== null) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text( "Transaction from Account" ) },
                        readOnly = true,
                        value = account!!.name,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )
                }

                if (category !== null) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text( "Category of transaction" ) },
                        readOnly = true,
                        value = category!!.name,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )
                }

                if (trxDateTime !== null) {
                    DateTimeTextField(
                        defaultDT = trxDateTime!!,
                        updateDT = {
                            trxDateTime = it
                            valTrxDateTime = sdfTrxDateTime.format(it)
                        }
                    )
                }


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

    private fun refresh() {
        val intent = intent
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