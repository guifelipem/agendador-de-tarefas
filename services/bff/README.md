# Backend for Frontend (BFF)

O Backend for Frontend (BFF) atua como camada intermediária entre o frontend e os microsserviços do sistema. Sua responsabilidade é centralizar as chamadas realizadas pela interface, agregando informações provenientes dos demais serviços e abstraindo detalhes de comunicação.

## Arquitetura

Este serviço faz parte do sistema de Agendamento de Tarefas, composto pelos seguintes microsserviços:

- API de Usuários
- API de Agendamento de Tarefas
- Serviço de Notificação
- Backend for Frontend (BFF)

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Maven

## Responsabilidades

- Centralizar as requisições realizadas pelo frontend
- Consumir os microsserviços do sistema
- Agregar respostas de diferentes serviços
- Simplificar a comunicação entre frontend e backend

## Estrutura do projeto

```text
src/main/java
├── controller
├── service
├── client
├── dto
└── config
```

## Como executar

### Requisitos

- Java 21

```bash
./mvnw spring-boot:run
```

## Integração

O BFF consome as APIs de Usuários e Agendamento para disponibilizar uma interface unificada ao frontend.
