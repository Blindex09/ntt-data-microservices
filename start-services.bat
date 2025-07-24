@echo off
echo ===================================================
echo        NTT DATA - Sistema de Microservicos
echo ===================================================
echo.

echo [1/4] Iniciando Eureka Server (Service Discovery)...
cd eureka-server
start "Eureka Server" cmd /k "mvn spring-boot:run"
echo Aguardando Eureka Server inicializar...
timeout /t 15

echo.
echo [2/4] Iniciando Product Service...
cd ..\product-service
start "Product Service" cmd /k "mvn spring-boot:run"
echo Aguardando Product Service inicializar...
timeout /t 10

echo.
echo [3/4] Iniciando Order Service...
cd ..\order-service
start "Order Service" cmd /k "mvn spring-boot:run"
echo Aguardando Order Service inicializar...
timeout /t 10

echo.
echo [4/4] Iniciando API Gateway...
cd ..\api-gateway
start "API Gateway" cmd /k "mvn spring-boot:run"

echo.
echo ===================================================
echo                SISTEMA INICIADO!
echo ===================================================
echo.
echo URLs de Acesso:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway:      http://localhost:8080
echo - Products:         http://localhost:8080/products
echo - Orders:           http://localhost:8080/orders
echo.
echo Aguarde todos os servicos terminarem de inicializar...
echo Pressione qualquer tecla para abrir o Eureka Dashboard
pause >nul
start http://localhost:8761