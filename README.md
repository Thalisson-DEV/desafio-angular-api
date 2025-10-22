<div align="center">
<h1>desafio-angular-api</h1>
<p>
<strong>API RESTful robusta desenvolvida em Spring Boot, projetada para ser o backend do desafio Angular proposto pela @Rocketseat, implementando autenticação JWT, gestão completa de produtos (CRUD) e otimização de performance via caching com Redis.</strong>
</p>
<p>
<img src="https://img.shields.io/badge/Status-Estável-green" alt="Status do Projeto: Estável">
<img src="https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white" alt="Java 17+">
<img src="https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=spring-boot" alt="Spring Boot 3.x">
<img src="https://img.shields.io/badge/Cache-Redis-red?logo=redis" alt="Redis Caching">
<img src="https://img.shields.io/badge/Segurança-JWT-yellow?logo=json-web-tokens" alt="JWT Security">
</p>
</div>

---

## 📋 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Estratégia de Caching com Redis](#-estratégia-de-caching-com-redis)
- [Endpoints da API](#-endpoints-da-api)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Contribuições](#-contribuições)
- [Contato](#-contato)

---

## 📚 Sobre o Projeto
A **desafio-angular-api** foi construída para atender aos requisitos de um *backend* moderno, fornecendo endpoints seguros, performáticos e bem estruturados para a gestão de produtos. O foco principal está na implementação completa do ciclo de vida do produto (CRUD) e na otimização de *queries* e leitura de dados utilizando o **Spring Cache Abstraction** com o **Redis**.

## ✨ Funcionalidades Principais
✅ 1.  **Autenticação JWT:** Proteção de rotas utilizando tokens de acesso.
✅ **Gestão de Produtos:** Rotas de Criação, Leitura (por ID e lista), Atualização e Exclusão (CRUD).
✅ **Paginação:** Listagem de produtos paginada e com suporte a ordenação.
✅ **Caching Inteligente:** Uso do Redis para armazenar resultados de consultas, reduzindo a latência e a carga sobre o banco de dados.

---

## ⚡ Estratégia de Caching com Redis
O design do cache utiliza dois *namespaces* dedicados para garantir a consistência dos dados:

1.  **Cache de Item Único (`product-item`):**
    * Usado para buscas por ID (`/products/{id}`) e perfil de usuário (`/auth/profile`).
    * Garante uma resposta imediata após a primeira consulta.
2.  **Cache de Lista (`product-list`):**
    * Usado para a listagem paginada (`/products`). A chave de cache é gerada com base nos parâmetros de paginação (página, tamanho e ordenação).

### Consistência de Cache
* **Update/Save:** Ao criar ou atualizar um produto, a operação utiliza `@CachePut` no `product-item` (atualiza o item individual) e **simultaneamente** utiliza `@CacheEvict(allEntries = true)` no `product-list` (limpa todas as páginas), forçando o recarregamento correto da lista na próxima requisição.
* **Delete:** Remove a entrada individual e limpa todo o cache de lista via anotação `@Caching`.

---

## 📌 Endpoints da API
Todos os endpoints protegidos exigem um *header* `Authorization: Bearer <JWT_TOKEN>`.

| Funcionalidade | Método | URL | Protegido (JWT)? |
| :--- | :--- | :--- | :--- |
| **Login** | `POST` | `/auth/login` | Não |
| **Perfil do Usuário** | `GET` | `/auth/profile` | Sim |
| **Listar Produtos** | `GET` | `/products?page=0&size=10&sort=id,asc` | Sim |
| **Buscar Produto por ID** | `GET` | `/products/{id}` | Sim |
| **Criar Produto** | `POST` | `/products` | Sim |
| **Atualizar Produto** | `PUT` | `/products/{id}` | Sim |
| **Deletar Produto** | `DELETE` | `/products/{id}` | Sim |

---

## 🛠️ Tecnologias Utilizadas
A API foi construída com foco em robustez, velocidade e aderência aos padrões de mercado.

* **Linguagem:** **Java 17+**
* **Framework:** **Spring Boot 3.x**
* **Cache:** Spring Data Redis
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** H2 Database (Em memória, para desenvolvimento)
* **Segurança:** Spring Security, JWT
* **Build Tool:** Maven
* **Contêineres:** Docker / Docker Compose

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* **Java JDK 17** ou superior
* **Maven 3.x**
* **Docker** e **Docker Compose**

### 1. Clonar o Repositório
```bash
git clone https://github.com/Thalisson-DEV/desafio-angular-api.git
cd desafio-angular-api
```

### 2. Configurar e Iniciar Serviços (Redis)

O Docker Compose é usado para iniciar a instância do Redis que a aplicação utilizará como cache.

```bash
# Inicia o contêiner do Redis em background
docker-compose up -d redis
```

### 3. Executar a Aplicação

Compile e execute a aplicação Spring Boot:

# Compila, instala dependências e Executa a aplicação
```bash
mvn clean install
mvn spring-boot:run
```

A API estará rodando em `http://localhost:8080`.

---

## 🤝 Contribuições
Contribuições, sugestões e feedbacks são bem-vindos! Para contribuir:

1.  Bifurque o projeto (Fork).
2.  Crie uma *branch* de *feature* (`git checkout -b feature/sua-melhoria`).
3.  Faça *commit* de suas alterações.
4.  Abra um **Pull Request** para a *branch* `master`.

## 👤 Contato
Se você tiver dúvidas, sugestões ou quiser discutir o projeto:

* **GitHub:** ThalissonDEV - https://github.com/Thalisson-DEV
* **Email:** thalissondamiao1@gmail.com
