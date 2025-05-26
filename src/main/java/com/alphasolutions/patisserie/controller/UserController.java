package com.alphasolutions.patisserie.controller;

import com.alphasolutions.patisserie.model.entities.User;
import com.alphasolutions.patisserie.model.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String phone = loginRequest.get("phone");
        String password = loginRequest.get("password");

        User user = authenticateUser(phone, password);
        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Número de telefone ou senha inválidos");
            return ResponseEntity.status(401).body(errorResponse);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("user", Map.of(
                "phone", user.getPhoneNumber(),
                "name", user.getUsername()
        ));
        return ResponseEntity.ok(response);
    }

    private User authenticateUser(String phone, String password) {
        User user = userRepository.findUserByPhoneNumber(phone);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}