package com.crud.democrudmysql.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crud.democrudmysql.modelo.Curso;
import com.crud.democrudmysql.repository.CursoRepository;

@Controller
public class HomeController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String index(Model model) {
        List<Curso> ultimosCursos = cursoRepository.findFirst8ByOrderByFechaCreacionDesc();
        model.addAttribute("ultimosCursos", ultimosCursos);
        return "index";
    }
}
