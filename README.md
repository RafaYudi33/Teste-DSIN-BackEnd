<h1 align="center">API de Gerenciamento de Tarefas 💻</h1>

<br>
<p align="center">
 <a href="#tecnologias">Tecnologias</a> •
 <a href="#inicializacao">Como Rodar</a> • 
 <a href="#banco-de-dados">Banco de Dados</a> • 
 <a href="#endpoints">Endpoints</a>
</p>

<br>

<p align="center">
  <b>Uma API REST para gerenciamento de tarefas pessoais.</b>
</p>

<h2 id="tecnologias">🛠 Tecnologias</h2>

- Java
- Spring (Security, Data JPA, etc.)
- MySQL
- Flyway
- JUnit
- Mockito

<h2 id="inicializacao">🚀 Como Rodar</h2>

### Pré-requisitos

- Java 17 ou superior
- Maven 3.9.5 ou superior
- MySQL 

### Clonando

```bash
git clone https://github.com/RafaYudi33/Teste-DSIN-BackEnd.git
```

Modifique o arquivo `application.yml`:

```yaml
spring:
  application:
    name: teste-dsin
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/teste-dsin 
    username: root  #altere para seu usuario
    password: root123 #altere para sua senha
  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: none
    show-sql: false

security:
  jwt:
    secretKey: teste123
    expirationInMillisecond: 1800000

logging:
  level:
    org.springframework.security: DEBUG

cors:
  originPatterns: "http://localhost:8000" #altere para o domínio do front end (Por padrão o front ja sobe na 8000, se precisar trocar, tem que modificar aqui também)
```

### Iniciando

```bash
cd /caminho/para/seu/projeto
mvn spring-boot:run
```





<h2 id="banco-de-dados">🗃 Banco de Dados</h2>

Para configurar o banco de dados, você só precisa criar o banco de dados e rodar a aplicação. O Flyway cuidará do resto, aplicando todas as migrações necessárias para configurar e popular o banco de dados.

### Criando o Banco de Dados

Antes de iniciar a aplicação, crie o banco de dados com o seguinte comando SQL:

```sql
CREATE DATABASE teste-dsin;
```

### Migrações do Flyway

As migrações do Flyway são aplicadas automaticamente ao iniciar a aplicação. Aqui estão as descrições das migrações disponíveis:

- **V1_Create_tables.sql**: Cria todas as tabelas necessárias para o sistema.
- **V2_Populate_Beauty_Services.sql**: Popula o banco de dados com os serviços disponíveis no salão de beleza.
- **V3_Create_Admin.sql**: Cria um usuário admin, já que o sistema requer que usuários com role de admin sejam criados manualmente.
- **V4_Populate_data_to_be_possible_test_weekly_reports.sql**: Popula o banco com dados para possibilitar a visualização do relatório de desempenho semanal. O sistema não permite agendamentos em datas passadas, então esses dados já são incluídos para facilitar.

Para mais detalhes sobre cada script de migração, acesse a pasta `src/resources/db/migration` no projeto.


<h2 id="endpoints">📍 Endpoints</h2>

### Endpoints de Usuário

**POST /api/client** - Cadastrar um novo usuário

**Exemplo de Payload - Sucesso**
```json
{
  "username": "letRotii",
  "name": "LeticiaRotoli",
  "password": "pass"
}
```
**Response**:
```json
{
    "id": 6,
    "name": "LeticiaRotoli",
    "password": "$2a$10$Cn91XcmlPEPaIph00JgP8.RoZFG.nIinxfVZuvoMr4TkFy.V3o1nW",
    "username": "letRotii",
    "role": "ROLE_CLIENT"
}
```

201 Created

**Exemplos de Erro**
```json
{
    "message": "Nome de usuário indisponível",
    "timestamp": "2025-03-20T20:20:20.2096665",
    "details": "uri=/api/client"
}
```
400 Bad Request

```json
{
    "message": "Validation error",
    "timestamp": "2025-03-20T20:13:00.1108445",
    "details": "uri=/api/client",
    "errors": {
        "name": "não deve estar em branco",
        "username": "não deve estar em branco"
    }
}
```
400 Bad Request

---

**POST /api/login** - Autenticar um usuário

**Exemplo de Payload - Sucesso**
```json
{
  "username": "lero",
  "password": "pass"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJUZXN0ZS1EU0lOIiwic3ViIjoibGVybyIsImV4cCI6MTc0NDMxMjk5OH0.zo2W9-hdPCQB57fOIDqSENIWnTjz6gODJmLzkQhX1ks",
  "expirationTime": 1742514798,
  "role": "ROLE_CLIENT",
  "idUser": 5,
  "name": "Leticia Rotoli",
  "username": "lero"
}
```
200 OK

**Exemplos de Erro**
```json
{
    "message": "Usuário e/ou senha incorreto(s)",
    "timestamp": "2025-03-20T20:26:26.3893928",
    "details": "uri=/api/login"
}
```
401 Unauthorized

```json
{
    "message": "Validation error",
    "timestamp": "2025-03-20T20:26:51.1971716",
    "details": "uri=/api/login",
    "errors": {
        "password": "não deve estar em branco"
    }
}
```
400 Bad Request

