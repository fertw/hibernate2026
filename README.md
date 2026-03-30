# 🧜‍♂️ Clase 3 – Mapeo de Herencia en JPA

En esta clase se implementaron y compararon las tres estrategias principales de mapeo de herencia en JPA/Hibernate, cada una organizada en su propio paquete y con ejemplos de uso.

## 🎯 Objetivos

- Entender cómo funciona la herencia en JPA.
- Aplicar los distintos tipos de mapeo.
- Ver cómo se reflejan en la base de datos.
- Probar consultas polimórficas.

---

## 📁 Estructura del Proyecto

```text
model/
├── singletable/
│   ├── EmpleadoA.java          (abstracta)
│   ├── EmpleadoPlantaA.java
│   └── EmpleadoContratadoA.java
├── joined/
│   ├── EmpleadoB.java          (abstracta)
│   ├── EmpleadoPlantaB.java
│   └── EmpleadoContratadoB.java
└── tableperclass/
    ├── EmpleadoC.java          (abstracta)
    ├── EmpleadoPlantaC.java
    └── EmpleadoContratadoC.java
```

---

## 1. SINGLE_TABLE

**Paquete:** `com.example.demo.model.singletable`

Toda la jerarquía se guarda en **una sola tabla**. Una columna discriminadora (`tipo_empleado`) indica de qué tipo concreto es cada fila.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_empleado", discriminatorType = DiscriminatorType.STRING)
public abstract class EmpleadoA { ... }

@Entity
@DiscriminatorValue("PLANTA")
public class EmpleadoPlantaA extends EmpleadoA {
    private Double salarioMensual;
}

@Entity
@DiscriminatorValue("CONTRATADO")
public class EmpleadoContratadoA extends EmpleadoA {
    private Double salario;
    private Integer horasTrabajadas;
}
```

**Tabla generada:**

| id  | tipo_empleado | nombre | salarioMensual | salario | horasTrabajadas |
| --- | ------------- | ------ | -------------- | ------- | --------------- |
| 1   | PLANTA        | Juan   | 500000.0       | null    | null            |
| 2   | CONTRATADO    | Ana    | null           | 3000.0  | 80              |

> **Ventaja:** una sola tabla, consultas simples.
> **Desventaja:** columnas nullable para atributos de subclases.

---

## 2. JOINED

**Paquete:** `com.example.demo.model.joined`

Cada clase tiene **su propia tabla**. La tabla padre almacena los atributos comunes y las tablas hijas los específicos. Las consultas usan `JOIN`.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EmpleadoB {
    private String nombre;
    private String apellido;
    private String dni;
}

@Entity
public class EmpleadoPlantaB extends EmpleadoB {
    private Double sueldoMensual;
}

@Entity
public class EmpleadoContratadoB extends EmpleadoB {
    private Double montoPorHora;
    private Integer horasTrabajadas;
}
```

**Tablas generadas:**

`empleado_b`: id | nombre | apellido | dni

`empleado_planta_b`: id (FK) | sueldo_mensual

`empleado_contratado_b`: id (FK) | monto_por_hora | horas_trabajadas

> **Ventaja:** esquema normalizado, sin columnas nullable.
> **Desventaja:** queries con JOIN pueden ser más lentas.

---

## 3. TABLE_PER_CLASS

**Paquete:** `com.example.demo.model.tableperclass`

Cada clase **concreta** tiene su propia tabla completa, incluyendo los atributos heredados. No existe tabla para la clase abstracta.

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EmpleadoC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ⚠️ No usar IDENTITY
    private Long id;
    private String nombre;
}

@Entity
public class EmpleadoPlantaC extends EmpleadoC {
    private double salarioMensual;

    public double calcularSueldo() {
        return salarioMensual;
    }
}

@Entity
public class EmpleadoContratadoC extends EmpleadoC {
    private int horasTrabajadas;
    private double valorHora;

    public double calcularSueldo() {
        return horasTrabajadas * valorHora;
    }
}
```

**Tablas generadas:**

`empleado_planta_c`: id | nombre | salario_mensual

`empleado_contratado_c`: id | nombre | horas_trabajadas | valor_hora

> **Ventaja:** tablas independientes y completas, sin JOINs.
> **Desventaja:** atributos comunes repetidos en cada tabla. Requiere `GenerationType.AUTO` para mantener IDs únicos entre tablas.

---

## 📊 Comparativa de Estrategias

| Estrategia      | Tablas generadas     | JOIN requerido | Columnas nullable | GenerationType     |
| --------------- | -------------------- | -------------- | ----------------- | ------------------ |
| SINGLE_TABLE    | 1                    | No             | Sí                | IDENTITY / AUTO    |
| JOINED          | 1 por clase          | Sí             | No                | IDENTITY / AUTO    |
| TABLE_PER_CLASS | 1 por clase concreta | No             | No                | AUTO (obligatorio) |

---

## 💡 Consideraciones Clave

- En `TABLE_PER_CLASS` **no se puede usar `GenerationType.IDENTITY`** porque cada tabla maneja su propia secuencia y los IDs podrían repetirse entre tablas hermanas. Se usa `GenerationType.AUTO` que delega en una secuencia global.
- `SINGLE_TABLE` es la estrategia **más performante** para consultas polimórficas (todo en una tabla).
- `JOINED` es la más **normalizada** y adecuada cuando las subclases tienen muchos atributos propios.
- `TABLE_PER_CLASS` evita JOINs pero **duplica** los atributos de la superclase en cada tabla.
