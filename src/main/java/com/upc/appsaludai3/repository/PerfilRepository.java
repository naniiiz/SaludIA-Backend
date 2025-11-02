package com.upc.appsaludai3.repository;

import com.upc.appsaludai3.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil,Long> {
    List<Perfil> findByNombreContaining(String palabra);

}