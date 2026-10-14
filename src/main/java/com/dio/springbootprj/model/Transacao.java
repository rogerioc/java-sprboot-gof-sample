package com.dio.springbootprj.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {

    private String id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private BigDecimal saldoApos;
    private LocalDateTime dataHora;
    private String descricao;
    private String numeroConta;

    public Transacao() {
    }

    public Transacao(String id, TipoTransacao tipo, BigDecimal valor, BigDecimal saldoApos, String descricao, String numeroConta) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoApos = saldoApos;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
        this.numeroConta = numeroConta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldoApos() {
        return saldoApos;
    }

    public void setSaldoApos(BigDecimal saldoApos) {
        this.saldoApos = saldoApos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
