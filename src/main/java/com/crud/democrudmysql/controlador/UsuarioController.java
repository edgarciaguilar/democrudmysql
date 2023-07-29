package com.crud.democrudmysql.controlador;

import java.security.Principal;
import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crud.democrudmysql.modelo.Curso;
import com.crud.democrudmysql.modelo.Inscripcion;
import com.crud.democrudmysql.modelo.Usuario;
import com.crud.democrudmysql.repository.CursoRepository;
import com.crud.democrudmysql.repository.InscripcionRepository;
import com.crud.democrudmysql.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("inscribir")
    String inscribir(@RequestParam("c") Integer idCurso, Principal principal) {

        Curso curso = cursoRepository.findById(idCurso).orElseThrow(EntityNotFoundException::new);
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow(EntityNotFoundException::new);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcionRepository.save(inscripcion);
        return "redirect:/usuario/inscribir/exito";
    }

    @GetMapping("/inscribir/exito")
    String exito() {
        return "exito-inscripcion";
    }

}
