package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Diagnostico")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Boolean consentimiento;

    // Relación con el Usuario/Perfil
    // CAMBIO: Lo ponemos (nullable = true) temporalmente.
    // Como tu tabla 'perfil' está vacía al inicio, esto evita que la app explote
    // si no encuentra el perfil del usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Perfil perfil;

    // Relación con Enfermedad (Ya corregida previamente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enfermedad", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Enfermedad enfermedad;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "diagnostico_sintoma",
            joinColumns = @JoinColumn(name = "id_diagnostico"),
            inverseJoinColumns = @JoinColumn(name = "id_sintoma")
    )
    private List<Sintoma> sintomas;
}