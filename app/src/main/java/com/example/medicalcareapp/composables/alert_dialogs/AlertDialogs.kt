package com.example.medicalcareapp.composables.alert_dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.ui.theme.SmokyBlack

enum class DialogState {
    NONE,
    INVALID_LOGIN,
}

@Composable
fun AlertDialogs(
    showDialog: DialogState,
    closeDialog: () -> Unit
) {
    when (showDialog) {
        DialogState.NONE -> return
        DialogState.INVALID_LOGIN -> WrongCredentialsDialog(closeDialog)
    }
}

@Composable
fun WrongCredentialsDialog(
    closeDialog: () -> Unit
) {
    GenericAlertDialog(
        visibility = true,
        title = "Incorrect Credentials",
        text = "You have submitted the incorrect credentials",
        onClose = {
            closeDialog()
        },
        middleButton = {
            Button(
                onClick = {
                    closeDialog()
                },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = SmokyBlack
                ),
                content = {
                    Text(
                        text = "Return",
                        fontSize = 17.sp
                    )
                }
            )
        }
    )
}