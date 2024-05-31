package com.talentman.sgthtalentman.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.talentman.sgthtalentman.models.EstadoCandidatoModel;
import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.services.EstadoCandidatoService;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.UsuarioService;
import com.talentman.sgthtalentman.transversal.EstadoCandidato;

import jakarta.servlet.http.HttpSession;

@Controller
public class CandidatoController {

    private final InformacionPersonalService informacionPersonalService;
    private final UsuarioService usuarioService;
    private final EstadoCandidatoService estadoCandidatoService;

    public CandidatoController(InformacionPersonalService informacionPersonalService, UsuarioService usuarioService, EstadoCandidatoService estadoCandidatoService) {
        this.informacionPersonalService = informacionPersonalService;
        this.usuarioService = usuarioService;
        this.estadoCandidatoService = estadoCandidatoService;
    }

    @GetMapping("/listaCandidatos")
    public String candidatos(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        if (currentPage < 1) {
            currentPage = 1;
        }

        Page<InformacionPersonalModel> candidatoPage = informacionPersonalService.findPaginatedCandidatos(currentPage,pageSize);
        model.addAttribute("candidatoPage", candidatoPage);

        List<InformacionPersonalModel> candidatos = candidatoPage.getContent();
        model.addAttribute("candidatos", candidatos);

        EstadoCandidato[] estados = EstadoCandidato.values();
        model.addAttribute("estados", estados);

        return "empleados/listaCandidatos";
    }

    @PostMapping("/guardarEstado")
    public String guardarEstado(@RequestParam("id") Long id, @RequestParam("estado") String estado, RedirectAttributes redirectAttrs) {
        InformacionPersonalModel candidato = informacionPersonalService.findById(id);
        EstadoCandidatoModel estadoCandidato = new EstadoCandidatoModel();
        estadoCandidato.setInformacionPersonal(candidato);
        estadoCandidato.setEstado(estado);
        estadoCandidato.setFechaActualizacion(LocalDate.now());

        EstadoCandidatoModel estadoGuardado = estadoCandidatoService.save(estadoCandidato);

        if (estadoGuardado != null) {
            redirectAttrs.addFlashAttribute("ok", "El estado del candidato se actualizó correctamente");
        } else {
            redirectAttrs.addFlashAttribute("error", "Hubo un error al actualizar el estado del candidato");
        }

        return "redirect:/listaCandidatos";
    }

    // @GetMapping("/aplicarVacante/{id}")
    // public String aplicarVacante(HttpSession sesion, @PathVariable("id") Long idVacante) {
    //     Long idCandidato = (Long) sesion.getAttribute("idInformacionPersonal");

    //     return null;
        
    // }
}
