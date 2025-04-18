package com.example.chat.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chats_holder.data.local.entities.ChatEntity


@Composable
fun ProfileItem(chat: ChatEntity?, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
//            .fillMaxHeight(0.12f)
//            .padding(5.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Avatar(

                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(5.dp)
            )

            Row(
                Modifier
                    .height(60.dp)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
//                    "Тех. Поддержка",
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) { // Синий цвет для "В"
                            append("Тех")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { // Синий цвет для "В"
                            append(".")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) { // Синий цвет для "В"
                            append(" Подержка")
                        }
                    },

                            fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
//                    fontWeight = FontWeight.Bold,
//                modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
        }

    }

}