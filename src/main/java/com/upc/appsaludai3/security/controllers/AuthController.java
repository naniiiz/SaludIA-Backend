package com.upc.appsaludai3.security.controllers;



import com.upc.appsaludai3.security.dtos.AuthRequestDTO;
import com.upc.appsaludai3.security.dtos.AuthResponseDTO;
import com.upc.appsaludai3.security.dtos.RegisterRequestDTO;
import com.upc.appsaludai3.security.services.CustomUserDetailsService;
import com.upc.appsaludai3.security.services.RegisterService;
import com.upc.appsaludai3.security.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
//@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final RegisterService registerService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, RegisterService registerService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.registerService = registerService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody @Valid AuthRequestDTO authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);
        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }
    // === 3. ¡AÑADE ESTE MÉTODO DE REGISTRO! ===
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        try {
            // Llama al servicio para hacer todo el trabajo
            registerService.registrarUsuario(registerRequest);

            // Si todo sale bien, respondemos con 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body("¡Usuario registrado exitosamente!");

        } catch (RuntimeException e) {
            // Si el servicio lanzó un error (ej. "usuario ya existe"), respondemos con 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}


