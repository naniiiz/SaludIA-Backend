package com.upc.appsaludai3.interfaces;

import com.upc.appsaludai3.dtos.UbicacionDTO;
import com.upc.appsaludai3.entidades.Ubicacion;

import java.util.List;

public interface IUbicacionServices {
    UbicacionDTO registrar(UbicacionDTO ubicacionDTO);
    List<UbicacionDTO> findAll();
    Ubicacion findById(Long id);
    Ubicacion actualizar(Ubicacion ubicacion);
    void borrar(Long id);
}
