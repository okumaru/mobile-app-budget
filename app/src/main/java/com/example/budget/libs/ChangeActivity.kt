package com.example.budget.libs

import androidx.compose.runtime.Composable

interface ChangeActivity {
    val navName: Int;

    @Composable
    fun Content()

    fun change(activity: Int)
}