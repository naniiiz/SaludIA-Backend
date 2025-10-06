package com.upc.appsaludai3.repository;

import com.upc.appsaludai3.entidades.UsuarioGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioGeneralRepository extends JpaRepository<UsuarioGeneral,Long> {
    List<UsuarioGeneral> findByNombreContaining(String palabra);

}