package com.crud.democrudmysql.controlador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.democrudmysql.modelo.Curso;
import com.crud.democrudmysql.repository.CursoRepository;

@Controller
public class CursoController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/cursos") 
    public String index(Model model, @RequestParam(required = false) String titulo) {
        List<Curso> cursos;
        if(StringUtils.hasText(titulo)) {
            cursos = cursoRepository.findByTituloContaining(titulo);
        } else {
            cursos = cursoRepository.findAll();
        }
        model.addAttribute("cursos", cursos);
        return "cursos/index";
    }

    @GetMapping("/cursos/nuevo")
    String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/formulario";
    }

    @PostMapping("/cursos/nuevo")
    String crear(@ModelAttribute Curso curso, RedirectAttributes ra) {
        cursoRepository.save(curso);
        ra.addFlashAttribute("msgExito", "El curso ha sido creado correctamente");
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository.findById(id).get();
        model.addAttribute("curso", curso);
        return "cursos/formulario";
    }

    @PostMapping("/cursos/{id}/editar")
    public String actualizar(@PathVariable Integer id, @ModelAttribute Curso curso, RedirectAttributes ra) {
        Curso cursoFromDb = cursoRepository.findById(id).get();
        cursoFromDb.setTitulo(curso.getTitulo());
        cursoFromDb.setDescripcion(curso.getDescripcion());
        cursoFromDb.setPrecio(curso.getPrecio());
        cursoFromDb.setFechaInicio(curso.getFechaInicio());
        cursoRepository.save(cursoFromDb);
        ra.addFlashAttribute("msgExito","El curso se ha modificado exitosamente");
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        Curso cursoFromDb = cursoRepository.findById(id).get();
        cursoRepository.delete(cursoFromDb);
        ra.addFlashAttribute("msgExito","El curso se ha eliminado"); 
        return "redirect:/cursos";
    }
    
}
