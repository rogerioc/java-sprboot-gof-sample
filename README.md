# Sistema Bancário REST API (Spring Boot & Design Patterns)

Uma API REST desenvolvida em **Java 21** e **Spring Boot** com **Gradle**, aplicando padrões de projeto clássicos (GoF e Enterprise Patterns) para operações bancárias como abertura de conta, depósitos, saques e consulta de extrato, com tratamento rigoroso de saldo e validações de regras de negócio.

O projeto utiliza o padrão **Repository** puro em memória com coleções thread-safe (`ConcurrentHashMap` e `CopyOnWriteArrayList`), mantendo o foco conceitual nos padrões de projeto sem a complexidade de ORM ou bibliotecas externas de banco de dados.

> [!NOTE]
> **Projeto para Fins Educacionais e de Estudo**  
> Este projeto foi desenvolvido exclusivamente com o objetivo de demonstrar, de forma prática e didática, a aplicação de **Padrões de Projeto (Design Patterns)** com o ecossistema Spring Boot.  
> Trata-se de um projeto de **estudo e experimentação**, e **não de um sistema bancário final ou pronto para produção**. Por essa razão, optou-se por uma persistência simplificada em memória (evitando o acoplamento precoce com infraestrutura de banco de dados/ORM) e não foram implementados requisitos avançados de segurança corporativa (como autenticação OAuth2/JWT, auditoria fiscal ou mensageria distribuída).

---

## 🎯 Padrões de Projeto Aplicados

### 1. Singleton Pattern
* No ecossistema Spring, componentes anotados com `@Service`, `@Component`, `@Repository` e `@RestController` possuem escopo **Singleton** por padrão.
* As classes `BancoFacade`, `InMemoryContaRepository`, `InMemoryTransacaoRepository`, `DepositoStrategy`, `SaqueStrategy` e `BancoController` possuem instâncias únicas no contexto da aplicação, garantindo reuso eficiente de memória e injeção de dependências desacoplada via Inversão de Controle (IoC).

### 2. Strategy Pattern
* **Interface**: `OperacaoBancariaStrategy`
* **Implementações**:
  * `DepositoStrategy`: Valida montantes positivos e credita o valor ao saldo da conta.
  * `SaqueStrategy`: Executa a validação de regras de negócio de saldo. Se o valor solicitado ultrapassar o saldo atual da conta, lança a exceção de negócio `SaldoInsuficienteException`.
* **Vantagem**: Novas operações bancárias (ex.: Transferência, Pix, Aplicação em Renda Fixa) podem ser adicionadas criando novas classes que implementam `OperacaoBancariaStrategy`, sem modificar as classes existentes (Princípio Aberto/Fechado - OCP). O Spring injeta dinamicamente todas as implementações disponíveis.

### 3. Repository Pattern
* Implementado de forma pura através de interfaces e implementações em memória:
  * `ContaRepository` (Interface) -> `InMemoryContaRepository` (`ConcurrentHashMap` thread-safe).
  * `TransacaoRepository` (Interface) -> `InMemoryTransacaoRepository` (`CopyOnWriteArrayList` thread-safe).
* **Vantagem**: Isola completamente a camada de negócio de como os dados são guardados. Se no futuro for necessário plugar JPA, MongoDB ou Redis, a camada de negócio (`BancoFacade`) permanece 100% inalterada.

### 4. Facade Pattern
* **Classe**: `BancoFacade`
* Atua como uma fachada centralizada que simplifica a interface para o controlador REST (`BancoController`), abstraindo a complexidade de orquestrar:
  * Localização de entidades no repositório;
  * Resolução dinâmica da estratégia correta (`OperacaoBancariaStrategy`);
  * Execução da operação e validação de regras financeiras;
  * Registro de auditoria em transações;
  * Conversão de modelos para Data Transfer Objects (DTOs).

---

## 🛠️ Tecnologias Utilizadas

* **Java 21** (LTS)
* **Spring Boot** (Spring Web)
* **Gradle** (Kotlin DSL)
* **Java Collections** (`ConcurrentHashMap`, `CopyOnWriteArrayList` para persistência thread-safe)
* **JUnit 5** (Testes automatizados integrados)

---

## 📁 Estrutura do Projeto

