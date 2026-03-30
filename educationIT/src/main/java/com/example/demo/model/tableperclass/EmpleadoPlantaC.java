package com.example.demo.model.tableperclass;

import jakarta.persistence.Entity;

@Entity
public class EmpleadoPlantaC extends EmpleadoC {
	
	private double salarioMensual;
	
	public EmpleadoPlantaC() {
		super();
	}
	
	public EmpleadoPlantaC(String nombre, double salarioMensual) {
		super(nombre);
		this.salarioMensual = salarioMensual;
	}

	public double getSalarioMensual() {
		return salarioMensual;
	}

	public void setSalarioMensual(double salarioMensual) {
		this.salarioMensual = salarioMensual;
	}
	
	public double calcularSueldo() {
		return salarioMensual;
	}
	

	

}
