package com.crud.democrudmysql.controlador;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.crud.democrudmysql.modelo.Curso;
import com.crud.democrudmysql.repository.CursoRepository;

@Controller
public class HomeController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String index(Model model) {
        // if(true) {
        //     throw new RuntimeException("este es un ejemplo de una excepcion lanzada intencionalmente");
        // }
        List<Curso> ultimosCursos = cursoRepository.findFirst8ByOrderByFechaCreacionDesc();
        model.addAttribute("ultimosCursos", ultimosCursos);
        return "index";
    }

    @GetMapping("/cursos")
    ModelAndView cursos(@PageableDefault(size = 8, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Curso> cursoPage = cursoRepository.findAll(pageable);
        return new ModelAndView("cursos").addObject("cursoPage", cursoPage);
    }

    @GetMapping("/cursos/{idCurso}")
    ModelAndView detallesCurso(@PathVariable Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso).orElseThrow(EntityNotFoundException::new);

        return new ModelAndView("detalles-curso").addObject("curso", curso);
    }

    /*
    @ExceptionHandler(EntityNotFoundException.class) 
    String handleEntityNotFoundException(EntityNotFoundException ex) {
        return "error-404";
    }*/
}
