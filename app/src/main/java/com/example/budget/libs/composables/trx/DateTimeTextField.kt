package com.example.budget.libs.composables.trx

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.sql.Timestamp
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeTextField(
    defaultDT: Date,
    updateDT: (Date) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    val currentDate = sdf.format(defaultDT)
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Timestamp(System.currentTimeMillis())

    val datePickerDialog =
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            selectedDate = "${month.toMonthName()} $dayOfMonth, $year"

            updateDT(newDate.time);

        }, year, month, day)

    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text( text = "Date of transaction" ) },
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )

    if (isPressed) {
        datePickerDialog.show()
    }
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}