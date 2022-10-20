FROM openjdk:19-jdk-alpine
MAINTAINER jovertki
COPY target/SpringBlogApp-0.0.1-SNAPSHOT.jar SpringBlogApp.jar
ENTRYPOINT ["java","-jar","/SpringBlogApp.jar"]