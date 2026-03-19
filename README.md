# 📘 Clase 1 – Introducción a Hibernate

## 📌 Descripción

En esta clase se introducen los fundamentos de la persistencia de datos en aplicaciones Java utilizando Hibernate como framework ORM (Object Relational Mapping).

Se aborda la diferencia entre JDBC y ORM, y se configura un proyecto base funcional conectado a una base de datos relacional.

---

## 🎯 Objetivos

- Comprender el concepto de persistencia.
- Diferenciar JDBC de ORM.
- Entender el rol de Hibernate.
- Configurar un proyecto con Hibernate.
- Realizar la primera operación de guardado en base de datos.

---

## 🧠 Conceptos Clave

### 🔹 Persistencia

Permite almacenar objetos Java en una base de datos para su uso posterior.

### 🔹 JDBC vs ORM

- **JDBC**: manejo manual de conexiones, queries y resultados.
- **ORM**: mapeo automático entre clases y tablas.

### 🔹 Hibernate

Framework ORM que simplifica el acceso a datos evitando SQL manual.

---

## ⚙️ Configuración del Proyecto

### 📦 Dependencias (Maven)

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.0.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
</dependencies>
```
