package com.example.demo.model.joined;

import jakarta.persistence.Entity;

@Entity
public class EmpleadoPlantaB extends EmpleadoB {
	
	public EmpleadoPlantaB(String nombre, String apellido, String dni, Double sueldoMensual) {
		super(nombre, apellido, dni);
		this.setSueldoMensual(sueldoMensual);
	}

	public Double getSueldoMensual() {
		return sueldoMensual;
	}

	public void setSueldoMensual(Double sueldoMensual) {
		this.sueldoMensual = sueldoMensual;
	}

	private Double sueldoMensual;


}
