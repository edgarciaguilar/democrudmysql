package com.crud.democrudmysql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.democrudmysql.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

    List<Usuario> findByNombreCompletoContaining(String nc);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String e);
    
}
