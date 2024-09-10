FROM openjdk:21-jdk
MAINTAINER wojciechbiernacki
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
ENTRYPOINT ["java", "-jar", "/product-service.jar"]
