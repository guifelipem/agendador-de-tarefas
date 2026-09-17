# API de Usuários

A API de Usuários é responsável pelo gerenciamento de identidade da aplicação. O serviço realiza cadastro e autenticação de usuários, além do gerenciamento de informações pessoais utilizadas pelos demais microsserviços do sistema.

## Arquitetura

Este serviço faz parte do sistema de Agendamento de Tarefas, composto pelos seguintes microsserviços:

- API de Usuários
- API de Agendamento de Tarefas
- Serviço de Notificação
- Backend for Frontend (BFF)

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- OpenFeign
- Swagger / OpenAPI
- Lombok
- Docker

## Responsabilidades

- Cadastro de usuários
- Autenticação utilizando JWT
- Consulta de usuários
- Gerenciamento de endereços e telefones
- Consulta automática de endereços via ViaCEP
- Documentação da API com Swagger/OpenAPI
- Tratamento global de exceções

## Estrutura do projeto

```text
src/main/java
├── business
├── controller
├── infrastructure
├── mapper
├── repository
└── dto
```

## Como executar

### Requisitos

- Java 21
- PostgreSQL
- Docker (opcional)

### Executando

```bash
./gradlew bootRun
```

A documentação da API estará disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

## Integração

Este serviço fornece autenticação e informações de usuários para os demais microsserviços do sistema através de APIs REST.
