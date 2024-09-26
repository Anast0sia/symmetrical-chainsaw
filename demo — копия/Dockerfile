FROM openjdk:22-jdk-slim
EXPOSE 8081
ADD target/demo-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]