FROM openjdk:17-jdk-slim

LABEL maintainer="NTT DATA"
LABEL description="API Gateway - Roteamento Central"

WORKDIR /app

# Copiar arquivos do Maven
COPY target/api-gateway-1.0.0.jar app.jar

# Expor porta
EXPOSE 8080

# Configurar JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Variáveis de ambiente
ENV EUREKA_SERVER_URL=http://eureka-server:8761/eureka/

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1