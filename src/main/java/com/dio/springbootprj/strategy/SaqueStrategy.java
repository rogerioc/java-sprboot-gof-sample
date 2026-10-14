package com.dio.springbootprj.strategy;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dio.springbootprj.exception.SaldoInsuficienteException;
import com.dio.springbootprj.exception.ValorInvalidoException;
import com.dio.springbootprj.model.Conta;
import com.dio.springbootprj.model.TipoTransacao;
import com.dio.springbootprj.model.Transacao;

/**
 * Padrão Strategy + Singleton:
 * Componente Spring (gerenciado como Singleton pelo container) que implementa
 * o algoritmo específico para a operação de Saque, validando regras de negócio de saldo.
 */
@Component
public class SaqueStrategy implements OperacaoBancariaStrategy {

    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.SAQUE;
    }

    @Override
    public Transacao executar(Conta conta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero. Informado: " + valor);
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException(String.format(
                    "Saldo insuficiente para realizar o saque na conta %s. Saldo atual: R$ %s, Valor solicitado: R$ %s",
                    conta.getNumeroConta(), conta.getSaldo(), valor));
        }

        BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
        conta.setSaldo(novoSaldo);

        return new Transacao(
                UUID.randomUUID().toString(),
                TipoTransacao.SAQUE,
                valor,
                novoSaldo,
                "Saque efetuado com sucesso na conta " + conta.getNumeroConta(),
                conta.getNumeroConta());
    }
}
