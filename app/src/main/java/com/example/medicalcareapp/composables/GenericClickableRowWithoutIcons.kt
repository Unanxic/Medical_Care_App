package com.example.medicalcareapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.extesions.capitalizeWords
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun GenericClickableRowWithoutIcons(
    text: String = "",
    status: String = "Skipped",
) {
    val capitalizedText = text.capitalizeWords()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
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
            Column {
                Text(
                    text = capitalizedText,
                    color = EerieBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = status,
                    color = EerieBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
//            Image(
//                painter = painterResource(id = R.drawable.arrow_right),
//                contentDescription = "",
//                modifier = Modifier.size(24.dp),
//                colorFilter = ColorFilter.tint(SmokyBlack)
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGenericClickableRowWithoutIcons() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SmokyBlack)
            .padding(17.dp),
    ) {
        GenericClickableRowWithoutIcons(
            text = "Aspirin",
            status = "Taken at 08:00 AM",
        )
    }
}