package com.dio.springbootprj.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.dio.springbootprj.model.Conta;
import com.dio.springbootprj.repository.ContaRepository;

/**
 * Padrão Repository + Singleton:
 * Implementação do repositório em memória utilizando Java Collections (ConcurrentHashMap).
 * O Spring gerencia esta classe como um Bean Singleton.
 */
@Repository
public class InMemoryContaRepository implements ContaRepository {

    private final Map<String, Conta> bancoDeDados = new ConcurrentHashMap<>();

    @Override
    public Conta save(Conta conta) {
        bancoDeDados.put(conta.getNumeroConta(), conta);
        return conta;
    }

    @Override
    public Optional<Conta> findByNumeroConta(String numeroConta) {
        return Optional.ofNullable(bancoDeDados.get(numeroConta));
    }

    @Override
    public List<Conta> findAll() {
        return new ArrayList<>(bancoDeDados.values());
    }

    @Override
    public boolean existsByNumeroConta(String numeroConta) {
        return bancoDeDados.containsKey(numeroConta);
    }
}
