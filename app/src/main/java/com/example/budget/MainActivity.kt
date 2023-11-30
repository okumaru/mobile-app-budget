package com.example.budget

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.Intent
import com.example.budget.home.HomeActivity
import com.example.budget.setting.ConfigActivity
import com.example.budget.ui.theme.BudgetTheme

class MainActivity : BudgetApp() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BudgetTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Welcome to SoH Budget App.")
                        Button(
                            modifier = Modifier.padding(vertical = 24.dp),
                            onClick = { change(
                                if (super.readyToUse) {
                                    R.string.nav_home
                                } else {
                                    R.string.nav_setting_config
                                }
                            )}
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        }
    }

    override fun change(activity: Int) {
        when(activity) {
            R.string.nav_setting_config -> {
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
            }
            R.string.nav_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}