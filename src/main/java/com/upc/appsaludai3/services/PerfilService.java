package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.PerfilDTO;
import com.upc.appsaludai3.entidades.Perfil;
import com.upc.appsaludai3.interfaces.IPerfilServices;
import com.upc.appsaludai3.repository.PerfilRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService implements IPerfilServices {
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PerfilDTO registrar(PerfilDTO perfilDTO) {
        Perfil perfil = modelMapper.map(perfilDTO, Perfil.class);
        perfilRepository.save(perfil);
        return modelMapper.map(perfil, PerfilDTO.class);
    }

    @Override
    public Perfil actualizar(Perfil perfil) {
        if (perfilRepository.existsById(perfil.getId())) {
            return perfilRepository.save(perfil);
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
                .toList();
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
                .toList();
    }


}
