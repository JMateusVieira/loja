# ETAPA 1: Build (Compilação)
# Usa uma imagem com Maven e Java 17 para compilar o projeto
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app
# Copia o pom.xml e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copia o código-fonte e gera o arquivo .jar
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Runtime (Execução)
# Usa uma imagem leve, apenas com o Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o .jar gerado na etapa anterior para a imagem final
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta 8080
EXPOSE 8080
# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]