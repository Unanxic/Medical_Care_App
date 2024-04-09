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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.capitalizeWords
import com.example.medicalcareapp.extesions.onClickWithScaleAnimation
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.SmokyBlack

enum class IconType(@DrawableRes val resourceId: Int) {
    INHALER(
        resourceId = R.drawable.inhaler
    ),
    PILL(
        resourceId = R.drawable.pill
    ),
    SOLUTION(
        resourceId = R.drawable.solution
    ),
    DROPS(
        resourceId = R.drawable.drops
    ),
    INJECTION(
        resourceId = R.drawable.injection_icon
    ),
    OTHER(
        resourceId = R.drawable.other
    )
}

@Composable
fun GenericClickableRowWithIcons(
    icon: IconType,
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
            containerColor = AliceBlue
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
                painter = painterResource(id = icon.resourceId),
                contentDescription = "type of medicine icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {

                Text(
                    text = capitalizedText,
                    color = EerieBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = capitalizedSecondaryText,
                    color = EerieBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(SmokyBlack)
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
            .background(SmokyBlack)
            .padding(17.dp),
        contentAlignment = Alignment.Center
    ) {
        GenericClickableRowWithIcons(
            icon = IconType.DROPS,
            text = "test",
            secondaryText = "inhaler",
            onClick = { /* Example click action */ }
        )
    }
}