package com.wafflestudio.draft.dto.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ParticipantsResponse {
    @NonNull
    private List<UserInformationResponse> team1;
    @NonNull
    private List<UserInformationResponse> team2;
}
