# Etapa 1: Usar uma imagem com Maven para compilar a aplicação
FROM maven:3.8.4-amazoncorretto-17 AS build

# Copiar o código-fonte da aplicação para dentro do container
COPY . /app

WORKDIR /app

# Rodar o comando Maven para gerar o .jar
RUN mvn clean package -DskipTests

# Etapa 2: Usar uma imagem com Amazon Corretto para rodar a aplicação
FROM amazoncorretto:17

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o .jar gerado na primeira etapa para a nova imagem
COPY --from=build /app/target/bloood-donation-0.0.1-SNAPSHOT.jar /app/blood-donation.jar

# Comando para rodar o .jar
CMD ["java", "-jar", "blood-donation.jar"]