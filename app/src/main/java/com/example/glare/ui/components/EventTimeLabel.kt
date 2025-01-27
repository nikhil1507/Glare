package com.example.glare.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glare.utils.Size

@Composable
fun EventTimeLabel(event: String, time: String, timeColor: Color = Color(0xffffffff)) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Size.pdMd + 0.75.dp, vertical = Size.pdMd - 1.25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            event,
            style = TextStyle(fontSize = Size.fontMd)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                time,
                style = TextStyle(
                    fontWeight = FontWeight.W600,
                    fontSize = Size.fontLg
                ),
                color = timeColor,
            )
            Text(
                "DST",
                style = TextStyle(color = timeColor, fontSize = 10.75.sp)
            )
        }
    }

}