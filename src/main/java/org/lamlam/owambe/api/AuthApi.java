package org.lamlam.owambe.api;

import org.lamlam.owambe.models.AuthRequest;
import org.lamlam.owambe.models.AuthResponse;
import org.lamlam.owambe.models.UserRegistration;
import org.lamlam.owambe.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthApi {
    
    final private AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserRegistration userRegistration) {
        return ResponseEntity.ok(authService.registerUser(userRegistration));
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateUser(authRequest));
    }
}
