package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.DiagnosticoDTO;
import com.upc.appsaludai3.entidades.Diagnostico;
import com.upc.appsaludai3.entidades.Enfermedad;
import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.entidades.Sintoma;
import com.upc.appsaludai3.entidades.Ubicacion;
import com.upc.appsaludai3.interfaces.IDiagnosticoServices;
import com.upc.appsaludai3.repository.*;
import com.upc.appsaludai3.security.entities.User;
import com.upc.appsaludai3.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagnosticoService implements IDiagnosticoServices {

    @Autowired
    private DiagnosticoRepository diagnosticoRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private EnfermedadRepository enfermedadRepository;
    @Autowired
    private SintomaRepository sintomaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Diagnostico findById(Long id) {
        return diagnosticoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DiagnosticoDTO registrar(DiagnosticoDTO diagnosticoDTO) {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setConsentimiento(diagnosticoDTO.getConsentimiento());
        diagnostico.setFecha(diagnosticoDTO.getFecha() != null ? diagnosticoDTO.getFecha() : LocalDateTime.now());

        // 1. Obtener usuario del Token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuarioLogueado = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Buscar/Crear Perfil
        Perfil perfil = buscarPerfilPorUsuario(usuarioLogueado);
        if (perfil == null) {
            perfil = crearPerfilAutomatico(usuarioLogueado);
        }
        diagnostico.setPerfil(perfil);

        // 3. Síntomas
        List<Sintoma> sintomasSeleccionados = null;
        if (diagnosticoDTO.getIdsSintomas() != null && !diagnosticoDTO.getIdsSintomas().isEmpty()) {
            sintomasSeleccionados = sintomaRepository.findAllById(diagnosticoDTO.getIdsSintomas());
            diagnostico.setSintomas(sintomasSeleccionados);
        }

        // 4. Calcular Enfermedad (IA Simple)
        if (diagnosticoDTO.getIdEnfermedad() == null && sintomasSeleccionados != null) {
            Enfermedad enfermedadDetectada = calcularEnfermedadMasProbable(sintomasSeleccionados);
            diagnostico.setEnfermedad(enfermedadDetectada);
        } else if (diagnosticoDTO.getIdEnfermedad() != null) {
            diagnostico.setEnfermedad(enfermedadRepository.findById(diagnosticoDTO.getIdEnfermedad()).orElse(null));
        }

        Diagnostico saved = diagnosticoRepository.save(diagnostico);
        return modelMapper.map(saved, DiagnosticoDTO.class);
    }

    @Override
    public List<DiagnosticoDTO> buscarPorPerfil(Long idPerfilSolicitado) {
        // --- LÓGICA INTELIGENTE PARA HISTORIAL ---
        // Ignoramos el ID '0' que manda Angular y usamos el usuario del Token.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuarioLogueado = userRepository.findByUsername(username).orElse(null);

        if (usuarioLogueado == null) return List.of();

        // Buscamos el perfil REAL de este usuario en la base de datos
        Perfil perfilReal = buscarPerfilPorUsuario(usuarioLogueado);

        if (perfilReal == null) {
            System.out.println("⚠️ Usuario " + username + " no tiene perfil, retornando lista vacía.");
            return List.of();
        }

        System.out.println("🔍 Mostrando historial para: " + username + " (Perfil ID: " + perfilReal.getId() + ")");

        // Retornamos los diagnósticos de ESE perfil
        return diagnosticoRepository.findByPerfil_Id(perfilReal.getId())
                .stream()
                .map(d -> modelMapper.map(d, DiagnosticoDTO.class))
                .collect(Collectors.toList());
    }

    // --- Métodos Privados de Apoyo ---

    private Perfil buscarPerfilPorUsuario(User user) {
        return perfilRepository.findAll().stream()
                .filter(p -> p.getUser() != null && p.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    private Perfil crearPerfilAutomatico(User user) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("-");
        ubicacion.setDistrito("-");
        ubicacion.setProvincia("-");
        ubicacion = ubicacionRepository.save(ubicacion);

        Perfil nuevoPerfil = new Perfil();
        nuevoPerfil.setUser(user);
        nuevoPerfil.setEmail(user.getUsername());
        nuevoPerfil.setNombre("Paciente " + user.getUsername());
        nuevoPerfil.setUbicacion(ubicacion);
        return perfilRepository.save(nuevoPerfil);
    }

    private Enfermedad calcularEnfermedadMasProbable(List<Sintoma> sintomasPaciente) {
        List<Enfermedad> todas = enfermedadRepository.findAll();
        Enfermedad candidata = null;
        int maxCoincidencias = 0;
        List<Long> idsPaciente = sintomasPaciente.stream().map(Sintoma::getId).collect(Collectors.toList());

        for (Enfermedad enf : todas) {
            int hits = 0;
            if (enf.getSintomas() != null) {
                for (Sintoma s : enf.getSintomas()) {
                    if (idsPaciente.contains(s.getId())) hits++;
                }
            }
            if (hits > maxCoincidencias) {
                maxCoincidencias = hits;
                candidata = enf;
            }
        }
        return maxCoincidencias > 0 ? candidata : null;
    }

    @Transactional
    @Override
    public void borrar(Long id) {
        diagnosticoRepository.deleteById(id);
    }

    @Override
    public List<DiagnosticoDTO> findAll() {
        return diagnosticoRepository.findAll().stream()
                .map(d -> modelMapper.map(d, DiagnosticoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Diagnostico actualizar(Diagnostico diagnostico) {
        if (diagnosticoRepository.existsById(diagnostico.getId())) {
            return diagnosticoRepository.save(diagnostico);
        }
        return null;
    }
}