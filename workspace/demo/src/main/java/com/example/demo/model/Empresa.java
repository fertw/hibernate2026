package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "cuit", nullable = false, length = 13)
	private String cuit;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	// 1:1 -> la @OneToOne es EAGER por defecto: al traer la empresa viene su dirección.
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;

	// 1:N -> LAZY por defecto: los productos se cargan recién al accederlos
	// (o con JOIN FETCH). Cascade ALL + orphanRemoval propagan el ciclo de vida.
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Producto> productos = new ArrayList<>();
	
	
	
	public Empresa() {
		super();
	}
	
	public Empresa(String nombre, String cuit, String email, Direccion direccion) {
		super();
		this.nombre = nombre;
		this.cuit = cuit;
		this.email = email;
		setDireccion(direccion);
	}
	
	
	public void addProducto(Producto producto) {
		productos.add(producto);
		producto.setEmpresa(this);
	}
	
	public void removeProducto(Producto producto) {
		productos.remove(producto);
		producto.setEmpresa(null);
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

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	// Sincroniza ambos lados del 1:1 para mantener el grafo consistente en memoria.
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
		if (direccion != null) {
			direccion.setEmpresa(this);
		}
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
