<h1 align="center">Backend do Teste da DSIN</h1>

<br>
<p align="center">
 <a href="#arquitetura">Arquitetura</a> • 
 <a href="#tecnologias">Tecnologias</a> •
 <a href="#banco-de-dados">Banco de Dados</a> • 
 <a href="#inicializacao">Como Rodar</a> • 
 <a href="#endpoints">Endpoints •</a>
 <a href="#testes">Testes Unitários •</a>
</p>

<br>

<p align="center">
  <b>Uma API REST para gerenciamento de agendamentos da Leilaleila.</b>
</p>


<h2 id="arquitetura">🏛 Arquitetura</h2>

Este projeto foi desenvolvido utilizando o framework Spring, que segue o padrão de arquitetura MVC (Model-View-Controller). Essa abordagem divide a aplicação em três camadas interconectadas, o que facilita a manutenção e a escalabilidade:

- **Model**: Representa a camada de dados e a lógica de negócios. Inclui classes de entidades que mapeiam para tabelas de banco de dados e classes de serviço que contêm a lógica de negócios.
- **View**: No contexto de uma API REST, essa camada é representada pelos formatos de resposta que são enviados ao cliente. Embora não haja uma "view" no sentido tradicional, o design dos endpoints e a estrutura dos dados retornados cumprem esse papel.
- **Controller**: Camada que lida com a recepção de todas as requisições HTTP, delegando a lógica de negócios para os serviços apropriados e retornando as respostas ao cliente.

Adicionalmente, o projeto utiliza as seguintes camadas:
  
- **DTOs (Data Transfer Objects)**: Objetos que facilitam o transporte de dados entre subcamadas do sistema, especialmente úteis para transferir dados entre a camada de serviço e a camada de controller.
- **Repository**: Camada que abstrai o acesso aos dados, permitindo que o restante da aplicação interaja com o banco de dados de forma mais simples e direta.
- **Security**: Configurações de segurança do Spring Security para autenticação e autorização dentro da aplicação.
- **Service**: Camada intermediária entre os controllers e os repositórios, onde a lógica de negócios é implementada, garantindo que os controllers permaneçam enxutos e focados apenas no roteamento de requisições.

<h2 id="banco-de-dados">🗃 Banco de Dados</h2>

Para configurar o banco de dados, você só precisa criar o banco de dados e rodar a aplicação. O Flyway cuidará do resto, aplicando todas as migrações necessárias para configurar e popular o banco de dados.

### Criando o Banco de Dados

Antes de iniciar a aplicação, crie o banco de dados com o seguinte comando SQL:

```sql
CREATE DATABASE `teste-dsin`;
```
OBS: Não esqueça do `` envolvendo o nome, use exatamente o exemplo acima, o mysql por padrão não aceita nome com "-".
### Migrações do Flyway

As migrações do Flyway são aplicadas automaticamente ao iniciar a aplicação. Aqui estão as descrições das migrações disponíveis:

- **V1__Create_tables.sql**: Cria todas as tabelas necessárias para o sistema.
- **V2__Populate_Beauty_Services.sql**: Popula o banco de dados com os serviços disponíveis no salão de beleza.
- **V3__Create_Admin.sql**: Cria um usuário admin, já que o sistema requer que usuários com role de admin sejam criados manualmente.
- **V4__Populate_data_to_be_possible_test_weekly_reports.sql**: Popula o banco com dados para possibilitar a visualização do relatório de desempenho semanal. O sistema não permite agendamentos em datas passadas, então esses dados já são incluídos para facilitar.

Para mais detalhes sobre cada script de migração, acesse a pasta `src/resources/db/migration` no projeto.

Para testar as rotas de admin, com o usuário que foi criado na migration, basta usar essas informações:
<p>username: Leilaleila</p>
<p>password: adminLeila**</p>

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
    url: jdbc:mysql://localhost:3306/teste-dsin #altere caso mude o nome do banco, ou o dominio e porta do banco sejam diferentes
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

