package dev.andrea.jobify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.andrea.jobify.DTOs.UserDTO;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.services.UserService;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(new UserDTO(newUser));
    }
}