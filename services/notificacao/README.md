# Serviço de Notificação

O Serviço de Notificação é responsável pelo envio de e-mails relacionados às tarefas cadastradas no sistema. O serviço recebe solicitações dos demais microsserviços e gera notificações utilizando templates HTML.

## Arquitetura

Este serviço faz parte do sistema de Agendamento de Tarefas, composto pelos seguintes microsserviços:

- API de Usuários
- API de Agendamento de Tarefas
- Serviço de Notificação
- Backend for Frontend (BFF)

## Tecnologias

- Java 21
- Spring Boot
- Spring Mail
- Thymeleaf
- Lombok

## Responsabilidades

- Envio de notificações por e-mail
- Geração de conteúdo HTML utilizando Thymeleaf
- Validação das informações recebidas
- Configuração do servidor SMTP

## Endpoint

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| POST | /email | Envia uma notificação |

## Estrutura do projeto

```text
src/main/java
├── business
├── controller
├── infrastructure
├── dto
└── config
```

## Como executar

### Requisitos

- Java 21
- Configuração SMTP

```bash
./gradlew bootRun
```

## Integração

Este serviço é consumido pela API de Agendamento para realizar o envio automático de notificações aos usuários.
