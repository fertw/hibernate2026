package com.example.demo;

import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.EmpresaService;
import com.example.demo.service.ProductoService;
import jakarta.validation.ValidatorFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Spring Boot NO conecta por defecto su Validator al ConstraintValidatorFactory
    // de Hibernate: sin este bean, la validación en el flush (pre-insert/pre-update)
    // usa reflexión pura y no puede instanciar validadores con dependencias inyectadas
    // (como NombreEmpresaUnicoValidator, que necesita EmpresaRepository).
    // El Validator autoconfigurado por Spring Boot ES un LocalValidatorFactoryBean,
    // que también implementa ValidatorFactory y ya trae SpringConstraintValidatorFactory
    // cableado (es ApplicationContextAware) — alcanza con reusarlo acá.
    @Bean
    HibernatePropertiesCustomizer springValidatorFactory(ValidatorFactory validatorFactory) {
        return properties -> properties.put("jakarta.persistence.validation.factory", validatorFactory);
    }

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 1 — ERROR PROVOCADO #1: falta el starter
    // Antes de agregar spring-boot-starter-validation al pom.xml:
    // @NotBlank compila pero no valida (o no existe el import).
    // Demostrar en el IDE, luego agregar la dependencia y reiniciar.
    // ══════════════════════════════════════════════════════════════════════

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 2 — ERROR PROVOCADO #2: ConstraintViolationException en flush
    // ══════════════════════════════════════════════════════════════════════
    @Bean
    @Order(1)
    CommandLineRunner bloque2_errorEnFlush(EmpresaRepository empresaRepository) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 2 — Error Provocado #2: guardar entidad inválida");
            System.out.println("======================================================");

            try {
                // Una entidad inválida vive feliz en memoria.
                // Explota cuando Hibernate intenta hacer el flush (pre-insert).
                Empresa invalida = new Empresa("");  // nombre en blanco viola @NotBlank
                empresaRepository.save(invalida);   // aquí se dispara el flush
                System.out.println("❌ No debería llegar aquí");
            } catch (Exception e) {
                System.out.println("✅ Excepción esperada: " + e.getClass().getSimpleName());
                // Navegar al root cause para ver el ConstraintViolationException
                Throwable causa = e;
                while (causa.getCause() != null) causa = causa.getCause();
                System.out.println("   Root cause: " + causa.getClass().getSimpleName());
                String mensaje = causa.getMessage();
                System.out.println("   Mensaje:    " + mensaje.substring(0, Math.min(mensaje.length(), 120)));
            }

            System.out.println("\n🎓 Moraleja #2: la entidad inválida vive en memoria;");
            System.out.println("   explota recién cuando intenta entrar a la base.");
            System.out.println("   Por eso conviene validar ANTES, en el servicio.\n");
        };
    }

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 3 — Guardado correcto con datos válidos
    // ══════════════════════════════════════════════════════════════════════
    @Bean
    @Order(2)
    CommandLineRunner bloque3_guardadoValido(EmpresaService empresaService) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 3 — Guardar empresa válida");
            System.out.println("======================================================");

            // guardarValidando() pasa por el Validator de Spring (Spring-aware):
            // @NombreEmpresaUnico puede resolver su EmpresaRepository inyectado.
            // Un empresaRepository.save() directo dispararía la validación de
            // Hibernate en el flush con el factory por reflexión → NoSuchMethodException
            // (ver Moraleja #4 en BLOQUE 6).
            Empresa acme = new Empresa("Acme S.A.", "contacto@acme.com", "30-12345678-9");
            empresaService.guardarValidando(acme);
            System.out.println("✅ Empresa guardada: " + acme);
        };
    }

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 4 — ERROR PROVOCADO #3: cascada de validación vs. de persistencia
    // ══════════════════════════════════════════════════════════════════════
    @Bean
    @Order(3)
    CommandLineRunner bloque4_cascadaValid(EmpresaService empresaService,
                                           EmpresaRepository empresaRepository) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 4 — Error Provocado #3: @Valid en cascada");
            System.out.println("======================================================");

            // Construir empresa válida con producto inválido
            Empresa tech = new Empresa("Tech Corp", "info@tech.com", "20-87654321-5");

            Producto productoMalo = new Producto();
            productoMalo.setNombre("Laptop");
            productoMalo.setPrecio(-999.0);    // viola @Positive
            productoMalo.setCantidad(0);        // viola @Min(1)
            productoMalo.setEmpresa(tech);
            tech.getProductos().add(productoMalo);

            System.out.println("Intentando guardar empresa con producto inválido...");

            try {
                // guardarValidando() usa el Validator de Spring con @Valid en cascada.
                // El path de las violaciones será: productos[0].precio / productos[0].cantidad
                empresaService.guardarValidando(tech);
                System.out.println("❌ No debería llegar aquí");
            } catch (IllegalArgumentException e) {
                System.out.println("✅ Validación capturada antes del flush:");
                System.out.println("   " + e.getMessage());
            }

            System.out.println("\n🎓 Moraleja #3: CascadeType.ALL propaga persistencia.");
            System.out.println("   @Valid propaga validaciones. Son cascadas distintas.\n");
        };
    }

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 5 — Validación programática exitosa (empresa + producto válidos)
    // ══════════════════════════════════════════════════════════════════════
    @Bean
    @Order(4)
    CommandLineRunner bloque5_validacionExitosa(EmpresaService empresaService) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 5 — Validación programática exitosa");
            System.out.println("======================================================");

            Empresa global = new Empresa("Global SA", "hola@global.com", "33-99887766-2");

            Producto p1 = new Producto("Notebook", 1500.0, 10);
            p1.setEmpresa(global);
            global.getProductos().add(p1);

            Producto p2 = new Producto("Mouse", 25.0, 50);
            p2.setEmpresa(global);
            global.getProductos().add(p2);

            Empresa guardada = empresaService.guardarValidando(global);
            System.out.println("✅ Guardada correctamente: " + guardada);
            System.out.println("   Productos: " + guardada.getProductos().size());
        };
    }

    // ══════════════════════════════════════════════════════════════════════
    // BLOQUE 6 — ERROR PROVOCADO #4: NPE en ConstraintValidator sin Spring
    // ══════════════════════════════════════════════════════════════════════
    @Bean
    @Order(5)
    CommandLineRunner bloque6_errorConstraintValidator(EmpresaRepository empresaRepository,
                                                        EmpresaService empresaService) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 6 — Error Provocado #4: @NombreEmpresaUnico");
            System.out.println("======================================================");

            // ── Parte A: nombre duplicado con validación vía SERVICIO (Spring valida → OK) ──
            System.out.println("Parte A: intentar duplicar 'Global SA' via servicio...");
            try {
                Empresa duplicada = new Empresa("Global SA", "otro@mail.com", "27-11111111-3");
                empresaService.guardarValidando(duplicada);
                System.out.println("❌ No debería llegar aquí");
            } catch (IllegalArgumentException e) {
                System.out.println("✅ Unicidad detectada por Spring: " + e.getMessage());
            }

            // ── Parte B: intentar via repo.save() directo (se salta el chequeo) ──
            System.out.println("\nParte B: intentar duplicar 'Global SA' via repo.save() directo...");
            // @NombreEmpresaUnico vive en el grupo ChequeoUnicidad, no en Default.
            // Hibernate solo valida Default en el flush automático → este save()
            // NO revisa unicidad y el duplicado entra sin ningún error.
            // (Antes tirábamos NPE acá: un ConstraintValidator que consulta la base
            // nunca es seguro de correr durante el flush — dispara un flush
            // reentrante sobre la misma Session. Por eso quedó fuera de Default.)
            Empresa duplicada2 = new Empresa("Global SA", "otro@mail.com", "27-22222222-4");
            empresaRepository.save(duplicada2);
            System.out.println("⚠️  Se guardó sin avisar: " + duplicada2.getNombre()
                    + " (id=" + duplicada2.getId() + ") — unicidad NO chequeada");

            System.out.println("\n🎓 Moraleja #4: un ConstraintValidator que consulta la base");
            System.out.println("   no es seguro de correr en el flush automático de Hibernate.");
            System.out.println("   Por eso vive en un grupo aparte (ChequeoUnicidad) y solo");
            System.out.println("   se valida explícitamente en el servicio, antes del save().\n");
        };
    }

    @Bean
    @Order(6)
    CommandLineRunner bloque8_actuator(EmpresaRepository empresaRepository,
                                       ProductoRepository productoRepository) {
        return args -> {
            System.out.println("\n======================================================");
            System.out.println("BLOQUE 6 — Spring Boot Actuator");
            System.out.println("======================================================");
            System.out.println("Datos de ejemplo cargados. Navegar en el browser:");
            System.out.println();
            System.out.println("  http://localhost:8080/actuator");
            System.out.println("    → árbol de endpoints disponibles");
            System.out.println();
            System.out.println("  http://localhost:8080/actuator/health");
            System.out.println("    → estado de la app y de la BD MySQL");
            System.out.println();
            System.out.println("  http://localhost:8080/actuator/metrics");
            System.out.println("    → listado de todas las métricas disponibles");
            System.out.println();
            System.out.println("  http://localhost:8080/actuator/metrics/hibernate.sessions.open");
            System.out.println("    → sesiones Hibernate activas ahora mismo");
            System.out.println();
            System.out.println("  http://localhost:8080/actuator/metrics/hikaricp.connections.active");
            System.out.println("    → conexiones del pool en uso");
            System.out.println();

            long totalEmpresas = empresaRepository.count();
            long totalProductos = productoRepository.count();
            System.out.println("  Registros en BD → empresas: " + totalEmpresas
                    + " | productos: " + totalProductos);
            System.out.println();
        };
    }
}