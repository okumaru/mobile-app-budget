package com.example.budget.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CompassCalibration
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.AddActivity
import com.example.budget.BudgetApp
import com.example.budget.R
import com.example.budget.account.AccountActivity
import com.example.budget.cat_type.CategoryTypeActivity
import com.example.budget.home.HomeActivity
import com.example.budget.label.LabelActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity

class SettingActivity: BudgetApp() {
    override val navName = R.string.nav_setting

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy( 16.dp )
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_setting_info) }
            ) {
                SettingItemCard(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    ).padding(
                        all = 8.dp
                    ),
                    icon = Icons.Outlined.Info,
                    name = R.string.nav_setting_info,
                    label = "Detail information about this application.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_setting_config) }
            ) {
                SettingItemCard(
                    icon = Icons.Outlined.Build,
                    name = R.string.nav_setting_config,
                    label = "Setup to save all data in this application.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_setting_accounts) }
            ) {
                SettingItemCard(
                    icon = Icons.Outlined.AccountBox,
                    name = R.string.nav_setting_accounts,
                    label = "Manage your accounts.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_setting_cat_type) }
            ) {
                SettingItemCard(
                    icon = Icons.Outlined.CompassCalibration,
                    name = R.string.nav_setting_cat_type,
                    label = "Manage your category types.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_trx_cat) }
            ) {
                SettingItemCard(
                    icon = Icons.Outlined.BrightnessHigh,
                    name = R.string.nav_setting_trx_cat,
                    label = "Manage your transaction categories.",
                )
            }

            Surface(
                shape = MaterialTheme.shapes.medium,
                onClick = { change(R.string.nav_setting_label) }
            ) {
                SettingItemCard(
                    icon = Icons.Outlined.Label,
                    name = R.string.nav_setting_label,
                    label = "Manage your transaction labels.",
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
            R.string.nav_setting_accounts -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_setting_cat_type -> {
                val intent = Intent(this, CategoryTypeActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_setting_config -> {
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_setting_label -> {
                val intent = Intent(this, LabelActivity::class.java)
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
            R.string.nav_setting_info -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/okumaru/android-example"))
                startActivity(intent)
            }
        }
    }
}