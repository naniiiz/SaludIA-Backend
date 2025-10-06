package com.upc.appsaludai3.repository;

import com.upc.appsaludai3.entidades.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion,Long> {

}
