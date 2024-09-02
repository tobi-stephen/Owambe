package org.lamlam.owambe.service;


import org.lamlam.owambe.models.AuthRequest;
import org.lamlam.owambe.models.AuthResponse;
import org.lamlam.owambe.models.User;
import org.lamlam.owambe.models.UserRegistration;
import org.lamlam.owambe.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    final private UserRepository userRepository;
    final private PasswordEncoder encoder;
    final private JwtService jwtService;
    final private AuthenticationManager authenticationManager;

    public AuthResponse registerUser(UserRegistration userRegistration) {
        // Encode password before saving the user
        log.info("User registration");
        User user = User
            .builder()
            .firstName(userRegistration.getFirstName())
            .lastName(userRegistration.getLastName())
            .email(userRegistration.getEmail())
            .phoneNumber(userRegistration.getPhoneNumber())
            .password(encoder.encode(userRegistration.getPassword()))
            .address(userRegistration.getAddress())
            .role(userRegistration.getRole())
            .build();

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(accessToken).build();
    }

    public AuthResponse authenticateUser(AuthRequest input) {
        log.info("User login");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow();

        String accessToken = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(accessToken).build();
    }

}