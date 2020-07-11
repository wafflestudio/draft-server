package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.User;
import lombok.Data;

@Data
public class GetUserInformationResponse {
    private Long id;
    private String username;
    private String email;

    public GetUserInformationResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
