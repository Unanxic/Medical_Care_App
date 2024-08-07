package com.example.domain.utils

import com.google.firebase.auth.FirebaseUser

fun GenericFlowState<FirebaseUser>.isLoggedIn(): Boolean {
    return if (this is FlowSuccess) !this.data?.email.isNullOrBlank()
    else false
}