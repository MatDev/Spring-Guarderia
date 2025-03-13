# Gestión de Guardería
## Descripción del proyecto
El sistema de gestión de guardería es una aplicación diseñada para facilitar la administración de actividades, asistencia y comunicación en una guardería.
Proporciona roles específicos con permisos diferenciados: Parvularia, Asistente de Párvulo, y Apoderado. 
A través de esta plataforma, los usuarios pueden gestionar actividades, registrar asistencia, administrar fotos y mantener un control organizado de los párvulos.

## Tecnologías utilizadas
### Backend:
- Java: Lenguaje principal.
- Spring Boot: Framework para desarrollo backend.
- JPA/Hibernate: Gestión de la base de datos.
- JWT (JSON Web Tokens): Autenticación y autorización.
### Base de datos:
- Postgress SQL: Base de datos relacional.
- Herramientas adicionales:
- ModelMapper: Para la conversión entre entidades y DTOs.
- SLF4J/Logback: Gestión de logs.
- Lombok: Reducir código repetitivo (getters, setters, etc.).

## Estructura del carpetas
```
src/
├── main/
│   ├── java/com/guarderia/gestion_guarderia/
│   │   ├── controller/        # Controladores REST para cada entidad.
│   │   │   ├── AuthController.java
│   │   │   ├── ParvulariaController.java
│   │   │   ├── ApoderadoController.java
│   │   │   ├── AsistenciaController.java
│   │   │   ├── ActividadController.java
│   │   │   └── FotoController.java
│   │   ├── dto/               # Objetos de transferencia de datos (DTOs).
│   │   │   ├── ParvuloDTO.java
│   │   │   ├── ApoderadoDTO.java
│   │   │   ├── AsistenciaDTO.java
│   │   │   ├── ActividadDTO.java
│   │   │   ├── FotoDTO.java
│   │   │   └── UsuarioDTO.java
│   │   ├── entities/          # Entidades que representan las tablas de la BD.
│   │   │   ├── Actividad.java
│   │   │   ├── Apoderado.java
│   │   │   ├── Asistencia.java
│   │   │   ├── Foto.java
│   │   │   ├── Parvularia.java
│   │   │   ├── Parvulo.java
│   │   │   └── Usuario.java
│   │   ├── repository/        # Repositorios JPA para acceso a la base de datos.
│   │   │   ├── ActividadRepository.java
│   │   │   ├── ApoderadoRepository.java
│   │   │   ├── AsistenciaRepository.java
│   │   │   ├── FotoRepository.java
│   │   │   ├── ParvulariaRepository.java
│   │   │   └── ParvuloRepository.java
│   │   ├── service/           # Interfaces de servicios y lógica de negocio.
│   │   │   ├── ActividadService.java
│   │   │   ├── ApoderadoService.java
│   │   │   ├── AsistenciaService.java
│   │   │   ├── FotoService.java
│   │   │   ├── ParvulariaService.java
│   │   │   └── UserService.java
│   │   ├── service/impl/      # Implementaciones de los servicios.
│   │   │   ├── ActividadServiceImpl.java
│   │   │   ├── ApoderadoServiceImpl.java
│   │   │   ├── AsistenciaServiceImpl.java
│   │   │   ├── FotoServiceImpl.java
│   │   │   ├── ParvulariaServiceImpl.java
│   │   │   └── UserServiceImpl.java
│   │   ├── exception/         # Excepciones personalizadas y manejadores globales.
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── NotFoundException.java
│   │   │   ├── BadRequestException.java
│   │   │   └── InternalServerErrorException.java
│   │   ├── security/          # Configuración y filtros de seguridad.
│   │   │   ├── SecurityConfig.java
│   │   │   ├── JwtService.java
│   │   │   ├── JwtAuthFilter.java
│   │   │   ├── RoleConstants.java
│   │   │   └── Permission.java
│   │   ├── utils/             # Clases de utilidad y constantes.
│   │   │   ├── AuthConstants.java
│   │   │   └── PasswordGenerator.java
│   │   └── GestionGuarderiaApplication.java # Clase principal de la aplicación.
│   └── resources/
│       ├── application.yml    # Configuración de la aplicación.
│       └── schema.sql         # Esquema inicial de la base de datos.
└── test/                      # Tests unitarios y de integración.

```

