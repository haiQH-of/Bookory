package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.User;
import com.bookoryteam.bookory.repository.UserRepository;
import com.bookoryteam.bookory.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	


    private final UserRepository userRepository;
    
	@Override
	public int countUsers() {
	    return (int) userRepository.count();
	}


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findByIsActiveTrue() {
        return userRepository.findByIsActiveTrue();
    }

    @Override
    public void softDelete(Long id) {
        // Tìm User theo ID
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleted(true); // Đặt cờ deleted thành true
            userRepository.save(user); // Lưu lại User đã cập nhật
        }
        // Có thể ném ngoại lệ nếu không tìm thấy User
    }
}