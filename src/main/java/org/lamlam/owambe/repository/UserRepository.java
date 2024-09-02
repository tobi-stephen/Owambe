package org.lamlam.owambe.repository;

import java.util.Optional;

import org.lamlam.owambe.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laplace zen
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    Page<User> findByVendorNameContainingIgnoreCaseOrServiceTypeContainingIgnoreCase(String vendorName, String serviceType, Pageable pageable);
}
