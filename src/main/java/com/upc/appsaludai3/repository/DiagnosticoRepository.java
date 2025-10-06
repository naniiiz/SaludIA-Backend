package com.upc.appsaludai3.repository;


import com.upc.appsaludai3.entidades.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico,Long> {
    List<Diagnostico> findByUsuario_Id(Long idUsuario);

}