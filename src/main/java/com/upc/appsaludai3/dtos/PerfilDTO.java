package com.upc.appsaludai3.dtos;

import com.upc.appsaludai3.entidades.Ubicacion;
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
    private  String email;
    private LocalDate fechaNacimiento;
    private Ubicacion ubicacion;
    private Long idRol;
}