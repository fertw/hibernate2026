package com.example.demo.model.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "EmpleadoTiempoCompletoSingleTable")
@DiscriminatorValue("TIEMPO_COMPLETO")
public class EmpleadoTiempoCompleto extends Empleado {

	@Column(name = "salario")
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
