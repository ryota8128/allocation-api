package com.example.moneyAllocation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    private Long id;
    private String name;
    private Integer numFreeTransfer;
    private Integer transferFee;
    private Long ownerId;

    public Integer getNumFreeTransfer() {
        return numFreeTransfer;
    }

    public void setNumFreeTransfer(Integer numFreeTransfer) {
        this.numFreeTransfer = numFreeTransfer;
    }

    public Integer getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Integer transferFee) {
        this.transferFee = transferFee;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
