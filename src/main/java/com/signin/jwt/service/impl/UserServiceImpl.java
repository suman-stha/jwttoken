package com.signin.jwt.service.impl;


import com.signin.jwt.entities.AuditLog;
import com.signin.jwt.entities.User;
import com.signin.jwt.repository.AuditLogRepository;
import com.signin.jwt.repository.UserRepository;
import com.signin.jwt.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final AuditLogRepository auditLogRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void deleteUser(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        String adminEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        userRepository.deleteById(id);

        AuditLog auditLog = new AuditLog();
        auditLog.setAction("DELETE_USER");
        auditLog.setTargetUserId(id);
        auditLog.setPerformedBy(adminEmail);

        auditLogRepository.save(auditLog);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUser() {
        List<User> allUser = null;
        try {
            log.info("✅ fetching from db");
            allUser = userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUser;
    }

    public Optional<User> getUserById(Integer id){
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!")));

    }

    @Cacheable
    public List<User> getAllUsers(){
        log.info("✅Fetching users from db: ");
        return userRepository.findAll();
    }
}
