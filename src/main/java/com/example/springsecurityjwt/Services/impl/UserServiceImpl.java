package com.example.springsecurityjwt.Services.impl;

import java.util.Optional;

import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Repositories.UserRepository;
import com.example.springsecurityjwt.Services.UserService;
// import com.example.springsecurityjwt.Entities.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;
    @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
    
}
