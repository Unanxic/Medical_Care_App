package com.example.medicalcareapp.screens.medicine_details_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.alert_dialogs.DeleteDialogState
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.extesions.IconType
import com.example.medicalcareapp.extesions.getIconType
import com.example.medicalcareapp.extesions.getLocalizedName
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.screens.medicine_history_screen.viewmodels.MedicationViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.JetStream
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.compose.koinInject


@Composable
fun MedicineDetailsScreen(
    navController: NavController,
    medicationId: String,
    viewModel: MedicationViewModel = koinInject(),
) {

    LaunchedEffect(medicationId) {
        viewModel.loadMedicationById(medicationId)
    }

    val medication by viewModel.currentMedication.collectAsState()
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var deleteDialogState by remember { mutableStateOf(DeleteDialogState.NONE) }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    if (!isNavigationInProgress) {
                        isNavigationInProgress = true
                        navController.popBackStack()
                    }
                },
            tint = EerieBlack
        )
        medication.let { med ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 45.dp)
            ) {
                val iconType = getIconType(med.formOfMedicine)
                ImageFunction(iconType)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = med.medication,
                    color = EerieBlack,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                ColoredCard(med.condition, med.formOfMedicine)
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ButtonComponent(
                        onClick = {
                            deleteDialogState = DeleteDialogState.DELETE
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(150.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = CircleShape
                            ),
                        text = stringResource(R.string.delete),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 20,
                        fillColorChoice = JetStream,
                        contentColorChoice = SmokyBlack
                    )
                    Spacer(modifier = Modifier.width(19.dp))
                    AddReminderImageButton(
                        modifier = Modifier.size(39.dp),
                        painter = painterResource(id = R.drawable.add_reminder),
                        contentDescription = "Add Reminder",
                        onClick = {
                            navController.medicineNavigateSingleTop("Screens.AddReminder.route/${med.medication}")
                        }
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.waves),
            contentDescription = "Waves",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MSUGreen),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
    DeleteDialogState(
        showDialog = deleteDialogState,
        closeDialog = { deleteDialogState = DeleteDialogState.NONE },
        onDelete = {
            viewModel.deleteMedication(medicationId)
            navController.popBackStack()
        }
    )
}

@Composable
fun ColoredCard(condition: String, type: String) {
    val iconType = getIconType(type)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue
        ),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.condition),
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = condition,
                color = EerieBlack,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = stringResource(R.string.type),
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = iconType.getLocalizedName(),
                color = EerieBlack,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddReminderImageButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    onClick: () -> Unit,
    colorFilter: ColorFilter? = null,
) {

    val interactionSource = remember { MutableInteractionSource() }

    var isPressed by remember { mutableStateOf(false) }
    val animateFloat by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f, label = " ")


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release,
                is PressInteraction.Cancel,
                -> isPressed = false
            }
        }
    }
    Image(
        painter = painter,
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        modifier = modifier
            .graphicsLayer {
                val scale = animateFloat
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
    )
}

@Composable
fun ImageFunction(icon: IconType) {
    Image(
        painter = painterResource(id = icon.medicationIcon),
        contentDescription = "inhaler",
        modifier = Modifier.size(50.dp)
    )
}
