package com.example.petheavennotify.handler

import com.example.petheavennotify.model.BroadcastMessage
import com.example.petheavennotify.model.NotifyVo
import com.fasterxml.jackson.databind.ObjectMapper
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class LineHandler : CommandLineRunner {
    @Value("\${line.access_token}")
    val lineAccessToken: String = ""

    private fun broadcastPush(data: ByteArray) {
        val messageList = msgHandler(data)
        val broadcastMessage = BroadcastMessage(messageList)

        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper.writeValueAsString(broadcastMessage)

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.line.me/v2/bot/message/broadcast"))
            .headers("Content-Type", "application/json", "Authorization", "Bearer $lineAccessToken")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
    }

    fun msgHandler(data: ByteArray): MutableList<Any> {
        val objectMapper = ObjectMapper()
        val notifyVo: NotifyVo = objectMapper.readValue(data, NotifyVo::class.java)

        var option = ""
        if (notifyVo.option == "buy") option = "購買"
        if (notifyVo.option == "sell") option = "販售"

        val messageHandler = MessageHandler()
        val text = messageHandler.textHandler(notifyVo, option)
        val flex = messageHandler.flexHandler(notifyVo, option)

        return mutableListOf(text, flex)
    }


    override fun run(vararg args: String?) {
//        val nc = Nats.connect()
        val nc = Nats.connect("nats://message-queue:4222")
        val dispatcher = nc.createDispatcher {
            val title = it.subject.split("com.petheaven.")[1]
            if (title == "line") {
                broadcastPush(it.data)
            }
            println("Received Message [${String(it.data)}] from [${it.subject}]")
        }
        dispatcher.subscribe("com.petheaven.*")
    }
}