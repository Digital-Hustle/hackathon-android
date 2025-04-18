package com.example.main.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.main.R
import com.example.main.presentation.MainContract
import com.example.main.presentation.ui.component.MainTopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect


@Composable
fun MainScreen(
    state: MainContract.State,
    effectFlow: Flow<MainContract.Effect>?,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
) {
    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                MainContract.Effect.Navigation.ToAuth -> onNavigationRequested(

                    MainContract.Effect.Navigation.ToAuth
                )

                MainContract.Effect.Navigation.ToSend -> onNavigationRequested(
                    MainContract.Effect.Navigation.ToSend
                )

                MainContract.Effect.Navigation.ToProfile -> onNavigationRequested(
                    MainContract.Effect.Navigation.ToProfile
                )


            }
        }?.collect()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(horizontal = 30.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopAppBar(state, onEventSent)

        Box(
            Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main_illustration),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.45f) // Или TopEnd / CenterEnd — как нужно
//                        .size(400.dp) // задаём "исходный" размер
//                        .scale(1.5f) // масштабируем
//                        .offset(x = 20.dp) // подвинуть чуть-чуть, если нужно
                )
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) { // Синий цвет для "В"
                            append("Экономьте\nкак ")

                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { // Синий цвет для "В"
                            append("_")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) { // Синий цвет для "В"
                            append("\nникогда\nраньше ") // Остальной текст

                        }
                    },
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 60.sp,
                )
                Text(
                    text = "Быстро рассчитайте нужные и максимально выгодные тарифы. Прощай волокита! \uD83D\uDC4B",
                    softWrap = true,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.BottomStart),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }

        }
        Spacer(Modifier.fillMaxHeight(0.2f))

        Button(
            onClick = { onEventSent(MainContract.Event.ToSendButtonClicked) },
            Modifier
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.3f)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "Произвести расчёт",
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 18.sp
            )
        }
        Spacer(Modifier.fillMaxHeight(0.1f))
        Box(Modifier
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth(0.6f)
            .fillMaxHeight(0.5f)
            .clickable { }, contentAlignment = Alignment.Center
        ) {
            Text(
                "Загрузить шаблоны",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }


    }


}