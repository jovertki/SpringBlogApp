FROM maven:3.8.6-openjdk-18 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -q -f /usr/src/app/pom.xml clean package

FROM openjdk:19-jdk-alpine
COPY --from=build /usr/src/app/target/SpringBlogApp-0.0.1-SNAPSHOT.jar /usr/app/SpringBlogApp-0.0.1-SNAPSHOT.jar

# Railway hosting
# -----------------
EXPOSE ${PORT}
# -----------------


# Local development
# -----------------
#EXPOSE 8080
# -----------------


ENTRYPOINT ["java","-jar","/usr/app/SpringBlogApp-0.0.1-SNAPSHOT.jar"]