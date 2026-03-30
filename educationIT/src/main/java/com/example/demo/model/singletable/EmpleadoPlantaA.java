package com.example.demo.model.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PLANTA")
public class EmpleadoPlantaA extends EmpleadoA {

	private Double salarioMensual;

	public EmpleadoPlantaA() {
		super();
	}
	
	public EmpleadoPlantaA(String nombre, Double salarioMensual) {
		super(nombre);
		this.setSalarioMensual(salarioMensual);
	}

	public Double getSalarioMensual() {
		return salarioMensual;
	}

	public void setSalarioMensual(Double salarioMensual) {
		this.salarioMensual = salarioMensual;
	}

	

}
