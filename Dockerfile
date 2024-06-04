
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY ./advocacia/pom.xml .

RUN mvn dependency:go-offline

COPY ./advocacia/src ./src


RUN mvn package -DskipTests


FROM openjdk:17


WORKDIR /app


COPY --from=build /app/target/Advocacia-0.0.1-SNAPSHOT.jar /app/Advocacia-0.0.1-SNAPSHOT.jar

COPY advocacia.db /app/advocacia.db

ENV DB_PATH=/app/advocacia.db

CMD ["java", "-jar", "Advocacia-0.0.1-SNAPSHOT.jar"]
