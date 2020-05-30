package com.wafflestudio.draft.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.wafflestudio.draft.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Async
    public void send(Region targetRegion, String title, String body) throws InterruptedException, ExecutionException {
        send(targetRegion.getName(), title, body);
    }

    @Async
    public void send(String topic, String title, String body) throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setNotification(new Notification(title, body))
                .setTopic(topic)
                .build();
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("Sent message: " + response);
    }

}
