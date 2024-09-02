package org.lamlam.owambe.service;


import java.util.List;
import java.util.Objects;

import org.lamlam.owambe.models.User;
import org.lamlam.owambe.models.VendorProject;
import org.lamlam.owambe.repository.VendorProjectRepository;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorProjectService {

    final private VendorProjectRepository vendorProjectRepository;

    public VendorProject getVendorProject(Long id) {
        log.info("Get vendor project");
        VendorProject vendorProject = vendorProjectRepository.findById(id).orElseThrow();

        return vendorProject;
    }

    public List<VendorProject> getVendorProjectsPaginated(Long vendorId, int page, int size) {
        log.info("Get vendor projects paginated");
        
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        Pageable paging = PageRequest.of(page-1, size, Sort.by("updatedAt").descending());

        List<VendorProject> vendorProjects = vendorProjectRepository.findByUserId(vendorId, paging).toList();

        return vendorProjects;
    }

    public VendorProject createOrUpdateVendorProject(Authentication authentication, VendorProject vendorProject) {
        log.info("Vendor profile Create/Update");
        User user = (User) authentication.getPrincipal();

        // Verify if project exists and is owned by authenticated user
        if (vendorProject.getId() > 0) {
            if (!vendorProjectRepository.existsById(vendorProject.getId())) {
                throw new RuntimeException(); // TODO: throw better error with details
            }
            
            VendorProject existingProject = vendorProjectRepository.findById(vendorProject.getId()).get();
            if (!Objects.equals(existingProject.getUserId(), user.getId())) {
                throw new RuntimeException(); // TODO: throw better error with details
            }
        }
        vendorProject.setUserId(user.getId());
        vendorProject = vendorProjectRepository.save(vendorProject);

        return vendorProject;
    }

    public void deleteVendorProject(Authentication authentication, Long id) {
        log.info("Delete vendor project");
        User user = (User) authentication.getPrincipal();

        VendorProject vendorProject = vendorProjectRepository.findById(id).orElseThrow();
        if (!Objects.equals(vendorProject.getUserId(), user.getId())) {
            throw new RuntimeException(); // TODO: throw better error with details
        }

        vendorProjectRepository.deleteById(id);
    }

    public List<VendorProject> searchVendorProjects(String query, int page, int size) {
        log.info("Search Vendor Projects");
        
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        Pageable paging = PageRequest.of(page-1, size, Sort.by("updatedAt").descending());

        List<VendorProject> vendorProjects = vendorProjectRepository.findByTitleContainingIgnoreCaseOrDescriptionContaining(query, query, paging).toList();

        return vendorProjects;
    }
}