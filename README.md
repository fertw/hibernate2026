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
