package com.example.domain.models.account

enum class ContinuationFlow {
    DeleteAccount
}

sealed class UserAccountUseCaseEvents {
    object ReAuthenticate : UserAccountUseCaseEvents()
    class ReAuthenticatedDone(val successful: Boolean, val continuationFlow: ContinuationFlow? = null) :
        UserAccountUseCaseEvents()
}