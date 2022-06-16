package com.example.petheavennotify.model

data class TextMessage(
    val type: String = "text",
    var text: String
)