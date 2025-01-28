FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
RUN mvn clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

FROM openjdk:17.0.2

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=target
ARG JAR_NAME=cursos-0.0.1-SNAPSHOT.jar

COPY --from=build /app/${TARGET_FOLDER}/${JAR_NAME} app.jar

ARG PORT_APP=8002
ENV PORT=${PORT_APP}
EXPOSE ${PORT}
#EXPOSE 8002

ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","app.jar"]
