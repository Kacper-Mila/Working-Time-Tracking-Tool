package com.project.w3t.service;

import com.project.w3t.model.user.UserSecurity;
import com.project.w3t.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUserId(username)
                .map(UserSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found."));
    }
}
