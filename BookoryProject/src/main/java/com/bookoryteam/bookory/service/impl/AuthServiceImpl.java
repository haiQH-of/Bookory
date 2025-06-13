package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.repository.UserRepository;
import com.bookoryteam.bookory.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return rawPassword.equals(user.getPasswordHash());
        }
        return false;
    }

    @Override
    public void registerUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Tên đăng nhập đã tồn tại.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(username + "@bookory.com");
        newUser.setPasswordHash(rawPassword);
        newUser.setDisplayName(username);
        newUser.setRole("USER");
        newUser.setIsActive(true);
        newUser.setDeleted(false);

        userRepository.save(newUser);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}