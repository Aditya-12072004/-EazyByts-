package com.meeting_point.controller;

import com.meeting_point.model.User;
import com.meeting_point.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()") // Sirf authenticated users hi list dekh sakte hain
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}