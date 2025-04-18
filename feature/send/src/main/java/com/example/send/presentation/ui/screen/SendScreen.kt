package com.example.send.presentation.ui.screen


import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.mLog
import com.example.send.R
import com.example.send.presentation.SendContract
import com.example.send.presentation.ui.component.ListItem
import com.example.send.presentation.ui.component.SendButton
import com.example.send.presentation.ui.component.SendTopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import com.example.send.presentation.VoltageLevel
//import com.example.send.presentation.ui.component.OutlinedTextField
import com.example.send.presentation.ui.component.RadioButtonSelector
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SendScreen(
    state: SendContract.State,
    effectFlow: Flow<SendContract.Effect>?,
    onEventSent: (event: SendContract.Event) -> Unit,
    onNavigationRequested: (SendContract.Effect.Navigation) -> Unit,
) {
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        mLog("z", "YATYTZ")
        val mimeType = it?.let { it1 -> context.contentResolver.getType(it1) }
        val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: it?.lastPathSegment?.substringAfterLast('.', "")

        if (fileExtension?.lowercase() in listOf("xlsx", "xls")) {
            it?.let { it1 ->
                fileExtension?.lowercase()
                    ?.let { it2 -> SendContract.Event.OnFileSelected(it1, it2) }
            }
                ?.let { it2 -> onEventSent(it2) }
        } else {
            mLog("z", "YATYTZ2")

            onEventSent(SendContract.Event.OnIncorrectFileFormat)
        }
    }



    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                SendContract.Effect.Navigation.ToAuth -> onNavigationRequested(

                    SendContract.Effect.Navigation.ToAuth
                )

                SendContract.Effect.Navigation.ToMain -> onNavigationRequested(
                    SendContract.Effect.Navigation.ToMain
                )

                SendContract.Effect.Navigation.ToProfile -> onNavigationRequested(
                    SendContract.Effect.Navigation.ToProfile
                )
            }
        }?.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SendTopAppBar(state, onEventSent)

        Box(
            Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) { // Синий цвет для "В"
                            append("Как это\nработает")

                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { // Синий цвет для "В"
                            append("?")
                        }
                    },
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 60.sp,
                )
                Row(
                    Modifier
                        .clickable {

                            onEventSent(
                                if (state.isManualOpened) {
                                    SendContract.Event.ClosedManualButtonClicked
                                } else {
                                    SendContract.Event.OpenManualButtonClicked
                                }
                            )
                        }
                        .fillMaxWidth(0.8f)
                        .align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Предложим подходящий для Вас тариф. Экономьте миллионы.",
                        softWrap = true,
                        modifier = Modifier.weight(1f),
//                            .align(Alignment.BottomStart),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (state.isManualOpened) 180f else 0f,
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
//                            .fillMaxHeight()
                            .graphicsLayer {
                                rotationZ = rotationAngle
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                }
            }

        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = state.isManualOpened) {
                    Column {
                        Spacer(Modifier.fillMaxHeight(0.05f))
                        ListItem(
                            painterResource(R.drawable.icon_list_1),
                            "Вы загружаете файл с вашим деталями потреблением электроэнергии"
                        )
                        ListItem(
                            painterResource(R.drawable.icon_list_2),
                            "Мы рассчитываем и предлагаем для Вас наиболее выгодный тариф"
                        )
                        ListItem(
                            painterResource(R.drawable.icon_list_3),
                            "Вы можете войти в свой личный кабинет для сохранения расчетов"
                        )
                    }

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = !state.isManualOpened) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.fillMaxHeight(0.05f))

                        OutlinedTextField(
                            label = { Text("Максимальная мощность (кВт)", color = MaterialTheme.colorScheme.onPrimary.copy(0.9f)) },
                            placeholder = { Text("5000") },
                            value = state.maxPower,
                            onValueChange = { newValue ->
                                if (newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                    onEventSent(SendContract.Event.OnPowerValueChanged(newValue))
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(
                                    0.8f
                                ),
                                unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                                focusedTextColor = MaterialTheme.colorScheme.primary,

                                ),
                            modifier = Modifier.fillMaxWidth(0.8f),
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        RadioButtonSelector(VoltageLevel.toListOfValues()) { newValue ->
                            onEventSent(SendContract.Event.OnVoltageValueChanged(newValue))
                        }
                    }

                }

            }
        }



        AnimatedVisibility(
            visible = state.isUploadError || state.isSuccessfulUpload || state.isFileSaved,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseOutQuint
                ), initialOffsetY = { fullHeight -> -fullHeight }

            )

        ) {
            if (state.isUploadError) {
                Text(
                    "Выберите файл формата .xlsx или .хls",
                    color = MaterialTheme.colorScheme.error.copy(0.7f)
                )
            }
            if (state.isSuccessfulUpload) {
                Text("Файл отправлен", color = MaterialTheme.colorScheme.primary.copy(0.7f))
            }
            if (state.isFileSaved) {
                Text(
                    "Файл сохранен под именем ${state.savedFileName}",
                    color = MaterialTheme.colorScheme.primary.copy(0.7f), textAlign = TextAlign.Center
                )
            }
        }



        SendButton({
            filePickerLauncher.launch("*/*")
            onEventSent(SendContract.Event.SendButtonClicked)
        })


    }


}
