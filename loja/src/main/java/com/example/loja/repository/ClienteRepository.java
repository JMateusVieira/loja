package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Cliente;
// ... outras importações
public interface ClienteRepository extends CrudRepository<Cliente, Long> {}
// Faça o mesmo para Marca, Cliente e Venda.