package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Categoria;
import com.example.demo.model.Direccion;
import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaDao;
import com.example.demo.repository.EmpresaDao;
import com.example.demo.service.ProductoService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner productoDemo(ProductoService productoService,
	                               EmpresaDao empresaDao,
	                               CategoriaDao categoriaDao) {
		return args -> {
			System.out.println("========== DEMO Clase 2 - Asociaciones JPA ==========");

			// 1) Empresa 1:1 Direccion. El cascade de la @OneToOne persiste también la dirección.
			Direccion direccion = new Direccion();
			direccion.setCalle("Av. Fontana 250");
			direccion.setCiudad("Trelew");

			Empresa empresa = new Empresa("Cooperativa Rawson", "30-11223344-5", "info@cooprawson.coop.ar", direccion);
			empresaDao.guardar(empresa); // tras persist, empresa.getId() ya viene cargado

			Categoria herramientas = new Categoria("Herramientas");
			Categoria construccion = new Categoria("Construcción");
			categoriaDao.guardar(herramientas);
			categoriaDao.guardar(construccion);

			Long empresaId = empresa.getId();

			// 2) Alta de productos vía el service (1:N con Empresa)
			Producto taladro = productoService.alta(empresaId, "Taladro", 15000.0);
			Producto sierra  = productoService.alta(empresaId, "Sierra",  22000.0);

			// 3) Asignar categorías (N:M, la escribe Producto por ser el dueño)
			productoService.asignarCategoria(taladro.getId(), herramientas.getId());
			productoService.asignarCategoria(taladro.getId(), construccion.getId());
			productoService.asignarCategoria(sierra.getId(),  herramientas.getId());

			// 4) Actualizar precio (dirty checking dentro de la transacción del service)
			productoService.actualizarPrecio(taladro.getId(), 16500.0);

			// 5) Lecturas con JPQL (solo campos simples: no tocamos colecciones LAZY fuera de transacción)
			System.out.println("-- Productos de la empresa " + empresaId + " (JOIN sobre 1:N) --");
			productoService.listarPorEmpresa(empresaId)
					.forEach(p -> System.out.println("  " + p.getNombre() + " -> $" + p.getPrecio()));

			System.out.println("-- Productos de la categoría 'Herramientas' (JOIN sobre N:M) --");
			productoService.listarPorCategoria("Herramientas")
					.forEach(p -> System.out.println("  " + p.getNombre()));

			// 6) JOIN FETCH: traemos la empresa con sus productos ya inicializados
			System.out.println("-- Empresa con productos (JOIN FETCH) --");
			Empresa conProductos = empresaDao.traerConProductos(empresaId);
			System.out.println("  " + conProductos.getNombre() + " tiene " + conProductos.getProductos().size() + " productos");

			System.out.println("=====================================================");
		};
	}

}
