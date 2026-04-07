# 📘 Clase 5 – Validaciones en Spring Boot con Hibernate Validator

## ✅ Temas vistos

- Introducción a Hibernate Validator (implementación de Bean Validation – JSR 380)
- Anotaciones de validación estándar:
  - `@NotNull`, `@Size`, `@Min`, `@Max`, `@Positive`, `@NotBlank`
- Aplicación de validaciones sobre entidades como `Producto`
- Validación de relaciones (`@NotNull` en campos `@ManyToOne`)
- Validación manual con `Validator.validate(obj)`
- Creación de validaciones personalizadas:
  - Definición de anotación (`@CodigoUnico`)
  - Implementación con `ConstraintValidator`
  - Inyección de dependencias (`ProductoRepository`) dentro del validador
- Errores comunes:
  - `NullPointerException` en validadores por falta de configuración
  - Necesidad del bean `LocalValidatorFactoryBean` para integración con Spring
- Pruebas desde el método `main()` sin usar controlador

---

## 📝 Tarea para la próxima clase

### 🎯 Objetivo:

Completar las **validaciones en todas las entidades del proyecto**.

### 📌 Instrucciones:

1. Agregar anotaciones de validación estándar en:
   - `Empresa` (ej: nombre obligatorio, CUIT único)
   - `Empleado` (nombre, apellido, sueldo)
   - `Sucursal` (nombre, dirección, teléfono)
   - `Categoria` (nombre)

2. Verificar relaciones con `@NotNull` (ej: cada `Empleado` debe tener una `Empresa`).

3. Aplicar mensajes personalizados para cada validación.

4. (Opcional) Crear una validación personalizada adicional, por ejemplo:
   - `@CuitUnico` para validar que no existan dos empresas con el mismo CUIT.

5. Probar todas las validaciones desde el `main()` del proyecto, mostrando errores si existen.

---

📆 **Mostrar funcionando al inicio de la próxima clase.**
