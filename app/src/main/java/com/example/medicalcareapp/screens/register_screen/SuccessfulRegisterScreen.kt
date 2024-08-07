package com.example.medicalcareapp.screens.register_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.PewterBlue

@Composable
fun SuccessfulRegisterScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PewterBlue),
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.success_creation),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )
        Text(
            text = stringResource(R.string.registration_was_successful),
            color = EerieBlack,
            modifier = Modifier.padding(horizontal = 15.dp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Clip,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.LastLineBottom
                )
            ),
        )
        Spacer(modifier = Modifier.height(50.dp))
        ButtonComponent(
            onClick = {
                navController.navigate(Screens.Home.route){
                    popUpTo(0)
                }
            },
            modifier = Modifier
                .height(60.dp)
                .width(250.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.let_s_begin),
            isFilled = true,
            fontSize = 20.sp,
            cornerRadius = 20,
            fillColorChoice = MSUGreen,
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.waves),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MSUGreen),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.waves),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MSUGreen),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .graphicsLayer(rotationZ = 180f)
        )
    }
}
}

@Preview(showBackground = true)
@Composable
fun SuccessfulRegisterScreenPreview() {
    SuccessfulRegisterScreen(navController = rememberNavController())
}