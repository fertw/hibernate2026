package com.example.demo.model.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity (name = "EmpleadoContratadoJoined")
@Table(name = "empleado_contratado_joined")
public class EmpleadoContratado extends Empleado {
	
	@Column(name = "tarifa_por_hora", nullable = false)
	private Double tarifaPorHora;
	@Column(name = "horas_contratadas", nullable = false)
	private Integer horasContratadas;

	public EmpleadoContratado() {
	}

	public EmpleadoContratado(String nombre, String apellido, String legajo, Double tarifaPorHora, Integer horasContratadas) {
		super(nombre, apellido, legajo);
		this.tarifaPorHora = tarifaPorHora;
		this.horasContratadas = horasContratadas;
	}

	public Double getTarifaPorHora() {
		return tarifaPorHora;
	}

	public void setTarifaPorHora(Double tarifaPorHora) {
		this.tarifaPorHora = tarifaPorHora;
	}

	public Integer getHorasContratadas() {
		return horasContratadas;
	}

	public void setHorasContratadas(Integer horasContratadas) {
		this.horasContratadas = horasContratadas;
	}
	
	

}
