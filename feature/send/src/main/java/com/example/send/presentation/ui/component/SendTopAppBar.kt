package com.example.send.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.send.presentation.SendContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendTopAppBar(state: SendContract.State,
                  onEventSent: (event: SendContract.Event) -> Unit
) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = {
            onEventSent(SendContract.Event.BackButtonClicked)
        }, Modifier.fillMaxHeight()) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description",
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
    },actions = {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clip(RoundedCornerShape(30.dp)).clickable {

                onEventSent(
                    if (state.isAuthorized) {
                        SendContract.Event.ToProfileButtonClicked
                    } else {
                        SendContract.Event.ToAuthButtonClicked
                    }
                )
        }) {
            Text(if (state.isAuthorized) "  Личный кабинет" else "  Войти в личный кабинет")
            IconButton(onClick = {
                onEventSent(
                    if (state.isAuthorized) {
                        SendContract.Event.ToProfileButtonClicked
                    } else {
                        SendContract.Event.ToAuthButtonClicked
                    }
                )
            },
                modifier =  Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Localized description",
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }
        }


    }, title = {}, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.onTertiary,
        scrolledContainerColor = MaterialTheme.colorScheme.onTertiary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
    )
}