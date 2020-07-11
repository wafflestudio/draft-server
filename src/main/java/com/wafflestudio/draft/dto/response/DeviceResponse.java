package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Device;
import lombok.Data;

@Data
public class DeviceResponse {
    private Long id;
    private String deviceToken;
    private String email;

    public DeviceResponse(Device device) {
        this.id = device.getId();
        this.deviceToken = device.getDeviceToken();
        this.email = device.getUser().getEmail();
    }
}