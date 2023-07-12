package com.example.moneyAllocation.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserDetails implements UserDetails {

    private final LoginUser loginUser;

    private final Collection<? extends GrantedAuthority> authorities;

    public LoginUserDetails(LoginUser loginUser, Collection<? extends GrantedAuthority> authorities) {
        this.loginUser = loginUser;
        this.authorities = authorities;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginUser.password();
    }

    @Override
    public String getUsername() {
        return loginUser.username();
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