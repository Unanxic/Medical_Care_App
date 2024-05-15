package com.example.medicalcareapp.screens.add_contacts_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.GenericOutlinedTextField
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun TextInputField(
    label: String = "",
    hint: String = "",
    text: String = "",
    onTextChanged: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next
) {
    Column {
        Text(
            text = label,
            color = SmokyBlack,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(6.dp))
        GenericOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = hint,
            initialValue = text,
            onValueChanged = onTextChanged,
            imeAction = imeAction,
        )
    }
}

@Composable
fun AddMoreButton(
    text: String,
    fontSize: TextUnit = 14.sp,
    onClick: () -> Unit = {},
    iconResourceId: Int
) {
    Row(
        modifier = Modifier.setNoRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResourceId),
            contentDescription = "Plus Icon"
        )
        Spacer(Modifier.width(13.dp))
        Text(
            text = text,
            fontSize = fontSize,
            color = Color.White
        )
    }
}

@Composable
fun GreenRoundedTopBar(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(HookersGreen)
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    onBackClick()
                },
            tint = SmokyBlack
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.rectangle_green_rounded),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(HookersGreen)
        )
        Column(
            modifier = Modifier.padding(horizontal = 31.dp)
        ) {
            Spacer(modifier = Modifier.height(57.dp))
            Text(
                text = stringResource(R.string.add_a_contact),
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.type_the_details_of_the_emergency_contact),
                color = EerieBlack,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ExpandableTextInputField(
    label: String,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {

    Column {
        AddMoreButton(
            text = if (expanded) "Insert another number" else "Insert another number",
            onClick = { onExpandChange(!expanded) },
            iconResourceId = if (expanded) R.drawable.collapse else R.drawable.add,
        )

        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            TextInputField(
                label = label,
                hint = hint,
                text = text,
                onTextChanged = onTextChanged,
                imeAction = imeAction
            )
        }
    }
}