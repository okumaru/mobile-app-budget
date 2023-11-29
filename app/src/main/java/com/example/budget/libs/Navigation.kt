package com.example.budget.libs

import android.graphics.Color
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budget.R

@Composable
fun BottomNav (
    activeNav: Int,
    changeNav: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = activeNav == R.string.nav_home,
            onClick = { changeNav(R.string.nav_home) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.nav_home)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_home))
            }
        )
        NavigationBarItem(
            selected = activeNav == R.string.nav_trx,
            onClick = { changeNav(R.string.nav_trx) },
            icon = {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = stringResource(id = R.string.nav_trx)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_trx))
            }
        )
        NavigationBarItem(
            selected = activeNav == R.string.nav_add,
            onClick = { changeNav(R.string.nav_add) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(id = R.string.nav_add)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_add))
            }
        )
        NavigationBarItem(
            selected = activeNav == R.string.nav_trx_cat,
            onClick = { changeNav(R.string.nav_trx_cat) },
            icon = {
                Icon(
                    imageVector = Icons.Default.BrightnessHigh,
                    contentDescription = stringResource(id = R.string.nav_trx_cat)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_trx_cat))
            }
        )
        NavigationBarItem(
            selected = activeNav == R.string.nav_setting,
            onClick = { changeNav(R.string.nav_setting) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(id = R.string.nav_setting)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.nav_setting))
            }
        )
    }
}

@Composable
fun RailNav (
    activeNav: Int,
    changeNav: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                selected = activeNav == R.string.nav_home,
                onClick = { changeNav(R.string.nav_home) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(id = R.string.nav_home)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_home))
                }
            )
            NavigationRailItem(
                selected = activeNav == R.string.nav_trx,
                onClick = { changeNav(R.string.nav_trx) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = stringResource(id = R.string.nav_trx)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_trx))
                }
            )
            NavigationRailItem(
                selected = activeNav == R.string.nav_add,
                onClick = { changeNav(R.string.nav_add) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(id = R.string.nav_add)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_add))
                }
            )
            NavigationRailItem(
                selected = activeNav == R.string.nav_trx_cat,
                onClick = { changeNav(R.string.nav_trx_cat) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.BrightnessHigh,
                        contentDescription = stringResource(id = R.string.nav_trx_cat)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_trx_cat))
                }
            )
            NavigationRailItem(
                selected = activeNav == R.string.nav_setting,
                onClick = { changeNav(R.string.nav_setting) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.nav_setting)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.nav_setting))
                }
            )
        }
    }
}