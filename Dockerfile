FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
RUN mvn clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

FROM openjdk:17.0.2

WORKDIR /app

RUN mkdir ./logs

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","app.jar"]
