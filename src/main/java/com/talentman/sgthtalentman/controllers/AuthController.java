package com.talentman.sgthtalentman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.talentman.sgthtalentman.dto.LoginDTO;
import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    InformacionPersonalService informacionPersonalService;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") LoginDTO loginDTO, BindingResult bindingResult, HttpSession sesion) {
        if (bindingResult.hasErrors()) {
            return "auth/login"; 
        }

        String usuario = loginDTO.getUsuario();
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario, loginDTO.getContrasena())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UsuarioModel usuarioSesion = usuarioService.findByUsuario(usuario);
            sesion.setAttribute("idUsuario", usuarioSesion.getId());
        } catch (AuthenticationException e) {
            bindingResult.rejectValue("contrasena", "error.login", "Usuario o contraseña incorrectos");
            return "auth/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession sesion) {
        sesion.invalidate();
        return "redirect:/login";
    }

    
}
