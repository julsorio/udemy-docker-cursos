FROM openjdk:17.0.2

WORKDIR /app

#es posible compilar y generar el jar al momento de crear la imagen
#COPY ./target/cursos-0.0.1-SNAPSHOT.jar .

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java","-jar","cursos-0.0.1-SNAPSHOT.jar"]