package com.wordrace.service;

import com.wordrace.model.User;
import com.wordrace.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = getUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),null);
    }

    private User getUserByUsername(final String username){
        return userRepository.findUserByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
