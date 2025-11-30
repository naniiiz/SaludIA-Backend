package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.UbicacionDTO;
import com.upc.appsaludai3.entidades.Ubicacion;
import com.upc.appsaludai3.interfaces.IUbicacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class UbicacionController {
    @Autowired
    private IUbicacionServices ubicacionService;

    // CREATE
    @PostMapping("/ubicaciones")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UbicacionDTO> registrar(@RequestBody UbicacionDTO ubicacionDTO) {
        return ResponseEntity.ok(ubicacionService.registrar(ubicacionDTO));
    }

    // READ ALL
    // Importante: Permitir listar ubicaciones para desplegables o info de usuario
    @GetMapping("/ubicaciones")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UbicacionDTO>> listar() {
        return ResponseEntity.ok(ubicacionService.findAll());
    }

    // READ BY ID
    @GetMapping("/ubicaciones/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ubicacion> buscarPorId(@PathVariable Long id) {
        Ubicacion ubicacion = ubicacionService.findById(id);
        if (ubicacion != null) {
            return ResponseEntity.ok(ubicacion);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE
    @PutMapping("/ubicaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Ubicacion> actualizar(@PathVariable Long id,
                                                @RequestBody Ubicacion ubicacion) {
        ubicacion.setId(id);
        Ubicacion actualizado = ubicacionService.actualizar(ubicacion);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/ubicaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        ubicacionService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}