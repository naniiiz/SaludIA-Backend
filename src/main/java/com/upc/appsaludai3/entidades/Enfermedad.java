package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Enfermedad")
public class Enfermedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    //Relacion ManyToMany con Sintoma
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="EnfermedadSintoma",
            joinColumns= @JoinColumn(name="idEnfermedad"),
            inverseJoinColumns = @JoinColumn(name="idSintoma")
    )
    private Set<Sintoma> sintomas = new HashSet<>();
    //@JsonIgnoreProperties("enfermedades")   // <- evita el ciclo

    //onetomany
    @OneToMany(mappedBy = "enfermedad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Diagnostico> diagnosticos;

}