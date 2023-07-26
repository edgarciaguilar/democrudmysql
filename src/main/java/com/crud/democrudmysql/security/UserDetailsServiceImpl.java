package com.crud.democrudmysql.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crud.democrudmysql.modelo.Usuario;
import com.crud.democrudmysql.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        Usuario usuario = usuarioRepository
                        .findByEmail(email)
                        .orElseThrow(()-> new UsernameNotFoundException(email + " no existe"));

        return new UserDetailsImpl(usuario);
    }
    
}
