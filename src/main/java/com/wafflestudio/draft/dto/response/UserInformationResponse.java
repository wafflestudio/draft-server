package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.User;
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
    private String profileImage;

    public UserInformationResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }
}
