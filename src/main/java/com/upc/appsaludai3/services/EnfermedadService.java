package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.EnfermedadDTO;
import com.upc.appsaludai3.entidades.Enfermedad;
import com.upc.appsaludai3.entidades.Sintoma;
import com.upc.appsaludai3.interfaces.IEnfermedadServices;
import com.upc.appsaludai3.repository.EnfermedadRepository;
import com.upc.appsaludai3.repository.SintomaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnfermedadService implements IEnfermedadServices {
    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Autowired
    private SintomaRepository sintomaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ---------------- CREAR ----------------
    @Override
    public EnfermedadDTO registrar(EnfermedadDTO enfermedadDTO) {
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setNombre(enfermedadDTO.getNombre());
        enfermedad.setDescripcion(enfermedadDTO.getDescripcion());

        // Relacionar síntomas desde los IDs
        if (enfermedadDTO.getIdsSintomas() != null) {
            Set<Sintoma> sintomas = new HashSet<>(sintomaRepository.findAllById(enfermedadDTO.getIdsSintomas()));
            enfermedad.setSintomas(sintomas);
        }

        enfermedadRepository.save(enfermedad);
        return mapToDTO(enfermedad);
    }

    // ---------------- LISTAR ----------------
    @Override
    public List<EnfermedadDTO> findAll() {
        return enfermedadRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ---------------- BUSCAR POR ID ----------------
    public Enfermedad findById(Long id) {
        return enfermedadRepository.findById(id).orElse(null);
    }

    // ---------------- ACTUALIZAR ----------------
    @Override
    public Enfermedad actualizar(Enfermedad enfermedad) {
        if (enfermedadRepository.existsById(enfermedad.getId())) {
            return enfermedadRepository.save(enfermedad);
        }
        return null;
    }

    // ---------------- ELIMINAR ----------------
    @Override
    public void borrar(Long id) {
        enfermedadRepository.deleteById(id);
    }

    // ---------------- BUSCAR POR NOMBRE ----------------
    @Override
    public List<EnfermedadDTO> buscarPorNombre(String palabra) {
        return enfermedadRepository.findByNombreContainingIgnoreCase(palabra)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ---------------- MÉTODO PRIVADO PARA MAPEAR ---------------- //
    private EnfermedadDTO mapToDTO(Enfermedad enfermedad) {
        EnfermedadDTO dto = modelMapper.map(enfermedad, EnfermedadDTO.class);
        dto.setIdsSintomas(enfermedad.getSintomas()
                .stream()
                .map(Sintoma::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}