package com.upc.appsaludai3.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGeneralDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String correo;
    private String password;
    private Long idUbicacion;
    private Long idRol;
}