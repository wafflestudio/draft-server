package com.wafflestudio.draft.security.password;

import com.wafflestudio.draft.model.User;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;

public class UserPrincipal extends User implements UserDetails {

    public UserPrincipal(User user) {
        super(user.getUsername(), user.getEmail());
        super.setId(user.getId());
        super.setPassword(user.getPassword());
        super.setRoles(user.getRoles());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
