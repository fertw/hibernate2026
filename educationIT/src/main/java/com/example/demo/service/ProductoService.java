package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	public void guardarProducto(Producto producto) {
		productoRepository.save(producto);
	}

	public Optional<Producto> obtenerProductoPorId(Long id) {
		return productoRepository.findById(id);
	}
	
	public List<Producto> obtenerTodosLosProductos() {
		return productoRepository.findAll();
	}
}
