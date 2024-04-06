package com.example.medicalcareapp.screens.account_settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.locale.Language
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.extesions.onClickWithScaleAnimation
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.Olivine
import com.example.medicalcareapp.ui.theme.TeaGreen
import com.example.medicalcareapp.utilities.LanguageHelper

@Composable
fun ChangeLanguageScreen(
    navController: NavController,
) {
    val context = LocalContext.current

    val languageOptions = createLanguageOptions()

    var selectedLanguage by remember(languageOptions) { mutableStateOf(languageOptions.find { it.language == LanguageHelper.getLanguage() }) }

    var isNavigationInProgress by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Olivine),
    ) {
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(50.dp)
                .background(TeaGreen)
        )
        Image(
            painter = painterResource(id = R.drawable.rectangle_green_rounded),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
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
            tint = DarkJungleGreen
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, bottom = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.choose_your_language),
                color = DarkJungleGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.select_your_preferred_language_to_use),
                color = DarkJungleGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.language),
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(104.dp))
            languageOptions.forEach { option ->
                ClickableRowWithIcon(
                    option = option,
                    isSelected = option == selectedLanguage,
                    onClick = {
                        selectedLanguage = option
                        LanguageHelper.setSelectedLanguage(context, option.language)
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}

@Composable
fun ClickableRowWithIcon(
    option: LanguageOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .onClickWithScaleAnimation(scaleFactor = 0.99f) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Honeydew
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = option.flagDrawableId),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = option.label,
                color = DarkJungleGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(visible = isSelected) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_check),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun createLanguageOptions() = listOf(
    LanguageOption(
        label = stringResource(R.string.english_language),
        flagDrawableId = R.drawable.english,
        language = Language.ENGLISH
    ),
    LanguageOption(
        label = stringResource(R.string.greek_language),
        flagDrawableId = R.drawable.greek,
        language = Language.GREEK
    )
)

data class LanguageOption(
    val label: String,
    val flagDrawableId: Int,
    val language: Language
)