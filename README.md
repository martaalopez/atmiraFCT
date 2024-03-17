# ATMIRA FTC SISTEMA GESTION PROYECTOS - Backend

<div align="center">
   <a href="https://github.com/luishidalgoa/FCT_Atmira_Front"></a>
      <img src="https://github.com/luishidalgoa/FCT_Atmira_Front/blob/master/src/assets/atmira_logo.png" alt="Logo" width="200" />
   </a>
</div>

## Índice:

1. [Acerca de](#acerca-de)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Documentación](#documentación)
4. [Pre Requisitos](#pre-requisitos)
5. [Instalación](#instalación)
6. [Colaborar](#colaborar)
   - [Guía rápida](#guía-rápida)
7. [Colaboradores](#colaboradores)

## Acerca de:

La API REST del Sistema de Gestión de Proyectos de ATMIRA FTC proporciona una plataforma robusta para gestionar proyectos, tareas y subtareas de manera eficiente y colaborativa. Desde el manejo de proyectos hasta la autenticación y autorización de usuarios, cada aspecto ha sido desarrollado con precisión y seguridad en mente.

<footer>
  
> Versión Publicada V-1.0
  
</footer>

## Documentación:
Para obtener más detalles sobre los endpoints disponibles y cómo interactuar con la API, consulta la documentación en 
   - [Swagger UI](https://atmira-backend.onrender.com/swagger-ui/index.html#/)
   - [Postman Doc](https://documenter.getpostman.com/view/32223959/2sA2xnwUY2)

## Tecnologías Utilizadas:

- Java 17
- Spring Boot 3
- Spring Security
- Maven
- Mockito
- Docker
- MySQL

## Pre Requisitos:

Para ejecutar la aplicación backend, se requiere tener instalado Java 17 y otras dependencias necesarias para ejecutar una aplicación Spring Boot.

## Instalación:

1. Clona el repositorio [https://github.com/luishidalgoa/Atmira_Backend](https://github.com/luishidalgoa/Atmira_Backend)
2. Ejecuta la aplicación utilizando las siguientes alternativas:
  - Maven
  ```sh
  mvn spring-boot:run -DDB_USERNAME=<nombre> -DPORT=<recomendado = 8080> -DFront_URL=<recomendado = *> -DDB_PASSWORD=<password> -DDB_jdbc=<jdbc:mysql://localhost/atmira">
  ```
  - Docker<br>
>  <p>En la carpeta raiz del proyecto</p>
  
  ```sh
  docker build -t atmira .
  ```
>  <p>Una vez se ha construido la imagen, ahora</p>
  
  ```sh
  docker run --rm -it -e "DB_USERNAME=<nombre>" -e "PORT=<recomendado = 8080>" -e "Front_URL=<recomendado = *>" -e "DB_PASSWORD=<password> -e "DB_jdbc=<jdbc:mysql://localhost/atmira"> atmira
  ```
## Colaborar:

### Guía rápida:

1. Haz un fork del proyecto.
2. Clona tu fork (`git clone <URL del fork>`).
3. Añade el repositorio original como remoto (`git remote add upstream <URL del repositorio original>`).
4. Crea tu Rama de Funcionalidad (`git switch -c feature/NuevaCaracteristica`).
5. Realiza tus Cambios (`git commit -m 'Agregado: alguna NuevaCaracteristica'`).
6. Haz Push a la Rama (`git push origin feature/NuevaCaracteristica`).
7. Abre una pull request para revisar tus cambios.

### Colaboradores:

[![Contribuidores](https://contrib.rocks/image?repo=luishidalgoa/Atmira_Backend)](https://github.com/luishidalgoa/Atmira_Backend/graphs/contributors)

## Stack Tecnologías:
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Mocha](https://img.shields.io/badge/-mocha-%238D6748?style=for-the-badge&logo=mocha&logoColor=white)
