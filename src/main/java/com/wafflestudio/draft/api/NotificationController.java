package com.wafflestudio.draft.api;

import java.util.concurrent.ExecutionException;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.wafflestudio.draft.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/push")
public class NotificationController {

    private final FCMService fcmService;

    @GetMapping(value = "/test/")
    public void send() throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setNotification(new Notification(
                        "title_test",
                        "body_test"))
                .setTopic("TEST_REGION")
                .build();
        fcmService.send(message);
    }
    // FIXME: Remove this temporary endpoint and make others

}

