package com.crud.democrudmysql.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Integer id;

    @NotBlank(message = "El titulo del curso es obligatorio")
    private String titulo;

    @Size(min = 3, max = 500, message = "La descripción debe contener entre 3 y 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio del curso es obligatorio")
    @DecimalMin(value = "1", message = "El precio debe ser 1 como minimo")
    @DecimalMax(value = "1000", message = "El precio debe ser 1000 como máximo")
    private BigDecimal precio;
    private String rutaImagen;

    @NotNull(message = "Debes especificar una fecha de inicio del curso")
    @FutureOrPresent(message = "La fecha del curso debe ser posterior a la fecha actual")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaInicio;

    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_act")
    private LocalDateTime fechaActualizacion;

    @Transient
    private MultipartFile imagen;

    @PrePersist
    private void asignarFechaCreacion() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    private void asignarFechaActualizacion() {
        fechaActualizacion = LocalDateTime.now();
    }
    
}
