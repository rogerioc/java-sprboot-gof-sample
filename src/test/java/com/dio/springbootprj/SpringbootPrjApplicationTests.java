package com.dio.springbootprj;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dio.springbootprj.dto.ContaResponseDTO;
import com.dio.springbootprj.dto.CriarContaDTO;
import com.dio.springbootprj.dto.TransacaoResponseDTO;
import com.dio.springbootprj.exception.SaldoInsuficienteException;
import com.dio.springbootprj.exception.ValorInvalidoException;
import com.dio.springbootprj.facade.BancoFacade;
import com.dio.springbootprj.model.TipoTransacao;

@SpringBootTest
class SpringbootPrjApplicationTests {

    @Autowired
    private BancoFacade bancoFacade;

    @Test
    void contextLoads() {
        assertNotNull(bancoFacade);
    }

    @Test
    void deveCriarContaComSucesso() {
        CriarContaDTO novaConta = new CriarContaDTO("11223-3", "Carlos Teste", new BigDecimal("200.00"));
        ContaResponseDTO response = bancoFacade.criarConta(novaConta);

        assertEquals("11223-3", response.getNumeroConta());
        assertEquals("Carlos Teste", response.getTitular());
        assertEquals(new BigDecimal("200.00"), response.getSaldo());
    }

    @Test
    void deveRealizarDepositoComSucesso() {
        CriarContaDTO novaConta = new CriarContaDTO("33445-5", "Ana Teste", new BigDecimal("100.00"));
        bancoFacade.criarConta(novaConta);

        TransacaoResponseDTO transacao = bancoFacade.depositar("33445-5", new BigDecimal("150.00"));

        assertEquals(TipoTransacao.DEPOSITO, transacao.getTipo());
        assertEquals(new BigDecimal("150.00"), transacao.getValor());
        assertEquals(new BigDecimal("250.00"), transacao.getSaldoApos());
    }

    @Test
    void deveRealizarSaqueComSucesso() {
        CriarContaDTO novaConta = new CriarContaDTO("55667-7", "Bruno Teste", new BigDecimal("300.00"));
        bancoFacade.criarConta(novaConta);

        TransacaoResponseDTO transacao = bancoFacade.sacar("55667-7", new BigDecimal("120.00"));

        assertEquals(TipoTransacao.SAQUE, transacao.getTipo());
        assertEquals(new BigDecimal("120.00"), transacao.getValor());
        assertEquals(new BigDecimal("180.00"), transacao.getSaldoApos());
    }

    @Test
    void deveLancarExcecaoQuandoSaldoForInsuficiente() {
        CriarContaDTO novaConta = new CriarContaDTO("77889-9", "Pedro Teste", new BigDecimal("50.00"));
        bancoFacade.criarConta(novaConta);

        assertThrows(SaldoInsuficienteException.class, () -> {
            bancoFacade.sacar("77889-9", new BigDecimal("100.00"));
        });
    }

    @Test
    void deveLancarExcecaoParaValoresInvalidos() {
        CriarContaDTO novaConta = new CriarContaDTO("99001-1", "Julia Teste", new BigDecimal("100.00"));
        bancoFacade.criarConta(novaConta);

        assertThrows(ValorInvalidoException.class, () -> {
            bancoFacade.depositar("99001-1", new BigDecimal("-10.00"));
        });

        assertThrows(ValorInvalidoException.class, () -> {
            bancoFacade.sacar("99001-1", BigDecimal.ZERO);
        });
    }
}
