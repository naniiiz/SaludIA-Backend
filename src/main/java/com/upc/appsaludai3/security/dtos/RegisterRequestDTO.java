package com.upc.appsaludai3.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importar NotNull
import jakarta.validation.constraints.Past;   // Importar Past
import jakarta.validation.constraints.Size;

import java.time.LocalDate; // Importar LocalDate
import java.util.Objects;

public class RegisterRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre; // Para tu entidad Perfil

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El distrito es obligatorio")
    private String distrito;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento; // <-- AÑADIDO

    // --- Constructor Vacío ---
    public RegisterRequestDTO() {
    }

    // --- Constructor con todos los campos ---
    public RegisterRequestDTO(String nombre, String username, String password, String distrito, String provincia, String direccion, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.distrito = distrito;
        this.provincia = provincia;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento; // <-- AÑADIDO
    }

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() { // <-- AÑADIDO
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { // <-- AÑADIDO
        this.fechaNacimiento = fechaNacimiento;
    }

    // --- equals(), hashCode() y toString() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequestDTO that = (RegisterRequestDTO) o;
        return Objects.equals(nombre, that.nombre) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(distrito, that.distrito) &&
                Objects.equals(provincia, that.provincia) &&
                Objects.equals(direccion, that.direccion) &&
                Objects.equals(fechaNacimiento, that.fechaNacimiento); // <-- AÑADIDO
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, username, password, distrito, provincia, direccion, fechaNacimiento); // <-- AÑADIDO
    }

    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "nombre='" + nombre + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTEGIDO]'" +
                ", distrito='" + distrito + '\'' +
                ", provincia='" + provincia + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento + // <-- AÑADIDO
                '}';
    }
}