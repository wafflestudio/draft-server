package com.wafflestudio.draft.dto.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserEmailResponse {
    @NonNull
    private String email;
}
