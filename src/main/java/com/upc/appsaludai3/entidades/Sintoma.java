package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Sintoma")
public class Sintoma {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    public Sintoma(String nombre) {this.nombre = nombre;}
    // Relación ManyToMany con Enfermedad
    @ManyToMany(mappedBy = "sintomas")
    @JsonIgnore
    private Set<Enfermedad> enfermedades = new HashSet<>();
    //@JsonIgnoreProperties("sintomas")  // <- evita el ciclo inverso

    // Relación ManyToMany con Diagnostico
    @ManyToMany(mappedBy = "sintomas")
    @JsonIgnore
    private Set<Diagnostico> diagnosticos = new HashSet<>();

}