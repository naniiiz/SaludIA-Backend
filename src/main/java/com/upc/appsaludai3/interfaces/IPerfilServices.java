package com.upc.appsaludai3.interfaces;


import com.upc.appsaludai3.dtos.PerfilDTO;
import com.upc.appsaludai3.entidades.Perfil;

import java.util.List;

public interface IPerfilServices {
    PerfilDTO registrar(PerfilDTO perfilDTO);
    Perfil actualizar(Perfil perfil);
    void borrar(Long id);
    List<PerfilDTO> findAll();
    Perfil findById(Long id);
    List<PerfilDTO> buscarPorNombre(String palabra);

}
