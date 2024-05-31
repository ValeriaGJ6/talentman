package com.talentman.sgthtalentman.controllers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.talentman.sgthtalentman.dto.VacanteDTO;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.models.VacanteModel;
import com.talentman.sgthtalentman.services.MunicipioService;
import com.talentman.sgthtalentman.services.PaisService;
import com.talentman.sgthtalentman.services.UsuarioService;
import com.talentman.sgthtalentman.services.VacanteService;
import com.talentman.sgthtalentman.transversal.Modalidad;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class VacanteController {

    private final PaisService paisService;
    private final VacanteService vacanteService;
    private final MunicipioService municipioService;
    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(VacanteController.class);

    public VacanteController(PaisService paisService, VacanteService vacanteService,
            MunicipioService municipioService, UsuarioService usuarioService) {
        this.paisService = paisService;
        this.vacanteService = vacanteService;
        this.municipioService = municipioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String vacantes() {
        return "vacantes/vacantes";
    }

    @GetMapping("/listaVacantes")
    public String listaVacantes(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, HttpSession sesion) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        if (currentPage < 1) {
            currentPage = 1;
        }

        Page<VacanteModel> vacantePage = vacanteService.findPaginated(currentPage, pageSize);

        model.addAttribute("vacantePage", vacantePage);
        List<VacanteModel> vacantes = vacantePage.getContent();
        model.addAttribute("vacantes", vacantes);       

        return "vacantes/listaVacantes";
    }

    @GetMapping("/crearVacante")
    public String mostrarFormularioCreacionVacante(Model model) {
        VacanteDTO nuevaVacante = new VacanteDTO();
        nuevaVacante.setModalidad(Modalidad.Presencial);
        nuevaVacante.setActivo(true);
        model.addAttribute("nuevaVacante", nuevaVacante);

        List<PaisModel> paises = paisService.findAll();
        model.addAttribute("paises", paises);
        model.addAttribute("error", false);

        return "vacantes/crearVacante";
    }

    @PostMapping("/crearVacante")
    public String crearVacante(@Valid @ModelAttribute("nuevaVacante") VacanteDTO vacante, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            List<PaisModel> paises = paisService.findAll();
            model.addAttribute("paises", paises);
            model.addAttribute("error", true);
            return "vacantes/crearVacante";
        }

        VacanteModel vacanteModel = vacanteService.convertToModel(vacante);
        VacanteModel nuevaVacante = vacanteService.save(vacanteModel);

        // Comprueba que la vacante se ha guardado correctamente
        if (nuevaVacante != null) {
            redirectAttrs.addFlashAttribute("ok", "La vacante se agregó correctamente");
        } else {
            redirectAttrs.addFlashAttribute("error", "Hubo un error al agregar la vacante");
        }

        return "redirect:/listaVacantes";
    }

    @GetMapping("/editarVacante/{id}")
    public String mostrarFormularioEdicionVacante(@PathVariable("id") Long id, Model model) {
        VacanteModel vacante = vacanteService.findById(id);

        if (vacante != null) {
            VacanteDTO vacanteDTO = vacanteService.convertToDTO(vacante);
            vacanteDTO.setId(vacante.getId());
            List<PaisModel> paises = paisService.findAll();
            logger.info("Vacante encontrada: " + vacanteDTO.toString());
            model.addAttribute("paises", paises);
            model.addAttribute("error", false);
            model.addAttribute("vacanteAEditar", vacanteDTO);
            return "vacantes/editarVacante";
        } else {
            return "redirect:/listaVacantes";
        }
    }

    @PostMapping("/editarVacante")
    public String editarVacante(@Valid @ModelAttribute("vacanteAEditar") VacanteDTO vacante, Model model,
            BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            List<PaisModel> paises = paisService.findAll();
            model.addAttribute("paises", paises);
            model.addAttribute("error", true);
            return "vacantes/crearVacante";
        }

        VacanteModel vacanteModel = new VacanteModel();
        vacanteModel.setId(vacante.getId());
        vacanteModel.setNombre(vacante.getNombre());
        vacanteModel.setDescripcion(vacante.getDescripcion());

        PaisModel paisElegido = paisService.findById(vacante.getIdPais());
        vacanteModel.setPais(paisElegido);

        if (vacante.getIdMunicipio() != null) {
            MunicipioModel municipio = municipioService.findById(vacante.getIdMunicipio());
            vacanteModel.setMunicipio(municipio);
        } else {
            vacanteModel.setMunicipio(null);
            vacanteModel.setCiudadOtroPais(vacante.getCiudadOtroPais());
        }

        vacanteModel.setFechaPublicacion(LocalDate.now());
        vacanteModel.setModalidad(vacante.getModalidad());
        vacanteModel.setActivo(vacante.isActivo());

        VacanteModel nuevaVacante = vacanteService.update(vacanteModel);

        // Comprueba que la vacante se ha guardado correctamente
        if (nuevaVacante != null) {
            redirectAttrs.addFlashAttribute("ok", "La vacante se actualizó correctamente");
        } else {
            redirectAttrs.addFlashAttribute("error", "Hubo un error al actualizar la vacante");
        }

        return "redirect:/listaVacantes";
    }

    @GetMapping("/verVacante/{id}")
    public ResponseEntity<VacanteDTO> verVacante(@PathVariable("id") Long id, Model model) {
        VacanteModel vacante = vacanteService.findById(id);

        if (vacante != null) {
            VacanteDTO vacanteDTO = vacanteService.convertToDTO(vacante);

            String nombrePais = vacante.getPais().getNombre();
            vacanteDTO.setNombrePais(nombrePais);

            if (vacante.getMunicipio() != null) {
                String nombreDepartamento = vacante.getMunicipio().getDepartamento().getNombre();
                vacanteDTO.setNombreDepartamento(nombreDepartamento);

                String nombreMunicipio = vacante.getMunicipio().getNombre();
                vacanteDTO.setNombreMunicipio(nombreMunicipio);
            } else {
                vacanteDTO.setNombreDepartamento(null);
                vacanteDTO.setNombreMunicipio(null);
            }

            return ResponseEntity.ok(vacanteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/eliminarVacante/{id}")
    public String eliminarVacante(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        if (id != null) {
            vacanteService.delete(id);
            redirectAttrs.addFlashAttribute("ok", "La vacante se eliminó correctamente");
        } else {
            redirectAttrs.addFlashAttribute("error", "Hubo un error al eliminar la vacante");
        }

        return "redirect:/listaVacantes";
    }
}
