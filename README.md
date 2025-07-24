# NTT DATA - Sistema de Microserviços

## 🎯 Desafio Técnico - Microserviços

Sistema de catálogo de produtos e simulação de pedidos desenvolvido com Spring Boot e Spring Cloud.

## 🏗️ Arquitetura

```
┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │────│  Eureka Server  │
│    Port: 8080   │    │   Port: 8761    │
└─────────────────┘    └─────────────────┘
         │
         ├─────────────────────────────────┐
         │                                 │
┌─────────────────┐              ┌─────────────────┐
│ Product Service │              │  Order Service  │
│   Port: 8081    │              │   Port: 8082    │
└─────────────────┘              └─────────────────┘
```

## 🚀 Componentes

1. **Eureka Server** - Service Discovery
2. **API Gateway** - Roteamento e entrada única
3. **Product Service** - Gerenciamento de produtos
4. **Order Service** - Gerenciamento de pedidos

## ⚡ Como Executar

### 1. Executar na ordem:

```bash
# 1. Eureka Server
cd eureka-server
mvn spring-boot:run

# 2. Product Service
cd ../product-service
mvn spring-boot:run

# 3. Order Service
cd ../order-service
mvn spring-boot:run

# 4. API Gateway
cd ../api-gateway
mvn spring-boot:run
```

### 2. URLs de Acesso:

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Product Service**: http://localhost:8080/products
- **Order Service**: http://localhost:8080/orders

## 📋 APIs Disponíveis

### Produtos
- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID
- `POST /products` - Criar novo produto
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto

### Pedidos
- `GET /orders` - Listar todos os pedidos
- `GET /orders/{id}` - Buscar pedido por ID
- `POST /orders` - Criar novo pedido
- `PUT /orders/{id}` - Atualizar pedido
- `DELETE /orders/{id}` - Deletar pedido

## 🧪 Testes

### Criar Produto:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone",
    "description": "Smartphone Android",
    "price": 999.99,
    "stock": 50
  }'
```

### Criar Pedido:
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2,
    "customerName": "João Silva",
    "customerEmail": "joao@email.com"
  }'
```

## 🔧 Tecnologias

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Data JPA**
- **H2 Database**
- **Eureka Server/Client**
- **Spring Cloud Gateway**

## 👨‍💻 Desenvolvido por

**Desafio NTT DATA Bootcamp**

---

🚀 **Sistema funcionando perfeitamente!** 🚀