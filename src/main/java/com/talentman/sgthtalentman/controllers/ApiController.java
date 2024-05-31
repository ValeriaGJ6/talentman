package com.talentman.sgthtalentman.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.talentman.sgthtalentman.dto.DepartamentoDTO;
import com.talentman.sgthtalentman.dto.MunicipioDTO;
import com.talentman.sgthtalentman.services.DepartamentoService;
import com.talentman.sgthtalentman.services.MunicipioService;

@RestController
public class ApiController {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/departamentos")
    public List<DepartamentoDTO> getDepartamentos() {
        return departamentoService.findAll();
    }

    @GetMapping("/municipios/{idDepto}")
    public List<MunicipioDTO> getMunicipios(@PathVariable Long idDepto) {
        return municipioService.findByDepartamentoId(idDepto);
    }
}
