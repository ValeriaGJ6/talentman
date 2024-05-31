package com.talentman.sgthtalentman.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talentman.sgthtalentman.services.NominaService;

@Controller
public class NominaController {

    private final NominaService nominaService;

    public NominaController(NominaService nominaService) {
        this.nominaService = nominaService;
    }

    @GetMapping("/nomina")
    public String nomina(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        if (currentPage < 1) {
            currentPage = 1;
        }

        

        return "nomina/nomina";
    }

}
