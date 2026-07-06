package com.example.demo.model.tablaporclase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "EmpleadoTablaPorClase")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Empleado {

	// GenerationType.TABLE crea una tabla adicional para generar los
	// identificadores únicos de las entidades. Esta estrategia es útil cuando se
	// desea tener un control más explícito sobre la generación de identificadores y
	// se quiere evitar posibles conflictos con otras estrategias de generación de
	// identificadores.
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "apellido", nullable = false)
	private String apellido;
	@Column(name = "legajo", nullable = false, unique = true)
	private String legajo;
	
	public Empleado() {
	}
	
	public Empleado(String nombre, String apellido, String legajo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.legajo = legajo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	
	

}
