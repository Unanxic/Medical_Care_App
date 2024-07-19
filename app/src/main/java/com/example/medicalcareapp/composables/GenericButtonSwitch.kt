package com.example.medicalcareapp.composables

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.extesions.getDefaultTweenAnimation
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.BlackOlive
import com.example.medicalcareapp.ui.theme.JetStream
import com.example.medicalcareapp.ui.theme.MSUGreen

@Composable
fun GenericButtonSwitch(
    text: String,
    height: Dp = 35.dp,
    width: Dp = 90.dp,
    textFontSize: TextUnit = 13.sp,
    initialBackgroundColor: Color = JetStream,
    initialTextColor: Color = BlackOlive,
    textOpacity: Float = 1f,
    cornerShape: Shape = RoundedCornerShape(25.dp),
    isSelected: Boolean = false,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
) {

    var backgroundColor by remember { mutableStateOf(initialBackgroundColor) }
    var hasBeenClicked by remember { mutableStateOf(false) }

    if (isSelected) {
        backgroundColor = MSUGreen
        hasBeenClicked = true
    } else {
        backgroundColor = initialBackgroundColor
        hasBeenClicked = false
    }

    Row(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color = backgroundColor, shape = cornerShape)
            .setNoRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedText(
            text = text,
            textFontSize = textFontSize,
            textOpacity = textOpacity,
            hasBeenClicked = hasBeenClicked,
            initialTextColor = initialTextColor,
            fontWeight = fontWeight
        )
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun AnimatedText(
    text: String = " ",
    textFontSize: TextUnit = 15.sp,
    textOpacity: Float,
    initialTextColor: Color,
    hasBeenClicked: Boolean,
    fontWeight: FontWeight = FontWeight.Normal
) {
    val targetTextColor = if (hasBeenClicked) Color.White else initialTextColor

    val textColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = getDefaultTweenAnimation(), label = "",
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Crossfade(
            targetState = hasBeenClicked,
            animationSpec = getDefaultTweenAnimation(),
            label = ""
        ) { _ ->
            Text(
                text = text,
                color = textColor,
                fontWeight = fontWeight,
                fontSize = textFontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .alpha(textOpacity)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCombattButtonSwitch() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        var selectedIndex by remember { mutableStateOf(-1) } // Track the index of the selected button

        Row {
            GenericButtonSwitch(
                text = "Doctor",
                onClick = {
                    // Update the selected index
                    selectedIndex = 0
                },
                isSelected = selectedIndex == 0 // Pass whether this button is selected
            )
            Spacer(modifier = Modifier.width(20.dp))
            GenericButtonSwitch(
                text = "Pharmacy",
                onClick = {
                    // Update the selected index
                    selectedIndex = 1
                },
                isSelected = selectedIndex == 1 // Pass whether this button is selected
            )
        }
    }
}