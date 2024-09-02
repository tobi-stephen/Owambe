package org.lamlam.owambe.service;


import java.util.List;

import org.lamlam.owambe.models.User;
import org.lamlam.owambe.models.UserInfo;
import org.lamlam.owambe.models.VendorInfo;
import org.lamlam.owambe.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    public UserInfo getUserProfile(Authentication authentication) {
        log.info("User profile check");
        User user = (User) authentication.getPrincipal();
        return UserInfo
            .builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phoneNumber(user.getPhoneNumber())
            .address(user.getAddress())
            .role(user.getRole())
            .build();
    }

    public UserInfo updateUserProfile(Authentication authentication, UserInfo userInfo) {
        log.info("User profile update");
        User user = (User) authentication.getPrincipal();

        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setAddress(userInfo.getAddress());
        userRepository.save(user);

        userInfo.setEmail(user.getEmail());
        userInfo.setRole(user.getRole());
        userInfo.setId(user.getId());

        return userInfo;
    }

    public VendorInfo getUserVendorProfile(Authentication authentication, Long id) {
        log.info("Vendor profile check");
        User user = userRepository.findById(id).orElseThrow();
        return VendorInfo
            .builder()
            .id(user.getId())
            .vendorName(user.getVendorName())
            .vendorDescription(user.getVendorDescription())
            .instagramUrl(user.getInstagramUrl())
            .serviceType(user.getServiceType())
            .build();
    }

    public VendorInfo updateUserVendorProfile(Authentication authentication, VendorInfo vendorInfo) {
        log.info("Vendor profile update");
        User user = (User) authentication.getPrincipal();
        user.setVendor(true);
        user.setVendorName(vendorInfo.getVendorName());
        user.setVendorDescription(vendorInfo.getVendorDescription());
        user.setInstagramUrl(vendorInfo.getInstagramUrl());
        user.setServiceType(vendorInfo.getServiceType());

        userRepository.save(user);

        vendorInfo.setId(user.getId());
        return vendorInfo;
    }

    public List<VendorInfo> searchVendors(String query, int page, int size) {
        log.info("Search vendors");
        
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        Pageable paging = PageRequest.of(page-1, size);
        
        List<VendorInfo> vendors = userRepository
            .findByVendorNameContainingIgnoreCaseOrServiceTypeContainingIgnoreCase(query, query, paging).map(user -> {
                return VendorInfo
                    .builder()
                    .id(user.getId())
                    .vendorName(user.getVendorName())
                    .vendorDescription(user.getVendorDescription())
                    .instagramUrl(user.getInstagramUrl())
                    .serviceType(user.getServiceType())
                    .build();
            }).toList();


        return vendors;
    }
}
