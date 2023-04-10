package com.crud.democrudmysql.controlador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.democrudmysql.modelo.Curso;
import com.crud.democrudmysql.repository.CursoRepository;
import com.crud.democrudmysql.service.StorageService;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private StorageService storageService;


    @GetMapping
    public String index(Model model, @RequestParam(required = false) String titulo, @PageableDefault(size = 5, sort = "titulo", direction = Direction.DESC) Pageable pageable) {
        Page<Curso> cursosPage;
        if(StringUtils.hasText(titulo)) {
            cursosPage = cursoRepository.findByTituloContaining(titulo, pageable);
        } else {
            cursosPage = cursoRepository.findAll(pageable);
        }
        model.addAttribute("cursosPage", cursosPage);
        return "cursos/index";
    }

    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/formulario";
    }

    @PostMapping("/nuevo")
    String crear(@ModelAttribute @Validated Curso curso, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "cursos/formulario";
        } 
        String rutaImagen = storageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);
        cursoRepository.save(curso);
        ra.addFlashAttribute("msgExito", "El curso ha sido creado correctamente");
        return "redirect:/admin/cursos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository.findById(id).get();
        model.addAttribute("curso", curso);
        return "cursos/formulario";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Integer id, @ModelAttribute @Validated Curso curso, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "cursos/formulario";
        }
        Curso cursoFromDb = cursoRepository.findById(id).get();
        cursoFromDb.setTitulo(curso.getTitulo());
        cursoFromDb.setDescripcion(curso.getDescripcion());
        cursoFromDb.setPrecio(curso.getPrecio());
        cursoFromDb.setFechaInicio(curso.getFechaInicio());
        cursoRepository.save(cursoFromDb);
        ra.addFlashAttribute("msgExito","El curso se ha modificado exitosamente");
        return "redirect:/admin/cursos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        Curso cursoFromDb = cursoRepository.findById(id).get();
        cursoRepository.delete(cursoFromDb);
        ra.addFlashAttribute("msgExito","El curso se ha eliminado"); 
        return "redirect:/admin/cursos";
    }
    
}
