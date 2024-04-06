package com.example.medicalcareapp.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.capitalizeWords
import com.example.medicalcareapp.extesions.onClickWithScaleAnimation
import com.example.medicalcareapp.ui.theme.Axolotl
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew


@Composable
fun GenericClickableRowWithIcons(
    @DrawableRes icon: Int,
    text: String = "",
    secondaryText: String = "",
    onClick: () -> Unit
) {

    val capitalizedText = text.capitalizeWords()
    val capitalizedSecondaryText = secondaryText.capitalizeWords()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .onClickWithScaleAnimation(scaleFactor = 0.99f) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Honeydew
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "type of medicine icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {

                Text(
                    text = capitalizedText,
                    color = DarkJungleGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = capitalizedSecondaryText,
                    color = DarkJungleGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericClickableRowWithIcons() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Axolotl)
            .padding(17.dp),
        contentAlignment = Alignment.Center
    ) {
        GenericClickableRowWithIcons(
            icon = R.drawable.injection_icon,
            text = "test",
            secondaryText = "inhaler",
            onClick = { /* Example click action */ }
        )
    }
}