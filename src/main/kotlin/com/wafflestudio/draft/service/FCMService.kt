package com.wafflestudio.draft.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.wafflestudio.draft.model.Region
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

@Service
class FCMService {
    @Async
    @Throws(InterruptedException::class, ExecutionException::class)
    fun send(targetRegion: Region, title: String?, body: String?) {
        send(targetRegion.name, title, body)
    }

    @Async
    @Throws(InterruptedException::class, ExecutionException::class)
    fun send(topic: String?, title: String?, body: String?) {
        val message = Message.builder()
                .setNotification(Notification(title, body))
                .setTopic(topic)
                .build()
        val response = FirebaseMessaging.getInstance().sendAsync(message).get()
        logger.info("Sent message: $response")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FCMService::class.java)
    }
}