---

### Endpoints de Agendamentos

**POST /api/appointment** - Criar um novo agendamento

**Requer Autenticação**: Bearer Token

**Exemplo de Payload - Sucesso**
```json
{
  "clientId": 2,
  "beautyServicesIds": [1, 2, 3],
  "dateTime": "2025-05-19T14:30:00"
}
```

**Response**:
```json
{
    "id": 30,
    "client": {
        "id": 2,
        "name": "Cliente Teste",
        "password": "$2a$10$0lczZCG0N9MAdeW7PzX9TuGRbDPgOTo8t8qVTtMBq0v48lAYVWerG",
        "username": "clientetest",
        "role": "ROLE_CLIENT"
    },
    "beautyServices": [
        {
            "id": 1,
            "name": "Corte de Cabelo",
            "description": "Corte profissional para todos os estilos",
            "price": 50.00,
            "durationMinutes": 45
        },
        {
            "id": 2,
            "name": "Escova e Finalização",
            "description": "Escova modelada para todos os tipos de cabelo",
            "price": 40.00,
            "durationMinutes": 30
        },
        {
            "id": 3,
            "name": "Coloração",
            "description": "Coloração capilar com tintas de alta qualidade",
            "price": 120.00,
            "durationMinutes": 90
        }
    ],
    "dateTime": "2025-05-19T14:30:00",
    "status": "PENDENTE"
}
```
201 Created

**Exemplos de Erro**
```json
{
    "message": "Cliente não foi encontrado",
    "timestamp": "2025-03-20T20:29:44.3746976",
    "details": "uri=/api/appointment"
}
```
400 Bad Request

```json
{
    "message": "Validation error",
    "timestamp": "2025-03-20T20:31:05.2883366",
    "details": "uri=/api/appointment",
    "errors": {
        "dateTime": "Não é permitido datas passadas"
    }
}
```
400 Bad Request

---

**PUT /api/appointment** - Atualizar um agendamento existente

**Requer Autenticação**: Bearer Token

**Exemplo de Payload - Sucesso**
```json
{
  "id": 9,
  "IdClient": 1,
  "beautyServicesIds": [1, 2],
  "dateTime": "2024-03-21T14:30:00"
}
```
**Response**:
200 OK

**Exemplos de Erro**
```json
{
    "message": "O agendamento não pertence a este cliente",
    "timestamp": "2025-03-20T21:27:14.3836297",
    "details": "uri=/api/appointment"
}
```
400 Bad Request

```json
{
    "message": "Validation error",
    "timestamp": "2025-03-20T21:28:11.224203",
    "details": "uri=/api/appointment",
    "errors": {
        "dateTime": "Não é permitido datas passadas",
        "id": "não deve ser nulo"
    }
}
```
400 Bad Request


```json
{
    "message": "Modificações só são permitidas em mais de 2 dias do agendamento",
    "timestamp": "2025-03-20T21:38:58.2196811",
    "details": "uri=/api/appointment"
}
```
400 Bad Request

Para documentar a requisição GET que você mencionou, onde a consulta não possui corpo mas um parâmetro de caminho, aqui está como você pode formatar essa documentação, incluindo o exemplo de sucesso e o código de status:

---

**GET /api/appointment?id=2** - Consultar agendamentos por ID

**Requer Autenticação**: Bearer Token

**Parâmetro de Caminho**: `id` - O identificador do agendamento a ser consultado.

**URL Completa de Exemplo**:
```
http://localhost:8080/api/appointment
```

**Exemplo de Sucesso**
```json
[
    {
        "id": 6,
        "client": {
            "id": 2,
            "name": "Cliente Teste",
            "password": "senha criptografada",
            "username": "clientetest",
            "role": "ROLE_CLIENT"
        },
        "beautyServices": [
            {
                "id": 1,
                "name": "Corte de Cabelo",
                "description": "Corte profissional para todos os estilos",
                "price": 50.00,
                "durationMinutes": 45
            },
            {
                "id": 3,
                "name": "Coloração",
                "description": "Coloração capilar com tintas de alta qualidade",
                "price": 120.00,
                "durationMinutes": 90
            }
        ],
        "dateTime": "2025-03-19T14:36:43",
        "status": "CONFIRMADO"
    },
    {
        "id": 7,
        "client": {
            "id": 2,
            "name": "Cliente Teste",
            "password": "senha criptografada",
            "username": "clientetest",
            "role": "ROLE_CLIENT"
        },
        "beautyServices": [
            {
                "id": 2,
                "name": "Escova e Finalização",
                "description": "Escova modelada para todos os tipos de cabelo",
                "price": 40.00,
                "durationMinutes": 30
            },
            {
                "id": 5,
                "name": "Hidratação Capilar",
                "description": "Tratamento profundo para cabelos ressecados",
                "price": 60.00,
                "durationMinutes": 40
            }
        ],
        "dateTime": "2025-03-18T14:36:43",
        "status": "CONFIRMADO"
    },
]
```
**Código de Status**: 200 OK



### Endpoints Administrativos (Acesso restrito a Administradores)
**Requer Role**: ADMIN

