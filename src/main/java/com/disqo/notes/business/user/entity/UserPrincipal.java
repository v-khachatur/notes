package com.disqo.notes.business.user.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal extends User {

    private static final long serialVersionUID = 1L;

    private Long userId;

    public UserPrincipal(Long userId, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

}
