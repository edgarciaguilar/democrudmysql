package com.crud.democrudmysql.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.democrudmysql.modelo.Usuario;
import com.crud.democrudmysql.repository.UsuarioRepository;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    ModelAndView index() {
        return new ModelAndView("registro").addObject("usuario", new Usuario());
    }

    @PostMapping
    ModelAndView crear(@Validated Usuario usuario, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("registro").addObject("usuario", usuario);
        }
        String email = usuario.getEmail();
        boolean usuarioYaExiste = usuarioRepository.existsByEmail(email);
        if (usuarioYaExiste) {
            bindingResult.rejectValue("email", "EmailAlreadyExists");
        }

        if(!usuario.getPassword1().equals(usuario.getPassword2())) {
            bindingResult.rejectValue("password1", "PasswordNotEquals");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("registro").addObject("usuario", usuario);
        }
        usuario.setRol(Usuario.Rol.ESTUDIANTE);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword1()));
        usuarioRepository.save(usuario);
        ra.addAttribute("registroExitoso","");
        return new ModelAndView("redirect:/login");
    }
}
