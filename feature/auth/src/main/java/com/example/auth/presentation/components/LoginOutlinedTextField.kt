package com.example.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun LoginOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit, label: String, hint: String, modifier: Modifier = Modifier
) {
    OutlinedTextField(

        value = value,
        onValueChange =onValueChange,
        label = { Text(label, color = MaterialTheme.colorScheme.onPrimary.copy(0.9f)) },
        placeholder = { Text(hint) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(0.8f),
            unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(0.8f),
            focusedTextColor = MaterialTheme.colorScheme.primary,


            ),
        modifier = modifier.fillMaxWidth(0.8f),
        singleLine = true
    )
}
