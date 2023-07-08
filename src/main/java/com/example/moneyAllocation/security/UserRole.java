package com.example.moneyAllocation.security;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public enum UserRole {
    ADMIN() {
        @Override
        public List<GrantedAuthority> getGrantedAuthority() {
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        }
    },
    USER() {
        @Override
        public List<GrantedAuthority> getGrantedAuthority() {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }
    };

    public abstract List<GrantedAuthority> getGrantedAuthority();
}
