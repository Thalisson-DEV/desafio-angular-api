# --- STAGE 1: BUILD (Compilação e Geração do JAR) ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

# Copia arquivos essenciais para o Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Garante permissão de execução
RUN chmod +x ./mvnw

# Se o pom.xml não mudar, esta etapa é cacheada e acelera as builds futuras.
RUN ./mvnw dependency:go-offline

# Copia o código fonte
COPY src src

# Usa 'package' em vez de 'install', pois instalar no repo local é desnecessário.
RUN ./mvnw clean package -DskipTests

# --- STAGE 2: RUNTIME (Execução) ---
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho padrão
WORKDIR /app

# Define o volume de logs/temporários
VOLUME /tmp

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Copia o JAR executável da etapa de build para o diretório de trabalho (/app/app.jar)
COPY --from=build /workspace/app/target/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]