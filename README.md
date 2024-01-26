README - Evaluacion Java
=
Este README proporciona instrucciones detalladas sobre cómo probar la aplicación de 
cliente de usuarios construida con Spring Boot. La aplicación utiliza JWT para la autenticación y autorización además
de funciones que requieren ejecutar login.

Requisitos Previos
-

- **Java:** Asegurate de tener un idle que tenga el sdk 17 
- **Cliente REST:** Puedes utilizar herramientas como Postman para realizar solicitudes HTTP.

Configuración del Proyecto
-
- Clonar el proyecto: 
    git clone https://github.com/Lorozcoe/EvaluacionJava.git
- una vez inicializado el proyecto ejecutando la clase StartApplication se utilizara el puerto 8090 si tiene conflictos
- para ingresar a la BDD utilizar el siguiente link "http://localhost:8080/h2-console", cambiando el valor de URL con
jdbc:h2:mem:pruebaJavadb si es necesario
- Dentro del proyecto existe un archivo llamado "Pruebajava.postman_collection.json" el cual estan los endpoint que se 
ocuparon para realizar las pruebas.

Ejemplos de consultas Postman
-
1. **Obtener Todos los Usuarios**
    - **Endpoint:** `GET http://localhost:8090/api/Usuarios`
    - **Descripción:** Obtiene todos los usuarios registrados.

2. **Crear un Nuevo Usuario**
    - **Endpoint:** `POST http://localhost:8090/api/Usuario`
    - **Body de la Solicitud (Ejemplo):**
      ```json
      {
       "nombre": "lucasOrozco",
       "email": "lucas1@gmail.com",
       "contraseña": "123456",
       "telefonos": [
          {
            "numero": "1234567",
            "codigoCiudad": "1",
            "codigoPais": "57"
          }
        ]
      }
      ```

    - **Descripción:** Crea un nuevo usuario no se pueden repetir emails.
3. **Eliminar Usuario por Id**
- **Endpoint:** 'DELETE http://localhost:8090/api/Usuario/{id}'
- **Descripción:** Elimina usuario del sistema según su Id.

Diagrama de Flujo de la Solución
-
![Diagrama de Flujo .png](..%2F..%2FDiagrama%20de%20Flujo%20.png)

Notas
-
- Este proyecto trabaja con JWT. Los token tienen una duracion de 15 min el cual se puede realizar un
  cambio en la duracion en application.properties