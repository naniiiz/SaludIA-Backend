package com.upc.appsaludai3.interfaces;

import com.upc.appsaludai3.dtos.RolDTO;
import com.upc.appsaludai3.entidades.Rol;

import java.util.List;

public interface IRolServices {
    RolDTO registrar(RolDTO rolDTO);
    List<RolDTO> findAll();
    Rol findById(Long id);
    Rol actualizar(Rol rol);
    void borrar(Long id);
    RolDTO findByRol(String rol);
}
