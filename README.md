# Jobify
### *Applicación de Búsqueda de Empleo*

Esta aplicación está diseñada para ayudar a los usuarios a mantener un control organizado y detallado de su búsqueda de empleo. Surge de una necesidad global con el objetivo de facilitarnos dicho proceso.

![Captura de pantalla 2024-12-12 020802](https://github.com/user-attachments/assets/26802649-6c8c-436b-bfc9-f8e2137d2719)

<br>

## Stack
🌱 **Lenguaje**: Java 21 con Spring Boot

🧩 **Testing**: JUnit, Hamcrest y Mockito

🐬 **Base de Datos**: MySQL con Workbench 8.0

🔑 **Validaciones**: Spring Security, Basic Auth

<br>

# Contenido Actual

## Funcionalidades Creadas

- **Gestión de Candidaturas**: El usuario podrá crear, ver, actualizar y eliminar candidaturas

- **Filtrar y Buscar**: El usuario podrá hacer uso de filtros para obtener candidaturas y dispondrá de un cuadro de búsqueda donde se mostrarán candidaturas asociadas

- **Autenticación**: El usuario se podrá registrar y loguear

- **Fases**: El usuario podrá marcar cada candidatura según la fase en la que esté (Exploring, Submitted, Reviewing, Interview, Tech interview, Final interview, Offer, Hired)

- **Estadísticas**: Se proporcionará una visión global al usuario de su situación de búsqueda de empleo

- **Testing**: Se llegará a un mínimo del 65% de cobertura

<br>

## Instalaciones

*Para probar este proyecto, necesitarás:*
- JDK 21
- Apache Maven
- MySQL Workbench (Una conexión usando username = root, password = 1234)
- Un IDE (Como IntelliJ IDEA o VSCode con extensiones de Java)
- Git (Para clonar el repositorio)
- Postman (Opcional, para probar endpoints)

## Diagrama UML
![image](https://github.com/user-attachments/assets/7b39bdea-a4a0-49e2-b435-f58a24f93199)

<br>

## Endpoints 🔗
*Muestra de enpoints funcionales del MVP*

![image](https://github.com/user-attachments/assets/2e65f07a-0024-427a-8412-a8960dcbf8d4)

<br>

## Password Encoder
Ejemplo de codificación de contraseñas en la base de datos:

![image](https://github.com/user-attachments/assets/04437d6e-1d7d-4249-a58e-5b80c1477a40)
<br>

## Coverage
Cobertura de tests (a mejorar)

![image](https://github.com/user-attachments/assets/27e2b8d8-b9ac-4808-bc9c-a08be0a77322)

<br><br>

# Implementaciones Futuras

*Logrado el MVP detallado, esta aplicación escalable fué desarrollada con vistas a futuras implementaciones que irán desarrollándose:*
    
* **Tareas/To-Do List**: El usuario podrá crear una lista de tareas. En estadísticas se añadirá el número de tareas realizadas
  
* **Calendario**: Habrá un calendario integrado con eventos/recordatorios
  
* **Notas**: El usuario podrá crear notas simples o incluyendo archivos (p.ej el CV). Podrá categorizar, priorizar y ordenar dichas notas
  
* **Recordatorios**: Se implementará una funcionalidad que avise al usuario de una fecha importante (ej. entrevista), usando Spring Scheduler para enviar alertas internas o correos de notificación
  
* **Historial**: El usuario dispondrá de un historial de cambios realizados en las fases de las candidaturas, guardado por fechas

* **FrontEnd**

  ---

  Este proyecto está actualmente en desarrollo 🚀
