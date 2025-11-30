package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.DiagnosticoDTO;
import com.upc.appsaludai3.entidades.Diagnostico;
import com.upc.appsaludai3.interfaces.IDiagnosticoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class DiagnosticoController {

    @Autowired
    private IDiagnosticoServices diagnosticoService;

    // 1. REGISTRAR: Permitir a cualquier usuario autenticado
    @Transactional
    @PostMapping("/diagnosticos")
    @PreAuthorize("isAuthenticated()")
    public DiagnosticoDTO registrar(@RequestBody DiagnosticoDTO diagnosticoDTO) {
        return diagnosticoService.registrar(diagnosticoDTO);
    }

    // 2. LISTAR TODOS: Solo ADMIN (o USER si quieres que vean todo, pero mejor restringir)
    @GetMapping("/diagnosticos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<DiagnosticoDTO> findAll() {
        return diagnosticoService.findAll();
    }

    // 3. BUSCAR POR ID
    @GetMapping("/diagnosticos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Diagnostico findById(@PathVariable Long id) {
        return diagnosticoService.findById(id);
    }

    // 4. ACTUALIZAR
    @Transactional
    @PutMapping("/diagnosticos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Diagnostico actualizar(@RequestBody Diagnostico diagnostico) {
        return diagnosticoService.actualizar(diagnostico);
    }

    // 5. BORRAR
    @Transactional
    @DeleteMapping("/diagnosticos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public void borrar(@PathVariable Long id) {
        diagnosticoService.borrar(id);
    }

    // --- ¡AQUÍ ESTÁ LA CORRECCIÓN CLAVE! ---
    // Antes quizás tenías 'hasRole(ADMIN)'.
    // Lo cambiamos a 'isAuthenticated()' para que tu usuario nuevo (ROLE_USER) pueda entrar.
    @GetMapping("/diagnosticos/usuario/{idPerfil}")
    @PreAuthorize("isAuthenticated()")
    public List<DiagnosticoDTO> buscarPorPerfil(@PathVariable Long idPerfil) {
        System.out.println("✅ Solicitud de historial recibida en Controller.");
        return diagnosticoService.buscarPorPerfil(idPerfil);
    }
}