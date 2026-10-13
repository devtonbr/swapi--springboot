FROM maven:3.9.9-eclipse-temurin-17 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 5000 9090

# logs em formato json (um evento por linha)
ENV SPRING_PROFILES_ACTIVE=dev,jsonlog

ENTRYPOINT ["java", "-jar", "app.jar"]
