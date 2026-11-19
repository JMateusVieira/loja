package com.example.loja.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.loja.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {}