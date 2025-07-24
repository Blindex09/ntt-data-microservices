FROM openjdk:17-jdk-slim

LABEL maintainer="NTT DATA"
LABEL description="Order Service - Gestão de Pedidos"

WORKDIR /app

# Copiar arquivos do Maven
COPY target/order-service-1.0.0.jar app.jar

# Expor porta
EXPOSE 8082

# Configurar JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Variáveis de ambiente
ENV EUREKA_SERVER_URL=http://eureka-server:8761/eureka/
ENV DB_URL=jdbc:h2:mem:orderdb
ENV PRODUCT_SERVICE_URL=http://product-service:8081

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8082/actuator/health || exit 1