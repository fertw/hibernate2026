package com.example.demo.dto;

public class ProductoDTO {
	
	private String nombre;
	private Double precio;
	private String empresaNombre;
	
	public ProductoDTO(String nombre, String string, Double precio, String empresaNombre) {
		super();
		this.nombre = string;
		this.precio = precio;
		this.empresaNombre = empresaNombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getEmpresaNombre() {
		return empresaNombre;
	}

	public void setEmpresaNombre(String empresaNombre) {
		this.empresaNombre = empresaNombre;
	}
	
	
	

}
