# 📚 Clase 4 – Introducción a Spring Data JPA

En esta clase incorporamos **Spring Data JPA** al proyecto para simplificar el acceso a la base de datos mediante la creación de interfaces `Repository`. Dejamos de usar el `EntityManager` directamente y comenzamos a aprovechar la potencia de Spring.

---

## ✅ Objetivos de la Clase

- Integrar Spring Data JPA en un proyecto Spring Boot.
- Crear `Repository` para nuestras entidades.
- Utilizar métodos automáticos para persistir y consultar datos.
- Explorar formas de definir consultas personalizadas.

---

## 🧱 Entidades trabajadas

- `Empresa`
- `Producto`
- `Empleado`
- `Categoria`
- `Sucursal`

---

## 🔌 Repositorios implementados

Se crearon interfaces que extienden `JpaRepository`, por ejemplo:

```java
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByNombre(String nombre);
    
}
empresaRepository.save(empresa);
empresaRepository.findById(1L);
empresaRepository.findAll();
