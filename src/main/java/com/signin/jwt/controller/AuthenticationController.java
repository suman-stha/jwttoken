package com.signin.jwt.controller;


import com.signin.jwt.dto.JwtAuthenticationResponse;
import com.signin.jwt.dto.SignInRequest;
import com.signin.jwt.dto.SignUpRequest;
import com.signin.jwt.entities.User;
import com.signin.jwt.service.AuthenticationService;
import com.signin.jwt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody  SignUpRequest signUpRequest){
        log.info("Request reached...");

        User user = authenticationService.signUp(signUpRequest);
        log.info("Sign Up successfully....!");
        return ResponseEntity.ok(user);

    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest signInRequest){
        log.info("Signin reached for email: {}", signInRequest.getEmail());
        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
        log.info("Signed In successfully...!");
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
