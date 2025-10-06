package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.RolDTO;
import com.upc.appsaludai3.entidades.Rol;
import com.upc.appsaludai3.interfaces.IRolServices;
import com.upc.appsaludai3.repository.RolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService implements IRolServices {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RolDTO registrar(RolDTO rolDTO) {
        Rol rol=modelMapper.map(rolDTO, Rol.class);
        rolRepository.save(rol);
        return modelMapper.map(rol, RolDTO.class);
    }

    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RolDTO.class))
                .toList();
    }

    @Override
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol actualizar(Rol rol) {
        if (rolRepository.existsById(rol.getId())) {
            return rolRepository.save(rol);
        }
        return null;
    }

    @Override
    public void borrar(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public RolDTO findByRol(String rol) {
        Rol entity = rolRepository.findByRol(rol);
        return modelMapper.map(entity, RolDTO.class);
    }
}
