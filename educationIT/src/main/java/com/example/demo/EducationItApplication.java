package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Empresa;
import com.example.demo.service.EmpresaService;
import com.example.demo.service.ProductoService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;


@SpringBootApplication
public class EducationItApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EducationItApplication.class, args);

		EmpresaService empresaService = context.getBean(EmpresaService.class);
		ProductoService productoService = context.getBean(ProductoService.class);
		Validator validator = context.getBean(Validator.class);
		
		Empresa empresaInvalida = new Empresa("AB", "1234567890123"); // Nombre demasiado corto y CUIT con formato incorrecto
        imprimirResultadoValidacion("Empresa inválida", empresaInvalida, validator.validate(empresaInvalida));
        
        Empresa empresaValida = new Empresa("Empresa XYZ", "30-12345678-1"); // Datos válidos
        imprimirResultadoValidacion("Empresa válida", empresaValida, validator.validate(empresaValida));
	
	}
	
	
	  @Bean
	    public Validator localValidatorFactoryBean() {
	        // Permite usar messages.properties y autowire en ConstraintValidators
	        return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	    }

	    // ----------------- Helpers de impresión -----------------

	    private static <T> void imprimirResultadoValidacion(String titulo, T bean, Set<ConstraintViolation<T>> violaciones) {
	        System.out.println("\n[" + titulo + "]");
	        if (violaciones.isEmpty()) {
	            System.out.println(" ✔ Válido");
	        } else {
	            System.out.println(" ✖ Inválido. Detalle:");
	            imprimirViolacionesOrdenadas(violaciones);
	        }
	    }

	    private static void imprimirViolacionesOrdenadas(Set<? extends ConstraintViolation<?>> violaciones) {
	        Map<String, List<String>> porPropiedad = new LinkedHashMap<>();
	        for (ConstraintViolation<?> v : violaciones) {
	            String path = pathToString(v.getPropertyPath());
	            porPropiedad.computeIfAbsent(path, k -> new ArrayList<>()).add(v.getMessage());
	        }
	        porPropiedad.forEach((prop, msgs) -> {
	            System.out.println(" - " + prop + ":");
	            for (String m : msgs) System.out.println("     • " + m);
	        });
	    }

	    private static String pathToString(Path path) {
	        return path == null ? "<root>" : path.toString();
	    }
}
