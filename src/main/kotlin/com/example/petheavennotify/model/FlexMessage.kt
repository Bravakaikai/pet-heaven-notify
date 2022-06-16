package com.example.petheavennotify.model

import com.fasterxml.jackson.annotation.JsonInclude

data class FlexMessage(
    val type: String = "flex",
    var altText: String,
    val contents: Bubble
)

data class Bubble(
    val type: String = "bubble",
    val hero: HeroImage,
    val body: Box
)

data class HeroImage(
    val type: String = "image",
    val url: String,
    val size: String = "full",
    val aspectRatio: String = "20:13",
    val aspectMode: String = "fit"
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Box(
    val type: String = "box",
    val layout: String = "vertical",
    val margin: String? = null,
    val spacing: String? = null,
    val contents: List<Any>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Text(
    val type: String = "text",
    val text: String,
    val wrap: Boolean = false,
    val color: String = "#666666",
    val weight: String? = null,
    val size: String,
    val align: String? = null,
    val margin: String? = null,
    val flex: Int? = null
)

data class Separator(
    val type: String = "separator"
)