package com.upc.appsaludai3.security.services;


import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.entidades.Ubicacion;
import com.upc.appsaludai3.repository.PerfilRepository;
import com.upc.appsaludai3.repository.UbicacionRepository;
import com.upc.appsaludai3.security.dtos.RegisterRequestDTO;
import com.upc.appsaludai3.security.entities.Role; // <-- CORREGIDO: Role
import com.upc.appsaludai3.security.entities.User;
import com.upc.appsaludai3.security.repositories.RoleRepository; // <-- CORREGIDO: RoleRepository
import com.upc.appsaludai3.security.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private RoleRepository roleRepository; // <-- CORREGIDO

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registrarUsuario(RegisterRequestDTO dto) {

        // 1. Validar si el usuario (email) ya existe
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Error: El nombre de usuario (email) ya está en uso.");
        }

        // 2. Hashear la contraseña
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        // 3. Buscar el ROL por defecto ("ROL_USER")
        // Asumiendo que tu método en RoleRepository es findByName
        Role userRole = roleRepository.findByName("ROLE_USER") // <-- CORREGIDO
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_USER' no encontrado."));

        // 4. Crear y guardar la Ubicacion (con los campos actualizados)
        Ubicacion nuevaUbicacion = new Ubicacion();
        nuevaUbicacion.setDistrito(dto.getDistrito());
        nuevaUbicacion.setProvincia(dto.getProvincia()); // <-- ACTUALIZADO
        nuevaUbicacion.setDireccion(dto.getDireccion()); // <-- ACTUALIZADO
        Ubicacion ubicacionGuardada = ubicacionRepository.save(nuevaUbicacion);

        // 5. Crear y guardar el User (de Security)
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setRoles(Set.of(userRole)); // Asignamos el rol
        User userGuardado = userRepository.save(newUser);

        // 6. Crear y guardar el Perfil (de tu App) y vincularlo todo (con los campos actualizados)
        Perfil nuevoPerfil = new Perfil();
        nuevoPerfil.setNombre(dto.getNombre());
        nuevoPerfil.setEmail(dto.getUsername()); // Asumimos que el perfil guarda el email
        nuevoPerfil.setFechaNacimiento(dto.getFechaNacimiento()); // <-- ACTUALIZADO


        nuevoPerfil.setUser(userGuardado); // Vinculamos Perfil con User (Security)
        nuevoPerfil.setUbicacion(ubicacionGuardada); // Vinculamos Perfil con Ubicacion

        perfilRepository.save(nuevoPerfil);
    }
}