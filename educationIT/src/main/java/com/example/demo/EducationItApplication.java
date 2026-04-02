package com.example.demo;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.service.EmpresaService;
import com.example.demo.service.ProductoService;

@SpringBootApplication
public class EducationItApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EducationItApplication.class, args);

		EmpresaService empresaService = context.getBean(EmpresaService.class);
		ProductoService productoService = context.getBean(ProductoService.class);

		// =====================================================================
		// 1. CREAR EMPRESAS Y PRODUCTOS DE PRUEBA
		// =====================================================================

		// Creamos dos empresas con sus productos asociados
		Empresa empresa1 = new Empresa("TechShop", "20-11111111-1");
		Empresa empresa2 = new Empresa("MegaStore", "20-22222222-2");
		empresaService.guardarEmpresaConProductos(empresa1);
		empresaService.guardarEmpresaConProductos(empresa2);

		// Creamos productos y los asociamos a las empresas
		Producto p1 = new Producto("Notebook", "Laptop 16GB RAM", 1500.0);
		p1.setEmpresa(empresa1);

		Producto p2 = new Producto("Mouse", "Mouse inalámbrico", 25.0);
		p2.setEmpresa(empresa1);

		Producto p3 = new Producto("Teclado", "Teclado mecánico", 80.0);
		p3.setEmpresa(empresa1);

		Producto p4 = new Producto("Monitor", "Monitor 4K 27 pulgadas", 600.0);
		p4.setEmpresa(empresa2);

		Producto p5 = new Producto("Auriculares", "Auriculares Bluetooth", 120.0);
		p5.setEmpresa(empresa2);

		// Guardamos cada producto en la base de datos
		productoService.guardarProducto(p1);
		productoService.guardarProducto(p2);
		productoService.guardarProducto(p3);
		productoService.guardarProducto(p4);
		productoService.guardarProducto(p5);

		System.out.println("\n=== Productos creados ===");
		productoService.obtenerTodosLosProductos()
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// =====================================================================
		// 2. BÚSQUEDA POR NOMBRE
		// =====================================================================

		// Busca el primer producto con ese nombre exacto
		Optional<Producto> porNombre = productoService.buscarPorNombre("Mouse");
		porNombre.ifPresent(p -> System.out.println("\nBuscado por nombre 'Mouse': " + p.getNombre()));

		// Busca productos cuyo nombre contenga "o" (ej: Mouse, Monitor, Notebook, Teclado)
		System.out.println("\n=== Productos que contienen 'o' en el nombre ===");
		productoService.buscarPorNombreContiene("o")
				.forEach(p -> System.out.println(p.getNombre()));

		// Verifica si existe un producto con ese nombre exacto
		boolean existe = productoService.existeProductoConNombre("Notebook");
		System.out.println("\n¿Existe 'Notebook'? " + existe);

		// =====================================================================
		// 3. BÚSQUEDA POR PRECIO
		// =====================================================================

		// Retorna productos con precio mayor a $100
		System.out.println("\n=== Productos con precio mayor a $100 ===");
		productoService.buscarPorPrecioMayorA(100.0)
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// Retorna productos con precio menor a $100
		System.out.println("\n=== Productos con precio menor a $100 ===");
		productoService.buscarPorPrecioMenorA(100.0)
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// Retorna productos cuyo precio esté entre $50 y $700
		System.out.println("\n=== Productos entre $50 y $700 ===");
		productoService.buscarPorRangoDePrecio(50.0, 700.0)
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// =====================================================================
		// 4. ORDENAMIENTO
		// =====================================================================

		// Todos los productos ordenados de menor a mayor precio
		System.out.println("\n=== Productos ordenados por precio (ASC) ===");
		productoService.obtenerOrdenadosPorPrecioAsc()
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// Todos los productos ordenados de mayor a menor precio
		System.out.println("\n=== Productos ordenados por precio (DESC) ===");
		productoService.obtenerOrdenadosPorPrecioDesc()
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// Todos los productos ordenados alfabéticamente
		System.out.println("\n=== Productos ordenados por nombre (A-Z) ===");
		productoService.obtenerOrdenadosPorNombre()
				.forEach(p -> System.out.println(p.getNombre()));

		// =====================================================================
		// 5. PRODUCTOS MÁS CARO Y MÁS BARATO
		// =====================================================================

		// Producto con el precio más alto
		productoService.obtenerMasCaro()
				.ifPresent(p -> System.out.println("\nProducto más caro: " + p.getNombre() + " - $" + p.getPrecio()));

		// Producto con el precio más bajo
		productoService.obtenerMasBarato()
				.ifPresent(p -> System.out.println("Producto más barato: " + p.getNombre() + " - $" + p.getPrecio()));

		// =====================================================================
		// 6. BÚSQUEDA POR EMPRESA
		// =====================================================================

		Long idEmpresa1 = empresa1.getId();

		// Retorna los productos de la empresa 1, ordenados por precio DESC
		System.out.println("\n=== Productos de TechShop (precio DESC) ===");
		productoService.buscarPorEmpresa(idEmpresa1)
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));

		// Cuenta cuántos productos tiene la empresa 1
		long totalEmpresa1 = productoService.contarProductosPorEmpresa(idEmpresa1);
		System.out.println("\nTotal de productos en TechShop: " + totalEmpresa1);

		// =====================================================================
		// 7. ACTUALIZACIÓN
		// =====================================================================

		// Actualiza nombre, descripción y precio del producto con id=1
		Producto datosNuevos = new Producto("Notebook Pro", "Laptop 32GB RAM", 1800.0);
		Producto actualizado = productoService.actualizarProducto(p1.getId(), datosNuevos);
		System.out.println("\nProducto actualizado: " + actualizado.getNombre() + " - $" + actualizado.getPrecio());

		// Aplica un aumento del 10% (factor 1.1) a todos los productos de la empresa 2
		int filasAfectadas = productoService.actualizarPreciosPorEmpresa(empresa2.getId(), 1.1);
		System.out.println("Precios actualizados en MegaStore: " + filasAfectadas + " productos");

		// =====================================================================
		// 8. ELIMINACIÓN
		// =====================================================================

		// Elimina el producto con el nombre exacto "Mouse"
		productoService.eliminarProductoPorNombre("Mouse");
		System.out.println("\nProducto 'Mouse' eliminado.");

		// Lista final de productos para confirmar el estado
		System.out.println("\n=== Lista final de productos ===");
		productoService.obtenerTodosLosProductos()
				.forEach(p -> System.out.println(p.getNombre() + " - $" + p.getPrecio()));
	}

}
