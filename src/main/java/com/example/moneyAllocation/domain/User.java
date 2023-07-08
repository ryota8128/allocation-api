package com.example.moneyAllocation.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class User  {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Boolean administratorFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdministratorFlag() {
        return administratorFlag;
    }

    public void setAdministratorFlag(Boolean administratorFlag) {
        this.administratorFlag = administratorFlag;
    }
}
