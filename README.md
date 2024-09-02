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
1. Crear usuario `curl -X POST "http://localhost:9000/user/create" \
     -H "Content-Type: application/json" \
     -d '{"username": "tu-usuario", "password": "tu-contraseña"}' 
2. Login `curl -X POST "http://localhost:9000/auth/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "tu-usuario", "password": "tu-contraseña"}' `
  ### * Si la informacion esta correcta envia el Token. Este es necesario para las consultas siguientes:

4. Obtener tareas`curl -X GET "http://localhost:9000/task/getAllTasks" \
     -H "Authorization: Bearer {Token}" \
     -H "Content-Type: application/json"`
   
6. Ingresar tarea nueva `curl -X POST "http://localhost:9000/tasks" \
     -H "Authorization: Bearer {Token} " \
     -H "Content-Type: application/json" \
     -d '{"name": "New Task", "description": "This is a new task description."}'`
7. Borrar tarea`curl -X DELETE "http://localhost:9000/tasks/123" \
     -H "Authorization: Bearer  {Token}" `
   
