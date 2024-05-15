package com.example.medicalcareapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.JetStream
import com.example.medicalcareapp.ui.theme.LightSilver
import com.example.medicalcareapp.ui.theme.SilverFoil


@Composable
fun ButtonComponent(
    modifier: Modifier,
    text: String,
    isFilled: Boolean = false,
    isDisabled: Boolean = false,
    isBold: Boolean = false,
    fontSize: TextUnit = 18.sp,
    contentColorChoice: Color = Color.White,
    disabledContentColor: Color = SilverFoil,
    backgroundColorChoice: Color = SilverFoil,
    fillColorChoice: Color = JetStream,
    cornerRadius: Int = 50,
    onClick: () -> Unit
) {
    val contentColor =
        if (isFilled && !isDisabled) contentColorChoice
        else LightSilver
    val borderColor =
        if (isDisabled) disabledContentColor
        else fillColorChoice
    val backgroundColor = if (isFilled) {
        if (isDisabled) backgroundColorChoice else fillColorChoice
    } else {
        Color.Transparent
    }
    Column(
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius.dp))
            .background(backgroundColor, RoundedCornerShape(cornerRadius.dp))
            .fillMaxSize()
            .setNoRippleClickable { if (!isDisabled) onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {
        Text(
            text = text,
            fontSize = fontSize,
            letterSpacing = 0.34.sp,
            lineHeight = 17.sp,
            color = contentColor,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}