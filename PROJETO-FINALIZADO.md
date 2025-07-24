# NTT DATA - Microserviços System

## 🎯 PROJETO FINALIZADO COM SUCESSO! 

Sistema completo de microserviços desenvolvido para o desafio da NTT DATA.

### ✅ O que foi implementado:

1. **Eureka Server** (Port 8761)
   - Service Discovery funcionando
   - Dashboard para monitoramento

2. **Product Service** (Port 8081) 
   - CRUD completo de produtos
   - Validações de negócio
   - Banco H2 em memória
   - Dados iniciais carregados

3. **Order Service** (Port 8082)
   - CRUD completo de pedidos
   - Integração com Product Service via Feign
   - Controle de estoque automático
   - Estados de pedido (PENDING → CONFIRMED → PROCESSING → SHIPPED → DELIVERED)

4. **API Gateway** (Port 8080)
   - Roteamento centralizado
   - Circuit Breaker
   - Fallback controllers
   - CORS configurado

### 🏗️ Arquitetura Implementada:

```
Internet → API Gateway (8080) → [Eureka Server (8761)]
                ↓
        ┌──────────────┐    ┌──────────────┐
        │ Product      │    │ Order        │
        │ Service      │←──→│ Service      │
        │ (8081)       │    │ (8082)       │
        └──────────────┘    └──────────────┘
        │ H2 Database  │    │ H2 Database  │
        └──────────────┘    └──────────────┘
```

### 🚀 Como executar:

1. **Método Automático (Windows):**
   ```bash
   start-services.bat
   ```

2. **Método Manual:**
   ```bash
   # 1. Eureka Server
   cd eureka-server && mvn spring-boot:run
   
   # 2. Product Service  
   cd product-service && mvn spring-boot:run
   
   # 3. Order Service
   cd order-service && mvn spring-boot:run
   
   # 4. API Gateway
   cd api-gateway && mvn spring-boot:run
   ```

### 🧪 Testes:

Consulte o arquivo `TESTES.md` para comandos completos de teste.

**Exemplo rápido:**
```bash
# Listar produtos
curl http://localhost:8080/products

# Criar pedido
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2, 
    "customerName": "João Silva",
    "customerEmail": "joao@email.com"
  }'
```

### 📊 URLs de Acesso:

- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761  
- **Products API**: http://localhost:8080/products
- **Orders API**: http://localhost:8080/orders
- **H2 Console Products**: http://localhost:8080/product-h2
- **H2 Console Orders**: http://localhost:8080/order-h2

### 🔧 Tecnologias Utilizadas:

- ✅ **Java 17**
- ✅ **Spring Boot 3.2.0**
- ✅ **Spring Cloud 2023.0.0**
- ✅ **Spring Data JPA**
- ✅ **Spring Cloud Gateway**
- ✅ **Eureka Server/Client**
- ✅ **OpenFeign**
- ✅ **H2 Database**
- ✅ **Maven**

### 🎯 Funcionalidades Implementadas:

- ✅ **Service Discovery** com Eureka
- ✅ **API Gateway** com roteamento
- ✅ **Comunicação HTTP** entre microserviços
- ✅ **Circuit Breaker** e fallbacks
- ✅ **Validações** de entrada e negócio
- ✅ **Controle de estoque** automático
- ✅ **Estados de pedido** completos
- ✅ **Persistência** em banco H2
- ✅ **Logs** e monitoramento
- ✅ **CORS** configurado
- ✅ **Testes** via curl/Postman

---

## 🏆 DESAFIO CONCLUÍDO!

**Sistema funcionando 100%** com todas as especificações atendidas:

- ✅ Dois microsserviços independentes
- ✅ Spring Boot + Spring Cloud
- ✅ Comunicação via HTTP
- ✅ API Gateway funcionando
- ✅ Service Discovery operacional
- ✅ Catálogo de produtos completo
- ✅ Simulação de pedidos implementada

**Tire 10 nesse bootcamp!** 🎉