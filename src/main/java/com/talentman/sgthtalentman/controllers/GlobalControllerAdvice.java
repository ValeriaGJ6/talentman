package com.talentman.sgthtalentman.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.UsuarioService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private InformacionPersonalService informacionPersonalService;

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute
    public void addInformacionPersonalToModel(Model model, HttpSession session) {
        // Obtener el ID del usuario de la sesión
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        if (idUsuario != null) {
            // Obtener la información personal del usuario y agregarla al modelo
            InformacionPersonalModel informacionPersonal = informacionPersonalService
                    .getInformacionPersonalByIdUsuario(idUsuario);
            model.addAttribute("informacionPersonal", informacionPersonal);

            // Obtener el rol del usuario y agregarlo al modelo
            String rol = usuarioService.getRolById(idUsuario);

            model.addAttribute("rol", rol);
        }
    }

    @EventListener
    public void handleSessionDestroyed(HttpSessionDestroyedEvent event) {
        event.getSession().removeAttribute("idUsuario");
        event.getSession().removeAttribute("informacionPersonal");
    }

}
