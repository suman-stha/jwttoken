package com.signin.jwt.service;

import com.signin.jwt.dto.JwtAuthenticationResponse;
import com.signin.jwt.dto.SignInRequest;
import com.signin.jwt.dto.SignUpRequest;
import com.signin.jwt.entities.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
}

