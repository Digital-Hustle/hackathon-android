package com.example.send.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RadioButtonSelector(
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(options.firstOrNull()) }
    Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Уровень напряжения",
            color = MaterialTheme.colorScheme.onPrimary.copy(0.9f),
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 12.sp, lineHeight = 8.sp
        )
    Row(modifier = Modifier
        .border(width = 1.dp, color = MaterialTheme.colorScheme.primary,RoundedCornerShape(10.dp))) {

        options.forEach { option ->
            Column(
                modifier = Modifier
//                    .
                    .clickable {
                        selectedOption = option
                        onOptionSelected(option)
                    }
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        selectedOption = option
                        onOptionSelected(option)
                    }
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
    }
}