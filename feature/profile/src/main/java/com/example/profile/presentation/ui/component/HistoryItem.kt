package com.example.profile.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profile.R
import com.example.profile.presentation.model.HistoryReport

@Composable
fun HistoryItem(report: HistoryReport) {
    Row(
        Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(R.drawable.excel), "excel icon", Modifier.fillMaxHeight())
        Row(verticalAlignment = Alignment.Bottom) {
            Spacer(Modifier.fillMaxWidth(0.05f))
            Column(Modifier.weight(1f)) {
                Text(
                    report.fileName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(report.size, color = MaterialTheme.colorScheme.onPrimary)

            }
            Row(Modifier.fillMaxHeight()) {
                Text(report.timestamp, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))

            }
        }
    }
}