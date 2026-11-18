package com.example.loja.controller;

import com.example.loja.model.Camisa;
import com.example.loja.model.Cliente;
import com.example.loja.model.Venda;
import com.example.loja.repository.CamisaRepository;
import com.example.loja.repository.ClienteRepository;
import com.example.loja.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CamisaRepository camisaRepository;

    @GetMapping
    public String listarVendas(Model model) {
        model.addAttribute("vendas", vendaRepository.findAll());
        return "vendas/listar";
    }

    // MÉTODO ATUALIZADO PARA RECEBER O CAMISA ID DA HOME
    @GetMapping("/novo")
    public String mostrarFormularioNovaVenda(@RequestParam(value = "camisaId", required = false) Long camisaId, Model model) {
        Venda venda = new Venda();
        List<Long> camisaIdsSelecionadas = new ArrayList<>();

        // Se o usuário clicou em "Comprar" na Home
        if (camisaId != null) {
            Camisa camisa = camisaRepository.findById(camisaId).orElse(null);
            if (camisa != null) {
                venda.getCamisas().add(camisa);
                camisaIdsSelecionadas.add(camisaId);
            }
        }

        model.addAttribute("venda", venda);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("camisas", camisaRepository.findAll());
        model.addAttribute("camisaIdsSelecionadas", camisaIdsSelecionadas);

        return "vendas/form";
    }

    @PostMapping("/salvar")
    public String salvarVenda(@ModelAttribute("venda") Venda venda,
                              @RequestParam(value = "camisaIds", required = false) List<Long> camisaIds,
                              Model model) {
        
        // 1. Correção de segurança para o Cliente (evita erros do Hibernate)
        if (venda.getCliente() != null && venda.getCliente().getId() != null) {
            Cliente clienteCompleto = clienteRepository.findById(venda.getCliente().getId()).orElse(null);
            venda.setCliente(clienteCompleto);
        }

        // 2. Validação: precisa ter pelo menos uma camisa
        if (camisaIds == null || camisaIds.isEmpty()) {
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("camisas", camisaRepository.findAll());
            model.addAttribute("erro", "Selecione pelo menos uma camisa.");
            return "vendas/form";
        }

        // 3. Limpa a lista antiga (importante para o caso de Edição) e adiciona as novas
        venda.getCamisas().clear();
        for (Long id : camisaIds) {
            Camisa camisa = camisaRepository.findById(id).orElse(null);
            if (camisa != null) {
                venda.getCamisas().add(camisa);
            }
        }

        vendaRepository.save(venda);
        return "redirect:/vendas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarVenda(@PathVariable("id") long id, Model model) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        // Prepara os IDs das camisas para marcar no <select multiple>
        List<Long> camisaIdsSelecionadas = new ArrayList<>();
        for (Camisa c : venda.getCamisas()) {
            camisaIdsSelecionadas.add(c.getId());
        }

        model.addAttribute("venda", venda);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("camisas", camisaRepository.findAll());
        model.addAttribute("camisaIdsSelecionadas", camisaIdsSelecionadas);

        return "vendas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVenda(@PathVariable("id") long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        vendaRepository.delete(venda);
        return "redirect:/vendas";
    }
}