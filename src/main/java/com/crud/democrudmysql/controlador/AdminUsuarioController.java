package com.crud.democrudmysql.controlador;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.democrudmysql.modelo.Usuario;
import com.crud.democrudmysql.repository.UsuarioRepository;

@Controller
public class AdminUsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/admin/usuarios") 
    public String index(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/index";
    }

    @GetMapping("/admin/usuarios/nuevo") 
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form-usuarios";
    }

    @PostMapping("/admin/usuarios/nuevo")
    public String crearUsuario(@ModelAttribute @Validated Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:admin/usuarios";
    }

    @GetMapping("/admin/usuarios/{id}/editar") 
    public String editar(@PathVariable Integer id, Model model) {
        Usuario usuario= usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("usuario", usuario);
        return "usuarios/form-usuarios";
    }

    @PostMapping("/admin/usuarios/{id}/editar") 
    public String actualizar(@PathVariable Integer id, @ModelAttribute Usuario usuario) {
        Usuario usuario2 = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuario2.setNombres(usuario.getNombres());
        usuario2.setApellidos(usuario.getApellidos());
        usuario2.setEmail(usuario.getEmail());
        usuario2.setRol(usuario.getRol());
        usuario2.setPassword(usuario.getPassword());
        //usuario2.setFechaCreacion(usuario.getFechaCreacion());
        usuarioRepository.save(usuario2);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/admin/usuarios/{id}/eliminar") 
    public String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        Usuario usuarioDB = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioRepository.delete(usuarioDB);
        return "redirect:/admin/usuarios";
    }

}
