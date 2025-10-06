package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.RolDTO;
import com.upc.appsaludai3.entidades.Rol;
import com.upc.appsaludai3.interfaces.IRolServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api/")
public class RolController {
    @Autowired
    private IRolServices rolService;

    // CREATE
    @PostMapping("roles")
    public ResponseEntity<RolDTO> registrar(@RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.registrar(rolDTO));
    }

    // READ ALL
    @GetMapping("roles")
    public ResponseEntity<List<RolDTO>> listar() {
        return ResponseEntity.ok(rolService.findAll());
    }

    // READ BY ID
    @GetMapping("roles/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
        Rol rol = rolService.findById(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE
    @PutMapping("roles/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id,
                                          @RequestBody Rol rol) {
        rol.setId(id);
        Rol actualizado = rolService.actualizar(rol);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("roles/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        rolService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre de rol ("usuario" o "admin")
    @GetMapping("roles/nombre/{rol}")
    public ResponseEntity<RolDTO> buscarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(rolService.findByRol(rol));
    }
}
