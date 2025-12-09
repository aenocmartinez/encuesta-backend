# Prueba Backend – Encuesta Crowdsignal

Este proyecto implementa una prueba técnica para consultar los resultados de una encuesta usando la API de **Crowdsignal**, desarrollado con **Arquitectura Hexagonal (Ports & Adapters)**.

Se ofrecen dos formas de interacción:

1. **Interfaz por consola (CLI)**
2. **Servicio API REST (HTTP)**

La lógica de dominio está desacoplada del proveedor externo mediante un puerto (`ProveedorResultadosEncuesta`) y un adaptador (`CrowdsignalProveedorResultadosEncuesta`).

---

## Tecnologías utilizadas

- **Java 17**
- **Maven**
- **Spark Java** (API REST)
- **HttpClient** (consumo API)
- **Jackson** (JSON)
- **Arquitectura Hexagonal**

---

## Estructura del proyecto

```
src/
 └─ main/
     ├─ java/
     │   └─ com/encuestas/
     │       ├─ App.java                # CLI
     │       ├─ AppRest.java            # API REST
     │       ├─ Config.java             # Carga config.properties
     │       ├─ application/            # Caso de uso
     │       ├─ domain/
     │       │   ├─ model/              # Entidades
     │       │   └─ port/               # Puertos (Interfaces)
     │       └─ infrastructure/         # Adaptador Crowdsignal
     └─ resources/
         ├─ config.properties           # Credenciales reales (ignorado por git)
         └─ config.example.properties   # Plantilla para el revisor
```

---

## Requisitos previos

Verificar instalación:

```bash
java -version
mvn -version
```

Debe mostrarse **Java 17** o superior.

---

## Instalación

Clonar el repositorio:

```bash
git clone https://github.com/aenocmartinez/encuesta-backend.git
cd encuesta-backend
```

---

## Configuración

### 1. Crear archivo de configuración

El proyecto incluye una plantilla:

```
src/main/resources/config.example.properties
```

Copiarla a:

```bash
cp src/main/resources/config.example.properties src/main/resources/config.properties
```

Editar `config.properties` con tus valores:

```
encuesta.id=10503173
crowdsignal.partnerGuid=TU_PARTNER_GUID
crowdsignal.userCode=TU_USER_CODE
```

⚠️ **Importante**:  
`config.properties` contiene credenciales y **no debe subir al repositorio**.

---

## Compilación

Ejecutar:

```bash
mvn clean package
```

Se genera:

```
target/encuesta-backend-1.0-SNAPSHOT.jar
```

---

# Ejecución por Consola (CLI)

Iniciar el modo consola:

```bash
java -cp target/encuesta-backend-1.0-SNAPSHOT.jar com.encuestas.App
```

Menú disponible:

```
====== Menú Encuesta ======
1. Registrar un voto (abre navegador)
2. Consultar resultados
3. Salir
```

- **Opción 1** abre la encuesta en el navegador
- **Opción 2** consulta resultados y los muestra en tabla

---

# Ejecución modo API REST

Iniciar el servicio HTTP:

```bash
java -cp target/encuesta-backend-1.0-SNAPSHOT.jar com.encuestas.AppRest
```

Servicio disponible en:

```
GET http://localhost:4567/encuestas/{id}
```

Ejemplo real:

```
GET http://localhost:4567/encuestas/10503173
```

Respuesta en JSON:

```json
{
  "id": "10503173",
  "respuestas": [
    { "id": 48579900, "texto": "Europa League", "total": 1, "porcentaje": 100.0 },
    ...
  ]
}
```

---

## Arquitectura Hexagonal (resumen)

La lógica de dominio define **qué debe suceder** mediante una interfaz (`ProveedorResultadosEncuesta`).  
La infraestructura implementa **cómo sucede** hablando con Crowdsignal.

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

Esto permite cambiar de proveedor externo sin modificar ninguna lógica del dominio.

---

## Autor

Proyecto desarrollado como **Prueba Backend** por:

**Abimelec Martínez**  
https://github.com/aenocmartinez/encuesta-backend

---

## Licencia
Uso académico y demostrativo. No usar credenciales reales en repositorios públicos.
