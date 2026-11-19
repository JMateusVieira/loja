package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Venda;

public interface VendaRepository extends CrudRepository<Venda, Long> {}