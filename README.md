# Agendador de tarefas

Sistema de agendamento de tarefas em Java 21 e Spring Boot, organizado em quatro microsserviços. O BFF expõe a API para o cliente, integra os serviços via OpenFeign e executa o cron que dispara notificações por e-mail. A autenticação usa JWT.

## Arquitetura

| Serviço | Diretório | Responsabilidade | Porta interna |
| --- | --- | --- | --- |
| Usuários | `services/usuario` | Cadastro, login e emissão de JWT; dados no PostgreSQL | 8080 |
| Tarefas | `services/tarefas` | Cadastro e consulta de tarefas; dados no MongoDB | 8081 |
| Notificação | `services/notificacao` | Envio de e-mail por SMTP | 8082 |
| BFF | `services/bff` | API pública, integração REST e agendamento com cron | 8083 |

O [Compose](compose.yaml) inicia os quatro serviços, PostgreSQL e MongoDB. Apenas a porta do BFF é publicada no host. Os outros serviços e bancos se comunicam pela rede interna do Compose.

## Histórico dos serviços

Este repositório reúne os quatro projetos que antes eram mantidos separadamente. Ele foi iniciado a partir dos arquivos consolidados, por isso não contém os commits anteriores de cada serviço. O histórico original pode ser consultado nos repositórios individuais:

