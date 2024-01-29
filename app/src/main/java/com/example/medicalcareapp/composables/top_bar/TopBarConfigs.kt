package com.example.medicalcareapp.composables.top_bar

data class TopBarConfigs(
    val topBarTitle: String = "",
    val topBarLayout: TopBarLayouts = TopBarLayouts.NONE,
    val leftActionClick: (() -> Unit)? = null
)
