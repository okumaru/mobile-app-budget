package com.example.budget.trx_cat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.budget.cat_type.CatTypeViewModel
import com.example.budget.data.AddBudgetFromCat
import com.example.budget.data.AddCatWithTypeBudget
import com.example.budget.data.AddTransaction
import com.example.budget.data.CategoryTypeWithBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.ChangeActivity
import com.example.budget.libs.LayoutWrapper
import com.example.budget.libs.composables.cat_type.CatTypeDropdownMenu
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.ui.theme.BudgetTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddTrxCatActivity: BudgetApp() {
    override val navName = R.string.nav_add_cat;

    private var name: String by mutableStateOf("")
    private var description: String? by mutableStateOf(null)
    private var typeid: Int? by mutableStateOf(null)

    private var budget: AddBudgetFromCat? by mutableStateOf(null)
    private var budgetPeriod: String? by mutableStateOf(null)
    private var budgetAllocated: String by mutableStateOf("0")

    @SuppressLint("SimpleDateFormat")
    private val sdfPeriod = SimpleDateFormat("MMM/yyyy");

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val catVM = TrxCatViewModel(super.apiConfig!!);
        val catTypeVM = CatTypeViewModel(super.apiConfig!!);
        var catTypesList by remember { mutableStateOf<List<CategoryTypeWithBudget>?>(value = null) }
        val coroutineScope = rememberCoroutineScope()
        var useBudget: Boolean by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit, block = {

            catTypesList = catTypeVM.getCatTypes();

        });

        if (catVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, catVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        if (!catTypeVM.errorMessage.isEmpty()) {
            Toast.makeText(this, catTypeVM.errorMessage,Toast.LENGTH_SHORT).show()
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
                        onClick = { change(R.string.nav_add) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }

                    Text(
                        text = "Add Category",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val resSubmit = catVM.addCategory(
                                AddCatWithTypeBudget(
                                    name = name,
                                    description = description,
                                    typeid = typeid!!,
                                    budget = if (useBudget)
                                        AddBudgetFromCat (
                                            periode = budgetPeriod ?: sdfPeriod.format(Date()),
                                            allocated = budgetAllocated.toInt(),
                                            spent = 0,
                                            available = budgetAllocated.toInt(),
                                        ) else null ,
                                )
                            );

                            if (resSubmit) {
                                change(R.string.nav_trx_cat)
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

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                if (catTypesList !== null) {
                    CatTypeDropdownMenu(
                        label = "Type of Category",
                        catTypesList = catTypesList,
                        changeCatTypeId = { typeid = it }
                    )
                }

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
                    label = { Text( text = "Description of Category" ) },
                    value = description ?: "",
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

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Enable budget for category", modifier = Modifier.padding(16.dp))
                    Checkbox(
                        checked = useBudget,
                        modifier = Modifier.padding(16.dp),
                        onCheckedChange = {
                            useBudget = it
                            if (!it)
                                budget = null

                            if (it) {
                                budget = AddBudgetFromCat (
                                    periode = budgetPeriod ?: sdfPeriod.format(Date()),
                                    allocated = budgetAllocated.toInt(),
                                    spent = 0,
                                    available = budgetAllocated.toInt(),
                                )
                            }
                        },
                    )
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
                    readOnly = !useBudget,
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
    }

    override fun change(activity: Int) {
        when(activity) {
            R.string.nav_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_trx -> {
                val intent = Intent(this, TrxActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_add -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_trx_cat -> {
                val intent = Intent(this, TrxCatActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent);
            }
        }
    }
}