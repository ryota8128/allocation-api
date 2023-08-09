package com.example.moneyAllocation.domain;


public class AccountSelector {
    public AccountSelector(Long ownerId, Long id, String name) {
        this.ownerId = ownerId;
        this.id = id;
        this.name = name;
    }

    public AccountSelector() {
    }

    private Long ownerId;

    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
