package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.SintomaDTO;
import com.upc.appsaludai3.entidades.Sintoma;
import com.upc.appsaludai3.interfaces.ISintomaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api")
public class SintomaController {
    @Autowired
    private ISintomaServices sintomaService;
    // CREATE
    @PostMapping("/sintomas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SintomaDTO> registrar(@RequestBody SintomaDTO sintomaDTO) {
        return ResponseEntity.ok(sintomaService.registrar(sintomaDTO));
    }

    // READ ALL
    @GetMapping("/sintomas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SintomaDTO>> listar() {
        return ResponseEntity.ok(sintomaService.findAll());
    }

    // READ BY ID
    @GetMapping("/sintomas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sintoma> buscarPorId(@PathVariable Long id) {
        Sintoma sintoma = sintomaService.findById(id);
        if (sintoma != null) {
            return ResponseEntity.ok(sintoma);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE

    @PutMapping("/sintomas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sintoma> actualizar(@PathVariable Long id,
                                              @RequestBody Sintoma sintoma) {
        sintoma.setId(id);
        Sintoma actualizado = sintomaService.actualizar(sintoma);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("/sintomas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        sintomaService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre
    @GetMapping("/sintomas/nombre/{palabra}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SintomaDTO>> buscarPorNombre(@PathVariable String palabra) {
        return ResponseEntity.ok(sintomaService.buscarPorNombre(palabra));
    }
}