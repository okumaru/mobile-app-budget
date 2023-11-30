package com.example.budget.trx_cat

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import com.example.budget.data.CategoryWithTypeBudget
import com.example.budget.home.HomeActivity
import com.example.budget.libs.HeadActivity
import com.example.budget.libs.composables.trx_cat.GridCategories
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity

class TrxCatActivity: BudgetApp() {
    override val navName = R.string.nav_trx_cat

    @Composable
    override fun Content() {

        if (super.apiConfig === null || !super.readyToUse)
            change(R.string.nav_setting_config)

        val catVM = TrxCatViewModel(super.apiConfig!!)
        var catsList by remember { mutableStateOf<List<CategoryWithTypeBudget>?>(value = null) }

        LaunchedEffect(Unit, block = {
            catsList = catVM.getCategories(null)
        })

        if (catVM.errorMessage.isNotEmpty()) {
            Toast.makeText(this, catVM.errorMessage, Toast.LENGTH_SHORT).show()
        }

        HeadActivity(
            title = R.string.trx_cat_head_title,
            addSection = {
                IconButton(
                    onClick = { change(R.string.nav_add_cat) },
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        Icons.Default.AddCircleOutline,
                        contentDescription = null
                    )
                }
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(1F).padding(bottom = 64.dp)
        ) {

            GridCategories(
                catsList,
                onClick = { detail(it) }
            )

        }
    }

    private fun detail(cat: CategoryWithTypeBudget) {
        val intent = Intent(this, DetailTrxCatActivity::class.java)
        intent.putExtra("catid", cat.id)
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
            R.string.nav_add_cat -> {
                val intent = Intent(this, AddTrxCatActivity::class.java)
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