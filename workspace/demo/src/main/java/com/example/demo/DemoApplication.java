package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.model.Direccion;
import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.service.EmpresaService;
import com.example.demo.service.ProductoService;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Todo el demo de la Clase 4 corre acá adentro para verlo apenas arranca la app
    @Bean
    CommandLineRunner demoClase4(EmpresaService empresaService,
                                  ProductoService productoService) {
        return args -> {

            // ════════════════════════════════════════════════════════
            // 1. CRUD básico heredado de JpaRepository — cero código propio
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 1. CRUD BÁSICO ===");

            Direccion direccion1 = new Direccion();
            direccion1.setCalle("Av. Siempre Viva 123");            
            Empresa empresa = new Empresa("Café Martínez","204443598","empre1@mail.com",direccion1);
            empresaService.guardar(empresa);
            System.out.println("Empresas guardadas: " + empresaService.contarEmpresas());

            Producto p1 = new Producto("Notebook", 1500.0);
            p1.setEmpresa(empresa);
            Producto p2 = new Producto("Mouse inalámbrico", 25.0);
            p2.setEmpresa(empresa);
            Producto p3 = new Producto("Teclado mecánico", 80.0);
            p3.setEmpresa(empresa);
            productoService.guardar(p1);
            productoService.guardar(p2);
            productoService.guardar(p3);
            System.out.println("Productos guardados: " + productoService.contarProductos());

            // ════════════════════════════════════════════════════════
            // 2. Query Methods — consultas derivadas del nombre
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 2. QUERY METHODS ===");

            List<Producto> conteniendoMouse = productoService.findByNombreContainingIgnoreCase("mouse");
            System.out.println("Contienen 'mouse': " + conteniendoMouse.size());

            List<Producto> caros = productoService.findByPrecioGreaterThan(100.0);
            System.out.println("Precio > 100: " + caros.size());

            List<Producto> porNombre = productoService.findByNombre("Notebook");
            porNombre.forEach(p -> System.out.println("  " + p.getNombre() + " ($" + p.getPrecio() + ")"));

            System.out.println("¿Existe 'Notebook'?: " + productoService.existsByNombre("Notebook"));

            // findFirst / findTop con Optional — seguros contra listas vacías
            productoService.findFirstByOrderByPrecioAsc()
                .ifPresent(p -> System.out.println("Más barato: " + p.getNombre() + " ($" + p.getPrecio() + ")"));

            List<Producto> top3 = productoService.findTop3ByOrderByPrecioDesc();
            System.out.println("Top 3 más caros:");
            top3.forEach(p -> System.out.printf("  %-20s $%.2f%n", p.getNombre(), p.getPrecio()));

            // ════════════════════════════════════════════════════════
            // 3. @Query — JPQL propia y SQL nativo
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 3. @QUERY ===");

            List<Producto> deLaEmpresa = productoService.buscarPorNombreDeEmpresa("Café Martínez");
            System.out.println("Productos de 'Café Martínez' (JPQL): " + deLaEmpresa.size());

            Producto masCaro = productoService.masCaro(50.0);
            System.out.println("Más caro por encima de $50 (SQL nativo): " + masCaro.getNombre());

            // ════════════════════════════════════════════════════════
            // 4. Paginación — mismo query method, con Pageable al final
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 4. PAGINACIÓN ===");

            Pageable primeraPagina = PageRequest.of(0, 2, Sort.by("precio").descending());
            Page<Producto> pagina = productoService.listadorPaginado(0, 2);
            
            while (true) {
				System.out.println("Página " + (pagina.getNumber() + 1) + " de " + pagina.getTotalPages());
				pagina.getContent().forEach(p -> System.out.println("  " + p.getNombre() + " ($" + p.getPrecio() + ")"));
				if (pagina.hasNext()) {
					pagina = productoService.listadorPaginado(pagina.getNumber() + 1, 2);
				} else {
					break;
				}
			}

            System.out.println("Total elementos: " + pagina.getTotalElements()
                + " | total páginas: " + pagina.getTotalPages()
                + " | ¿es la última?: " + pagina.isLast());
            pagina.getContent().forEach(p -> System.out.println("  " + p.getNombre()));

            // ════════════════════════════════════════════════════════
            // 5. Capa de servicio con @Transactional
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 5. SERVICIO TRANSACCIONAL ===");

           

            List<Producto> resultado = productoService.buscarPorTexto("monitor");
            System.out.println("Buscado vía servicio: " + resultado.size());
        };
    }
}