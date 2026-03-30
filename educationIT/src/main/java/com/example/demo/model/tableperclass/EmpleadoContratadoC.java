package com.example.demo.model.tableperclass;

import jakarta.persistence.Entity;

@Entity
public class EmpleadoContratadoC  extends EmpleadoC {
	
	private int horasTrabajadas;
	private double valorHora;
	
	public EmpleadoContratadoC() {
		super();
	}
	
	public EmpleadoContratadoC(String nombre, int horasTrabajadas, double valorHora) {
		super(nombre);
		this.horasTrabajadas = horasTrabajadas;
		this.valorHora = valorHora;
	}

	public int getHorasTrabajadas() {
		return horasTrabajadas;
	}

	public void setHorasTrabajadas(int horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}

	public double getValorHora() {
		return valorHora;
	}

	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}
	
	public double calcularSueldo() {
		return horasTrabajadas * valorHora;
	}

}
