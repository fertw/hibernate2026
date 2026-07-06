package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.joined.EmpleadoContratado;
import com.example.demo.model.joined.EmpleadoTiempoCompleto;
import com.example.demo.repository.joined.EmpleadoJoinedRepository;
import com.example.demo.repository.singletable.EmpleadoSingleTableRepository;
import com.example.demo.repository.tablaporclase.EmpleadoTablaPorClaseRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// ---------- TABLE_PER_CLASS ----------
	@Bean
	CommandLineRunner demoTablaPorClase(
			EmpleadoTablaPorClaseRepository repo) {
		return args -> {
			com.example.demo.model.tablaporclase.EmpleadoTiempoCompleto tc =
					new com.example.demo.model.tablaporclase.EmpleadoTiempoCompleto("Ana", "Pérez", "TC1-001", 950000.0);
			com.example.demo.model.tablaporclase.EmpleadoContratado ec =
					new com.example.demo.model.tablaporclase.EmpleadoContratado("Luis", "Gómez", "CT1-014", 8500.0, 120);

			repo.save(tc);
			repo.save(ec);

			System.out.println("\n=== TABLE_PER_CLASS ===");
			repo.findAll().forEach(e ->
				System.out.println(e.getClass().getSimpleName() + " -> " + e.getNombre() + " (legajo " + e.getLegajo() + ")")
			);
		};
	}

	// ---------- JOINED ----------
	@Bean
	CommandLineRunner demoJoined(EmpleadoJoinedRepository repo) {
		return args -> {
			EmpleadoTiempoCompleto tc = new EmpleadoTiempoCompleto("Ana","Pérez", "TC1-001", 950000.0);
			EmpleadoContratado ec = new EmpleadoContratado("Luis", "Gómez", "CT1-014", 8500.0, 120);

			repo.save(tc);
			repo.save(ec);

			System.out.println("\n=== JOINED ===");
			// Con spring.jpa.show-sql=true acá se ve el LEFT JOIN generado por Hibernate.
			repo.findAll().forEach(e ->
				System.out.println(e.getClass().getSimpleName() + " -> " + e.getNombre() + " (legajo " + e.getLegajo() + ")")
			);
		};
	}

	// ---------- SINGLE_TABLE ----------
	@Bean
	CommandLineRunner demoSingleTable(
			EmpleadoSingleTableRepository repo) {
		return args -> {
			com.example.demo.model.singletable.EmpleadoTiempoCompleto tc =
					new com.example.demo.model.singletable.EmpleadoTiempoCompleto("Ana", "Pérez", "TC1-001", 950000.0);
			com.example.demo.model.singletable.EmpleadoContratado ec =
					new com.example.demo.model.singletable.EmpleadoContratado("Luis", "Gómez", "CT1-014", 8500.0, 120);

			repo.save(tc);
			repo.save(ec);

			System.out.println("\n=== SINGLE_TABLE ===");
			// Acá se ve el discriminador tipo_empleado en la misma tabla empleados_single.
			repo.findAll().forEach(e ->
				System.out.println(e.getClass().getSimpleName() + " -> " + e.getNombre() + " (legajo " + e.getLegajo() + ")")
			);
		};
	}
}