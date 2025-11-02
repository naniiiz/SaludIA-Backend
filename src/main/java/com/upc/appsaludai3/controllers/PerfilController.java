package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.PerfilDTO;
import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.interfaces.IPerfilServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api/")
public class PerfilController {
    @Autowired
    private IPerfilServices perfilService;

    // CREATE - POST
    @PostMapping("perfiles")
    public ResponseEntity<PerfilDTO> registrar(@RequestBody PerfilDTO perfilDTO) {
        return ResponseEntity.ok(perfilService.registrar(perfilDTO));
    }

    // READ ALL - GET
    @GetMapping("perfiles")
    public ResponseEntity<List<PerfilDTO>> listar() {
        return ResponseEntity.ok(perfilService.findAll());
    }

    // READ BY ID - GET
    @GetMapping("perfiles/{id}")
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
        Perfil perfil = perfilService.findById(id);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE - PUT
    @PutMapping("perfiles/{id}")
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
    @DeleteMapping("perfiles/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        perfilService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre (containing)
    @GetMapping("perfiles/nombre/{palabra}")
    public ResponseEntity<List<PerfilDTO>> buscarPorNombre(@PathVariable String palabra) {
        return ResponseEntity.ok(perfilService.buscarPorNombre(palabra));
    }



}
