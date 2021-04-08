package edu.bbardisoftwaredesign.bookstore.user.repository;

import edu.bbardisoftwaredesign.bookstore.user.model.ERole;
import edu.bbardisoftwaredesign.bookstore.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
