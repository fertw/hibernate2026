package com.example.demo.model.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("CONTRATADO")
public class EmpleadoContratadoA extends EmpleadoA {

	private Double salario;
	private Integer horasTrabajadas;

	public EmpleadoContratadoA() {
		super();
	}
	
	public EmpleadoContratadoA(String nombre, Double salario, Integer horasTrabajadas) {
		super(nombre);
		this.setSalario(salario);
		this.setHorasTrabajadas(horasTrabajadas);
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getHorasTrabajadas() {
		return horasTrabajadas;
	}

	public void setHorasTrabajadas(Integer horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}


}
