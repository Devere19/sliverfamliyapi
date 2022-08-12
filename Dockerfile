FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/sliverfamliyapi.jar sliverfamliyapi.jar
ENTRYPOINT ["java","-jar","/sliverfamliyapi.jar", "&"]
