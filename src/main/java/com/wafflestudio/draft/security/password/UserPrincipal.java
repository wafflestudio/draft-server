package com.wafflestudio.draft.security.password;

import com.wafflestudio.draft.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal extends User implements UserDetails {

    public UserPrincipal(User user) {
        super(user.getUsername(), user.getEmail());
        super.setId(user.getId());
        super.setPassword(user.getPassword());
        super.setRoles(user.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (super.getRoles() == null) return new ArrayList<>();

        List<String> roles = Arrays.asList(super.getRoles().split(","));
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
