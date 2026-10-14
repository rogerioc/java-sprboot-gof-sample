package com.dio.springbootprj.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.springbootprj.dto.ContaResponseDTO;
import com.dio.springbootprj.dto.CriarContaDTO;
import com.dio.springbootprj.dto.TransacaoRequestDTO;
import com.dio.springbootprj.dto.TransacaoResponseDTO;
import com.dio.springbootprj.facade.BancoFacade;

/**
 * Padrão Singleton:
 * O controlador BancoController é um Singleton gerenciado pelo Spring MVC.
 * Ele depende unicamente da fachada BancoFacade (Facade Pattern) para delegar todas as operações.
 */
@RestController
@RequestMapping("/api/contas")
public class BancoController {

    private final BancoFacade bancoFacade;

    public BancoController(BancoFacade bancoFacade) {
        this.bancoFacade = bancoFacade;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody CriarContaDTO dto) {
        ContaResponseDTO response = bancoFacade.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarContas() {
        return ResponseEntity.ok(bancoFacade.listarContas());
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(bancoFacade.buscarConta(numeroConta));
    }

    @PostMapping("/{numeroConta}/deposito")
    public ResponseEntity<TransacaoResponseDTO> depositar(
            @PathVariable String numeroConta,
            @RequestBody TransacaoRequestDTO request) {
        TransacaoResponseDTO response = bancoFacade.depositar(numeroConta, request.getValor());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{numeroConta}/saque")
    public ResponseEntity<TransacaoResponseDTO> sacar(
            @PathVariable String numeroConta,
            @RequestBody TransacaoRequestDTO request) {
        TransacaoResponseDTO response = bancoFacade.sacar(numeroConta, request.getValor());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{numeroConta}/extrato")
    public ResponseEntity<List<TransacaoResponseDTO>> consultarExtrato(@PathVariable String numeroConta) {
        return ResponseEntity.ok(bancoFacade.consultarExtrato(numeroConta));
    }
}
