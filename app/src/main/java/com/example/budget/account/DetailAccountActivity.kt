package com.example.budget.account

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.data.AddAccount
import com.example.budget.home.HomeActivity
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity
import kotlinx.coroutines.launch

class DetailAccountActivity: BudgetApp() {
    override val navName = R.string.nav_detail_account
    private var accountId: Int by mutableStateOf(0)

    private var name: String by mutableStateOf("")
    private var description: String by mutableStateOf("")
    private var star: Boolean by mutableStateOf(false)
    private var type: String by mutableStateOf("")
    private var balance: String by mutableStateOf("0")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val accountVM = AccountViewModel(super.apiConfig!!)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            accountId = intent.getIntExtra("accountid", 0)

            val detail = accountVM.detailAccount(accountId)
            if (detail != null) {
                name = detail.name
                type = detail.type
                description = detail.description.toString()
                star = detail.star
                type = detail.type
                balance = detail.balance.toString()
            }

        })

        if (accountVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, accountVM.errorMessage,Toast.LENGTH_SHORT).show()
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
                        onClick = { change(R.string.nav_account) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Detail of Account",
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
                                val resSubmit = accountVM.deleteAccount(accountId)
                                if (resSubmit) {
                                    change(R.string.nav_account)
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
                                val resSubmit = accountVM.updateAccount(
                                    accountId,
                                    AddAccount(
                                        name = name,
                                        description = description,
                                        star = star,
                                        type = type,
                                        balance = balance.toInt()
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
                            contentDescription = null
                        )
                    }
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Name of Account" ) },
                value = name,
                onValueChange = { name = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    label = { Text( text = "Description of Account" ) },
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text( text = "e.g. Stripe" ) },
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { star = !star }) {
                    Icon(
                        imageVector = if (star) {
                            Icons.Default.Star} else {
                            Icons.Default.StarOutline},
                        contentDescription = null
                    )
                }
                Text(
                    text = "Your priority account",
                    modifier = Modifier.padding(16.dp)
                )
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Type of Account" ) },
                value = type,
                onValueChange = { type = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Balance of Account" ) },
                value = balance,
                onValueChange = { balance = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
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
            R.string.nav_account -> {
                val intent = Intent(this, AccountActivity::class.java)
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