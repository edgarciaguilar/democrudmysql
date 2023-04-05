package com.crud.democrudmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.democrudmysql.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
}
