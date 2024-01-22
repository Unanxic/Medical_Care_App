package com.example.medicalcareapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.ui.theme.TeaGreen

@Composable
fun TopBar(
    header: String
) {
    val height = 50.dp
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(TeaGreen),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = header,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

    }
}