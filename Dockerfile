FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ARG JAR_FILE=target/bookordermanagement-0.0.1.jar
ADD ${JAR_FILE} bookordermanagement.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/name_of_your_db", "-Djava.security.egd=file:/dev/./urandom","-jar","/bookordermanagement.jar"]
