package com.example.demo.model.tablaporclase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "EmpleadoTiempoCompletoTablaPorClase")
@Table(name = "empleado_tiempo_completo_tabla_por_clase")
public class EmpleadoTiempoCompleto extends Empleado {

	@Column(name = "salario", nullable = false)
	private Double salario;

	public EmpleadoTiempoCompleto() {
	}

	public EmpleadoTiempoCompleto(String nombre, String apellido, String legajo, Double salario) {
		super(nombre, apellido, legajo);
		this.salario = salario;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

}
