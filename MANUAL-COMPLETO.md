# 🚀 NTT DATA - Manual Completo de Execução

## 📋 Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+**
- **Docker** e **Docker Compose** (opcional)
- **Git**
- **curl** (para testes)

## 🎯 Formas de Execução

### 1. 🏃‍♂️ Execução Local (Desenvolvimento)

#### Método Automático (Windows):
```bash
# Executar script que inicia todos os serviços
start-services.bat
```

#### Método Manual:
```bash
# 1. Compilar todos os projetos
mvn clean package -DskipTests

# 2. Executar na ordem (abrir um terminal para cada):

# Terminal 1 - Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2 - Product Service (aguardar Eureka iniciar)
cd product-service
mvn spring-boot:run

# Terminal 3 - Order Service (aguardar Product Service)
cd order-service
mvn spring-boot:run

# Terminal 4 - API Gateway (aguardar todos os anteriores)
cd api-gateway
mvn spring-boot:run
```

### 2. 🐳 Execução com Docker

#### Método Automático:
```bash
# Script que compila e executa tudo com Docker
docker-deploy.bat
```

#### Método Manual:
```bash
# 1. Compilar projetos
mvn clean package -DskipTests

# 2. Build das imagens
docker-compose build

# 3. Executar sistema
docker-compose up -d

# 4. Ver logs
docker-compose logs -f

# 5. Parar sistema
docker-compose down
```

## 🌐 URLs de Acesso

| Serviço | URL Local | Descrição |
|---------|-----------|-----------|
| **API Gateway** | http://localhost:8080 | Ponto de entrada principal |
| **Eureka Dashboard** | http://localhost:8761 | Monitoramento de serviços |
| **Products API** | http://localhost:8080/products | Catálogo de produtos |
| **Orders API** | http://localhost:8080/orders | Gestão de pedidos |
| **H2 Console Products** | http://localhost:8080/product-h2 | BD dos produtos |
| **H2 Console Orders** | http://localhost:8080/order-h2 | BD dos pedidos |

## 🧪 Testes Rápidos

### Verificar Sistema:
```bash
# Health check do Gateway
curl http://localhost:8080/fallback/health

# Listar produtos (com dados iniciais)
curl http://localhost:8080/products

# Listar pedidos (vazio inicialmente)
curl http://localhost:8080/orders
```

### Criar Produto:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15 Pro",
    "description": "iPhone 15 Pro 256GB Titanium",
    "price": 6999.99,
    "stock": 20
  }'
```

### Fazer Pedido:
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2,
    "customerName": "Ana Silva",
    "customerEmail": "ana.silva@email.com"
  }'
```

## 🔧 Executar Testes Unitários

```bash
# Todos os testes
mvn test

# Apenas Product Service
cd product-service
mvn test

# Com relatório de cobertura
mvn test jacoco:report
```

## 🛠️ Troubleshooting

### Problema: Porta em uso
```bash
# Verificar portas ocupadas
netstat -ano | findstr :8080
netstat -ano | findstr :8761

# Matar processo (Windows)
taskkill /PID <PID> /F
```

### Problema: Serviços não se conectam
1. Verificar se Eureka está rodando primeiro
2. Aguardar ~30 segundos para registro
3. Verificar logs de cada serviço
4. Verificar firewall/antivírus

### Problema: Docker não funciona
```bash
# Verificar Docker
docker --version
docker-compose --version

# Limpar containers
docker-compose down --volumes
docker system prune -f
```

## 📊 Monitoramento

### Verificar Status dos Serviços:
- **Eureka**: http://localhost:8761
- **Gateway Health**: http://localhost:8080/actuator/health
- **Product Health**: http://localhost:8081/actuator/health
- **Order Health**: http://localhost:8082/actuator/health

### Logs em Tempo Real:
```bash
# Local
tail -f logs/application.log

# Docker
docker-compose logs -f [service-name]
```

## 🎯 Cenários de Teste Completos

### Cenário 1: Fluxo Completo
```bash
# 1. Criar produto
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Notebook Gamer", "price": 4999.99, "stock": 5}'

# 2. Fazer pedido
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"productId": 11, "quantity": 1, "customerName": "João", "customerEmail": "joao@email.com"}'

# 3. Processar pedido
curl -X PUT http://localhost:8080/orders/1/process

# 4. Enviar pedido
curl -X PUT http://localhost:8080/orders/1/ship

# 5. Entregar pedido
curl -X PUT http://localhost:8080/orders/1/deliver
```

### Cenário 2: Teste de Estoque
```bash
# Tentar pedido com estoque insuficiente
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 1000, "customerName": "Teste", "customerEmail": "teste@email.com"}'

# Resultado esperado: Erro 400 - Estoque insuficiente
```

## 🏆 Sistema Completo e Funcional!

✅ **Todos os requisitos atendidos**:
- Microserviços independentes
- Spring Boot + Spring Cloud
- Service Discovery (Eureka)
- API Gateway
- Comunicação HTTP
- Catálogo de produtos
- Simulação de pedidos
- Containerização (Docker)
- Testes unitários
- Monitoramento
- Documentação completa

**🎉 Pronto para o Bootcamp NTT DATA!**