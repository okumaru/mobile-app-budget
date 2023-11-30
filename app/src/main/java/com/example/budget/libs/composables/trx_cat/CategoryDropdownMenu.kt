package com.example.budget.libs.composables.trx_cat

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
import com.example.budget.data.CategoryWithTypeBudget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsDropMenu(
    label: String,
    catsList: List<CategoryWithTypeBudget>?,
    initItem: Boolean,
    changeCategoryId: (Int) -> Unit
) {

    var selectedDataCats by remember { mutableStateOf<CategoryWithTypeBudget?>(value = null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {

        if (!initItem && catsList !== null) {
            selectedDataCats = catsList[0]
            changeCategoryId(catsList[0].id)
        }

    })

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text( text = label ) },
                readOnly = true,
                value = if (selectedDataCats != null) {selectedDataCats!!.name} else {"Select category"},
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
                        text = { Text("Select category.") },
                        onClick = {
                            changeCategoryId(0)
                            expanded = false
                        }
                    )
                }

                catsList?.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.name) },
                        onClick = {
                            selectedDataCats = selectionOption
                            changeCategoryId(selectionOption.id)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}