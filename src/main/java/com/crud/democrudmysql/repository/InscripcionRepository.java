package com.crud.democrudmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.democrudmysql.modelo.Inscripcion;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer>{
    
}
