package com.example.moneyAllocation.security;

import com.example.moneyAllocation.domain.User;
import com.example.moneyAllocation.domain.UserSelector;
import com.example.moneyAllocation.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSelector selector = new UserSelector();
        selector.setUsername(username);
        User user;
        try {
            user = userRepository.find(selector);
        } catch(Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                user.getAdministratorFlag());
        return new LoginUserDetails(loginUser, determineRoles(user.getAdministratorFlag()));
    }

    private List<GrantedAuthority> determineRoles(boolean isAdmin) {
        return isAdmin ? UserRole.ADMIN.getGrantedAuthority() : UserRole.USER.getGrantedAuthority();
    }
}
