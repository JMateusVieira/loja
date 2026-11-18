package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Venda;
// ... outras importações

public interface VendaRepository extends CrudRepository<Venda, Long> {}
// Faça o mesmo para Marca, Cliente e Venda.