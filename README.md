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
| 1 | Introducción a Hibernate: persistencia, JDBC vs ORM, primer guardado | _(pendiente)_ |

> Se irá completando a medida que avance el curso.
