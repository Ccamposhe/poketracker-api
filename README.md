# 🎮 poketracker-api

API RESTful em Java e Spring Boot para registrar os Pokémon capturados por treinadores. Ao cadastrar um Pokémon, a API consulta a [PokéAPI](https://pokeapi.co/) para obter o ID da Pokédex, o sprite e as regras de nível mínimo de evolução e de jogo.

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)

---

## 📌 Sumário

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#️-tecnologias)
- [Endpoints](#-endpoints)
- [Como executar](#-como-executar)
- [Variáveis de ambiente](#-variáveis-de-ambiente)
- [Autor](#-autor)

---

## Funcionalidades

- Cadastro e login de usuários, com senha protegida por BCrypt
- Autenticação com token JWT
- CRUD de Pokémon capturados, com cada usuário acessando somente os próprios registros
- Preenchimento automático do ID da Pokédex, nome oficial e sprite via PokéAPI
- Validação de nível mínimo conforme a cadeia de evolução
- Validação de jogo: o Pokémon precisa existir na versão escolhida

---

## 🛠️ Tecnologias

- **Java 21** e **Spring Boot 4**
- **Spring Security** com JWT (biblioteca jjwt)
- **Spring Data JPA** e **Hibernate**
- **PostgreSQL 16**, executado via Docker Compose
- **RestClient** para consumir a PokéAPI
- **Lombok** e **Jakarta Validation**
- **Maven**

---

## 🌐 Endpoints

### Autenticação (pública)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/auth/register` | Cadastrar usuário (`email`, `password`, `nickname`) |
| `POST` | `/api/v1/auth/login` | Fazer login e receber o token JWT |

### Pokémon capturados (autenticado)

Envie o token no cabeçalho `Authorization: Bearer <token>`.

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/pokemon` | Cadastrar um Pokémon capturado |
| `GET` | `/api/v1/pokemon` | Listar os seus Pokémon |
| `GET` | `/api/v1/pokemon/{id}` | Buscar um Pokémon pelo id |
| `GET` | `/api/v1/pokemon/pokedex/{pokedexId}` | Listar os seus Pokémon pelo número da Pokédex |
| `PUT` | `/api/v1/pokemon/{id}` | Atualizar um Pokémon |
| `DELETE` | `/api/v1/pokemon/{id}` | Remover um Pokémon |

Exemplo de cadastro:

```bash
curl -X POST http://localhost:8080/api/v1/pokemon \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"charizard","nickname":"Zard","level":36,"game":"YELLOW"}'
```

---

## Como executar

### Pré-requisitos

- Java 21+
- Docker e Docker Compose
- Uma IDE ou terminal com acesso ao Maven (o projeto usa o `mvnw`)

### 1. Clonar o repositório

```bash
git clone https://github.com/Ccamposhe/poketracker-api.git
cd poketracker-api
```

### 2. Configurar as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto.

### 3. Subir o banco de dados

```bash
docker compose up -d
```

O PostgreSQL ficará disponível em `localhost:5435`.

### 4. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

---

## 🔐 Variáveis de ambiente

| Variável | Descrição |
|----------|-----------|
| `DB_NAME` | Nome do banco de dados |
| `DB_USER` | Usuário do PostgreSQL |
| `DB_PASSWORD` | Senha do PostgreSQL |
| `JWT_SECRET` | Chave de assinatura do JWT (use no mínimo 32 caracteres) |

Para gerar uma chave segura:

```bash
openssl rand -base64 48
```

---

## 👤 Autor

Desenvolvido por **Carlos Henrique Campos**.

💼 [LinkedIn](https://www.linkedin.com/in/ccamposhe/) · 💻 [GitHub](https://github.com/Ccamposhe)
