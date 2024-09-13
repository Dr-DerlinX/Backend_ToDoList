# Usa una imagen base de Java
FROM openjdk:21-jdk-slim

# Copia el archivo JAR generado por el build al contenedor
COPY target/to-do-list-0.0.1-SNAPSHOT.jar /app/to-do-list.jar

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/to-do-list.jar"]

# Expone el puerto en el que la aplicación se ejecuta
EXPOSE 8080