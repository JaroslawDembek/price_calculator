FROM maven:3.8-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17
COPY --from=build /usr/src/app/target/price-calculator.jar /usr/app/price-calculator.jar
COPY --from=build /usr/src/app/src/main/resources/application.yml /usr/app/conf/application.yml
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/price-calculator.jar","--spring.config.location=file:/usr/app/conf/application.yml"]
