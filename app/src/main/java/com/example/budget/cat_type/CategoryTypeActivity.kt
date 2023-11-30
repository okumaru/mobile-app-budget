package com.example.budget.cat_type

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.budget.data.CategoryTypeWithBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.HeadActivity
import com.example.budget.libs.composables.cat_type.GridCatTypesSummary
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity

class CategoryTypeActivity: BudgetApp() {
    override val navName = R.string.nav_setting_cat_type

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val catTypeVM = CatTypeViewModel(super.apiConfig!!)
        var catTypesList by remember { mutableStateOf<List<CategoryTypeWithBudget>?>(value = null) }

        LaunchedEffect(Unit, block = {

            catTypesList = catTypeVM.getCatTypes()

        })

        if (catTypeVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, catTypeVM.errorMessage,Toast.LENGTH_SHORT).show()
        }

        Column(modifier = Modifier
            .fillMaxSize(1F)
            .padding(bottom = 64.dp)){
            HeadActivity(
                title = R.string.cat_type_head_title,
                addSection = {
                    IconButton(
                        onClick = { change(R.string.nav_add_cat_type) },
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.AddCircleOutline,
                            contentDescription = null
                        )
                    }
                }
            )

            GridCatTypesSummary(
                types = catTypesList,
                onClick = { detail(it) }
            )
        }
    }

    private fun detail(type: CategoryTypeWithBudget) {
        val intent = Intent(this, DetailCatTypeActivity::class.java)
        intent.putExtra("typeid", type.id)
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
            R.string.nav_add_cat_type -> {
                val intent = Intent(this, AddCatTypeActivity::class.java)
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