package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.model.tableperclass.EmpleadoC;
import com.example.demo.model.tableperclass.EmpleadoContratadoC;
import com.example.demo.model.tableperclass.EmpleadoPlantaC;
import com.example.demo.service.EmpleadoService;
import com.example.demo.service.EmpresaService;


@SpringBootApplication
public class EducationItApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EducationItApplication.class, args);
		
		
		EmpresaService empresaService = context.getBean(EmpresaService.class);
		EmpleadoService empleadoService = context.getBean(EmpleadoService.class);
		//empresaService.guardarEmpresaConProductos();
		
//		EmpleadoContratadoA empleadoContratado1 = new EmpleadoContratadoA("Juan", 500.0, 40);
//		EmpleadoContratadoA empleadoContratado2 = new EmpleadoContratadoA("Maria", 600.0, 35);
//		EmpleadoPlantaA empleadoPlanta1 = new EmpleadoPlantaA("Carlos", 3000.0);	
//		EmpleadoPlantaA empleadoPlanta2 = new EmpleadoPlantaA("Ana", 3500.0);
//		empleadoService.guardarEmpleadoA(empleadoPlanta1);
//		empleadoService.guardarEmpleadoA(empleadoPlanta2);
//		
//		empleadoService.guardarEmpleadoA(empleadoContratado1);
//		empleadoService.guardarEmpleadoA(empleadoContratado2);
		
//		EmpleadoContratadoB empleadoContratado3 = new EmpleadoContratadoB("Pedro", "Sanchez", "20300439", 550.0, 30);
//		EmpleadoContratadoB empleadoContratado4 = new EmpleadoContratadoB("Lucia", "Gomez", "20300440", 650.0, 25);
//		EmpleadoPlantaB empleadoPlanta3 = new EmpleadoPlantaB("Die Gomez", "Perez", "20300441", 3200.0);
//		EmpleadoPlantaB empleadoPlanta4 = new EmpleadoPlantaB("Sofia", "Lopez", "20300442", 3700.0);
//		
//		empleadoService.guardarEmpleadoB(empleadoPlanta3);
//		empleadoService.guardarEmpleadoB(empleadoPlanta4);
//		empleadoService.guardarEmpleadoB(empleadoContratado3);
//		empleadoService.guardarEmpleadoB(empleadoContratado4);
		
		EmpleadoC empleadoContratado5 = new EmpleadoContratadoC("Miguel", 45, 20.0);
		EmpleadoC empleadoPlanta5 = new EmpleadoPlantaC("Laura", 4000.0);
		
		empleadoService.guardarEmpleadoC(empleadoContratado5);
		empleadoService.guardarEmpleadoC(empleadoPlanta5);
	}

}
