package com.example.petheavennotify.handler

import com.example.petheavennotify.model.*
import java.text.SimpleDateFormat
import java.util.*

class MessageHandler {
    fun textHandler(notifyVo: NotifyVo, option: String): TextMessage {
        return TextMessage(text = "${notifyVo.userName}成功${option}${notifyVo.amount}份${notifyVo.equipmentName}")
    }

    fun flexHandler(notifyVo: NotifyVo, option: String): FlexMessage {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")

        val heroImage = HeroImage(url = notifyVo.equipmentUrl)
        val body = Box(
            contents = mutableListOf(
                Separator(),
                Text(
                    text = notifyVo.equipmentName,
                    weight = "bold",
                    size = "xl",
                    align = "center",
                    margin = "lg"
                ),
                Box(
                    margin = "lg",
                    spacing = "sm",
                    contents = mutableListOf(
                        Box(
                            layout = "baseline",
                            spacing = "sm",
                            contents = mutableListOf(
                                Text(text = "會員名稱", color = "#aaaaaa", size = "sm", flex = 2),
                                Text(text = notifyVo.userName, wrap = true, size = "sm", flex = 5)
                            )
                        ),
                        Box(
                            layout = "baseline",
                            spacing = "sm",
                            contents = mutableListOf(
                                Text(text = "交易數量", color = "#aaaaaa", size = "sm", flex = 2),
                                Text(text = notifyVo.amount.toString(), wrap = true, size = "sm", flex = 5)
                            )
                        ),
                        Box(
                            layout = "baseline",
                            spacing = "sm",
                            contents = mutableListOf(
                                Text(text = "交易金額", color = "#aaaaaa", size = "sm", flex = 2),
                                Text(text = "$" + notifyVo.cost.toString(), wrap = true, size = "sm", flex = 5)
                            )
                        ),
                        Box(
                            layout = "baseline",
                            spacing = "sm",
                            contents = mutableListOf(
                                Text(text = "交易時間", color = "#aaaaaa", size = "sm", flex = 2),
                                Text(
                                    text = dateFormat.format(notifyVo.transactionTime),
                                    wrap = true,
                                    size = "sm",
                                    flex = 5
                                )
                            )
                        )
                    )
                )
            )
        )

        val bubble = Bubble(hero = heroImage, body = body)
        return FlexMessage(
            altText = "${notifyVo.userName}成功${option}${notifyVo.amount}份${notifyVo.equipmentName}",
            contents = bubble
        )
    }
}