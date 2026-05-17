package com.signin.jwt.controller;


import com.signin.jwt.entities.User;
import com.signin.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/alluser")
    public ResponseEntity<List<User>> getAllUser(){

        log.info("Request reached ...!");
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id   ){
        Optional<User> userById = userService.getUserById(id);
        return ResponseEntity.ok(userById);

    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }
}
