package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.validator.NombreEmpresaUnico;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empresas")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 100)
	@NombreEmpresaUnico(groups = com.example.demo.validator.ChequeoUnicidad.class)
    @NotBlank(message = "{empresa.nombre.obligatorio}")
	@Size(min = 3, max = 100, message = "{empresa.nombre.tamano}")
	private String nombre;
	
	@Column(name = "cuit", nullable = false, length = 13)
	@Pattern(regexp = "\\d{2}-\\d{8}-\\d", message = "{empresa.cuit.formato}")
	private String cuit;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "activo", nullable = false)
	private Boolean activo = true;

	// 1:1 -> la @OneToOne es EAGER por defecto: al traer la empresa viene su dirección.
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;

	// 1:N -> LAZY por defecto: los productos se cargan recién al accederlos
	// (o con JOIN FETCH). Cascade ALL + orphanRemoval propagan el ciclo de vida.
	// @Valid es la cascada de VALIDACIÓN (distinta de CascadeType.ALL, que es
	// cascada de PERSISTENCIA): sin esto, validator.validate(empresa) nunca
	// mira dentro de productos.
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
	@Valid
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
	
	
	public Empresa(String string) {
		super();
		this.nombre = string;
	}

	public Empresa(String nombre, String mail, String cuit) {
		super();
		this.nombre = nombre;
		this.email = mail;
		this.cuit = cuit;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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
