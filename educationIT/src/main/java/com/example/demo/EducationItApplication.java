package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.Producto;
import com.example.demo.model.tableperclass.EmpleadoC;
import com.example.demo.model.tableperclass.EmpleadoContratadoC;
import com.example.demo.model.tableperclass.EmpleadoPlantaC;
import com.example.demo.service.EmpleadoService;
import com.example.demo.service.EmpresaService;
import com.example.demo.service.ProductoService;


@SpringBootApplication
public class EducationItApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EducationItApplication.class, args);
		
		
		EmpresaService empresaService = context.getBean(EmpresaService.class);
		EmpleadoService empleadoService = context.getBean(EmpleadoService.class);
		
		ProductoService productoService = context.getBean(ProductoService.class);
		
		//Generamos 6000 productos random
//		 for (int i = 1; i <= 6000; i++) {
//	            Producto producto = new Producto("Producto "+ i,"Desc " + i , Math.random() * 100);
//	            productoService.guardarProducto(producto);
//	        }
		
		List <Producto> productos = productoService.obtenerTodosLosProductos();
		for (Producto producto : productos) {
			System.out.println("Producto: " + producto.getNombre() + ", Precio: " + producto.getPrecio());
		}

	}

}
