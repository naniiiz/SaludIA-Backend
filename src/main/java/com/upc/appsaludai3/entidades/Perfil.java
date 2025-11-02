package com.upc.appsaludai3.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    @Column(unique = true)
    //Tener en cuenta que el cascade guarda AUTOMATICAMENTE
    //Por ejemplo
    //si guardas un usuario con sus diagnosticos -> se guardan todos
    //Asi mismo si borraas un usuario -> se borran sus diagnosticos
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Diagnostico> diagnosticos;
    @ManyToOne
    @JoinColumn(name = "idUbicacion", nullable = false) // clave foránea en UsuarioGeneral
    private Ubicacion ubicacion;



}