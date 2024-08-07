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
    SOMETHING_WENT_WRONG,
}

enum class LogoutDialogState {
    NONE,
    LOGOUT
}

@Composable
fun AlertDialogs(
    showDialog: DialogState,
    closeDialog: () -> Unit
) {
    when (showDialog) {
        DialogState.NONE -> return
        DialogState.INVALID_LOGIN -> WrongCredentialsDialog(closeDialog)
        DialogState.SOMETHING_WENT_WRONG -> SomethingWentWrongDialog(closeDialog)
    }
}

@Composable
fun LogoutDialogState(
    showDialog: LogoutDialogState,
    closeDialog: () -> Unit,
    onLogout: () -> Unit
) {
    when (showDialog) {
        LogoutDialogState.LOGOUT -> LogoutDialog(closeDialog, onLogout)
        LogoutDialogState.NONE -> return
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

@Composable
fun SomethingWentWrongDialog(
    closeDialog: () -> Unit
) {
    GenericAlertDialog(
        visibility = true,
        title = "Something went wrong",
        text = "Please try again",
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

@Composable
fun LogoutDialog(
    closeDialog: () -> Unit,
    onLogout: () -> Unit
) {
    GenericAlertDialog(
        visibility = true,
        title = "Logout",
        text = "Are you sure you want to log out?",
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
                        text = "Cancel",
                        fontSize = 17.sp
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onLogout()
                    closeDialog()
                },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmokyBlack,
                    contentColor = Color.White
                ),
                content = {
                    Text(
                        text = "Logout",
                        fontSize = 17.sp
                    )
                }
            )
        }
    )
}