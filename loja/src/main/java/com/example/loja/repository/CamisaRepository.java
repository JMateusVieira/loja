package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Camisa;
// ... outras importações

public interface CamisaRepository extends CrudRepository<Camisa, Long> {}
// Faça o mesmo para Marca, Cliente e Venda.