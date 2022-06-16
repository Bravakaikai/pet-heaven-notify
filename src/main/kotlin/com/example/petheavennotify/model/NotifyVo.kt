package com.example.petheavennotify.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class NotifyVo (
    @JsonProperty("option")
    val option: String,
    @JsonProperty("userName")
    val userName: String,
    @JsonProperty("equipmentName")
    val equipmentName: String,
    @JsonProperty("equipmentUrl")
    val equipmentUrl: String,
    @JsonProperty("amount")
    val amount: Int,
    @JsonProperty("cost")
    val cost: Int,
    @JsonProperty("transactionTime")
    val transactionTime: Date = Calendar.getInstance().time,
)