package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.PerfilDTO;
import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.entidades.Ubicacion;
import com.upc.appsaludai3.interfaces.IPerfilServices;
import com.upc.appsaludai3.repository.PerfilRepository;
import com.upc.appsaludai3.repository.UbicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService implements IPerfilServices {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository; // Agregado: Vital para actualizar dirección

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PerfilDTO registrar(PerfilDTO perfilDTO) {
        Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
        // Guardar ubicación si es nueva antes de guardar el perfil
        if (perfil.getUbicacion() != null && perfil.getUbicacion().getId() == null) {
            ubicacionRepository.save(perfil.getUbicacion());
        }
        perfil = perfilRepository.save(perfil);
        return modelMapper.map(perfil, PerfilDTO.class);
    }

    // --- LÓGICA DE ACTUALIZACIÓN SEGURA ---
    @Transactional
    @Override
    public Perfil actualizar(Perfil perfilEntrante) {
        // 1. Buscamos el perfil real en la base de datos
        Perfil perfilExistente = perfilRepository.findById(perfilEntrante.getId()).orElse(null);

        if (perfilExistente != null) {
            // 2. Actualizamos los datos básicos del perfil
            perfilExistente.setNombre(perfilEntrante.getNombre());
            perfilExistente.setEmail(perfilEntrante.getEmail());
            perfilExistente.setFechaNacimiento(perfilEntrante.getFechaNacimiento());

            // 3. Gestión de la Ubicación (El punto crítico)
            if (perfilEntrante.getUbicacion() != null) {
                Ubicacion ubicacionNueva = perfilEntrante.getUbicacion();
                Ubicacion ubicacionExistente = perfilExistente.getUbicacion();

                if (ubicacionExistente != null) {
                    // Si ya tenía ubicación, actualizamos SUS campos.
                    // Esto evita errores de "detached entity" o duplicados.
                    ubicacionExistente.setDireccion(ubicacionNueva.getDireccion());
                    ubicacionExistente.setDistrito(ubicacionNueva.getDistrito());
                    ubicacionExistente.setProvincia(ubicacionNueva.getProvincia());

                    // Guardamos explícitamente los cambios en la ubicación
                    ubicacionRepository.save(ubicacionExistente);
                } else {
                    // Si el perfil no tenía ubicación, le asignamos la nueva
                    if (ubicacionNueva.getId() == null) {
                        ubicacionRepository.save(ubicacionNueva);
                    }
                    perfilExistente.setUbicacion(ubicacionNueva);
                }
            }

            // 4. Guardamos el perfil actualizado
            return perfilRepository.save(perfilExistente);
        }
        return null;
    }

    @Override
    public void borrar(Long id) {
        perfilRepository.deleteById(id);
    }

    @Override
    public List<PerfilDTO> findAll() {
        return perfilRepository.findAll()
                .stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Perfil findById(Long id) {
        return perfilRepository.findById(id).orElse(null);
    }

    @Override
    public List<PerfilDTO> buscarPorNombre(String palabra) {
        return perfilRepository.findByNombreContaining(palabra)
                .stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDTO.class))
                .collect(Collectors.toList());
    }
}