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
@Table(name = "UsuarioGeneral")
public class UsuarioGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    @Column(unique = true)
    private String correo;
    private String password;
    //Tener en cuenta que el cascade guarda AUTOMATICAMENTE
    //Por ejemplo
    //si guardas un usuario con sus diagnosticos -> se guardan todos
    //Asi mismo si borraas un usuario -> se borran sus diagnosticos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Diagnostico> diagnosticos;
    @ManyToOne
    @JoinColumn(name = "idUbicacion", nullable = false) // clave foránea en UsuarioGeneral
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false) // FK en UsuarioGeneral
    @JsonIgnore
    private Rol rol;


}