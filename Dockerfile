#Construccion de la imagen
FROM amazoncorretto:17-alpine-jdk AS builder
#Directorio de trabajo
WORKDIR /app

#Se copia el pom y se descargan las dependencias
COPY pom.xml .
#Este script permite ejecutar Maven sin necesidad de tenerlo instalado previamente.
COPY mvnw .
#Copio archivos de configuracion
COPY .mvn .mvn
#Se cambia los permisos del script para que pueda ser ejecutado
# sed -i 's/\r$//' mvnw -> Elimina los caracteres de retorno de carro que pueden causar problemas en sistemas Unix al ejecutar el script.
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
#Se descargan las dependencias
RUN ./mvnw dependency:go-offline

#Se copian el codigo fuente
COPY src src
#Se compila el proyecto
RUN ./mvnw package -DskipTests

#Creacion de la imagen
FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
#Directorio de trabajo
WORKDIR /app
#Se copia el jar generado
COPY --from=builder /app/target/*.jar app.jar
#Se expone el puerto 8080
EXPOSE 8080
#se define el punto de entrada
ENTRYPOINT ["java","-jar","app.jar"]


