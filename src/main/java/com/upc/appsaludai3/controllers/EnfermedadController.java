package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.EnfermedadDTO;
import com.upc.appsaludai3.entidades.Enfermedad;
import com.upc.appsaludai3.interfaces.IEnfermedadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api")
public class EnfermedadController {
    @Autowired
    private IEnfermedadServices enfermedadService;

    // CREATE
    @PostMapping("/enfermedades")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnfermedadDTO> registrar(@RequestBody EnfermedadDTO enfermedadDTO) {
        return ResponseEntity.ok(enfermedadService.registrar(enfermedadDTO));
    }

    // READ ALL
    @GetMapping("/enfermedades")
    @PreAuthorize("isAuthenticated()")
    public List<EnfermedadDTO> listar() {
        return enfermedadService.findAll();
    }
    // READ BY ID
    @GetMapping("/enfermedades/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Enfermedad> buscarPorId(@PathVariable Long id) {
        Enfermedad enfermedad = enfermedadService.findById(id);
        if (enfermedad != null) {
            return ResponseEntity.ok(enfermedad);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE
    @PutMapping("/enfermedades/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Enfermedad> actualizar(@PathVariable Long id,
                                                 @RequestBody Enfermedad enfermedad) {
        enfermedad.setId(id);
        Enfermedad actualizado = enfermedadService.actualizar(enfermedad);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/enfermedades/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        enfermedadService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre
    @GetMapping("/enfermedades/nombre/{palabra}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EnfermedadDTO>> buscarPorNombre(@PathVariable String palabra) {
        return ResponseEntity.ok(enfermedadService.buscarPorNombre(palabra));
    }
}
