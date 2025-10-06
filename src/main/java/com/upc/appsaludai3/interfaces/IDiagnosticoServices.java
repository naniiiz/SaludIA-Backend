package com.upc.appsaludai3.interfaces;


import com.upc.appsaludai3.dtos.DiagnosticoDTO;
import com.upc.appsaludai3.entidades.Diagnostico;

import java.util.List;

public interface IDiagnosticoServices {
    Diagnostico findById(Long id);
    DiagnosticoDTO registrar(DiagnosticoDTO diagnosticoDTO);
    void borrar(Long id);
    List<DiagnosticoDTO> findAll();
    Diagnostico actualizar(Diagnostico diagnostico);
    List<DiagnosticoDTO> buscarPorUsuario(Long idUsuario);
}