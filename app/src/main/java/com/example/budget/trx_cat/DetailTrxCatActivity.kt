package com.example.budget.trx_cat

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.budget.AddBudgetActivity
import com.example.budget.budget.BudgetViewModel
import com.example.budget.budget.HistoriesBudgetActivity
import com.example.budget.data.CategoryBudget
import com.example.budget.data.CategoryType
import com.example.budget.data.UpdateCategory
import com.example.budget.home.HomeActivity
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import kotlinx.coroutines.launch

class DetailTrxCatActivity: BudgetApp() {
    override val navName = R.string.nav_detail_cat
    private var catId: Int by mutableStateOf(0)

    private var name: String by mutableStateOf("")
    private var description: String by mutableStateOf("")
    private var type: CategoryType? by mutableStateOf(null)
    private var budget: CategoryBudget? by mutableStateOf(null)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val catVM = TrxCatViewModel(super.apiConfig!!)
        val budgetVM = BudgetViewModel(super.apiConfig!!)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit, block = {

            //get data from intent
            val intent = intent
            catId = intent.getIntExtra("catid", 0)

            val detail = catVM.detailCat(catId)
            if (detail != null) {
                name = detail.name
                description = detail.description.toString()
                type = detail.type
                budget = detail.budget
            }

        })

        if (catVM.errorMessage.isNotEmpty())
            Toast.makeText(this, catVM.errorMessage, Toast.LENGTH_SHORT).show()

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
                        onClick = { change(R.string.nav_trx_cat) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Detail of Category",
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
                                val resSubmit = catVM.deleteCat(catId)
                                if (resSubmit) {
                                    change(R.string.nav_trx_cat)
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
                                val resSubmit = catVM.updateCat(
                                    catId,
                                    UpdateCategory(
                                        name = name,
                                        description = description,
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
                label = { Text( text = "Type of Category" ) },
                value = if (type !== null) { type!!.type } else { "" },
                readOnly = true,
                onValueChange = {  },
                shape = MaterialTheme.shapes.medium,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "Name of Category" ) },
                value = name,
                onValueChange = { name = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                label = { Text( text = "Description of Type Category" ) },
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

            if (budget === null) {
                Button(
                    onClick = { change(R.string.nav_add_budget) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Add budget to this category",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (budget !== null) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.onSecondary,
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Button(onClick = { change(R.string.nav_history_budget) }) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.LibraryBooks, contentDescription = null)
                                    Text(text = "Histories budget")
                                }
                            }

                            Button(onClick = {
                                coroutineScope.launch {
                                    val resSubmit = budgetVM.deleteBudget(budget!!.id)
                                    if (resSubmit) {
                                        refresh()
                                    }
                                }
                            }) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null)
                                    Text(text = "Delete budget")
                                }
                            }
                        }

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text( text = "Budget Period" ) },
                            value = if (budget !== null) { budget!!.periode } else { "" },
                            readOnly = true,
                            onValueChange = {  },
                            shape = MaterialTheme.shapes.medium,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text( text = "Budget Allocated" ) },
                            value = if (budget !== null) { "Rp. ${budget!!.allocated}" } else { "" },
                            readOnly = true,
                            onValueChange = {  },
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text( text = "Budget Spent" ) },
                            value = if (budget !== null) { "Rp. ${budget!!.spent}" } else { "" },
                            readOnly = true,
                            onValueChange = {  },
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                        )

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text( text = "Budget Available" ) },
                            value = if (budget !== null) { "Rp. ${budget!!.available}" } else { "" },
                            readOnly = true,
                            onValueChange = {  },
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                        )
                    }
                }
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
            R.string.nav_add_budget -> {
                val intent = Intent(this, AddBudgetActivity::class.java)
                intent.putExtra("catid", catId)
                startActivity(intent)
            }
            R.string.nav_history_budget -> {
                val intent = Intent(this, HistoriesBudgetActivity::class.java)
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