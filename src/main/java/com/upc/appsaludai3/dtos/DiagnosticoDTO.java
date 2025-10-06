package com.upc.appsaludai3.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoDTO {
    private Long id;
    private Boolean consentimiento;
    private LocalDateTime fecha;
    private Long idUsuario;
    private Long idEnfermedad;
    private List<Long> idsSintomas;

}
