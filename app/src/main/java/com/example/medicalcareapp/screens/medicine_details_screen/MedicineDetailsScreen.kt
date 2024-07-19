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
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.JetStream
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack


@Composable
fun MedicineDetailsScreen(
    navController: NavController,
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 45.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.inhaler),
                contentDescription = "inhaler",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Aerolin",
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            ColoredCard()
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonComponent(
                    onClick = {
                        //todo
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
                        //todo
                    }
                )
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
}

@Composable
fun ColoredCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(314.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue
        ),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 45.dp),
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
                text = "asthma",
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
                text = "inhaler",
                color = EerieBlack,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = stringResource(R.string.allergy),
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.yes_no),
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

