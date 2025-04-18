package com.example.send.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.send.R

@Composable
fun SendButton(onClick: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val sizes: List<Float> = listOf(1f, 0.85f, 0.7f)
        val colorFactors: List<Float> = listOf(0.05f, 0.1f, 0.13f)
//        val colors: List<Color> = listOf(Color.Red, Color.Green, Color.Blue)
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val baseSize = min(maxWidth, maxHeight)

            val offset = baseSize * 0.1f

            Box(Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .clickable { onClick() }) {

                Box(modifier = Modifier.fillMaxSize()) {
                    sizes.forEachIndexed { index, sizeFactor ->
                        val circleSize = baseSize * sizeFactor
                        Box(
                            modifier = Modifier
                                .size(circleSize)
                                .background(
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = colorFactors[index]),
                                    CircleShape
                                )
                                .align(Alignment.Center)
                                .offset(
                                    x = if (index == 0) 0.dp else offset,
                                    y = if (index == 0) 0.dp else offset
                                ),
                        ) {
                        }
                    }
                }
                Image(
                    painter = painterResource(R.drawable.upload), "",
                    modifier = Modifier
                        .fillMaxSize(0.2f)
                        .align(
                            Alignment.Center
                        ),
                )
                Box(
                    Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Нажмите, что бы выбрать файл",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(0.7f),
                            lineHeight = 12.sp
                        )
                        Text(
                            ".xlsx, .xls",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
                        )
                    }
                }
            }

        }
    }
}