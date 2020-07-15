package com.wafflestudio.draft.dto.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserInformationResponse {
    @NonNull
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String email;
}
