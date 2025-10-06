package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Boolean consentimiento;
    private LocalDateTime fecha;
    //AGREGADO
    @ManyToOne
    @JoinColumn(name="idUsuario", nullable = false)
    @JsonIgnore
    private UsuarioGeneral usuario;
    // Relación ManyToOne con Enfermedad
    @ManyToOne
    @JoinColumn(name="idEnfermedad", nullable = false)
    private Enfermedad enfermedad;
    //Relación ManyToMany con Sintoma
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "DiagnosticoSintoma",
            joinColumns = @JoinColumn(name = "idDiagnostico"),
            inverseJoinColumns = @JoinColumn(name = "idSintoma")
    )
    @JsonIgnore
    private List<Sintoma> sintomas;
}
