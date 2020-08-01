package com.wafflestudio.draft.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckUsernameRequest {
    @NotNull
    private String username;
}
