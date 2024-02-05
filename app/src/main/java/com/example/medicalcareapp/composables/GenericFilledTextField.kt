package com.example.medicalcareapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.SoldierGreen
import com.example.medicalcareapp.ui.theme.TeaGreen
import kotlinx.coroutines.delay

@Composable
fun GenericFilledTextField(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    titleTextSize: TextUnit = 18.sp,
    isTitleBold: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    updateText: (String) -> Unit,
    isError: MutableState<Boolean> = mutableStateOf(false),
    singleLine: Boolean = true,
    isErrorTextField: Boolean = false,
    showTrailingIcon: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {
    val isVisible by remember { mutableStateOf(keyboardType != KeyboardType.Password) }

    LaunchedEffect(isError.value) {
        delay(4000)
        isError.value = false
    }

    Column(
        modifier = modifier
            .height(50.dp)
            .shadow(
                elevation = 30.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .clip(MaterialTheme.shapes.medium)
            .background(TeaGreen)
    ) {
        if (title.isNotBlank()) {
            TextFieldLabel(
                title = title,
                titleTextSize = titleTextSize,
                isTitleBold = isTitleBold
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BaseFilledTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                textState = value,
                updateText = {
                    updateText(it)
                },
                isVisible = isVisible,
                keyboardType = keyboardType,
                singleLine = singleLine,
                showTrailingIcon = showTrailingIcon,
                imeAction = imeAction
            )
        }
    }
}

@Composable
private fun TextFieldLabel(
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
private fun BaseFilledTextField(
    modifier: Modifier,
    textState: String,
    isVisible: Boolean,
    updateText: (String) -> Unit,
    keyboardType: KeyboardType,
    singleLine: Boolean,
    showTrailingIcon: Boolean = false,
    imeAction: ImeAction = ImeAction.Next
) {

    var passwordVisibility by remember { mutableStateOf(isVisible) }

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = textState,
        onValueChange = { updateText(it) },
        textStyle = TextStyle(
            color = SoldierGreen,
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = singleLine,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier,
        cursorBrush = SolidColor(SoldierGreen),
    ){ innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = textState,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            innerTextField = innerTextField,
            singleLine = singleLine,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(bottom = 6.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = if (showTrailingIcon) {
                {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .setNoRippleClickable {
                                passwordVisibility = !passwordVisibility
                            }
                    ) {
                        val eyeIcon = if (passwordVisibility) {
                            R.drawable.mdi_eye_on
                        } else {
                            R.drawable.mdi_eye
                        }
                        Image(
                            painter = painterResource(id = eyeIcon),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(DarkJungleGreen)
                        )
                    }
                }
            } else null
        )
    }
}
