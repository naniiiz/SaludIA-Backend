package com.upc.appsaludai3.interfaces;


import com.upc.appsaludai3.dtos.UsuarioGeneralDTO;
import com.upc.appsaludai3.entidades.UsuarioGeneral;

import java.util.List;

public interface IUsuarioGeneralServices {
    UsuarioGeneralDTO registrar(UsuarioGeneralDTO usuarioDTO);
    UsuarioGeneral actualizar(UsuarioGeneral usuario);
    void borrar(Long id);
    List<UsuarioGeneralDTO> findAll();
    UsuarioGeneral findById(Long id);
    List<UsuarioGeneralDTO> buscarPorNombre(String palabra);

}
