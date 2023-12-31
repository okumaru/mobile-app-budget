package com.example.budget.budget

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.budget.data.AddBudget
import com.example.budget.home.HomeActivity
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.DetailTrxCatActivity
import com.example.budget.trx_cat.TrxCatActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddBudgetActivity: BudgetApp() {
    override val navName = R.string.nav_add_budget
    private var catId: Int by mutableStateOf(0)

    private var budgetPeriod: String? by mutableStateOf(null)
    private var budgetAllocated: String by mutableStateOf("0")

    @SuppressLint("SimpleDateFormat")
    private val sdfPeriod = SimpleDateFormat("MMM/yyyy")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val budgetVM = BudgetViewModel(super.apiConfig!!)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            catId = intent.getIntExtra("catid", 0)

        })

        if (budgetVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, budgetVM.errorMessage, Toast.LENGTH_SHORT).show()
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
                        onClick = { change(R.string.nav_detail_cat) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Add budget for category",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val resSubmit = budgetVM.addBudget(
                                AddBudget(
                                    periode = budgetPeriod ?: sdfPeriod.format(Date()),
                                    allocated = budgetAllocated.toInt(),
                                    spent = 0,
                                    available = budgetAllocated.toInt(),
                                    categoryid = catId,
                                )
                            )

                            if (resSubmit) {
                                change(R.string.nav_detail_cat)
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

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Budget Period" ) },
                value = budgetPeriod ?: sdfPeriod.format(Date()),
                readOnly = true,
                onValueChange = { budgetPeriod = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Allocated" ) },
                value = budgetAllocated,
                onValueChange = { budgetAllocated = it },
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
            R.string.nav_detail_cat -> {
                val intent = Intent(this, DetailTrxCatActivity::class.java)
                intent.putExtra("catid", catId)
                startActivity(intent)
            }
        }
    }
}