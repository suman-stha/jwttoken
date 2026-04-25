package com.signin.jwt.service;

import com.signin.jwt.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    // No additional methods needed; just inherit from UserDetailsService
    UserDetails loadUserByUsername(String username);

    void deleteUser(Integer id);

   List<User>  getAllUser();

   Optional<User> getUserById(Integer id);
}
