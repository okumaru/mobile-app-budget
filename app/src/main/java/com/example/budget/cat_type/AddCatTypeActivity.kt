package com.example.budget.cat_type

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.budget.account.AccountActivity
import com.example.budget.account.AccountViewModel
import com.example.budget.data.AddBudgetFromCat
import com.example.budget.data.AddCatType
import com.example.budget.data.AddCatWithTypeBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.ChangeActivity
import com.example.budget.libs.HeadActivity
import com.example.budget.libs.LayoutWrapper
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity
import com.example.budget.ui.theme.BudgetTheme
import kotlinx.coroutines.launch
import java.util.Date

class AddCatTypeActivity: BudgetApp() {
    override val navName = R.string.nav_add_cat_type;

    private var type: String by mutableStateOf("");
    private var description: String by mutableStateOf("")
    private var icon: String by mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val catTypeVM = CatTypeViewModel(super.apiConfig!!);
        val coroutineScope = rememberCoroutineScope()

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
                        onClick = { change(R.string.nav_setting_cat_type) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Add Type of Category",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val resSubmit = catTypeVM.addCatType(
                                AddCatType(
                                    type = type,
                                    description = description,
                                    icon = icon
                                )
                            );

                            if (resSubmit) {
                                change(R.string.nav_setting_cat_type)
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
                label = { Text( text = "Type of Category" ) },
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

        }
    }

    override fun change(activity: Int) {
        when(activity) {
            R.string.nav_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_account -> {
                val intent = Intent(this, AccountActivity::class.java)
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
            R.string.nav_setting_cat_type -> {
                val intent = Intent(this, CategoryTypeActivity::class.java)
                startActivity(intent);
            }
        }
    }
}