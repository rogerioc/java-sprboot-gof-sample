package com.dio.springbootprj.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dio.springbootprj.dto.CriarContaDTO;
import com.dio.springbootprj.facade.BancoFacade;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(BancoFacade bancoFacade) {
        return args -> {
            try {
                bancoFacade.criarConta(new CriarContaDTO("12345-6", "Rogerio Silva", new BigDecimal("1000.00")));
                bancoFacade.criarConta(new CriarContaDTO("98765-4", "Maria Oliveira", new BigDecimal("500.00")));
                System.out.println(">>> Banco de dados inicializado com contas de exemplo: 12345-6 e 98765-4 <<<");
            } catch (Exception e) {
                // Contas já existentes
            }
        };
    }
}
