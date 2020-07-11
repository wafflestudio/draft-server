package com.wafflestudio.draft.model.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class GetUserInformationResponse {
    @NonNull
    @NotBlank
    private String email;
}
