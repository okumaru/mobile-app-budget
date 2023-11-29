package com.example.budget.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
//import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.budget.AddActivity
import com.example.budget.R
import com.example.budget.data.Config
import com.example.budget.data.ConfigDao
import com.example.budget.data.ConfigDatabase
import com.example.budget.home.HomeActivity
import com.example.budget.libs.ChangeActivity
import com.example.budget.libs.LayoutWrapper
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity
import com.example.budget.ui.theme.BudgetTheme

class ConfigActivity: ComponentActivity(), ChangeActivity {
    override val navName = R.string.nav_setting_config;
    private var configDao: ConfigDao? by mutableStateOf(null)

    private var apiHost: String? by mutableStateOf(null)
    private var apiPathAccount: String? by mutableStateOf(null)
    private var apiPathCatType: String? by mutableStateOf(null)
    private var apiPathTrxCat: String? by mutableStateOf(null)
    private var apiPathBudget: String? by mutableStateOf(null)
    private var apiPathTrx: String? by mutableStateOf(null)

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            ConfigDatabase::class.java, "sohfin"
        ).allowMainThreadQueries().build();

        configDao = db.configDao();

        setContent {
            BudgetTheme {
                Surface(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSizeClass = calculateWindowSizeClass(this)
                    LayoutWrapper(
                        windowSize = windowSizeClass,
                        navState = navName,
                        changeNav = { change -> change( change ) },
                        activity = { Content() }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val getApiHost: Config? = configDao?.findByName("host")
        if (getApiHost !== null && apiHost === null)
            apiHost = getApiHost.configValue;

        val getApiPathAccount: Config? = configDao?.findByName("pathAccount")
        if (getApiPathAccount !== null && apiPathAccount === null)
            apiPathAccount = getApiPathAccount.configValue;

        val getApiPathCatType: Config? = configDao?.findByName("pathCatType")
        if (getApiPathCatType !== null && apiPathCatType === null)
            apiPathCatType = getApiPathCatType.configValue;

        val getApiPathTrxCat: Config? = configDao?.findByName("pathTrxCat")
        if (getApiPathTrxCat !== null && apiPathTrxCat === null)
            apiPathTrxCat = getApiPathTrxCat.configValue;

        val getApiPathBudget: Config? = configDao?.findByName("pathBudget")
        if (getApiPathBudget !== null && apiPathBudget === null)
            apiPathBudget = getApiPathBudget.configValue;

        val getApiPathTrx: Config? = configDao?.findByName("pathTrx")
        if (getApiPathTrx !== null && apiPathTrx === null)
            apiPathTrx = getApiPathTrx.configValue;

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
                    onClick = { change(R.string.nav_setting) },
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }

                Text(
                    text = "Setup Config",
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            IconButton(
                onClick = {
                    configDao?.insert(Config (
                        id = 1,
                        configName = "host",
                        configValue = apiHost.toString()
                    ));

                    configDao?.insert(Config (
                        id = 2,
                        configName = "pathAccount",
                        configValue = apiPathAccount.toString()
                    ));

                    configDao?.insert(Config (
                        id = 3,
                        configName = "pathCatType",
                        configValue = apiPathCatType.toString()
                    ));

                    configDao?.insert(Config (
                        id = 4,
                        configName = "pathTrxCat",
                        configValue = apiPathTrxCat.toString()
                    ));

                    configDao?.insert(Config (
                        id = 5,
                        configName = "pathBudget",
                        configValue = apiPathBudget.toString()
                    ));

                    configDao?.insert(Config (
                        id = 6,
                        configName = "pathTrx",
                        configValue = apiPathTrx.toString()
                    ));

                    refresh();
                },
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Host" ) },
                value = apiHost ?: "",
                onValueChange = { apiHost = it; println(it) },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Path Account" ) },
                value = apiPathAccount ?: "",
                onValueChange = { apiPathAccount = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Path Category Type" ) },
                value = apiPathCatType ?: "",
                onValueChange = { apiPathCatType = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Path Transaction Category" ) },
                value = apiPathTrxCat ?: "",
                onValueChange = { apiPathTrxCat = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Path Category Budget" ) },
                value = apiPathBudget ?: "",
                onValueChange = { apiPathBudget = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text( text = "API Path Transaction" ) },
                value = apiPathTrx ?: "",
                onValueChange = { apiPathTrx = it },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }

    private fun refresh() {
        val intent = intent;
        startActivity(intent);
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