# Usando una imagen base de Amazon Corretto 17
FROM amazoncorretto:17

# Copiar los archivos JAR de los módulos correspondientes
COPY application/target/application-0.0.1-SNAPSHOT.jar application.jar
COPY domain/target/domain-0.0.1-SNAPSHOT.jar domain.jar
COPY infraestructure/target/infraestructure-0.0.1-SNAPSHOT.jar infrastructure.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar el JAR de la aplicación
ENTRYPOINT ["java", "-jar", "application.jar"]