## Buenas prácticas utilizadas en este proyecto
El proyecto de gestión de guardería ha sido diseñado y desarrollado siguiendo una serie de buenas prácticas de ingeniería de software para garantizar calidad, mantenibilidad y escalabilidad. A continuación, se destacan las principales buenas prácticas aplicadas:

### 1. Arquitectura limpia y modular
**Separación de responsabilidades (SoC)**: Cada capa tiene una responsabilidad específica:

- Controller: Maneja las solicitudes HTTP y responde con datos o errores.
- Service: Implementa la lógica de negocio.
- Repository: Se encarga de las operaciones de persistencia en la base de datos.
- DTOs: Gestionan la transferencia de datos entre las capas, manteniendo independencia entre entidades y datos expuestos.
- Entities: Representan el modelo de la base de datos.
  
### 2. Seguridad y autenticación
**Uso de JWT (JSON Web Tokens):**

- Autenticación basada en tokens para mejorar la seguridad.
- Validez y expiración configuradas para mitigar riesgos.
- Refresh tokens para extender sesiones sin requerir credenciales nuevamente.
  
**Autorización basada en roles y permisos:**

- Roles claramente definidos (Parvularia, Asistente de Párvulo, Apoderado) con permisos específicos.
- Uso de @PreAuthorize para restringir accesos en los servicios según roles.

**Cifrado de contraseñas:**

- Contraseñas almacenadas de manera segura utilizando BCrypt.
### 3. Gestión de errores
**Excepciones personalizadas:**

- Clases específicas para errores comunes como NotFoundException, BadRequestException, y InternalServerErrorException.
- GlobalExceptionHandler: Centralización del manejo de excepciones para responder con mensajes uniformes y códigos de estado HTTP adecuados.
  
**Logs detallados:**

- Uso de SLF4J para registrar información importante de eventos, como errores, operaciones críticas y flujos de ejecución.
- Diferenciación de niveles (DEBUG, INFO, WARN, ERROR) para facilitar el monitoreo y la depuración.

### 4. Código limpio

**Uso de Lombok:**

- Reducción de código repetitivo como getters, setters, constructores y métodos toString().
  
**Convenciones de nombres:**

-Métodos y variables utilizan nombres descriptivos que indican claramente su propósito.

**Comentarios y documentación:**

- Métodos y clases documentados para facilitar la comprensión del código.
- Uso de anotaciones estándar (@Override, @NonNull) para garantizar consistencia y reducir errores.
### 5. Reutilización y modularidad
**Servicios reutilizables:**
- Un UserService centralizado para la gestión de usuarios, evitando lógica duplicada en cada servicio específico.
  
**Uso de ModelMapper:**
- Conversión automática entre entidades y DTOs para reducir la complejidad del código.
  
### 6. Configuración centralizada

**Uso de application.yml:**
- Centralización de configuraciones críticas (base de datos, JWT, etc.).
- Facilidad para cambiar parámetros sin modificar el código fuente.
  
### 7. Buen diseño de base de datos
**Relaciones bien definidas:**

- Uso de relaciones entre tablas para garantizar consistencia y minimizar redundancia.
- Uso de claves foráneas para mantener integridad referencial.
  
  **Herencia en la base de datos:**

- Implementación de una estrategia de herencia (@Inheritance) para entidades como Usuario y sus subtipos (Parvularia, Apoderado, etc.).
### 8. Validación y normalización
**Validaciones de datos:**

- Uso de anotaciones como @NotEmpty, @Email, y validadores personalizados (e.g., validador de RUT).
- Validaciones a nivel de DTOs para garantizar datos limpios antes de la lógica de negocio.

  **Configuración de CORS:**

- Configuración flexible de CORS para permitir accesos seguros desde múltiples dominios.
### 9. Uso de Swagger
- Swagger es un conjunto de herramientas que facilita la documentación y prueba de APIs. Al integrarlo en la aplicacion, se puede generar automáticamente una interfaz interactiva que permite explorar y probar los endpoints.
  [! WARNING] > Se implemnto swagger, no obstante no se valido la prueba de los endpoints a travez de este.
### 10. Dockerizacion
- Dockerizar la aplicación permite empaquetarla junto con todas sus dependencias en un contenedor, garantizando que se ejecute de manera consistente en cualquier entorno.

## Pendientes
[! WARNING] > Solo hay algunas rutas protegidas con el fin de ver que funciones el securityfilterchain
- Creacion de Pruebas 
- Validar el manejo de archivos
- Creacion de pipeline con github actions para creacion de contendor , pruebas y despliegue

