package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Device;
import lombok.Data;

@Data
public class DeviceResponse {
    private Long id;
    private String deviceToken;
    private Long userId;
    private String username;

    public DeviceResponse(Device device) {
        this.id = device.getId();
        this.deviceToken = device.getDeviceToken();
        this.userId = device.getUser().getId();
        this.username = device.getUser().getUsername();
    }
}
