package com.upc.appsaludai3.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Long idUbicacion;
    private Long idRol;
}