package com.example.budget

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.CompareArrows
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.home.HomeActivity
import com.example.budget.setting.SettingActivity
import com.example.budget.setting.SettingItemCard
import com.example.budget.trx.AddTrxActivity
import com.example.budget.trx.TransTrxActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.AddTrxCatActivity
import com.example.budget.trx_cat.TrxCatActivity

class AddActivity: BudgetApp() {
    override val navName = R.string.nav_add;

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change( R.string.nav_add_trx_income ) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.SaveAlt,
                    name = R.string.nav_add_trx_income,
                    label = "The source of income transactions.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change(R.string.nav_add_trx_saving) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.EnergySavingsLeaf,
                    name = R.string.nav_add_trx_saving,
                    label = "The cost for saving in period of time.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change(R.string.nav_add_trx_need) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.Balance,
                    name = R.string.nav_add_trx_need,
                    label = "The cost of buying something to live on.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change(R.string.nav_add_trx_want) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.Celebration,
                    name = R.string.nav_add_trx_want,
                    label = "The cost of buying something to improve yourself.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change(R.string.nav_add_trx_transfer) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.CompareArrows,
                    name = R.string.nav_add_trx_transfer,
                    label = "Transfer transactions from account to other accounts.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onSecondary,
                onClick = { change(R.string.nav_add_cat) }
            ) {
                SettingItemCard(
                    modifier = Modifier
                        .padding(
                            horizontal =  8.dp,
                            vertical = 16.dp
                        ),
                    icon = Icons.Outlined.BrightnessHigh,
                    name = R.string.nav_add_cat,
                    label = "The thing to categorize a transaction.",
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
            R.string.nav_add_trx_income -> {
                val intent = Intent(this, AddTrxActivity::class.java)
                intent.putExtra("type", "Income")
                startActivity(intent);
            }
            R.string.nav_add_trx_saving -> {
                val intent = Intent(this, AddTrxActivity::class.java)
                intent.putExtra("type", "Saving")
                startActivity(intent);
            }
            R.string.nav_add_trx_need -> {
                val intent = Intent(this, AddTrxActivity::class.java)
                intent.putExtra("type", "Need")
                startActivity(intent);
            }
            R.string.nav_add_trx_want -> {
                val intent = Intent(this, AddTrxActivity::class.java)
                intent.putExtra("type", "Want")
                startActivity(intent);
            }
            R.string.nav_add_trx_transfer -> {
                val intent = Intent(this, TransTrxActivity::class.java)
                startActivity(intent);
            }
            R.string.nav_add_cat -> {
                val intent = Intent(this, AddTrxCatActivity::class.java)
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