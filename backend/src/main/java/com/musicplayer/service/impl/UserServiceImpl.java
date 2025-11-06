package com.musicplayer.service.impl;

import com.musicplayer.entity.User;
import com.musicplayer.repository.UserRepository;
import com.musicplayer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("Phone number already registered");
        }
        // 这里应该对密码进行加密处理，示例中为了简单直接存储明文
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String phone, String password) {
        Optional<User> userOptional = userRepository.findByPhone(phone);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (!existingUserOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        User existingUser = existingUserOptional.get();
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            existingUser.setGender(user.getGender());
        }

        return userRepository.save(existingUser);
    }
}