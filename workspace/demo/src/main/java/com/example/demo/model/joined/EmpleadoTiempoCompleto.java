package com.example.demo.model.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "EmpleadoTiempoCompletoJoined")
@Table(name = "empleado_tc_joined")
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
