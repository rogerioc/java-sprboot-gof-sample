package com.dio.springbootprj.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dio.springbootprj.dto.ContaResponseDTO;
import com.dio.springbootprj.dto.CriarContaDTO;
import com.dio.springbootprj.dto.TransacaoResponseDTO;
import com.dio.springbootprj.exception.ContaNaoEncontradaException;
import com.dio.springbootprj.exception.ValorInvalidoException;
import com.dio.springbootprj.model.Conta;
import com.dio.springbootprj.model.TipoTransacao;
import com.dio.springbootprj.model.Transacao;
import com.dio.springbootprj.repository.ContaRepository;
import com.dio.springbootprj.repository.TransacaoRepository;
import com.dio.springbootprj.strategy.OperacaoBancariaStrategy;

/**
 * Padrão Facade + Singleton:
 * Este serviço atua como uma Fachada (Facade) que simplifica o acesso ao subsistema bancário.
 * Ele orquestra os repositórios (Repository) e as estratégias de operação bancária (Strategy),
 * fornecendo uma interface limpa e de alto nível para os controladores.
 * 
 * Sendo uma anotação @Service, o Spring o gerencia como Singleton no contexto da aplicação.
 */
@Service
public class BancoFacade {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final Map<TipoTransacao, OperacaoBancariaStrategy> strategies;

    public BancoFacade(
            ContaRepository contaRepository,
            TransacaoRepository transacaoRepository,
            List<OperacaoBancariaStrategy> strategyList) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        // Mapeia automaticamente todas as implementações de OperacaoBancariaStrategy (Strategy Pattern)
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(OperacaoBancariaStrategy::getTipo, Function.identity()));
    }

    public ContaResponseDTO criarConta(CriarContaDTO dto) {
        if (dto.getNumeroConta() == null || dto.getNumeroConta().isBlank()) {
            throw new ValorInvalidoException("O número da conta é obrigatório.");
        }
        if (contaRepository.existsByNumeroConta(dto.getNumeroConta())) {
            throw new ValorInvalidoException("Já existe uma conta com o número: " + dto.getNumeroConta());
        }

        BigDecimal saldoInicial = dto.getSaldoInicial() != null ? dto.getSaldoInicial() : BigDecimal.ZERO;
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValorInvalidoException("O saldo inicial não pode ser negativo.");
        }

        Conta conta = new Conta(dto.getNumeroConta(), dto.getTitular(), saldoInicial);
        Conta salva = contaRepository.save(conta);

        if (saldoInicial.compareTo(BigDecimal.ZERO) > 0) {
            Transacao inicial = new Transacao(
                    UUID.randomUUID().toString(),
                    TipoTransacao.DEPOSITO,
                    saldoInicial,
                    saldoInicial,
                    "Abertura de conta com saldo inicial",
                    salva.getNumeroConta());
            transacaoRepository.save(inicial);
            salva.adicionarTransacao(inicial);
        }

        return ContaResponseDTO.fromModel(salva);
    }

    public ContaResponseDTO buscarConta(String numeroConta) {
        Conta conta = obterContaEntidade(numeroConta);
        return ContaResponseDTO.fromModel(conta);
    }

    public List<ContaResponseDTO> listarContas() {
        return contaRepository.findAll().stream()
                .map(ContaResponseDTO::fromModel)
                .toList();
    }

    public TransacaoResponseDTO depositar(String numeroConta, BigDecimal valor) {
        return executarOperacao(numeroConta, TipoTransacao.DEPOSITO, valor);
    }

    public TransacaoResponseDTO sacar(String numeroConta, BigDecimal valor) {
        return executarOperacao(numeroConta, TipoTransacao.SAQUE, valor);
    }

    public List<TransacaoResponseDTO> consultarExtrato(String numeroConta) {
        obterContaEntidade(numeroConta); // Valida se a conta existe
        List<Transacao> transacoes = transacaoRepository.findByNumeroContaOrderByDataHoraDesc(numeroConta);
        return transacoes.stream()
                .map(TransacaoResponseDTO::fromModel)
                .toList();
    }

    private TransacaoResponseDTO executarOperacao(String numeroConta, TipoTransacao tipo, BigDecimal valor) {
        Conta conta = obterContaEntidade(numeroConta);

        OperacaoBancariaStrategy strategy = strategies.get(tipo);
        if (strategy == null) {
            throw new IllegalArgumentException("Nenhuma estratégia encontrada para o tipo: " + tipo);
        }

        // Executa a estratégia específica (Strategy Pattern)
        Transacao transacao = strategy.executar(conta, valor);

        // Persiste as alterações no repositório em memória (Repository Pattern)
        conta.adicionarTransacao(transacao);
        contaRepository.save(conta);
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return TransacaoResponseDTO.fromModel(transacaoSalva);
    }

    private Conta obterContaEntidade(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
    }
}
