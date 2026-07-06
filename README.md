# 📘 Curso de Hibernate — EducacionIT

Curso práctico de persistencia en Java con **Hibernate / JPA** y **Spring Boot**.
El código se desarrolla en vivo, clase a clase, sobre una historia lineal.

---

## 🗂️ Cómo está organizado el repositorio

- El curso se desarrolla en la rama **`main`** (historia lineal).
- Al cerrar cada clase se crea un **tag** (`clase-1`, `clase-2`, …) con el estado exacto de ese día.
- El material del curso anterior queda archivado en las ramas `clase-1` … `clase-6`.

### Ir al estado de una clase puntual

```bash
git checkout clase-3      # ver el código tal como quedó en la clase 3
git checkout main         # volver a la última versión
```

### Ver qué cambió entre dos clases

```bash
git diff clase-2 clase-3
```

---

## 🐳 Entorno de base de datos (Docker)

El proyecto incluye un `docker-compose.yml` con MySQL y phpMyAdmin.

```bash
docker compose up -d        # levantar MySQL + phpMyAdmin
docker compose ps           # verificar estado (mysql debe quedar "healthy")
docker compose down         # apagar (los datos persisten)
docker compose down -v      # apagar y borrar los datos (empezar de cero)
```

| Servicio | Acceso | Usuario / Pass |
|---|---|---|
| MySQL 8.4 | `localhost:3306` · base `empresa` | `root` / `root` |
| phpMyAdmin | http://localhost:8080 | `root` / `root` |

> Para usar **MySQL Workbench** de escritorio: conexión a `127.0.0.1:3306`, usuario `root`, password `root`.

---

## 📁 Estructura

```
.
├── docker-compose.yml     # entorno de base de datos
├── workspace/             # proyecto Spring Boot del curso (se desarrolla en vivo)
└── README.md
```

---

## 📚 Índice de clases

| Clase | Tema | Tag |
|-------|------|-----|
| 1 | Introducción a Hibernate: persistencia, JDBC vs ORM, primer guardado | `clase-1` |
| 2 | Asociaciones JPA (1:1, 1:N, N:M), capa DAL con EntityManager, servicio y demo | `clase-2` |
| 3 | Mapeo de herencia JPA: `TABLE_PER_CLASS`, `JOINED`, `SINGLE_TABLE` | `clase-3` |

> Se irá completando a medida que avance el curso.

---

## 📘 Clase 2 — Asociaciones JPA, capa de servicio y demo

Modelo de dominio con las tres cardinalidades sobre entidades reales:

- **Empresa 1:1 Direccion** — `@OneToOne` (EAGER) con `cascade = ALL`.
- **Empresa 1:N Producto** — `@OneToMany(mappedBy = "empresa")` (LAZY) con `cascade = ALL` y `orphanRemoval = true`.
- **Producto N:M Categoria** — `@ManyToMany` con `@JoinTable(producto_categoria)`; `Producto` es el dueño.

Conceptos que se trabajan:

- `mappedBy`, cascada y `orphanRemoval`; fetching **LAZY/EAGER**.
- **Helpers de sincronización bidireccional** (`addProducto`, `addCategoria`, `setDireccion`).
- **Capa DAL con `EntityManager`**: `EmpresaDao`, `ProductoDao`, `CategoriaDao`, `DireccionDao`.
- Consultas **JPQL** con `JOIN` y `JOIN FETCH` para recorrer las asociaciones.
- **Capa de servicio** (`ProductoService`) que orquesta varios DAOs con `@Transactional`.
- `CommandLineRunner` de demo que puebla el modelo al arrancar.

> Requiere **JDK 21+** (`java.version = 21`). El proyecto se probó con Temurin 25.
> Ejecutar con la DB levantada (`docker compose up -d`): `./mvnw spring-boot:run`.

---

## 📘 Clase 3 — Mapeo de Herencia con JPA/Hibernate

Se implementaron las tres estrategias de mapeo de herencia de JPA (`TABLE_PER_CLASS`, `JOINED`, `SINGLE_TABLE`) sobre una jerarquía común `Empleado` / `EmpleadoTiempoCompleto` / `EmpleadoContratado`, conviviendo en el mismo `EntityManagerFactory` para comparar el DDL y el comportamiento de consultas polimórficas de cada una.

### Estrategias implementadas

| Estrategia | Paquete | Tablas generadas | Generación de ID |
|---|---|---|---|
| `TABLE_PER_CLASS` | `model.tablaporclase` | `empleados_tiempo_completo`, `empleados_contratados` | `GenerationType.TABLE` |
| `JOINED` | `model.joined` | `empleados_joined`, `empleados_tc_joined`, `empleados_contratados_joined` | `GenerationType.IDENTITY` |
| `SINGLE_TABLE` | `model.singletable` | `empleados_single` (+ columna discriminadora `tipo_empleado`) | `GenerationType.IDENTITY` |

### Errores provocados y resueltos (valor pedagógico)

| Error | Causa | Solución |
|---|---|---|
| `AnnotationException: identity generator ... union-subclass` | `TABLE_PER_CLASS` con `GenerationType.IDENTITY` | Cambiar a `GenerationType.TABLE` |
| `AnnotationException: Entity has no identifier` | `@Id` ausente en la clase abstracta raíz | El `@Id` siempre va en la raíz de la jerarquía |
| `DuplicateMappingException: share the entity name 'Empleado'` | Clases con mismo simple name en distintos paquetes, sin `@Entity(name=...)` | Nombre de entidad único explícito en las 9 clases (`EmpleadoTPC`, `EmpleadoJoined`, `EmpleadoSingleTable`, etc.) |
| `BeanCreationException: bean 'empleadoRepository' ... already been defined` | Interfaces `JpaRepository` con mismo simple name en distintos paquetes | Renombrar interfaces: `EmpleadoTablaPorClaseRepository`, `EmpleadoJoinedRepository`, `EmpleadoSingleTableRepository` |

### Archivos principales

```
model/tablaporclase/Empleado.java
model/tablaporclase/EmpleadoTiempoCompleto.java
model/tablaporclase/EmpleadoContratado.java

model/joined/Empleado.java
model/joined/EmpleadoTiempoCompleto.java
model/joined/EmpleadoContratado.java

model/singletable/Empleado.java
model/singletable/EmpleadoTiempoCompleto.java
model/singletable/EmpleadoContratado.java

repository/tablaporclase/EmpleadoTablaPorClaseRepository.java
repository/joined/EmpleadoJoinedRepository.java
repository/singletable/EmpleadoSingleTableRepository.java

DemoApplication.java (3 CommandLineRunner, uno por estrategia)
```

### Aprendizajes clave

- El modelo relacional no soporta la relación "es un" nativamente; JPA resuelve esto con `@Inheritance`.
- El nombre de entidad JPA y el nombre de bean de repositorio Spring se resuelven por **simple name**, no por paquete completo — reusar nombres de clase entre paquetes exige desambiguar explícitamente en cada capa (`@Entity(name=...)`, nombre de interfaz).
- `TABLE_PER_CLASS` requiere `GenerationType.TABLE` para evitar colisión de IDs entre tablas de subclase.
- `SINGLE_TABLE` es la estrategia más performante pero exige que los atributos de subclase acepten `NULL`.
