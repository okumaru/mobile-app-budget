package com.example.budget.libs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budget.ui.theme.BudgetTheme

@Composable
fun LayoutWrapper(
    windowSize: WindowSizeClass,
    navState: Int,
    changeNav: (Int) -> Unit,
    activity: @Composable (Modifier) -> Unit
) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            LayoutPortrait(
                activeNav = navState,
                changeNav,
                activity
            )
        }
        WindowWidthSizeClass.Expanded -> {
            LayoutLandscape(
                activeNav = navState,
                changeNav,
                activity
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutPortrait(
    activeNav: Int,
    changeNav: (Int) -> Unit,
    activity: @Composable (Modifier) -> Unit
) {
    BudgetTheme {
        Scaffold(
            bottomBar = { BottomNav(activeNav, changeNav) }
        ) { padding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp,
                    ).fillMaxWidth()
            ) {
                activity(Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun LayoutLandscape(
    activeNav: Int,
    changeNav: (Int) -> Unit,
    activity: @Composable (Modifier) -> Unit
) {
    BudgetTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                RailNav(activeNav, changeNav)
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        )
                ) {
                    activity(Modifier)
                }
            }
        }
    }
}