package com.example.loja.controller;

import com.example.loja.model.Camisa;
import com.example.loja.repository.CamisaRepository;
import com.example.loja.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/camisas")
public class CamisaController {

    @Autowired
    private CamisaRepository camisaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public String listarCamisas(Model model) {
        model.addAttribute("camisas", camisaRepository.findAll());
        return "camisas/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovaCamisa(Model model) {
        model.addAttribute("camisa", new Camisa());
        model.addAttribute("marcas", marcaRepository.findAll());
        return "camisas/form";
    }

    @PostMapping("/salvar")
    public String salvarCamisa(@Valid @ModelAttribute("camisa") Camisa camisa,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("marcas", marcaRepository.findAll());
            return "camisas/form";
        }
        camisaRepository.save(camisa);
        return "redirect:/camisas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCamisa(@PathVariable("id") long id, Model model) {
        Camisa camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("camisa", camisa);
        model.addAttribute("marcas", marcaRepository.findAll());
        return "camisas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCamisa(@PathVariable("id") long id) {
        Camisa camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        camisaRepository.delete(camisa);
        return "redirect:/camisas";
    }
}
