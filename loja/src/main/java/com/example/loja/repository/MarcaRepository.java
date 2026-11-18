package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Marca;
// ... outras importações


public interface MarcaRepository extends CrudRepository<Marca, Long> {}
// Faça o mesmo para Marca, Cliente e Venda.
