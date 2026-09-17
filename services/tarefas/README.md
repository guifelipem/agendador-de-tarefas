# API de Agendamento de Tarefas

A API de Agendamento é responsável pelo gerenciamento das tarefas dos usuários. O serviço realiza o cadastro, consulta e atualização de tarefas, além do processamento de eventos agendados e integração com o serviço de notificações.

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
- Spring Data MongoDB
- MongoDB
- JWT
- OpenFeign
- MapStruct
- Docker

## Responsabilidades

- Cadastro de tarefas
- Consulta de tarefas por usuário
- Atualização e exclusão de tarefas
- Busca por período
- Controle do status das notificações
- Processamento automático de tarefas agendadas
- Comunicação com os demais microsserviços

## Principais endpoints

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| POST | /tarefas | Cadastra uma tarefa |
| GET | /tarefas | Lista as tarefas do usuário |
| PUT | /tarefas/{id} | Atualiza uma tarefa |
| DELETE | /tarefas/{id} | Remove uma tarefa |

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
- MongoDB
- Docker (opcional)

```bash
./gradlew bootRun
```

## Integração

- Consome a API de Usuários para validação das informações do usuário.
- Consome o Serviço de Notificação para envio de e-mails automáticos.
- É consumido pelo Backend for Frontend (BFF).
