package com.upc.appsaludai3.repository;

import com.upc.appsaludai3.entidades.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SintomaRepository extends JpaRepository<Sintoma,Long> {
    List<Sintoma> findByNombreContainingIgnoreCase(String palabra);

}