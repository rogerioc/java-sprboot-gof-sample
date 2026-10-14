package com.dio.springbootprj.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(String numeroConta) {
        super("Conta com número " + numeroConta + " não foi encontrada.");
    }
}
