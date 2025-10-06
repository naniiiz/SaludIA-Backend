package com.upc.appsaludai3.services;

import com.upc.appsaludai3.dtos.SintomaDTO;
import com.upc.appsaludai3.entidades.Sintoma;
import com.upc.appsaludai3.interfaces.ISintomaServices;
import com.upc.appsaludai3.repository.SintomaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SintomaService implements ISintomaServices {
    @Autowired
    private SintomaRepository sintomaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SintomaDTO registrar(SintomaDTO sintomaDTO) {
        Sintoma sintoma = modelMapper.map(sintomaDTO, Sintoma.class);
        sintomaRepository.save(sintoma);
        return modelMapper.map(sintoma, SintomaDTO.class);
    }

    @Override
    public List<SintomaDTO> findAll() {
        return sintomaRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, SintomaDTO.class))
                .toList();
    }

    @Override
    public Sintoma findById(Long id) {
        return sintomaRepository.findById(id).orElse(null);
    }

    @Override
    public Sintoma actualizar(Sintoma sintoma) {
        if (sintomaRepository.existsById(sintoma.getId())) {
            return sintomaRepository.save(sintoma);
        }
        return null;
    }

    @Override
    public void borrar(Long id) {
        sintomaRepository.deleteById(id);
    }

    @Override
    public List<SintomaDTO> buscarPorNombre(String palabra) {
        return sintomaRepository.findByNombreContainingIgnoreCase(palabra)
                .stream()
                .map(s -> modelMapper.map(s, SintomaDTO.class))
                .toList();
    }
}
