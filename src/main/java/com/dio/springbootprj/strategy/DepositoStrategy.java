package com.dio.springbootprj.strategy;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dio.springbootprj.exception.ValorInvalidoException;
import com.dio.springbootprj.model.Conta;
import com.dio.springbootprj.model.TipoTransacao;
import com.dio.springbootprj.model.Transacao;

/**
 * Padrão Strategy + Singleton:
 * Componente Spring (gerenciado como Singleton pelo container) que implementa
 * o algoritmo específico para a operação de Depósito.
 */
@Component
public class DepositoStrategy implements OperacaoBancariaStrategy {

    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.DEPOSITO;
    }

    @Override
    public Transacao executar(Conta conta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser maior que zero. Informado: " + valor);
        }

        BigDecimal novoSaldo = conta.getSaldo().add(valor);
        conta.setSaldo(novoSaldo);

        return new Transacao(
                UUID.randomUUID().toString(),
                TipoTransacao.DEPOSITO,
                valor,
                novoSaldo,
                "Depósito efetuado com sucesso na conta " + conta.getNumeroConta(),
                conta.getNumeroConta());
    }
}
