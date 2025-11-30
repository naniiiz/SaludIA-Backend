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

        // 1. SEGURIDAD: Obtener el usuario REAL desde el token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("📥 Registrando diagnóstico para usuario: " + username);

        User usuarioLogueado = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario del token no encontrado en BD"));

        // 2. BUSCAR O CREAR PERFIL
        // Buscamos si este usuario User ya tiene un Perfil asociado
        Perfil perfil = buscarPerfilPorUsuario(usuarioLogueado);

        // Si no tiene perfil, lo creamos automáticamente
        if (perfil == null) {
            System.out.println("⚠️ Usuario " + username + " no tiene Perfil. Creando uno automático...");
            perfil = crearPerfilAutomatico(usuarioLogueado);
        }

        System.out.println("✅ Perfil asignado ID: " + perfil.getId());
        diagnostico.setPerfil(perfil);

        // 3. ASIGNAR SÍNTOMAS
        List<Sintoma> sintomasSeleccionados = null;
        if (diagnosticoDTO.getIdsSintomas() != null && !diagnosticoDTO.getIdsSintomas().isEmpty()) {
            sintomasSeleccionados = sintomaRepository.findAllById(diagnosticoDTO.getIdsSintomas());
            diagnostico.setSintomas(sintomasSeleccionados);
        }

        // 4. LÓGICA DE IA (Calcular enfermedad)
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
        // 1. SEGURIDAD: Identificar quién está pidiendo los datos
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("🔍 Buscando historial para: " + username);

        // 2. Obtener el usuario y su perfil REAL
        User usuarioLogueado = userRepository.findByUsername(username).orElse(null);
        if (usuarioLogueado == null) return List.of();

        Perfil perfilReal = buscarPerfilPorUsuario(usuarioLogueado);

        // Si el usuario no tiene perfil, no tiene historial
        if (perfilReal == null) {
            return List.of();
        }

        // 3. FUERZA BRUTA: Ignoramos el idPerfil que viene del frontend
        // y usamos el ID del perfil asociado al token.
        // (A menos que fueras ADMIN, pero por seguridad general lo forzamos al usuario)
        Long idPerfilReal = perfilReal.getId();
        System.out.println("🔒 Forzando búsqueda para Perfil ID: " + idPerfilReal);

        return diagnosticoRepository.findByPerfil_Id(idPerfilReal)
                .stream()
                .map(d -> modelMapper.map(d, DiagnosticoDTO.class))
                .collect(Collectors.toList());
    }

    // --- MÉTODOS AUXILIARES ---

    private Perfil buscarPerfilPorUsuario(User user) {
        // Busca en todos los perfiles aquel que tenga este User ID
        // Nota: Idealmente deberías tener un método en repositorio: findByUser_Id(Long userId)
        return perfilRepository.findAll().stream()
                .filter(p -> p.getUser() != null && p.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    private Perfil crearPerfilAutomatico(User user) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("Sin dirección");
        ubicacion.setDistrito("Sin distrito");
        ubicacion.setProvincia("Sin provincia");
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