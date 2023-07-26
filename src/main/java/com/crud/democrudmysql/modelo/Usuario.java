package com.crud.democrudmysql.modelo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;
    @Column(name = "nom_completo")
    private String nombreCompleto;

    @NotBlank
    @Email
    private String email;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private Rol rol;
    private LocalDateTime fechaCreacion;
    @Column(name="fecha_act")
    private LocalDateTime fechaActualizacion;


    @NotBlank
    @Transient
    private String password1;

    @NotBlank
    @Transient
    private String password2;

    @OneToMany(mappedBy = "usuario")
    private List<Inscripcion> inscripciones;

    public enum Rol {
        ADMIN,
        ESTUDIANTE
    }

    @PrePersist
    private void asignarFechaCreacion() {
        fechaCreacion = LocalDateTime.now();
        nombreCompleto = nombres + " " + apellidos;
    }

    @PreUpdate
    private void asignarFechaActualizacion() {
        fechaActualizacion = LocalDateTime.now();
        nombreCompleto = nombres + " " + apellidos;
    }
}
