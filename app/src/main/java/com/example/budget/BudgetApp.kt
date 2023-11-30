package com.example.budget

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.budget.data.APIConfig
import com.example.budget.data.Config
import com.example.budget.data.ConfigDatabase
import com.example.budget.home.HomeActivity
import com.example.budget.libs.ChangeActivity
import com.example.budget.libs.LayoutWrapper
import com.example.budget.setting.SettingActivity
import com.example.budget.trx.TrxActivity
import com.example.budget.trx_cat.TrxCatActivity
import com.example.budget.ui.theme.BudgetTheme

open class BudgetApp: ComponentActivity(), ChangeActivity {
    override val navName = R.string.app_name
    protected var apiConfig: APIConfig? by mutableStateOf(null)
    protected var readyToUse: Boolean by  mutableStateOf(false)

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupConfig()

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

    private fun setupConfig() {
        try {

            val db = Room.databaseBuilder(
                applicationContext,
                ConfigDatabase::class.java, "sohfin"
            ).allowMainThreadQueries().build()

            val configDao = db.configDao()

            val getApiHost: Config? = configDao.findByName("host")
            if (getApiHost === null)
                throw Exception("Config host is empty")

            val getApiPathAccount: Config? = configDao.findByName("pathAccount")
            if (getApiPathAccount === null)
                throw Exception("Config api path account is empty")

            val getApiPathCatType: Config? = configDao.findByName("pathCatType")
            if (getApiPathCatType === null)
                throw Exception("Config api path cat type is empty")

            val getApiPathTrxCat: Config? = configDao.findByName("pathTrxCat")
            if (getApiPathTrxCat === null)
                throw Exception("Config api path trx cat is empty")

            val getApiPathBudget: Config? = configDao.findByName("pathBudget")
            if (getApiPathBudget === null)
                throw Exception("Config api path budget is empty")

            val getApiPathTrx: Config? = configDao.findByName("pathTrx")
            if (getApiPathTrx === null)
                throw Exception("Config api path trx is empty")

            apiConfig = APIConfig(
                apiHost = getApiHost.configValue,
                apiPathAccount = getApiPathAccount.configValue,
                apiPathCatType = getApiPathCatType.configValue,
                apiPathTrxCat = getApiPathTrxCat.configValue,
                apiPathBudget = getApiPathBudget.configValue,
                apiPathTrx = getApiPathTrx.configValue
            )

            readyToUse = true

        } catch (e: Exception) {
            readyToUse = false
        }
    }

    @Composable
    override fun Content() {}

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
        }
    }
}