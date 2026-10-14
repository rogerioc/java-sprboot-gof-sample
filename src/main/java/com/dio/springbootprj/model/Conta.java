package com.dio.springbootprj.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private String numeroConta;
    private String titular;
    private BigDecimal saldo;
    private List<Transacao> transacoes = new ArrayList<>();

    public Conta() {
    }

    public Conta(String numeroConta, String titular, BigDecimal saldoInicial) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
    }
}
