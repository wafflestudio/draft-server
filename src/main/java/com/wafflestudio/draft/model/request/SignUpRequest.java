package com.wafflestudio.draft.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignUpRequest extends AuthenticationRequest {
    private String username;

    // TODO: More information about region, preference... should be added
}
