package com.example.loja.controller;

import com.example.loja.repository.CamisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CamisaRepository camisaRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("camisas", camisaRepository.findAll());
        return "index";
    }
    
    @GetMapping("/loja")
    public String loja() {
        return "loja";
    }
}