```
SpringBootPrj/
├── build.gradle.kts                      # Dependências limpas (Spring Web + Testes)
├── settings.gradle.kts                   # Configurações do projeto
├── src/
│   ├── main/
│   │   ├── java/com/dio/springbootprj/
│   │   │   ├── SpringbootPrjApplication.java  # Bootstrap da aplicação
│   │   │   ├── config/
│   │   │   │   └── DataLoader.java             # Inicialização de contas para teste
│   │   │   ├── controller/
│   │   │   │   └── BancoController.java        # Endpoints REST
│   │   │   ├── dto/
│   │   │   │   ├── CriarContaDTO.java
│   │   │   │   ├── ContaResponseDTO.java
│   │   │   │   ├── TransacaoRequestDTO.java
│   │   │   │   └── TransacaoResponseDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── ContaNaoEncontradaException.java
│   │   │   │   ├── SaldoInsuficienteException.java
│   │   │   │   ├── ValorInvalidoException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java # @RestControllerAdvice
│   │   │   ├── facade/
│   │   │   │   └── BancoFacade.java            # Facade Pattern
│   │   │   ├── model/
│   │   │   │   ├── Conta.java                  # Modelo POJO
│   │   │   │   ├── Transacao.java              # Modelo POJO
│   │   │   │   └── TipoTransacao.java          # Enum (DEPOSITO, SAQUE)
│   │   │   ├── repository/
│   │   │   │   ├── ContaRepository.java        # Interface do Repositório
│   │   │   │   ├── TransacaoRepository.java    # Interface do Repositório
│   │   │   │   └── impl/
│   │   │   │       ├── InMemoryContaRepository.java     # Singleton em memória
│   │   │   │       └── InMemoryTransacaoRepository.java # Singleton em memória
│   │   │   └── strategy/
│   │   │       ├── OperacaoBancariaStrategy.java # Strategy Interface
│   │   │       ├── DepositoStrategy.java
│   │   │       └── SaqueStrategy.java
│   │   └── resources/
│   │       └── application.properties          # Porta e nome da aplicação
│   └── test/
│       └── java/com/dio/springbootprj/
│           └── SpringbootPrjApplicationTests.java # Testes automatizados
```

---

## 🚀 Como Executar o Projeto

No terminal, a partir do diretório raiz do projeto:

```bash
cd SpringBootPrj
./gradlew bootRun
```

A aplicação iniciará na porta `8080`.

---

## 📚 Endpoints da API

Ao iniciar, a classe `DataLoader` cria automaticamente duas contas em memória para testes:
* Conta `12345-6` (Titular: Rogerio Silva, Saldo: R$ 1.000,00)
* Conta `98765-4` (Titular: Maria Oliveira, Saldo: R$ 500,00)

### 1. Criar Nova Conta
* **Rota:** `POST /api/contas`
* **Exemplo de Requisição:**
  ```bash
  curl -X POST http://localhost:8080/api/contas \
       -H "Content-Type: application/json" \
       -d '{
         "numeroConta": "54321-0",
         "titular": "Ana Souza",
         "saldoInicial": 350.00
       }'
  ```

---

### 2. Listar Todas as Contas
* **Rota:** `GET /api/contas`
* **Exemplo de Requisição:**
  ```bash
  curl -X GET http://localhost:8080/api/contas
  ```

---

### 3. Consultar Conta Específica
* **Rota:** `GET /api/contas/{numeroConta}`
* **Exemplo de Requisição:**
  ```bash
  curl -X GET http://localhost:8080/api/contas/12345-6
  ```

---

### 4. Realizar Depósito
* **Rota:** `POST /api/contas/{numeroConta}/deposito`
* **Exemplo de Requisição:**
  ```bash
  curl -X POST http://localhost:8080/api/contas/12345-6/deposito \
       -H "Content-Type: application/json" \
       -d '{"valor": 250.00}'
  ```

---

### 5. Realizar Saque
* **Rota:** `POST /api/contas/{numeroConta}/saque`
* **Exemplo de Requisição:**
  ```bash
  curl -X POST http://localhost:8080/api/contas/12345-6/saque \
       -H "Content-Type: application/json" \
       -d '{"valor": 400.00}'
  ```

---

### 6. Teste de Saldo Insuficiente
* **Exemplo de Requisição:**
  ```bash
  curl -i -X POST http://localhost:8080/api/contas/12345-6/saque \
       -H "Content-Type: application/json" \
       -d '{"valor": 99999.00}'
  ```
* Retorna `400 Bad Request` com o erro explicativo formatado pelo `GlobalExceptionHandler`.

---

### 7. Consultar Extrato da Conta
* **Rota:** `GET /api/contas/{numeroConta}/extrato`
* **Exemplo de Requisição:**
  ```bash
  curl -X GET http://localhost:8080/api/contas/12345-6/extrato
  ```

---

## 🧪 Execução dos Testes Automatizados

```bash
./gradlew test
```
