FROM openjdk:11-jre-alpine

MAINTAINER readingisgood

USER root

ADD target/bookordermanagement-0.0.1.jar .

EXPOSE 8092

ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["java","-jar", "bookordermanagement-0.0.1.jar"]