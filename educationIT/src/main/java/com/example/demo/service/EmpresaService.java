package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void guardarEmpresaConProductos() {
		// Crear una nueva empresa
		Empresa empresa = new Empresa("Tech Solutions", "30-12345678-9");
		
		// Crear productos asociados a la empresa
		Producto producto1 = new Producto();
		producto1.setNombre("Laptop");
		producto1.setDescripcion("Laptop de alta gama");
		producto1.setPrecio(1500.00);
		producto1.setEmpresa(empresa);
		
		Producto producto2 = new Producto();
		producto2.setNombre("Smartphone");
		producto2.setDescripcion("Smartphone con excelente cámara");
		producto2.setPrecio(800.00);
		producto2.setEmpresa(empresa);
		
		// Agregar los productos a la empresa
		empresa.setProductos(List.of(producto1, producto2));
		
		// Guardar la empresa (y los productos asociados) en la base de datos
		em.persist(empresa);
		
	}
	
	public List<Empresa> obtenerEmpresasConProductos() {
		return em.createQuery("SELECT e FROM Empresa e JOIN FETCH e.productos", Empresa.class).getResultList();
	}
}
