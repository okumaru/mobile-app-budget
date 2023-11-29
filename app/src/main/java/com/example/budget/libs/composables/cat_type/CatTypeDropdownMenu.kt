package com.example.budget.libs.composables.cat_type

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
import com.example.budget.cat_type.CatTypeViewModel
import com.example.budget.data.Account
import com.example.budget.data.CategoryType
import com.example.budget.data.CategoryTypeWithBudget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatTypeDropdownMenu(
    label: String?,
    catTypesList: List<CategoryTypeWithBudget>?,
    changeCatTypeId: (Int) -> Unit,
) {

    var selectedDataCatType by remember { mutableStateOf<CategoryTypeWithBudget?>(value = null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {

        if (catTypesList !== null) {
            selectedDataCatType = catTypesList[0];
            changeCatTypeId(catTypesList[0].id);
        }
    });

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            if (selectedDataCatType != null) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = { Text( label ?: "Account" ) },
                    readOnly = true,
                    value = selectedDataCatType!!.type,
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

                    catTypesList?.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.type) },
                            onClick = {
                                selectedDataCatType = selectionOption
                                changeCatTypeId(selectionOption.id)
                                expanded = false
                            }
                        )
                    }

                }
            }
        }
    }
}