package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.DiagnosticoDTO;
import com.upc.appsaludai3.entidades.Diagnostico;
import com.upc.appsaludai3.interfaces.IDiagnosticoServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api")
public class DiagnosticoController {
    @Autowired
    private IDiagnosticoServices diagnosticoService;
    @Transactional
    @PostMapping("/diagnosticos")
    @PreAuthorize("hasRole('ADMIN')")
    public DiagnosticoDTO registrar(@RequestBody DiagnosticoDTO diagnosticoDTO) {
        return diagnosticoService.registrar(diagnosticoDTO);
    }

    @GetMapping("/diagnosticos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DiagnosticoDTO> findAll() {
        return diagnosticoService.findAll();
    }

    @GetMapping("/diagnosticos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Diagnostico findById(@PathVariable Long id) {
        return diagnosticoService.findById(id);
    }

    @PutMapping("/diagnosticos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Diagnostico actualizar(@RequestBody Diagnostico diagnostico) {
        return diagnosticoService.actualizar(diagnostico);
    }

    @DeleteMapping("/diagnosticos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void borrar(@PathVariable Long id) {
        diagnosticoService.borrar(id);
    }

    @GetMapping("/diagnosticos/usuario/{idPerfil}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DiagnosticoDTO> buscarPorPerfil(@PathVariable Long idPerfil) {
        return diagnosticoService.buscarPorPerfil(idPerfil);
    }


}