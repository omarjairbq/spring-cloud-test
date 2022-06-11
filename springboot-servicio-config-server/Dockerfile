FROM openjdk:11-jdk
VOLUME /tmp
COPY ./target/springboot-servicio-config-server-0.0.1-SNAPSHOT.jar config-server.jar
EXPOSE 8888
ENTRYPOINT [ "java", "-jar", "config-server.jar" ]