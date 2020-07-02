package com.wafflestudio.draft.api;

import com.wafflestudio.draft.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class DeviceApiController {
    DeviceService deviceService;

    @PostMapping
    public void setDevice(@RequestBody String deviceToken){
    }
}
