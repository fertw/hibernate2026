package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "{producto.nombre.notblank}")
	@Size(min = 3, max = 100, message = "{empresa.nombre.size}")
	private String nombre;
	@Size(max = 255, message = "{producto.descripcion.size}")
	private String descripcion;
	
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
	private Double precio;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")	
	private Empresa empresa;
	
	
	public Producto() {
		super();
	}
	
	
	public Producto(String nombre, String descripcion, Double precio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;		
	}
	
	

}
