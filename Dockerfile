FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} URL-shortener.jar
ENTRYPOINT ["java", "-jar", "/URL-shortener.jar"]
EXPOSE 8080
