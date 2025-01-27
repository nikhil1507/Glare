package com.example.glare.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.glare.utils.Size


@Composable
fun MoonEventLabel(event: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Size.pdMd + 4.75.dp, vertical = Size.pdMd + 2.25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            event,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = Size.fontMd,
                color = Color(0xFF777779),
                textAlign = TextAlign.Start
            )
        )
        Text(
            time,
            style = TextStyle(fontSize = Size.fontMd, textAlign = TextAlign.End),
            modifier = Modifier
                .padding(start = Size.pdSm - 2.75.dp)
                .weight(1f)
        )
    }
}