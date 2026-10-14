package com.dio.springbootprj.dto;

import java.math.BigDecimal;

public class TransacaoRequestDTO {
    private BigDecimal valor;

    public TransacaoRequestDTO() {
    }

    public TransacaoRequestDTO(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
