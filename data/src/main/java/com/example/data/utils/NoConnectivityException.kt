package com.example.data.utils

import okio.IOException

class NoConnectivityException: IOException() {
    override val message: String
        get() = "No connectivity exception"
}