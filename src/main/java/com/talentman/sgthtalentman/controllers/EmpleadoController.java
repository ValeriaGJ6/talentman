package com.talentman.sgthtalentman.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.UsuarioService;

@Controller
public class EmpleadoController {

    private final InformacionPersonalService informacionPersonalService;
    private final UsuarioService usuarioService;

    public EmpleadoController(InformacionPersonalService informacionPersonalService, UsuarioService usuarioService) {
        this.informacionPersonalService = informacionPersonalService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/empleados")
    public String empleados(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        if (currentPage < 1) {
            currentPage = 1;
        }

        Page<InformacionPersonalModel> empleadoPage = informacionPersonalService.findPaginatedEmpleados(currentPage,
                pageSize);

        model.addAttribute("empleadoPage", empleadoPage);
        List<InformacionPersonalModel> empleados = empleadoPage.getContent();

        model.addAttribute("empleados", empleados);

        return "empleados/listaEmpleados";
    }

    // @GetMapping("/verEmpleado/{id}")
    // public ResponseEntity<> verEmpleado(Model model, @RequestParam("id") Long id)
    // {
    // InformacionPersonalModel empleado = informacionPersonalService.findById(id);
    // UsuarioModel usuario = usuarioService.findByInformacionPersonal(empleado);
    // Map<String, Object> empleadoMap = new HashMap<>();
    // empleadoMap.put("empleado", empleado);
    // empleadoMap.put("usuario", usuario);
    // model.addAllAttributes(empleadoMap);
    // return "empleados/verEmpleado";
    // }

}
