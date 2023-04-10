package com.crud.democrudmysql.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.democrudmysql.modelo.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Integer>{
    
    List<Curso> findByTitulo(String t);
    Page<Curso> findByTituloContaining(String t, Pageable p);
}
