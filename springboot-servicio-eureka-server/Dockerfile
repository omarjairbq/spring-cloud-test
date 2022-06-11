FROM openjdk:11-jdk
VOLUME /tmp
COPY ./target/springboot-servicio-eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT [ "java", "-jar", "eureka-server.jar" ]