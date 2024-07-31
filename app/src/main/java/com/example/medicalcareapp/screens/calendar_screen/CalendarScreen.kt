package com.example.medicalcareapp.screens.calendar_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicalcareapp.screens.calendar_screen.composables.DatesHeader
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun CalendarScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatesHeader(onDateSelected = { _ ->
                // Handle the selected date
                // For example, you might want to navigate to another screen or update some state
                // navController.navigate("some_route/${selectedDate.date}")
            })
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MedicationBreakCard()
            }
        }
    }

}
@Composable
fun MedicationBreakCard(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue,
            contentColor = SmokyBlack
        ),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Medication Break",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = "No medications scheduled for this date. Take a break and relax.",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
