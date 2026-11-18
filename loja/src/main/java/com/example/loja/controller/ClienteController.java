package com.example.loja.controller;

import com.example.loja.model.Cliente;
import com.example.loja.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable("id") long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        clienteRepository.delete(cliente);
        return "redirect:/clientes";
    }
}