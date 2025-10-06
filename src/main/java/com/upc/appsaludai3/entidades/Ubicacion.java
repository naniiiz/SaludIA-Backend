package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Ubicacion")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String distrito;
    private String provincia;
    private String direccion;
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UsuarioGeneral> usuarios;
}
