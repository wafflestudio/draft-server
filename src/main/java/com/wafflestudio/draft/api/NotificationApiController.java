package com.wafflestudio.draft.api;

import com.wafflestudio.draft.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noti")
public class NotificationApiController {

    private final FCMService fcmService;

    @GetMapping(value = "/test/")
    public void testNotification() throws InterruptedException, ExecutionException {
        fcmService.send("TEST_REGION", "title_test", "body_test");
    }
    // FIXME: Remove this temporary endpoint and make others

}

