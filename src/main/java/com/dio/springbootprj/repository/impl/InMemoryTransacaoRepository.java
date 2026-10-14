package com.dio.springbootprj.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.dio.springbootprj.model.Transacao;
import com.dio.springbootprj.repository.TransacaoRepository;

/**
 * Padrão Repository + Singleton:
 * Implementação do repositório de transações em memória utilizando Java Collections thread-safe.
 */
@Repository
public class InMemoryTransacaoRepository implements TransacaoRepository {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    @Override
    public Transacao save(Transacao transacao) {
        transacoes.add(transacao);
        return transacao;
    }

    @Override
    public List<Transacao> findByNumeroContaOrderByDataHoraDesc(String numeroConta) {
        List<Transacao> filtradas = new ArrayList<>();
        for (Transacao t : transacoes) {
            if (t.getNumeroConta() != null && t.getNumeroConta().equals(numeroConta)) {
                filtradas.add(t);
            }
        }
        filtradas.sort(Comparator.comparing(Transacao::getDataHora).reversed());
        return Collections.unmodifiableList(filtradas);
    }
}
