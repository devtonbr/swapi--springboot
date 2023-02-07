FROM maven:3-eclipse-temurin-17 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN mvn clean install

FROM eclipse-temurin:17.0.3_7-jdk
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar

WORKDIR /app
ENV JAVA_OPTS "$JAVA_OPTS \
        -Dserver.port=8080"

EXPOSE 8080 9090

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar app.jar"]
