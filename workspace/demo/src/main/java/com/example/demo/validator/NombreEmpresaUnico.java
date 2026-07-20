package com.example.demo.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// @Constraint enlaza esta anotación con la clase que implementa la lógica
@Documented
@Constraint(validatedBy = NombreEmpresaUnicoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NombreEmpresaUnico {

    // Usamos clave para externalizar en ValidationMessages.properties
    String message() default "{empresa.nombre.unico}";

    // Estos dos atributos son OBLIGATORIOS por la especificación Bean Validation
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
