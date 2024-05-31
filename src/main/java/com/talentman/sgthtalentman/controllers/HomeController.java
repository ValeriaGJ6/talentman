package com.talentman.sgthtalentman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.talentman.sgthtalentman.services.InformacionPersonalService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    InformacionPersonalService informacionPersonalService;

    @GetMapping("/home")
    public String home(Model model, HttpSession sesion) {
        return "home";
    }

}
