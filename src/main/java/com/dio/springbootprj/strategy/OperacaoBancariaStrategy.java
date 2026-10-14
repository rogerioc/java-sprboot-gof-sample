package com.dio.springbootprj.strategy;

import java.math.BigDecimal;

import com.dio.springbootprj.model.Conta;
import com.dio.springbootprj.model.TipoTransacao;
import com.dio.springbootprj.model.Transacao;

/**
 * Padrão Strategy: Define a interface comum para diferentes algoritmos
 * de operações bancárias (ex: Depósito, Saque).
 */
public interface OperacaoBancariaStrategy {

    TipoTransacao getTipo();

    Transacao executar(Conta conta, BigDecimal valor);
}