- API de Usuários: [guifelipem/usuario](https://github.com/guifelipem/usuario)
- API de Agendamento de Tarefas: [guifelipem/agendador-tarefas](https://github.com/guifelipem/agendador-tarefas)
- Serviço de Notificação: [guifelipem/notificacao](https://github.com/guifelipem/notificacao)
- BFF (Backend for Frontend): [guifelipem/bff-agendador-tarefas](https://github.com/guifelipem/bff-agendador-tarefas)

## Requisitos

- Docker com o plugin Docker Compose.
- Uma conta SMTP com permissão para envio de e-mail.
- Para executar os serviços sem Docker: Java 21, PostgreSQL e MongoDB.

## Primeira execução com Docker

1. Copie [`.env.example`](.env.example) para `.env` (`Copy-Item .env.example .env` no PowerShell ou `cp .env.example .env` em um shell Unix).
2. Edite o `.env`: substitua todas as senhas e os valores de exemplo, configure o SMTP e informe a origem do frontend em `CORS_ORIGIN`. Gere `JWT_SECRET_BASE64` com pelo menos 32 bytes aleatórios. No PowerShell:

   ```powershell
   [Convert]::ToBase64String([Security.Cryptography.RandomNumberGenerator]::GetBytes(64))
   ```

   A senha `MONGO_PASSWORD` deve conter apenas letras e números, pois compõe a URI de conexão. O `.env` é ignorado pelo Git.
3. Suba a aplicação na raiz do projeto:

   ```sh
   docker compose up -d --build
   docker compose ps
   ```

4. Cadastre, pela API pública, a conta definida em `CRON_USUARIO_EMAIL` e `CRON_USUARIO_SENHA`. O cron faz login com essa conta a cada execução. Exemplo no PowerShell:

   ```powershell
   Invoke-RestMethod -Uri 'http://localhost:8083/usuario' -Method Post -ContentType 'application/json' -Body '{"nome":"Agendador","email":"cron@example.com","senha":"SENHA_CONFIGURADA","enderecos":[],"telefones":[]}'
   ```

   Troque o e-mail e a senha pelos valores do `.env`. Até essa conta existir, o cron não conseguirá autenticar para consultar as tarefas.

O BFF atende em `http://localhost:8083` por padrão. `BFF_PORT` altera a porta publicada no host. Em um servidor público, configure HTTPS por meio de um proxy reverso.

## Configurar o e-mail de notificações

O serviço `notificacao` envia mensagens por SMTP autenticado com STARTTLS. No arquivo `.env`, configure:

```dotenv
SMTP_HOST=smtp.seu-provedor.com
SMTP_PORT=587
SMTP_USERNAME=seu-email@example.com
SMTP_PASSWORD=senha-ou-token-smtp
EMAIL_REMETENTE=seu-email@example.com
EMAIL_NOME_REMETENTE=Agendador de Tarefas
```

Use o host, a porta e a credencial SMTP informados pelo provedor do seu e-mail. Alguns provedores exigem uma senha específica para aplicativos ou um token SMTP. `EMAIL_REMETENTE` é o endereço exibido no campo **De**; em geral, ele deve ser o mesmo de `SMTP_USERNAME` ou um endereço que sua conta tenha permissão para enviar. A mensagem é enviada ao e-mail do usuário associado à tarefa.

Depois de editar o `.env`, aplique a configuração com `docker compose up -d --force-recreate notificacao` e acompanhe `docker compose logs -f notificacao bff`. Para confirmar o envio, crie uma tarefa de teste com horário que entre na janela consultada pelo cron: entre uma hora e uma hora e cinco minutos à frente. O cron roda a cada minuto por padrão e precisa da conta configurada em `CRON_USUARIO_EMAIL` e `CRON_USUARIO_SENHA`.

## API pelo BFF

| Método | Rota | Uso |
| --- | --- | --- |
| `POST` | `/usuario` | Cadastrar usuário |
| `POST` | `/usuario/login` | Autenticar e receber token JWT |
| `GET` | `/usuario?email=...` | Consultar usuário |
| `POST` | `/tarefas` | Criar tarefa |
| `GET` | `/tarefas` | Listar tarefas do usuário |
| `GET` | `/tarefas/eventos?dataInicial=...&dataFinal=...` | Buscar tarefas por período |

Nas rotas protegidas, envie `Authorization: Bearer <token>`. O BFF também oferece operações de atualização e exclusão de usuários e tarefas. Os controladores em `services/bff/src/main/java` definem os parâmetros e corpos aceitos.

## Configuração

| Variável no `.env` | Uso |
| --- | --- |
| `POSTGRES_PASSWORD`, `MONGO_PASSWORD` | Senhas dos bancos |
| `JWT_SECRET_BASE64` | Chave JWT compartilhada pelos serviços de usuários e tarefas |
| `SMTP_HOST`, `SMTP_PORT`, `SMTP_USERNAME`, `SMTP_PASSWORD` | Servidor e credenciais SMTP usados pelo serviço de notificação |
| `EMAIL_REMETENTE`, `EMAIL_NOME_REMETENTE` | Endereço e nome exibidos como remetente |
| `CRON_USUARIO_EMAIL`, `CRON_USUARIO_SENHA` | Conta usada pelo cron para acessar a API |
| `CRON_HORARIO` | Expressão cron do Spring; padrão: a cada minuto |
| `CORS_ORIGIN` | Origem permitida para o frontend |
| `BFF_PORT` | Porta pública do BFF; padrão: `8083` |

Os bancos usam volumes Docker chamados `postgres_data` e `mongo_data`. Para acompanhar logs, execute `docker compose logs -f bff`. Para atualizar, use `docker compose up -d --build`. Para parar, use `docker compose down`. Faça backup dos bancos antes de atualizações; `docker compose down -v` remove os volumes e seus dados.

## Desenvolvimento

Cada serviço mantém seu próprio build: Gradle em `usuario`, `tarefas` e `notificacao`; Maven em `bff`. Os wrappers (`gradlew`, `mvnw`) e seus arquivos de configuração devem ser versionados. Para executar fora do Compose, configure os bancos e as variáveis de ambiente de cada serviço; os `application.properties` e `application.yaml` contêm os valores locais padrão e as portas 8080 a 8083.

Os pacotes Java estão sob `com.github.guifelipem`. O arquivo `.gitignore` da raiz exclui builds, caches, configurações de IDE e segredos locais, sem excluir os arquivos necessários para compilar o projeto.
