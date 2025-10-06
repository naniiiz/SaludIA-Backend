package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.UbicacionDTO;
import com.upc.appsaludai3.entidades.Ubicacion;
import com.upc.appsaludai3.interfaces.IUbicacionServices;
import com.upc.appsaludai3.repository.UbicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbicacionService implements IUbicacionServices {
    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UbicacionDTO registrar(UbicacionDTO ubicacionDTO) {
        Ubicacion ubicacion = modelMapper.map(ubicacionDTO, Ubicacion.class);
        ubicacionRepository.save(ubicacion);
        return modelMapper.map(ubicacion, UbicacionDTO.class);
    }

    @Override
    public List<UbicacionDTO> findAll() {
        return ubicacionRepository.findAll()
                .stream()
                .map(ubi -> modelMapper.map(ubi, UbicacionDTO.class))
                .toList();
    }

    @Override
    public Ubicacion findById(Long id) {
        return ubicacionRepository.findById(id).orElse(null);
    }

    @Override
    public Ubicacion actualizar(Ubicacion ubicacion) {
        if (ubicacionRepository.existsById(ubicacion.getId())) {
            return ubicacionRepository.save(ubicacion);
        }
        return null;
    }

    @Override
    public void borrar(Long id) {
        ubicacionRepository.deleteById(id);
    }

}