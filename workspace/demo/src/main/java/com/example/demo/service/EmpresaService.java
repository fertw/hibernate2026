package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.EmpresaDao;

@Service
public class EmpresaService {
	
	private final EmpresaDao dao;
	
	public EmpresaService(EmpresaDao dao) {
		this.dao = dao;
	}

	public Empresa altaConProductos(String cuit, String email ,String nombre, String nombreProducto, Double precioProducto) {
		
		Empresa empresa = new Empresa();
		empresa.setNombre(nombre);
		empresa.setCuit(cuit);
		empresa.setEmail(email);
		
		Producto producto = new Producto();
		producto.setNombre(nombreProducto);
		producto.setPrecio(precioProducto);
		empresa.addProducto(producto);
		
		dao.guardar(empresa);
		
		return empresa;
	}
	

}
