package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.Producto;
import com.example.demo.service.EmpresaService;


@SpringBootApplication
public class EducationItApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EducationItApplication.class, args);
		
		
		EmpresaService empresaService = context.getBean(EmpresaService.class);
		//empresaService.guardarEmpresaConProductos();
		
		List<Producto> productos = empresaService.obtenerEmpresasConProductos().stream()
				.flatMap(empresa -> empresa.getProductos().stream())
				.collect(Collectors.toList());
		
		for (Producto producto : productos) {
			System.out.println("Producto: " + producto.getNombre() + ", Precio: " + producto.getPrecio());
		}
		
	}

}
