package com.example.demo.model.joined;

import jakarta.persistence.Entity;

@Entity
public class EmpleadoContratadoB  extends EmpleadoB {
	
	private Double montoPorHora;
	private Integer horasTrabajadas;
	
	public EmpleadoContratadoB(String nombre, String apellido, String dni, Double montoPorHora,
			Integer horasTrabajadas) {
		super(nombre, apellido, dni);
		this.setMontoPorHora(montoPorHora);
		this.setHorasTrabajadas(horasTrabajadas);
	}

	public Double getMontoPorHora() {
		return montoPorHora;
	}

	public void setMontoPorHora(Double montoPorHora) {
		this.montoPorHora = montoPorHora;
	}

	public Integer getHorasTrabajadas() {
		return horasTrabajadas;
	}

	public void setHorasTrabajadas(Integer horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}

}
