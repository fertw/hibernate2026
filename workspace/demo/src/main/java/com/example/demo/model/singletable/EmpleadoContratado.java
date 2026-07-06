package com.example.demo.model.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity (name = "EmpleadoContratadoSingleTable")
@DiscriminatorValue("CONTRATADO")
public class EmpleadoContratado extends Empleado {
	
	@Column(name = "tarifa_por_hora")
	private Double tarifaPorHora;
	@Column(name = "horas_contratadas")
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
