package com.zephyr.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser {

    private String username;
    private String password;
    private List<String> permissionValueList;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
