package com.upc.appsaludai3.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionDTO {
    private Long id;
    private String distrito;
    private String provincia;
    private String direccion;
}
