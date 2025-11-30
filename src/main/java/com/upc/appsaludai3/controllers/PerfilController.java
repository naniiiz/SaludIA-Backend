package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.PerfilDTO;
import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.interfaces.IPerfilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class PerfilController {
    @Autowired
    private IPerfilServices perfilService;

    // CREATE - POST
    @PostMapping("/perfiles")
    @PreAuthorize("isAuthenticated()") // Permitir a cualquier logueado crear perfil si no tiene
    public ResponseEntity<PerfilDTO> registrar(@RequestBody PerfilDTO perfilDTO) {
        return ResponseEntity.ok(perfilService.registrar(perfilDTO));
    }

    // READ ALL - GET
    // CAMBIO: Usamos hasAnyAuthority para permitir que tu usuario ROLE_USER vea la lista
    @GetMapping("/perfiles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PerfilDTO>> listar() {
        return ResponseEntity.ok(perfilService.findAll());
    }

    // READ BY ID - GET
    @GetMapping("/perfiles/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
        Perfil perfil = perfilService.findById(id);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE - PUT
    // CAMBIO: Permitimos editar. El frontend envía el objeto para actualizar.
    @PutMapping("/perfiles/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Perfil> actualizar(@PathVariable Long id,
                                             @RequestBody Perfil perfil) {
        perfil.setId(id);
        Perfil actualizado = perfilService.actualizar(perfil);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/perfiles/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Borrar solo ADMIN (por seguridad)
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        perfilService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre
    @GetMapping("/perfiles/nombre/{palabra}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<PerfilDTO>> buscarPorNombre(@PathVariable String palabra) {
        return ResponseEntity.ok(perfilService.buscarPorNombre(palabra));
    }
}