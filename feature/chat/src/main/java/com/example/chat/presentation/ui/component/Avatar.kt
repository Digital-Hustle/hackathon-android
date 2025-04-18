package com.example.chat.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

@Composable
fun Avatar(modifier: Modifier = Modifier) {
    Image(
        imageVector = Icons.Default.AccountCircle,

//            painter = painterResource(R.drawable.avatar),
        contentDescription = "Аватар",
        modifier = modifier
            .clip(CircleShape),
        contentScale = ContentScale.Crop,


    )

}