package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductoDTO {
	
	@NotBlank(message = "{producto.nombre.notblank}")
	private String nombre;
	
	@Size(min=4, max = 255, message = "{producto.descripcion.size}")	
	private String descripcion;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
	private Double precio;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	

}
