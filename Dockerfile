FROM sapmachine:jdk-ubuntu-17.0.8
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]