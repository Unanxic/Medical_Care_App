package com.example.medicalcareapp.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.extesions.getDefaultTweenAnimation
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import kotlinx.coroutines.delay

@Composable
fun GenericTextField(
    modifier: Modifier = Modifier,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    updateText: (String) -> Unit,
    title: String = "",
    isError: MutableState<Boolean> = mutableStateOf(false),
    singleLine: Boolean = true,
    fontSize: TextUnit = 22.sp,
    titleTextSize: TextUnit = 18.sp,
    imeAction: ImeAction = ImeAction.Next,
    isTitleBold: Boolean = false,
) {
    val isVisible by remember { mutableStateOf(keyboardType != KeyboardType.Password) }

    LaunchedEffect(isError.value) {
        delay(4000)
        isError.value = false
    }

    Column(modifier = modifier.animateContentSize(getDefaultTweenAnimation())) {
        if (title.isNotBlank()) {
            TextFieldNormalLabel(
                title = title,
                titleTextSize = titleTextSize,
                isTitleBold = isTitleBold
            )
        }
        BaseTextField(
            modifier = Modifier
                .fillMaxWidth(),
            textState = value,
            updateText = {
                updateText(it)
            },
            isVisible = isVisible,
            keyboardType = keyboardType,
            singleLine = singleLine,
            imeAction = imeAction,
            fontSize = fontSize
        )
    }
}

@Composable
private fun TextFieldNormalLabel(
    modifier: Modifier = Modifier,
    title: String,
    titleTextSize: TextUnit,
    isTitleBold: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = title,
            color = Color.White,
            fontSize = titleTextSize,
            fontWeight = if (isTitleBold) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(
            modifier = Modifier
                .width(12.dp)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseTextField(
    modifier: Modifier,
    textState: String,
    isVisible: Boolean,
    updateText: (String) -> Unit,
    keyboardType: KeyboardType,
    singleLine: Boolean,
    fontSize: TextUnit,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next
) {
    val interactionSource = remember { MutableInteractionSource() }

    val indicatorColor = SmokyBlack

    BasicTextField(
        value = textState,
        onValueChange = {
            updateText(it)
        },
        textStyle = TextStyle(
            color = SmokyBlack,
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = singleLine,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier.padding(top = 8.dp),
        cursorBrush = SolidColor(SmokyBlack),
        interactionSource = interactionSource
    ) { innerTextField ->
        val focused = interactionSource.collectIsFocusedAsState().value
        TextFieldDefaults.DecorationBox(
            value = textState,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            innerTextField = innerTextField,
            singleLine = singleLine,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(bottom = 6.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = if (focused) MSUGreen else indicatorColor,
                unfocusedIndicatorColor = if (!focused && isEnabled) indicatorColor else MSUGreen
            )
        )
    }
}