package com.dio.springbootprj.repository;

import java.util.List;

import com.dio.springbootprj.model.Transacao;

/**
 * Padrão Repository:
 * Define a interface de acesso aos dados da entidade Transacao.
 */
public interface TransacaoRepository {

    Transacao save(Transacao transacao);

    List<Transacao> findByNumeroContaOrderByDataHoraDesc(String numeroConta);
}
