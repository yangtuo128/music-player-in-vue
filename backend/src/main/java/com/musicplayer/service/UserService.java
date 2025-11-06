package com.musicplayer.service;

import com.musicplayer.entity.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> login(String phone, String password);
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User user);
}