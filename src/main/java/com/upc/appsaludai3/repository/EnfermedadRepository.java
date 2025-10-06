package com.upc.appsaludai3.repository;


import com.upc.appsaludai3.entidades.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfermedadRepository extends JpaRepository<Enfermedad,Long> {
    List<Enfermedad> findByNombreContainingIgnoreCase(String palabra);
}