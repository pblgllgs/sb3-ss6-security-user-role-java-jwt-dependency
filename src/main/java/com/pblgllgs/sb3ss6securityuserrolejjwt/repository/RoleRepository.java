package com.pblgllgs.sb3ss6securityuserrolejjwt.repository;

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String roleName);

}
