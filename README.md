# Prueba técnica Agilsoft 

## Descripción
Desarrollar una API Rest para un sistema TODO List 

## Prerrequisitos
Java 17
Docker
Docker-compose

## Configuración
En el archivo application.properties están los campos de configuración los más relevantes son:

- El puerto por el cual se está ejecutando la app actualmente es el 9000
`server.port=9000`
- Llave para la encriptación del token JWT
`spring.jwt.secretkey`
- Tiempo de duración del token JWT 
`spring.jwt.plusHourExpiration`


## Construir el Proyecto
En la carpeta de proyecto se deben realizar los siguientes pasos.
1. Ejecutar `./gradlew build` para hacer el build del proyecto java.
2. Una ves completado, se debe crear la imagen Docker `docker-compose build. Si no hay imagen creada, preguntará si desea crear una. 

## Iniciar el proyecto
1. Ejecutar `docker-compose up` esto iniciara el proyecto.

## Probar el api
1. Login Post
