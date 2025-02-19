# Usa a imagem do OpenJDK 21
FROM openjdk:21-jdk-slim AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . .

# Dá permissão de execução ao mvnw
RUN chmod +x mvnw

# Executa o build do projeto (gera o JAR)
RUN ./mvnw clean package -DskipTests

# Copia o JAR gerado para um novo container menor
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia o JAR gerado na etapa anterior para o container final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
