# Proyecto API de Tareas

## Descripción

Esta es una API para gestionar tareas. Permite crear, listar, actualizar y eliminar tareas de los usuarios registrados. Cada usuario tiene sus propias tareas y la API está protegida con JWT para la autenticación.

## Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Security con JWT
- PostgreSQL
- Docker
- Docker Compose
- JPA/Hibernate

## Funcionalidades

- Registro de usuarios
- Autenticación con JWT
- CRUD de tareas (Crear, Leer, Actualizar, Eliminar)
- Asociación de tareas a usuarios específicos

## Requisitos

- Docker
- Docker Compose

## Instalación y Ejecución

Sigue estos pasos para descargar y ejecutar el proyecto en tu máquina utilizando Docker Compose:

1. Clona el repositorio:

    ```bash
    git clone https://github.com/usuario/proyecto-tareas.git
    cd proyecto-tareas
    ```

2. Construye y levanta los contenedores de la aplicación y la base de datos con Docker Compose:

    ```bash
    docker-compose up --build
    ```

   Esto levantará tanto la aplicación de Spring Boot como una instancia de PostgreSQL. La API estará disponible en `http://localhost:8080`.

3. Accede a la API desde tu cliente REST favorito o desde la línea de comandos usando `curl`.

## Variables de Entorno

El archivo `docker-compose.yml` está configurado para tomar las siguientes variables de entorno:

- `JWT_SECRET`: Clave secreta para firmar los tokens JWT.
- `DATABASE_URL`: URL de la base de datos PostgreSQL.
  
Asegúrate de configurar estos valores correctamente en el archivo `.env`.

## Endpoints

| Método | URL                                          | Autorización | Body (JSON)                               |
|--------|----------------------------------------------|--------------|-------------------------------------------|
| POST   | `/auth/register`                             | No           | `{ "name": "John", "email": "john@mail.com", "password": "123456" }` |
| POST   | `/auth/login`                                | No           | `{ "name": "john", "password": "123456" }`                           |
| GET    | `/api/v1/userProfile`                        | Sí           | -                                                                    |
| PUT    | `/api/v1/userProfile`                        | Si           | `{   "id": 402,  "name": "johnEdit","email": "jonh@gmail.com","password": "123"}`
| DELETE | `/api/v1/userProfile/8`                      | Si           |                                                                      | 
| DET    | `/api/v1/tareas/{tarea_id}/usuario/{user_id}`| Si           |                                                                      |
| POST   | `/api/v1/tareas/{user_id}`| Sí               | `{ "descripcion": "Nueva tarea", "estado": "PENDIENTE" }`                           |
| PUT    | `/api/v1/tareas/{tarea_id}/usuario/{user_id}`| Sí           | `{ "descripcion": "Tarea actualizada", "estado": "COMPLETADA" }`     |
| DELETE | `/api/v1/tareas/{id}`                        | Sí           | -                                                                    |

## Uso de la API

1. **Registro**: Los usuarios pueden registrarse enviando una petición POST a `/api/v1/users/register` con su nombre, correo y contraseña.
   
2. **Autenticación**: Para iniciar sesión, los usuarios deben enviar su correo y contraseña a `/api/v1/users/login`. Si las credenciales son correctas, recibirán un token JWT que deben usar en las peticiones posteriores.

3. **CRUD de Tareas**: Después de autenticarse, los usuarios pueden gestionar sus tareas utilizando los endpoints `/api/v1/tareas`.

### Autorización

Los endpoints que requieren autorización deben incluir el token JWT en el encabezado `Authorization` con el formato:

