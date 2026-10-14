package com.dio.springbootprj.dto;

import java.math.BigDecimal;

public class CriarContaDTO {
    private String numeroConta;
    private String titular;
    private BigDecimal saldoInicial;

    public CriarContaDTO() {
    }

    public CriarContaDTO(String numeroConta, String titular, BigDecimal saldoInicial) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldoInicial = saldoInicial;
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

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
