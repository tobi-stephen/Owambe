package org.lamlam.owambe.api;

import java.util.List;

import org.lamlam.owambe.models.User;
import org.lamlam.owambe.models.VendorInfo;
import org.lamlam.owambe.models.VendorProject;
import org.lamlam.owambe.service.VendorProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/vendor-projects")
@RestController
@RequiredArgsConstructor
public class VendorProjectApi {

    final private VendorProjectService vendorProjectService;

    @GetMapping("/{id}")
    public ResponseEntity<VendorProject> getVendorProject(@PathVariable Long id) {
        return ResponseEntity.ok(vendorProjectService.getVendorProject(id));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorProject>> getVendorProjectsPaginated(
        @PathVariable Long vendorId,
        @RequestParam(defaultValue="1") int page,
        @RequestParam(defaultValue="10") int size) {
        return ResponseEntity.ok(vendorProjectService.getVendorProjectsPaginated(vendorId, page, size));
    }

    @PostMapping
    public ResponseEntity<VendorProject> createOrUpdateVendorProject(@Valid @RequestBody VendorProject vendorProject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(vendorProjectService.createOrUpdateVendorProject(authentication, vendorProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVendorProject(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        vendorProjectService.deleteVendorProject(authentication, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/s")
    public ResponseEntity<List<VendorProject>> searchVendorProjects(
        @RequestParam String query,
        @RequestParam(defaultValue="1") int page,
        @RequestParam(defaultValue="10") int size) {
        return ResponseEntity.ok(vendorProjectService.searchVendorProjects(query, page, size));
    }
}