<h2 id="endpoints">📍 Endpoints</h2>

### Endpoints de Cliente

*Todos os endpoints que precisam de token estão sujeitos ao retorno 403(forbidden), caso o token seja inválido/expirado. Ou se até mesmo o token pertencer a  uma role que não tem permissão ao endpoint.*

**POST /api/client** - Cadastrar um novo cliente

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


---

**GET /api/appointment** - Consultar agendamentos por ID do cliente

**Requer Autenticação**: Bearer Token

**Parâmetro de Caminho**: `id` - O identificador do cliente, para buscar todos os seus agendamentos.

**URL Completa de Exemplo**:
```
http://localhost:8080/api/appointment?id=2
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
200 OK

---

**GET /api/beauty-services** - Listar todos os serviços de beleza disponíveis

**Requer Autenticação**: Bearer Token

**Exemplo de Sucesso**
```json
[
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
    },
    {
        "id": 4,
        "name": "Manicure e Pedicure",
        "description": "Cuidado completo para unhas das mãos e pés",
        "price": 70.00,
        "durationMinutes": 60
    },
    {
        "id": 5,
        "name": "Hidratação Capilar",
        "description": "Tratamento profundo para cabelos ressecados",
        "price": 60.00,
        "durationMinutes": 40
    }
]
```
200 OK

---
### Endpoints Administrativos (Acesso restrito a Administradores)
**Requer Role**: ADMIN

**PUT /api/appointment/full-update** - Atualização completa de um agendamento

**Requer Autenticação**: Bearer Token

**Exemplo de Payload - Sucesso**
```json
{
  "id": 30,
  "beautyServicesIds": [1, 2, 3],
  "dateTime": "2024-04-19T14:30:00",
  "status": "CONFIRMADO"
}
```
200 OK 

**Exemplos de Erro**
```json
{
    "message": "Validation error",
    "timestamp": "2025-03-20T22:07:21.4506934",
    "details": "uri=/api/appointment/full-update",
    "errors": {
        "dateTime": "Não é permitido datas passadas"
    }
}
```
400 Bad Request

```json
{
    "message": "Agendamento não encontrado",
    "timestamp": "2025-03-20T22:05:41.1065145",
    "details": "uri=/api/appointment/full-update"
}
```
400 Bad Request

---

**PUT /api/appointment/confirm** - Confirmar um agendamento

**Requer Autenticação**: Bearer Token

**Parâmetro de Caminho**: `id` - O identificador do agendamento a ser confirmado.

**URL Completa de Exemplo**:
```
http://localhost:8080/api/appointment/confirm?id=2
```

**Exemplo de Sucesso**
200 OK

**Exemplo de Erro** 

```json
{
    "message": "Agendamento não encontrado",
    "timestamp": "2025-03-21T08:29:56.063445",
    "details": "uri=/api/appointment/confirm"
}
```
400 Bad Request

---

**GET /api/appointment/all** - Listar todos os agendamentos, de todos os clientes.

**Requer Autenticação**: Bearer Token

**Exemplo de Sucesso**
```json
[
    {
        "id": 6,
        "client": {
            "id": 2,
            "name": "Cliente Teste",
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
            "id": 4,
            "name": "Rafael",
            "username": "rafay",
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
    }
]
```
200 OK

---

**GET /api/reports/last-week** - Relatório de desempenho da última semana

**Requer Autenticação**: Bearer Token

**Observação**: Este relatório apresenta métricas dos últimos 7 dias, contabilizando apenas os agendamentos que ficaram como confirmados, ou seja, agendamentos que estão no passado e com o status confirmado, são considerados concluidos pelo sistema no cálculo das métricas.

**Exemplo de Sucesso**

```json
{
    "Faturamento total": 460.00,
    "Média de faturamento diário": 65.71,
    "Total de agendamentos concluídos": 3,
    "Serviço mais lucrativo": "Coloração",
    "Duração média dos agendamentos (min)": 118
}
```
200 OK

<h2 id="Testes">✅ Testes Unitários</h2>

Os testes unitários foram realizados principalmente na camada de serviço, foco das regras de negócio mais complexas do sistema. Optei por concentrar os esforços de teste na classe `AppointmentService`, que apresenta a maior complexidade e densidade de lógica de negócio. Dada a limitação de tempo, os testes foram direcionados às funcionalidades críticas desta classe. Para detalhes adicionais sobre os testes implementados, os mesmos podem ser consultados no diretório `src/test/orgs/rafs/tstedsin/unnitests/service`.

### Configuração Inicial para os Testes

A estrutura de testes foi configurada utilizando mocks para os repositórios e mapeadores necessários, além de injeção de dependências na classe de serviço testada. Segue um exemplo da configuração básica utilizada:

```java
@Mock
private AppointmentRepository appointmentRepository;
@Mock
private ClientRepository clientRepository;
@Mock
private BeautyServiceRepository beautyServiceRepository;
@Mock
private AppointmentMapper appointmentMapper;

@InjectMocks
private AppointmentService appointmentService;

private Client client;
private BeautyService beautyService;
private Appointment appointment;
private CreateAppointmentRequestDTO createAppointmentRequestDto;
private AdmUpdateAppointmentRequestDTO admUpdateAppointmentRequestDTO;
private UpdateAppointmentRequestDTO updateAppointmentRequestDTO;

@BeforeEach
public void setUp() {
    client = new Client();
    beautyService = new BeautyService();
    appointment = new Appointment();
    LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
    createAppointmentRequestDto = new CreateAppointmentRequestDTO(1L, List.of(1L), futureDateTime);
    updateAppointmentRequestDTO = new UpdateAppointmentRequestDTO(1L, List.of(1L), futureDateTime, 1L);
    admUpdateAppointmentRequestDTO = new AdmUpdateAppointmentRequestDTO(1L, List.of(1L), futureDateTime, AppointmentStatus.CANCELADO);
}
```

### Testes Implementados

1. **Criação de Agendamento (Sucesso)**:
   - **Descrição**: Testa a criação de um agendamento com sucesso.
   - **Assertiva**: Verifica se o agendamento é criado e persistido corretamente.
   - **Mock Verificações**: Garante que os métodos dos repositórios e mapeadores são chamados conforme esperado.

   ```java
   @Test
   public void testCreateAppointmentSuccess() {
       when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
       when(appointmentMapper.toModel(any(), any(), any(), any())).thenReturn(appointment);
       when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

       Appointment createdAppointment = appointmentService.createAppointment(createAppointmentRequestDto);

       assertNotNull(createdAppointment);
       verify(clientRepository).findById(createAppointmentRequestDto.clientId());
       verify(beautyServiceRepository).findAllById(createAppointmentRequestDto.beautyServicesIds());
       verify(appointmentRepository).save(appointment);
   }
   ```

2. **Criação de Agendamento com Cliente Inexistente**:
   - **Descrição**: Verifica a robustez do sistema ao tentar criar um agendamento para um cliente não existente.
   - **Assertiva**: Espera-se que o sistema lance uma exceção indicando que o cliente não foi encontrado.

   ```java
   @Test
   public void testCreateAppointmentWithNonExistentClient() {
       when(clientRepository.findById(createAppointmentRequestDto.clientId())).thenReturn(Optional.empty());

       assertThrows(ClientNotFoundException.class, () -> {
           appointmentService.createAppointment(createAppointmentRequestDto);
       });
   }
   ```

3. **Atualização de Agendamento pelo Administrador (Sucesso)**:
   - **Descrição**: Testa a funcionalidade de atualização de um agendamento pelo administrador, incluindo a capacidade de alterar o status do agendamento.
   - **Validações**: Confirma que os dados necessários são buscados e atualizados corretamente.

   ```java
   @Test
   public void testAdmUpdateAppointmentSuccess() {
       when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
       when(beautyServiceRepository.findAllById(anyList())).thenReturn(List.of(beautyService));
       when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

       appointmentService.admUpdateAppointment(admUpdateAppointmentRequestDTO);

       verify(appointmentRepository).findById(1L);
       verify(beautyServiceRepository).findAllById(List.of(1L));
       verify(appointmentRepository).save(appointment);
   }
   ```

   4. **Atualização de Agendamento**: 
   - **Descrição**: Testa a funcionalidade de atualizar um agendamento existente, garantindo que todas as informações sejam atualizadas corretamente no banco de dados.
   - **Assertiva**: Confirma que o repositório é chamado para buscar, atualizar e salvar o agendamento com os novos dados.

   ```java
   @Test
   public void testUpdateAppointment() {
       Client client = new Client();
       client.setId(1L);
       Appointment appointmentToUpdate = new Appointment(client, List.of(beautyService), LocalDateTime.now().plusDays(3L));

       when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointmentToUpdate));
       when(beautyServiceRepository.findAllById(anyList())).thenReturn(List.of(beautyService));
       when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointmentToUpdate);

       appointmentService.updateAppointment(updateAppointmentRequestDTO);

       verify(appointmentRepository).findById(1L);
       verify(beautyServiceRepository).findAllById(List.of(1L));
       verify(appointmentRepository).save(appointmentToUpdate);
   }
   ```

5. **Buscar Todos os Agendamentos por ID do Cliente**: 
   - **Descrição**: Verifica a capacidade do serviço de retornar todos os agendamentos associados a um cliente específico.
   - **Assertiva**: Confirma que a lista de agendamentos retornada é a esperada e que o repositório foi chamado corretamente.

   ```java
   @Test
   public void testFindAllByClientIdSuccess() {
       Long clientId = 1L;
       List<Appointment> expectedAppointments = List.of(new Appointment(), new Appointment());

       when(appointmentRepository.findAllByClientId(clientId)).thenReturn(expectedAppointments);
       List<Appointment> actualAppointments = appointmentService.findAllByClientId(clientId);

       assertNotNull(actualAppointments);
       assertEquals(expectedAppointments, actualAppointments);
       verify(appointmentRepository).findAllByClientId(clientId);
   }
   ```

6. **Confirmação de Agendamento**: 
   - **Descrição**: Testa a funcionalidade de confirmar um agendamento, alterando o status do mesmo para confirmado.
   - **Assertiva**: Verifica se o status do agendamento é atualizado para confirmado e se o repositório é chamado para salvar a alteração.

   ```java
   @Test
   public void testConfirmAppointmentSuccess() {
       Long appointmentId = 1L;
       when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
       when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

       appointmentService.confirmAppointment(1L);

       assertEquals(AppointmentStatus.CONFIRMADO, appointment.getStatus());
       verify(appointmentRepository).save(appointment);
   }

   @Test
   public void testConfirmAppointmentWithNonExistentAppointment() {
       when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

       assertThrows(AppointmentNotFoundException.class, () -> {
           appointmentService.confirmAppointment(1L);
       });
   }
   ```

7. **Buscar Todos os Agendamentos**: 
   - **Descrição**: Verifica a capacidade do serviço de listar todos os agendamentos existentes no sistema.
   - **Assertiva**: Confirma que a lista de agendamentos é retornada corretamente e que todos os agendamentos são recuperados do repositório.

   ```java
   @Test
   public void testFindAllAppointmentsSuccess() {
       List<Appointment> expectedAppointments = List.of(new Appointment(), new Appointment());
       when(appointmentRepository.findAll()).thenReturn(expectedAppointments);

       List<Appointment> result = appointmentService.findAll();
       assertNotNull(result);
       verify(appointmentRepository).findAll();
   }
   ```
