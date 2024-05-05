FROM openjdk:17-alpine

COPY . .

RUN mvn install

COPY /target/finalProject-0.0.1-SNAPSHOT.jar finalProject.jar

ENTRYPOINT ["java", "-jar" ,"finalProject.jar"]

EXPOSE 8080