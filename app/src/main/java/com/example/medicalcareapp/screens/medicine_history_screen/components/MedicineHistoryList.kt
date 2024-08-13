package com.example.medicalcareapp.screens.medicine_history_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.composables.GenericClickableRowWithIcons
import com.example.medicalcareapp.extesions.IconType

@Composable
fun MedicineHistoryList(
    modifier: Modifier = Modifier,
    items: List<Pair<IconType, String>>,
    onItemClick: (String) -> Unit
) {
    Box{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 17.dp),
        ) {
            item {
                Spacer(Modifier.height(18.dp))
            }
            itemsIndexed(items) { index, item ->
                val (iconType, text) = item
                GenericClickableRowWithIcons(
                    icon = iconType,
                    text = text,
                    onClick = { onItemClick(text) }
                )
                if (index < items.size - 1) {
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}