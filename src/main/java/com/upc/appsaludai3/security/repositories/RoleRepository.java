package com.upc.appsaludai3.security.repositories;

import com.upc.appsaludai3.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
