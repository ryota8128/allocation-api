package com.example.moneyAllocation.util;

import com.example.moneyAllocation.domain.User;

public class UnitTestUtil {
    public static User createTestUser(boolean isAdmin) {
        User user = new User();
        user.setUsername("test-user");
        user.setEmail("test@example.xx.xx");
        user.setPassword("test-encoded-password");
        user.setAdministratorFlag(isAdmin);
        return user;
    }

}