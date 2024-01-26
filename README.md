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

1. **Crear un Nuevo Usuario**
    - **Endpoint:** `POST http://localhost:8090/api/Usuario`
    - **Descripción:** Crea un nuevo usuario no se pueden repetir emails.
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
2. **Obtener Todos los Usuarios**
   - **Endpoint:** `GET http://localhost:8090/api/Usuarios`
   - **Descripción:** Obtiene la información de todos los usuarios registrados.
   
   
3. **Eliminar Usuario por Id**
- **Endpoint:** 'DELETE http://localhost:8090/api/Usuario/{id}'
- **Descripción:** Elimina usuario del sistema según su Id.

4. **Login**
   - **Endpoint:** `POST http://localhost:8090/api/login`
   - **Descripción:** Validador que activa la sesion del usuario.
   - **Body de la Solicitud (Ejemplo):**
    ```json
   {
     "email": "lucas1@gmail.com",
     "password": "123456"
   }
   ```
5. **Actualizar Usuario**
   - **Endpoint:** `PUT http://localhost:8090/api/Usuario`
   - **Descripción:** Actualiza la información del usuario registrado, para hacer este cambio debe ejecutar el `/login`
   - **Body de la Solicitud (Ejemplo):**
    ```json
   {
     "nombre": "Lucas Elguin",
    "email": "lucas2@gmail.com",
    "contraseña": "123456",
    "telefonos": [
        {
            "numero": "7654321",
            "codigoCiudad": "2",
            "codigoPais": "58"
        }
      ]
   }
   ```
6. **Actualizar Contraseña**
   - **Endpoint:** `PATCH http://localhost:8090/api/Contraseña/{id}`
   - **Descripción:** Actualiza la contraseña del usuario registrado según su Id, para hacer este cambio debe ejecutar el `/login`
   - **Body de la Solicitud (Ejemplo):**
   ```json
   {
     "contraseña": "passNueva"
   }
   ```
7. **Logout**
   - **Endpoint:** `POST http://localhost:8090/api/logout`
   - **Descripción:** Realiza la desactivación de la sesion del usuario actual.
   - **Body de la Solicitud (Ejemplo):**
   ```json
   {
     "email": "lucas1@gmail.com"
   }
   ```

Diagrama de Flujo de la Solución
-

![Diagrama de Flujo .png](Diagrama%20de%20Flujo%20.png)

Notas
-
- Este proyecto trabaja con JWT. Los token tienen una duracion de 15 min el cual se puede realizar un
  cambio en la duracion en application.properties