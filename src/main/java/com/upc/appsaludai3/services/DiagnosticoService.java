package com.upc.appsaludai3.services;


import com.upc.appsaludai3.dtos.DiagnosticoDTO;
import com.upc.appsaludai3.entidades.Diagnostico;
import com.upc.appsaludai3.interfaces.IDiagnosticoServices;
import com.upc.appsaludai3.repository.DiagnosticoRepository;
import com.upc.appsaludai3.repository.EnfermedadRepository;
import com.upc.appsaludai3.repository.SintomaRepository;
import com.upc.appsaludai3.repository.PerfilRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosticoService implements IDiagnosticoServices {
    @Autowired
    private DiagnosticoRepository diagnosticoRepository;
    @Autowired
    private PerfilRepository usuarioGeneralRepository;
    @Autowired
    private EnfermedadRepository enfermedadRepository;
    @Autowired
    private SintomaRepository sintomaRepository;
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
        diagnostico.setFecha(diagnosticoDTO.getFecha());

        // Relaciones
        diagnostico.setPerfil(usuarioGeneralRepository.findById(diagnosticoDTO.getIdPerfil()).orElse(null));
        diagnostico.setEnfermedad(enfermedadRepository.findById(diagnosticoDTO.getIdEnfermedad()).orElse(null));

        if (diagnosticoDTO.getIdsSintomas() != null) {
            diagnostico.setSintomas(
                    sintomaRepository.findAllById(diagnosticoDTO.getIdsSintomas())
            );
        }

        Diagnostico saved = diagnosticoRepository.save(diagnostico);
        return modelMapper.map(saved, DiagnosticoDTO.class);
    }
    @Transactional
    @Override
    public void borrar(Long id) {
        diagnosticoRepository.deleteById(id);
    }

    @Override
    public List<DiagnosticoDTO> findAll() {
        return diagnosticoRepository.findAll()
                .stream()
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

    @Override
    public List<DiagnosticoDTO> buscarPorPerfil(Long idPerfil) {
        return diagnosticoRepository.findByPerfil_Id(idPerfil)
                .stream()
                .map(d -> modelMapper.map(d, DiagnosticoDTO.class))
                .collect(Collectors.toList());
    }

}