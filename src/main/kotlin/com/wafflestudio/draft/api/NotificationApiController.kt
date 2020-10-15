package com.wafflestudio.draft.api

import com.wafflestudio.draft.service.FCMService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ExecutionException

@RestController
@RequestMapping("/api/v1/noti")
class NotificationApiController(private val fcmService: FCMService) {
    @GetMapping(value = ["/test/"])
    @Throws(InterruptedException::class, ExecutionException::class)
    fun testNotification() {
        fcmService.send("TEST_REGION", "title_test", "body_test")
    } // FIXME: Remove this temporary endpoint and make others
}
