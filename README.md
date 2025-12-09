# Prueba Backend
Proyecto backend para consultar los resultados de una encuesta usando la API de Crowdsignal, diseñado con Arquitectura Hexagonal.

Ofrece dos formas de uso:

1. Interfaz por consola (CLI).
2. Servicio API REST (HTTP).

---

## Tecnologías utilizadas

- Java 17  
- Maven  
- HttpClient  
- Jackson  
- Spark Java  
- Arquitectura Hexagonal  

---

## Arquitectura del proyecto

```
src/
 └─ main/
     ├─ java/
     │   └─ com/encuestas/
     │       ├─ App.java                  # CLI 
     │       ├─ AppRest.java              # API REST
     │       ├─ application/              # Casos de uso
     │       │    └─ ConsultarResultadosEncuestaService.java
     │       ├─ domain/
     │       │    ├─ model/               # Entidades
     │       │    │    ├─ Encuesta.java
     │       │    │    └─ Respuesta.java
     │       │    └─ port/                # Interfaces 
     │       │         └─ ProveedorResultadosEncuesta.java
     │       └─ infrastructure/           # Adaptador API Crowdsignal
     │            └─ CrowdsignalProveedorResultadosEncuesta.java
     └─ resources/
         └─ config.properties             # Configuración interna
```
---

## Instalación y Compilación

### Requisitos

```
java -version
mvn -version
```

### Clonar el repositorio

```
git clone https://github.com/aenocmartinez/encuesta-backend.git
cd encuesta-backend
```

### Compilar

```
mvn clean package
```

JAR generado:

```
target/encuesta-backend-1.0-SNAPSHOT.jar
```

---

## Ejecución por Consola (CLI)

```
java -cp target/encuesta-backend-1.0-SNAPSHOT.jar com.encuestas.App
```

---

## Ejecución modo API REST

```
java -cp target/encuesta-backend-1.0-SNAPSHOT.jar com.encuestas.AppRest
```

Endpoint disponible:

```
GET http://localhost:4567/encuestas/{id}
```

Ejemplo:

```
GET http://localhost:4567/encuestas/10503173
```

---

## Autor

Proyecto desarrollado por:  
**Abimelec Martínez**

Repositorio:  
https://github.com/aenocmartinez/encuesta-backend

