package com.example.demo.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmpresaDTO {
	
	@NotBlank(message = "{empresa.nombre.notblank}")
	@Size(min = 3, max = 100, message = "{empresa.nombre.size}")
	private String nome;
	
	@Size(max = 255, message = "{empresa.cuit.size}")
	@NotBlank(message = "{empresa.cuit.notblank}")
	private String cuit;
	
	@Valid
	private List<ProductoDTO> productos;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public List<ProductoDTO> getProductos() {
		return productos;
	}
	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}
	
	
}
