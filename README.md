# Prueba Backend
La solución está implementada usando **Arquitectura Hexagonal (Ports & Adapters)**, permitiendo desacoplar la lógica de dominio del proveedor externo y ofreciendo dos formas de interacción:

1. **Interfaz por consola (CLI)**
2. **Servicio API REST (HTTP)**

---

## Tecnologías utilizadas

- **Java 17**
- **Maven**
- **HttpClient**
- **Jackson**
- **Spark Java**
- Arquitectura Hexagonal

---

## Estructura del proyecto

```
src/
 └─ main/
     └─ java/
         └─ com/encuestas/
             ├─ App.java                 # CLI
             ├─ AppRest.java             # API REST
             ├─ domain/
             │    ├─ model/              # Entidades
             │    └─ port/               # Puertos (Interfaces)
             ├─ application/             # Casos de uso
             └─ infrastructure/          # Adaptadores Crowdsignal
```

---

## Requisitos previos

Verificar instalación de Java y Maven:

```bash
java -version
mvn -version
```

---

## Clonar el repositorio

```bash
git clone https://github.com/aenocmartinez/encuesta-backend.git
cd encuesta-backend
```

---

## Compilación

```bash
mvn clean package
```

Se generará el JAR en:

```
target/encuesta-backend-1.0-SNAPSHOT.jar
```

---

# Ejecución por Consola (CLI)

Iniciar:

```bash
java -cp target/encuesta-backend-1.0-SNAPSHOT.jar com.encuestas.App
```

Se mostrará un menú en la terminal:

```
1. Registrar un voto   (abre el navegador con la encuesta)
2. Consultar resultados
3. Salir
```

---

# Ejecución modo API REST

Iniciar:

```bash
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

## Arquitectura Hexagonal (resumen)

El dominio define **puertos** (`ProveedorResultadosEncuesta`) y la capa de infraestructura provee **adaptadores** (Crowdsignal).  
Esto permite cambiar de proveedor externo sin modificar la lógica del dominio.

```
      +-----------------------------+
      |         Application         |
      +--------------+--------------+
                     |
               (Port / Interface)
                     |
      +--------------+--------------+
      |            Domain            |
      +--------------+--------------+
                     |
               (Adapter Impl)
                     |
      +--------------+--------------+
      |        Infrastructure        |
      +-----------------------------+
```

---

## Autor

Proyecto desarrollado por:

**Abimelec Martínez**  
Repositorio público:
https://github.com/aenocmartinez/encuesta-backend.git

