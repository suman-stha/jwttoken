package com.signin.jwt.service.impl;


import com.signin.jwt.SignUpException;
import com.signin.jwt.dto.JwtAuthenticationResponse;
import com.signin.jwt.dto.SignInRequest;
import com.signin.jwt.dto.SignUpRequest;
import com.signin.jwt.entities.Role;
import com.signin.jwt.entities.User;
import com.signin.jwt.repository.UserRepository;
import com.signin.jwt.service.AuthenticationService;
import com.signin.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    private final JwtService jwtService;
    public User signUp(SignUpRequest signUpRequest){

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new SignUpException("Email already exists");
        }
        User user =new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

       return userRepository.save(user);
    }


    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest ){
        log.info("Inside the signIn request.....!");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()
                    )
            );

        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage(), e);
            throw e;
        }

      var user=  userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        var jwt=jwtService.generateToken(user);
        var refreshToken=jwtService.generateFreshToken(new HashMap<>(), user    );

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        log.info("JwtAuthenticationResponse is created.....!");
        return jwtAuthenticationResponse;
    }
}
