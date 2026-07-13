package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import com.example.demo.dto.ProductoDTO;
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

    // Demo de la Clase 5: la capa de presentación (este runner) ya NO ve
    // entidades Producto — solo recibe ProductoDTO desde el servicio.
    @Bean
    CommandLineRunner demoClase5(EmpresaService empresaService,
                                  ProductoService productoService) {
        return args -> {

            // ════════════════════════════════════════════════════════
            // 1. CRUD básico — la carga inicial sigue usando entidades
            //    (es el "adentro" de la app); las LECTURAS ya devuelven DTO
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 1. CRUD BÁSICO ===");

            Direccion direccion1 = new Direccion();
            direccion1.setCalle("Av. Siempre Viva 123");
            Empresa empresa = new Empresa("Café Martínez", "204443598", "empre1@mail.com", direccion1);
            empresaService.guardar(empresa);
            System.out.println("Empresas guardadas: " + empresaService.contarEmpresas());

            Producto p1 = new Producto("Notebook", 1500.0);
            p1.setEmpresa(empresa);
            Producto p2 = new Producto("Mouse inalámbrico", 25.0);
            p2.setEmpresa(empresa);
            Producto p3 = new Producto("Teclado mecánico", 80.0);
            p3.setEmpresa(empresa);
            // guardar() ahora DEVUELVE un DTO: la entidad entra, el DTO sale
            ProductoDTO guardado = productoService.guardar(p1);
            System.out.println("Guardado (DTO): " + guardado);
            productoService.guardar(p2);
            productoService.guardar(p3);
            System.out.println("Productos guardados: " + productoService.contarProductos());

            // ════════════════════════════════════════════════════════
            // 1-B. ALTA 100% CON DTO — entra DTO, sale DTO.
            //      La empresa viaja como String y el SERVICIO la resuelve.
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 1-B. ALTA CON DTO ===");

            ProductoDTO nuevoDTO = new ProductoDTO(null, "Monitor 24 pulgadas", 320.0, "Café Martínez");
            ProductoDTO creado = productoService.crearProducto(nuevoDTO);
            System.out.println("Creado: " + creado); // ya viene con id y empresa resueltos

            // Empresa inexistente → orElseThrow → ROLLBACK: no queda producto huérfano
            try {
                productoService.crearProducto(
                        new ProductoDTO(null, "Producto Fantasma", 999.0, "Empresa Trucha SA"));
            } catch (RuntimeException ex) {
                System.out.println("Excepción: " + ex.getMessage());
            }
            System.out.println("¿Quedó 'Producto Fantasma'?: "
                    + productoService.existsByNombre("Producto Fantasma")); // No existe ✅

            // ════════════════════════════════════════════════════════
            // 2. Query Methods — mismas consultas, pero el runner recibe DTOs
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 2. QUERY METHODS (via DTO) ===");

            List<ProductoDTO> conteniendoMouse = productoService.buscarPorTexto("mouse");
            System.out.println("Contienen 'mouse': " + conteniendoMouse.size());

            List<ProductoDTO> caros = productoService.buscarPorPrecioMayorA(100.0);
            System.out.println("Precio > 100: " + caros.size());

            List<ProductoDTO> porNombre = productoService.buscarPorNombre("Notebook");
            porNombre.forEach(dto -> System.out.println("  " + dto.getNombre() + " ($" + dto.getPrecio() + ")"));

            System.out.println("¿Existe 'Notebook'?: " + productoService.existsByNombre("Notebook"));

            // Optional<ProductoDTO> — sigue siendo seguro contra listas vacías
            productoService.masBarato()
                .ifPresent(dto -> System.out.println("Más barato: " + dto.getNombre() + " ($" + dto.getPrecio() + ")"));

            List<ProductoDTO> top3 = productoService.top3MasCaros();
            System.out.println("Top 3 más caros:");
            top3.forEach(dto -> System.out.printf("  %-20s $%.2f%n", dto.getNombre(), dto.getPrecio()));

            // ════════════════════════════════════════════════════════
            // 3. @Query — JPQL propia y SQL nativo, salida en DTO.
            //    empresaNombre ya viene APLANADO: acá afuera no hay sesión,
            //    no hay proxies, no hay LazyInitializationException posible.
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 3. @QUERY (via DTO) ===");

            List<ProductoDTO> deLaEmpresa = productoService.buscarPorNombreDeEmpresa("Café Martínez");
            System.out.println("Productos de 'Café Martínez' (JPQL): " + deLaEmpresa.size());
            deLaEmpresa.forEach(dto -> System.out.println("  " + dto.getNombre() + " -> " + dto.getEmpresaNombre()));

            ProductoDTO masCaro = productoService.masCaro(50.0);
            System.out.println("Más caro por encima de $50 (SQL nativo): "
                    + (masCaro != null ? masCaro.getNombre() : "sin resultados"));

            // ════════════════════════════════════════════════════════
            // 4. Paginación — Page<ProductoDTO>: el map() a DTO conservó
            //    toda la metadata (número, total, hasNext...)
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 4. PAGINACIÓN (via DTO) ===");

            Page<ProductoDTO> pagina = productoService.listadoPaginado(0, 2);

            while (true) {
                System.out.println("Página " + (pagina.getNumber() + 1) + " de " + pagina.getTotalPages());
                pagina.getContent().forEach(dto -> System.out.println("  " + dto.getNombre() + " ($" + dto.getPrecio() + ")"));
                if (pagina.hasNext()) {
                    pagina = productoService.listadoPaginado(pagina.getNumber() + 1, 2);
                } else {
                    break;
                }
            }

            System.out.println("Total elementos: " + pagina.getTotalElements()
                + " | total páginas: " + pagina.getTotalPages()
                + " | ¿es la última?: " + pagina.isLast());

            // ════════════════════════════════════════════════════════
            // 5. Capa de servicio con @Transactional
            // ════════════════════════════════════════════════════════
            System.out.println("\n=== 5. SERVICIO TRANSACCIONAL ===");

            List<ProductoDTO> resultado = productoService.buscarPorTexto("monitor");
            System.out.println("Buscado vía servicio: " + resultado.size()); // ahora encuentra el del bloque 1-B
        };
    }
}