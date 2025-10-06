package com.upc.appsaludai3.interfaces;


import com.upc.appsaludai3.dtos.EnfermedadDTO;
import com.upc.appsaludai3.entidades.Enfermedad;

import java.util.List;

public interface IEnfermedadServices {
    EnfermedadDTO registrar(EnfermedadDTO enfermedadDTO);
    List<EnfermedadDTO> findAll();
    Enfermedad findById(Long id);
    Enfermedad actualizar(Enfermedad enfermedad);
    void borrar(Long id);
    List<EnfermedadDTO> buscarPorNombre(String palabra);
}