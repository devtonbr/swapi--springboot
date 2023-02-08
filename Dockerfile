FROM maven:3.8.4-eclipse-temurin-11 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN mvn clean install

FROM eclipse-temurin:11
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar

WORKDIR /app
ENV JAVA_OPTS "$JAVA_OPTS \
        -Dserver.port=5000"

EXPOSE 5000 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
