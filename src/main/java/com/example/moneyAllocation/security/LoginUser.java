package com.example.moneyAllocation.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
    private Long id;
    private String username;
    private boolean administratorFlag;

    public LoginUser(Long id, String username, String password, boolean administratorFlag, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.administratorFlag = administratorFlag;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdministratorFlag() {
        return administratorFlag;
    }

    public void setAdministratorFlag(boolean administratorFlag) {
        this.administratorFlag = administratorFlag;
    }
}
