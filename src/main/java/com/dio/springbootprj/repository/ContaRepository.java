package com.dio.springbootprj.repository;

import java.util.List;
import java.util.Optional;

import com.dio.springbootprj.model.Conta;

/**
 * Padrão Repository:
 * Define a interface de acesso e coleção aos objetos de domínio Conta,
 * desacoplando a camada de negócio de qualquer mecanismo de persistência.
 */
public interface ContaRepository {

    Conta save(Conta conta);

    Optional<Conta> findByNumeroConta(String numeroConta);

    List<Conta> findAll();

    boolean existsByNumeroConta(String numeroConta);
}
