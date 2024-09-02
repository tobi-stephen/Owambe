package org.lamlam.owambe.api;

import org.lamlam.owambe.models.UserInfo;
import org.lamlam.owambe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserApi {
    
    final private UserService userService;
    
    @GetMapping
    public ResponseEntity<UserInfo> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getUserProfile(authentication));
    }

    @PostMapping
    public ResponseEntity<UserInfo> updateUserProfile(@Valid @RequestBody UserInfo userInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.updateUserProfile(authentication, userInfo));
    }
}
