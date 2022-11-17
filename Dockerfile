FROM adoptopenjdk/openjdk11:alpine-jre
COPY /target/practice5-1.0-SNAPSHOT-jar-with-dependencies.jar practice5-1.0-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","practice5-1.0-SNAPSHOT-jar-with-dependencies.jar"]
