package com.dio.springbootprj.dto;

import java.math.BigDecimal;

import com.dio.springbootprj.model.Conta;

public class ContaResponseDTO {
    private String numeroConta;
    private String titular;
    private BigDecimal saldo;

    public ContaResponseDTO() {
    }

    public ContaResponseDTO(String numeroConta, String titular, BigDecimal saldo) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldo;
    }

    public static ContaResponseDTO fromModel(Conta conta) {
        return new ContaResponseDTO(
                conta.getNumeroConta(),
                conta.getTitular(),
                conta.getSaldo());
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
}
