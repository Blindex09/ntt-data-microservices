FROM openjdk:17-jdk-slim

LABEL maintainer="NTT DATA"
LABEL description="Eureka Server - Service Discovery"

WORKDIR /app

# Copiar arquivos do Maven
COPY target/eureka-server-1.0.0.jar app.jar

# Expor porta
EXPOSE 8761

# Configurar JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando de execução
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8761/actuator/health || exit 1