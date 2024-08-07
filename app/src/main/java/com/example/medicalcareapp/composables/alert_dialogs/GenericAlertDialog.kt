package com.example.medicalcareapp.composables.alert_dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun GenericAlertDialog(
    visibility: Boolean,
    title: String,
    text: String,
    onClose: () -> Unit,
    middleButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (() -> Unit)? = null,
    buttons: @Composable () -> Unit = {},
    textFontSize: TextUnit? = null,
    cornerRadius: Dp = 10.dp,
    backgroundColor: Color = HookersGreen, // Set default background color here
    offset: IntOffset = IntOffset(0, 0)
) {


    if (visibility) {
        Dialog(
            onDismissRequest = {
                onClose()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { onClose() }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(backgroundColor, RoundedCornerShape(cornerRadius))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (title.isNotEmpty()) {
                            Text(
                                text = title,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Text(
                            text = text,
                            color = Color.White,
                            fontSize = textFontSize ?: 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            buttons()
                            middleButton?.invoke()
                            confirmButton?.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CustomAlertDialogPreview() {
    GenericAlertDialog(
        visibility = true,
        title = "Custom Dialog Title",
        text = "This is the custom dialog message.",
        onClose = {},
        middleButton = {
            Button(
                onClick = {},
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = SmokyBlack
                ),
                content = {
                    Text(text = "Retry")
                }
            )
        },
    )
}

@Composable
@Preview
fun CustomAlertDialogPreviewTwo() {
    GenericAlertDialog(
        visibility = true,
        title = "",
        text = "This is the custom dialog message, more info.",
        onClose = {}
    )
}