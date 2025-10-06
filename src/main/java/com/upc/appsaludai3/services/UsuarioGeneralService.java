package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.UsuarioGeneralDTO;
import com.upc.appsaludai3.entidades.UsuarioGeneral;
import com.upc.appsaludai3.interfaces.IUsuarioGeneralServices;
import com.upc.appsaludai3.repository.UsuarioGeneralRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioGeneralService implements IUsuarioGeneralServices {
    @Autowired
    private UsuarioGeneralRepository usuarioGeneralRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioGeneralDTO registrar(UsuarioGeneralDTO usuarioDTO) {
        UsuarioGeneral usuario = modelMapper.map(usuarioDTO, UsuarioGeneral.class);
        usuarioGeneralRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioGeneralDTO.class);
    }

    @Override
    public UsuarioGeneral actualizar(UsuarioGeneral usuario) {
        if (usuarioGeneralRepository.existsById(usuario.getId())) {
            return usuarioGeneralRepository.save(usuario);
        }
        return null;
    }

    @Override
    public void borrar(Long id) {
        usuarioGeneralRepository.deleteById(id);
    }

    @Override
    public List<UsuarioGeneralDTO> findAll() {
        return usuarioGeneralRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioGeneralDTO.class))
                .toList();
    }

    @Override
    public UsuarioGeneral findById(Long id) {
        return usuarioGeneralRepository.findById(id).orElse(null);
    }


    @Override
    public List<UsuarioGeneralDTO> buscarPorNombre(String palabra) {
        return usuarioGeneralRepository.findByNombreContaining(palabra)
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioGeneralDTO.class))
                .toList();
    }


}
