#Build the application with Maven
FROM openjdk:17-jdk-slim as build-stage
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY mvnw .
COPY .mvn .mvn


#Copy the Maven project files and download dependencies
COPY pom.xml .
RUN chmod +x .mvn
RUN mvn dependency:go-offline
RUN mvn package

#Copy the application source code
COPY src src

#Build the application
RUN mvn package -DskipTests

#Create final Docker Image
FROM openjdk:17-jdk-slim as runtime

WORKDIR /app

COPY --from=build-stage /app/target/marketplacehn-0.0.1-SNAPSHOT.jar marketplacehn.jar

ENTRYPOINT ["java", "-jar", "marketplacehn.jar"]
EXPOSE 8080