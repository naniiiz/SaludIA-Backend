package com.upc.appsaludai3.interfaces;

import com.upc.appsaludai3.dtos.SintomaDTO;
import com.upc.appsaludai3.entidades.Sintoma;

import java.util.List;

public interface ISintomaServices {
    SintomaDTO registrar(SintomaDTO sintomaDTO);
    List<SintomaDTO> findAll();
    Sintoma findById(Long id);
    Sintoma actualizar(Sintoma sintoma);
    void borrar(Long id);
    List<SintomaDTO> buscarPorNombre(String palabra);
}