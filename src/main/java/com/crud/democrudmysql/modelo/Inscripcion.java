package com.crud.democrudmysql.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import lombok.Data;

@Data
@Entity
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idinscripcion")
    private Integer id;
    private LocalDateTime fechaInscripcion;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idcurso",referencedColumnName = "idcurso")
    private Curso curso;
    
    
}
