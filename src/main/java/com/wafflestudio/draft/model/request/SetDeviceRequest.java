package com.wafflestudio.draft.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SetDeviceRequest {
    @NotNull
    private String email;
    @NotNull
    private String deviceToken;
}
