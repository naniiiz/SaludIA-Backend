package com.upc.appsaludai3.controllers;

import com.upc.appsaludai3.dtos.UsuarioGeneralDTO;
import com.upc.appsaludai3.entidades.UsuarioGeneral;
import com.upc.appsaludai3.interfaces.IUsuarioGeneralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api/")
public class UsuarioGeneralController {
    @Autowired
    private IUsuarioGeneralServices usuarioGeneralService;

    // CREATE - POST
    @PostMapping("usuarios")
    public ResponseEntity<UsuarioGeneralDTO> registrar(@RequestBody UsuarioGeneralDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioGeneralService.registrar(usuarioDTO));
    }

    // READ ALL - GET
    @GetMapping("usuarios")
    public ResponseEntity<List<UsuarioGeneralDTO>> listar() {
        return ResponseEntity.ok(usuarioGeneralService.findAll());
    }

    // READ BY ID - GET
    @GetMapping("usuarios/{id}")
    public ResponseEntity<UsuarioGeneral> buscarPorId(@PathVariable Long id) {
        UsuarioGeneral usuario = usuarioGeneralService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE - PUT
    @PutMapping("usuarios/{id}")
    public ResponseEntity<UsuarioGeneral> actualizar(@PathVariable Long id,
                                                     @RequestBody UsuarioGeneral usuario) {
        usuario.setId(id);
        UsuarioGeneral actualizado = usuarioGeneralService.actualizar(usuario);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping("usuarios/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        usuarioGeneralService.borrar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre (containing)
    @GetMapping("usuarios/nombre/{palabra}")
    public ResponseEntity<List<UsuarioGeneralDTO>> buscarPorNombre(@PathVariable String palabra) {
        return ResponseEntity.ok(usuarioGeneralService.buscarPorNombre(palabra));
    }



}
