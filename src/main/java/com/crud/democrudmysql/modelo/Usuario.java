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

import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;
    private String nombres;
    private String apellidos;
    @Column(name = "nom_completo")
    private String nombreCompleto;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Rol rol;
    private LocalDateTime fechaCreacion;
    @Column(name="fecha_act")
    private LocalDateTime fechaActualizacion;

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
