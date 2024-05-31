package com.talentman.sgthtalentman.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.talentman.sgthtalentman.dto.RegistroDTO;
import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.PaisService;
import com.talentman.sgthtalentman.services.UsuarioService;
import com.talentman.sgthtalentman.transversal.Genero;
import com.talentman.sgthtalentman.transversal.TipoDocumento;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RegistroController {

    private final PaisService paisService;
    private final UsuarioService usuarioService;
    private final InformacionPersonalService informacionPersonalService;
    private static final Logger logger = LoggerFactory.getLogger(VacanteController.class);

    public RegistroController(PaisService paisService, UsuarioService usuarioService, InformacionPersonalService informacionPersonalService) {
        this.paisService = paisService;
        this.usuarioService = usuarioService;
        this.informacionPersonalService = informacionPersonalService;
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        List<PaisModel> paises = paisService.findAll();
        model.addAttribute("paises", paises);

        RegistroDTO registroDTO = new RegistroDTO();
        model.addAttribute("registro", registroDTO);
        model.addAttribute("generos", Genero.values());
        model.addAttribute("tipoDocumento", TipoDocumento.values());
        model.addAttribute("error", false);

        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registro(@Valid @ModelAttribute("registro") RegistroDTO registroDTO, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttrs) {

        UsuarioModel usuarioExistente = usuarioService.findByUsuario(registroDTO.getUsuario());
        if (usuarioExistente != null) {
            bindingResult.rejectValue("usuario", "errorUsuario", "El nombre de usuario ya existe");
        }

        if (bindingResult.hasErrors()) {
            List<PaisModel> paises = paisService.findAll();
            model.addAttribute("paises", paises);
            model.addAttribute("generos", Genero.values());
            model.addAttribute("tipoDocumento", TipoDocumento.values());
            model.addAttribute("error", true);
            return "auth/registro";
        }

        UsuarioModel nuevoUsuario = usuarioService.convertToModel(registroDTO);
        UsuarioModel usuarioGuardado = usuarioService.save(nuevoUsuario);

        if (usuarioGuardado != null) {
            InformacionPersonalModel nuevaInfoPersonal = informacionPersonalService.convertToModel(registroDTO);
            UsuarioModel usuario = usuarioService.findByUsuario(registroDTO.getUsuario());
            nuevaInfoPersonal.setIdUsuario(usuario);
            InformacionPersonalModel infoPersonalGuardada = informacionPersonalService.save(nuevaInfoPersonal);            
            
            if (infoPersonalGuardada != null) {
                redirectAttrs.addFlashAttribute("ok", "Usuario registrado correctamente. Inicie sesión para continuar.");
                return "redirect:/login";
            } else {
                usuarioService.delete(usuarioGuardado);
                redirectAttrs.addFlashAttribute("error", "Hubo un error al agregar la información. Intente nuevamente.");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Hubo un error al guardar la información. Intente nuevamente.");
        }

        return "redirect:/registro";
    }
}
