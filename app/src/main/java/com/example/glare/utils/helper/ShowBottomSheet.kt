package com.example.glare.utils.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glare.ui.theme.primaryBackgroundColor
import com.example.glare.utils.Size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(onDismissRequest: () -> Unit, sheetState: SheetState) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest, sheetState = sheetState,
        containerColor = primaryBackgroundColor,
        dragHandle = {
            Column {
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(38.75.dp)
                        .height(6.25.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = Color(0xff2a2d39)
                        )
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.pdMd)
                .padding(vertical = Size.pdMd)
        ) {
            Text("Glare", style = TextStyle(fontSize = 17.75.sp))
            Text("v1.0.0", style = TextStyle(fontSize = 14.sp))

            Spacer(Modifier.height(10.dp))

            Text("Made with❣️ by Nikhil T", style = TextStyle(fontSize = 14.sp))
        }

    }
}