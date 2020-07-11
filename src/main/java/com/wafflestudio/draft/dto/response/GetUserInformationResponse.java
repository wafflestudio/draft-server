package com.wafflestudio.draft.dto.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetUserInformationResponse {
    @NonNull
    private String email;
}
