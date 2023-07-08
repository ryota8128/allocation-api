package com.example.moneyAllocation.security;


public record LoginUser(Long id, String username, String email, String password, boolean administratorFlag){
}
