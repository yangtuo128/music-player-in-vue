package com.musicplayer.controller;

import com.musicplayer.entity.User;
import com.musicplayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Phone number already registered", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> loggedInUser = userService.login(user.getPhone(), user.getPassword());
        if (loggedInUser.isPresent()) {
            return new ResponseEntity<>(loggedInUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid phone number or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}