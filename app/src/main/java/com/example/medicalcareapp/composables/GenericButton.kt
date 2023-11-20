package com.example.medicalcareapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.Axolotl
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.ui.theme.TeaGreen


@Composable
fun ButtonComponent(
    modifier: Modifier,
    text: String,
    isFilled: Boolean = false,
    isDisabled: Boolean = false,
    isBold: Boolean = false,
    fontSize: TextUnit = 18.sp,
    contentColorChoice: Color = Color.White,
    disabledContentColor: Color = TeaGreen,
    backgroundColorChoice: Color = TeaGreen,
    fillColorChoice: Color = Axolotl,
    onClick: () -> Unit
) {
    val contentColor =
        if (isFilled && !isDisabled) contentColorChoice
        else Color.White
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
            .border(1.dp, borderColor, CircleShape)
            .background(backgroundColor, CircleShape)
            .padding(12.dp)
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

@Composable
@Preview
fun OutlinedTextPreview() {
    ButtonComponent(
        text = "Outlined Text",
        isFilled = false,
        isDisabled = false,
        modifier = Modifier
            .height(65.dp)
            .width(313.dp),
        onClick = {}
    )
}

@Composable
@Preview
fun FilledTextPreview() {
    ButtonComponent(
        text = "Filled Text",
        isFilled = true,
        isDisabled = false,
        modifier = Modifier
            .height(65.dp)
            .width(313.dp),
        onClick = {}
    )
}

@Composable
@Preview
fun DisabledOutlinedTextPreview() {
    ButtonComponent(
        text = "Disabled Outlined Text",
        modifier = Modifier
            .height(65.dp)
            .width(313.dp),
        onClick = {},
        isFilled = true,
        fontSize = 20.sp,
        contentColorChoice = SmokyBlack,
        fillColorChoice = TeaGreen
    )
}

@Composable
@Preview
fun DisabledFilledTextPreview() {
    ButtonComponent(
        text = "Disabled Filled Text",
        isFilled = true,
        isDisabled = true,
        modifier = Modifier
            .height(65.dp)
            .width(313.dp),
        onClick = {}
    )
}