package org.lamlam.owambe.repository;

import org.lamlam.owambe.models.VendorProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProjectRepository extends JpaRepository<VendorProject, Long>{
    Page<VendorProject> findByUserId(Long userId, Pageable paging);
    Page<VendorProject> findByTitleContainingIgnoreCaseOrDescriptionContaining(String title, String description, Pageable pageable);
}
