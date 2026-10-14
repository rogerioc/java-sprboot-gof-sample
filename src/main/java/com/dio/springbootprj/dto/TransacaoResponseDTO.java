package com.dio.springbootprj.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dio.springbootprj.model.TipoTransacao;
import com.dio.springbootprj.model.Transacao;

public class TransacaoResponseDTO {
    private String id;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private BigDecimal saldoApos;
    private LocalDateTime dataHora;
    private String descricao;

    public TransacaoResponseDTO() {
    }

    public TransacaoResponseDTO(String id, TipoTransacao tipo, BigDecimal valor, BigDecimal saldoApos,
            LocalDateTime dataHora, String descricao) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoApos = saldoApos;
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    public static TransacaoResponseDTO fromModel(Transacao transacao) {
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getSaldoApos(),
                transacao.getDataHora(),
                transacao.getDescricao());
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
}
