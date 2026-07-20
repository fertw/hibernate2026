package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "{producto.nombre.obligatorio}")
	private String nombre;
	
	@Positive(message = "{producto.precio.positivo}")
	private Double precio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	
	@Positive(message = "{producto.cantidad.minima}")
	private int cantidad;

	@ManyToMany
	@JoinTable(name = "producto_categoria", joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private Set<Categoria> categorias = new HashSet<>();

	public Producto(String string, double d) {
		super();
		this.nombre = string;
		this.precio = d;

	}
	
	public Producto() {
	}

	public Producto(String nombre, Double precio, Empresa empresa) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.empresa = empresa;
	}

	public Producto(String nombre, double valor, int cantidad) {
		super();
		this.nombre = nombre;
		this.precio = valor;
		this.cantidad = cantidad;
	}

	// Helpers de sincronización bidireccional para la N:M.
	// Producto es el dueño de la relación, así que mantenemos ambos lados en
	// memoria.
	public void addCategoria(Categoria categoria) {
		categorias.add(categoria);
		categoria.getProductos().add(this);
	}

	public void removeCategoria(Categoria categoria) {
		categorias.remove(categoria);
		categoria.getProductos().remove(this);
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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	

}
