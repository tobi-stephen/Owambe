package org.lamlam.owambe.api;

import java.util.List;

import org.lamlam.owambe.models.VendorInfo;
import org.lamlam.owambe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/vendors")
@RestController
@RequiredArgsConstructor
public class VendorApi {

    final private UserService userService;

    @GetMapping("/v/{vendorId}")
    public ResponseEntity<VendorInfo> getUserVendorProfile(@PathVariable Long vendorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getUserVendorProfile(authentication, vendorId));
    }

    @PostMapping
    public ResponseEntity<VendorInfo> updateUserVendorProfile(@Valid @RequestBody VendorInfo vendorInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.updateUserVendorProfile(authentication, vendorInfo));
    }

    @GetMapping("/s")
    public ResponseEntity<List<VendorInfo>> searchVendors(
        @RequestParam String query,
        @RequestParam(defaultValue="1") int page,
        @RequestParam(defaultValue="10") int size) {
        return ResponseEntity.ok(userService.searchVendors(query, page, size));
    }
}
