# NTT DATA - Testes do Sistema de Microserviços

## 🧪 Comandos de Teste

### 1. Verificar se todos os serviços estão funcionando

```bash
# Verificar Eureka Server
curl http://localhost:8761

# Verificar API Gateway
curl http://localhost:8080/fallback/health

# Verificar Product Service
curl http://localhost:8080/products

# Verificar Order Service
curl http://localhost:8080/orders
```

### 2. Testes de Produtos

#### Listar todos os produtos:
```bash
curl -X GET http://localhost:8080/products
```

#### Buscar produto específico:
```bash
curl -X GET http://localhost:8080/products/1
```

#### Criar novo produto:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15",
    "description": "Smartphone Apple iPhone 15 128GB",
    "price": 4999.99,
    "stock": 25
  }'
```

#### Atualizar produto:
```bash
curl -X PUT http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone Samsung Galaxy S23 Ultra",
    "description": "Samsung Galaxy S23 Ultra 256GB",
    "price": 2999.99,
    "stock": 45
  }'
```

#### Buscar produtos por nome:
```bash
curl -X GET "http://localhost:8080/products/search?name=Samsung"
```

#### Produtos em estoque:
```bash
curl -X GET http://localhost:8080/products/in-stock
```

### 3. Testes de Pedidos

#### Listar todos os pedidos:
```bash
curl -X GET http://localhost:8080/orders
```

#### Criar novo pedido:
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2,
    "customerName": "João Silva",
    "customerEmail": "joao.silva@email.com"
  }'
```

#### Buscar pedido específico:
```bash
curl -X GET http://localhost:8080/orders/1
```

#### Processar pedido:
```bash
curl -X PUT http://localhost:8080/orders/1/process
```

#### Enviar pedido:
```bash
curl -X PUT http://localhost:8080/orders/1/ship
```

#### Entregar pedido:
```bash
curl -X PUT http://localhost:8080/orders/1/deliver
```

#### Cancelar pedido:
```bash
curl -X PUT http://localhost:8080/orders/1/cancel
```

#### Buscar pedidos por cliente:
```bash
curl -X GET http://localhost:8080/orders/customer/joao.silva@email.com
```

#### Buscar pedidos por status:
```bash
curl -X GET http://localhost:8080/orders/status/CONFIRMED
```

### 4. Cenários de Teste Completo

#### Cenário 1: Criar produto e fazer pedido
```bash
# 1. Criar produto
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro",
    "description": "MacBook Pro M3 16GB 512GB",
    "price": 12999.99,
    "stock": 10
  }'

# 2. Verificar produto criado
curl -X GET http://localhost:8080/products

# 3. Fazer pedido do produto
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 11,
    "quantity": 1,
    "customerName": "Maria Santos",
    "customerEmail": "maria.santos@email.com"
  }'

# 4. Verificar pedido criado
curl -X GET http://localhost:8080/orders

# 5. Verificar estoque reduzido
curl -X GET http://localhost:8080/products/11
```

#### Cenário 2: Teste de estoque insuficiente
```bash
# 1. Tentar fazer pedido com quantidade maior que estoque
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 1000,
    "customerName": "Teste Estoque",
    "customerEmail": "teste@email.com"
  }'
```

## 🎯 Resultados Esperados

- ✅ Produtos são criados e listados corretamente
- ✅ Pedidos são processados e estoque é reduzido automaticamente
- ✅ Validações de estoque funcionam (rejeita pedidos sem estoque)
- ✅ Estados dos pedidos são atualizados corretamente
- ✅ Comunicação entre microserviços via Feign Client funciona
- ✅ Service Discovery via Eureka está operacional
- ✅ API Gateway roteia requisições corretamente
- ✅ Fallbacks funcionam quando serviços estão indisponíveis

## 🔍 Monitoramento

- **Eureka Dashboard**: http://localhost:8761
- **H2 Console Products**: http://localhost:8080/product-h2
- **H2 Console Orders**: http://localhost:8080/order-h2
- **Gateway Actuator**: http://localhost:8080/actuator/health

---

## 🎉 Sistema Funcionando 100%!

O sistema está completamente funcional com todas as funcionalidades implementadas:
- Microserviços independentes
- Service Discovery
- API Gateway
- Comunicação HTTP entre serviços
- Persistência de dados
- Validações de negócio
- Tratamento de erros