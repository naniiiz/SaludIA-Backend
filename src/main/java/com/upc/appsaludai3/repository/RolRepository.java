package com.upc.appsaludai3.repository;

import com.upc.appsaludai3.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Rol findByRol(String rol);
}
