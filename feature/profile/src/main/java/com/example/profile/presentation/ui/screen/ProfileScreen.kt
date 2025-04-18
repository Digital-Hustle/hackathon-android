package com.example.profile.presentation.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.mLog
import com.example.network.presentation.TokenViewModel
import com.example.profile.presentation.ProfileContract
import com.example.profile.presentation.model.FileType
import com.example.profile.presentation.model.HistoryReport
import com.example.profile.presentation.ui.component.HistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.sql.Date
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.profile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileContract.State,
    effectFlow: Flow<ProfileContract.Effect>?,
    onEventSent: (event: ProfileContract.Event) -> Unit,
    onNavigationRequested: (ProfileContract.Effect.Navigation) -> Unit,
) {


    val tokenViewModel: TokenViewModel = hiltViewModel()
//    val history = state.historyPagingFlow?.collectAsLazyPagingItems()
    val history = List(20) { i ->
        HistoryReport(
            i, "Отчёт $i.xlsx", "10MB", "21:4$i-06.05.2025",
            contentType = FileType.XLSX
        )
    }
    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is ProfileContract.Effect.Navigation.ToChat -> onNavigationRequested(
                    ProfileContract.Effect.Navigation.ToChat(effect.chatId)
                )

                ProfileContract.Effect.Navigation.ToMain -> onNavigationRequested(
                    ProfileContract.Effect.Navigation.ToMain
                )

                ProfileContract.Effect.Logout ->{ tokenViewModel.logout()
                    onNavigationRequested(
                        ProfileContract.Effect.Navigation.ToMain
                    )
                }
            }
        }?.collect()
    }


    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    onEventSent(ProfileContract.Event.BackButtonClicked)
                }, Modifier.fillMaxHeight()) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
            },
            actions = {
                IconButton(onClick = { onEventSent(ProfileContract.Event.OnMenuBottomClicked) }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                }
                DropdownMenu(
                    expanded = state.showMenu,
                    onDismissRequest = { onEventSent(ProfileContract.Event.OnDismissMenu) },
                    Modifier.background(MaterialTheme.colorScheme.tertiary)
                ) {
                    DropdownMenuItem(
                        text = { Text("Выйти",color =MaterialTheme.colorScheme.onPrimary ) },
                        onClick = {
                            onEventSent(ProfileContract.Event.OnDismissMenu)
                            onEventSent(ProfileContract.Event.Logout)
                        }
                    )
                }
            },
            title = { Text("Профиль", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                scrolledContainerColor = MaterialTheme.colorScheme.onTertiary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
//                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                state.userName ?: "",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 48.sp, modifier = Modifier
                    .fillMaxWidth(0.9f), fontWeight = FontWeight.Bold,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.fillMaxHeight(0.1f))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .clickable { onEventSent(ProfileContract.Event.ToChatButtonClicked) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Чат с поддержкой",
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.95f),
                    fontSize = 24.sp, fontWeight = FontWeight.Bold

                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .clickable {

                        onEventSent(
                            if (state.isHistoryOpened) {
                                ProfileContract.Event.ClosedHistoryButtonClicked
                            } else {
                                ProfileContract.Event.OpenHistoryButtonClicked
                            }
                        )
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "История",
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.95f),
                    fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)

                )

                val rotationAngle by animateFloatAsState(
                    targetValue = if (state.isHistoryOpened) 180f else 0f,
                    animationSpec = tween(
                        durationMillis = 300, // Продолжительность анимации в миллисекундах
                        easing = FastOutSlowInEasing // Плавное замедление в конце
                    ),
                    label = "rotation"
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .fillMaxHeight()
                        .graphicsLayer {
                            rotationZ = rotationAngle
                        },
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
            AnimatedVisibility(
                visible = state.isHistoryOpened
            ) {
                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(history.size) { index ->
                        history.get(index).let {
                            HistoryItem(it)

                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
                            )

                        }
                    }


                }

            }




            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
            )
        }
        Box(Modifier.fillMaxWidth().fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.illustration),
                "illuctation",
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ).fillMaxHeight()
                    .fillMaxWidth(0.6f)
            )

        }


    }
}