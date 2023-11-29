package com.example.budget.libs.composables.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.budget.account.AccountViewModel
import com.example.budget.data.Account

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsDropdownMenu(
    label: String?,
    accounts: List<Account>,
    initItem: Boolean,
    changeAccountId: (Int) -> Unit
) {

    var selectedDataAccount by remember { mutableStateOf<Account?>(value = null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        if (!initItem) {
            selectedDataAccount = accounts[0];
            changeAccountId(accounts[0].id);
        }
    });

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text( label ?: "Account" ) },
                readOnly = true,
                value = if (selectedDataAccount != null) {selectedDataAccount!!.name} else {"Select account."},
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {

                if (initItem) {
                    DropdownMenuItem(
                        text = { Text("Select account.") },
                        onClick = {
                            changeAccountId(0)
                            expanded = false
                        }
                    )
                }

                accounts.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.name) },
                        onClick = {
                            selectedDataAccount = selectionOption
                            changeAccountId(selectionOption.id)
                            expanded = false
                        }
                    )
                }

            }
        }
    }
}