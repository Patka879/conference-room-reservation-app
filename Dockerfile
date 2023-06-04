#1zaciagnięcie obrazu bazowego
FROM openjdk:17-alpine

#2wkopiowanie własnego pliku jar
COPY /target/finalProject-0.0.1-SNAPSHOT.jar finalProject.jar

#3 wskazanie punktu startu aplikacji
ENTRYPOINT ["java", "-jar" ,"finalProject"]