package com.example.data.database.json_initialization

import org.json.JSONObject

interface JsonInitializable {
    fun initializeFromJson(jsonObject: JSONObject)
}