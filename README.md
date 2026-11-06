# TecnoMichi

Aplicación de escritorio para la gestión de una tienda de celulares: catálogo, carrito, compras y usuarios, con persistencia en MongoDB.

Proyecto académico de **Programación Orientada a Objetos** (tercer semestre), desarrollado en Java con Swing y evolucionado hasta una arquitectura por capas con interfaces, pruebas unitarias y medición de cobertura.

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-5-78A641)
![MongoDB](https://img.shields.io/badge/MongoDB-6-47A248?logo=mongodb&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.13-D22128)

---

## Qué hace

- **Inicio de sesión y registro** de usuarios con roles (administrador / comprador).
- **Catálogo de celulares** con imágenes, búsqueda y detalle por producto.
- **Carrito y compras**: el cliente arma su pedido y el sistema registra la compra.
- **Panel de administración** para dar de alta celulares y consultar usuarios.
- **Persistencia en MongoDB**: usuarios, celulares y compras en colecciones separadas.

## Arquitectura

El proyecto está separado en cuatro capas y depende de **interfaces**, no de implementaciones (principio de inversión de dependencias):

| Paquete | Responsabilidad |
|---|---|
| `Main` | Punto de entrada (`Main.P2Actividad`): arranca la vista de login |
| `Vista` | Formularios Swing (`Inicio`, `Registro`, `Celulares`, `VistaCompra`, `VistaUsuario`) |
| `Controlador` | Casos de uso y coordinación entre vista y persistencia |
| `Modelo` | Entidades (`Celular`, `Compra`, `Usuario`) e interfaces `IRepository`, `I*Service` |
| `Modelo` / `Controlador` | Implementaciones de repositorios y conexión a MongoDB |

Las interfaces `ICelularRepository`, `ICompraRepository`, `IUsuarioRepository` y sus servicios permiten sustituir la persistencia sin tocar la lógica, y es lo que hace testeable la capa de servicios.

## Pruebas

```bash
mvn test
```

- 7 pruebas unitarias con **JUnit 5** y **Mockito** sobre `CelularServiceImpl`, `CompraServiceImpl` y `UsuarioServiceImpl`.
- Los repositorios se simulan con mocks, de modo que las pruebas no necesitan una instancia de MongoDB.
- **JaCoCo** genera el reporte de cobertura al ejecutar:

```bash
mvn verify
# reporte: target/site/jacoco/index.html
```

## Cómo ejecutarlo

### Requisitos

- JDK 21 o superior
- Maven 3.9+
- MongoDB escuchando en `mongodb://localhost:27017`

### Pasos

```bash
git clone https://github.com/Corinaast/TecnoMichi.git
cd TecnoMichi
mvn -B verify     # compila, ejecuta las pruebas y genera el reporte de cobertura
mvn exec:java     # inicia la aplicación (Main.P2Actividad)
```

La conexión se configura en `Controlador/MongoDBConnection.java` y `Modelo/Mongodb.java`. Ajusta la URI y el nombre de la base si usas MongoDB Atlas u otra instalación.

## Estructura

```
TecnoMichi/
├── pom.xml
├── nbproject/                  # metadatos del proyecto NetBeans
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── Main/           # clase ejecutable
    │   │   ├── Controlador/    # controladores + conexión MongoDB
    │   │   ├── Modelo/         # entidades e interfaces
    │   │   └── Vista/          # formularios Swing (.java + .form)
    │   └── resources/          # imágenes del catálogo e iconos
    └── test/java/ControladorTest/   # pruebas con JUnit 5 + Mockito
```

## Contexto del proyecto

Este repositorio consolida la versión final del proyecto, que en el curso se entregó por etapas: interfaz Swing, migración a MVC, aplicación de SOLID, pruebas con Mockito y medición con JaCoCo. Las versiones intermedias quedaron archivadas en los repositorios `P3ProyectoMaven`, `P3JACOCO`, `Solid-y-Mockito` y `Cambios-de-TecnoMichi`.

## Equipo

Proyecto grupal desarrollado por:

- **Corina Acosta** — [@Corinaast](https://github.com/Corinaast)
- **Bryan Cevallos**

---

Proyecto académico sin fines comerciales. Las imágenes del catálogo pertenecen a sus respectivos autores.
