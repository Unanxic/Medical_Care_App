package com.example.medicalcareapp.composables.alert_dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
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

enum class DeleteDialogState {
    NONE,
    DELETE,
    DELETE_CONTACT
}

@Composable
fun DeleteDialogState(
    showDialog: DeleteDialogState,
    closeDialog: () -> Unit,
    onDelete: () -> Unit
) {
    when (showDialog) {
        DeleteDialogState.DELETE -> DeleteDialog(closeDialog, onDelete)
        DeleteDialogState.NONE -> return
        DeleteDialogState.DELETE_CONTACT -> DeleteContactDialog(closeDialog, onDelete)
    }
}

@Composable
fun DeleteDialog(
    closeDialog: () -> Unit,
    onDelete: () -> Unit
) {
    GenericAlertDialog(
        visibility = true,
        title = stringResource(R.string.delete_medication),
        text = stringResource(R.string.are_you_sure_you_want_to_delete_this_medication),
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
                        text = stringResource(R.string.cancel_uppercase),
                        fontSize = 17.sp
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    closeDialog()
                },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmokyBlack,
                    contentColor = Color.White
                ),
                content = {
                    Text(
                        text = stringResource(R.string.delete_uppercase),
                        fontSize = 17.sp
                    )
                }
            )
        }
    )
}

@Composable
fun DeleteContactDialog(
    closeDialog: () -> Unit,
    onDelete: () -> Unit
) {
    GenericAlertDialog(
        visibility = true,
        title = stringResource(R.string.delete_contact),
        text = stringResource(R.string.are_you_sure_you_want_to_delete_this_contact),
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
                        text = stringResource(R.string.cancel_uppercase),
                        fontSize = 17.sp
                    )
                }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    closeDialog()
                },
                modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmokyBlack,
                    contentColor = Color.White
                ),
                content = {
                    Text(
                        text = stringResource(R.string.delete_uppercase),
                        fontSize = 17.sp
                    )
                }
            )
        }
    )
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
        title = stringResource(R.string.incorrect_credentials),
        text = stringResource(R.string.you_have_submitted_the_incorrect_credentials),
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
                        text = stringResource(R.string.return_uppercase),
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
        title = stringResource(R.string.something_went_wrong),
        text = stringResource(R.string.please_try_again),
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
                        text = stringResource(R.string.return_uppercase),
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
        title = stringResource(R.string.logout_uppercase),
        text = stringResource(R.string.are_you_sure_you_want_to_log_out),
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
                        text = stringResource(R.string.cancel),
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
                        text = stringResource(R.string.logout_uppercase),
                        fontSize = 17.sp
                    )
                }
            )
        }
    )
}