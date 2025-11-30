package com.upc.appsaludai3.security.services;

import com.upc.appsaludai3.security.entities.User;
import com.upc.appsaludai3.security.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para Lazy Loading

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Busca al usuario en la BD por su username.
 * Convierte sus roles en GrantedAuthority.
 * Devuelve un UserDetails para Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true) // <--- CRUCIAL: Mantiene la sesión de BD abierta para leer roles
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Convertir Roles a Authorities
        // Esto asegura que el Token se genere con los permisos correctos
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // 3. Retornar usuario de seguridad
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}