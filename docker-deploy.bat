@echo off
echo ===================================================
echo     NTT DATA - Build e Deploy com Docker
echo ===================================================
echo.

echo [1/5] Compilando todos os projetos...
call mvn clean package -DskipTests

echo.
echo [2/5] Construindo imagens Docker...
docker-compose build

echo.
echo [3/5] Parando containers existentes...
docker-compose down

echo.
echo [4/5] Iniciando sistema com Docker...
docker-compose up -d

echo.
echo [5/5] Aguardando serviços iniciarem...
timeout /t 30

echo.
echo ===================================================
echo           SISTEMA DOCKER INICIADO!
echo ===================================================
echo.
echo URLs de Acesso:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway:      http://localhost:8080
echo - Products:         http://localhost:8080/products
echo - Orders:           http://localhost:8080/orders
echo.
echo Verificando status dos containers...
docker-compose ps

echo.
echo Para ver os logs, execute:
echo docker-compose logs -f [service-name]
echo.
echo Para parar o sistema:
echo docker-compose down