package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

/**
 * Capa de servicio: orquesta varios DAOs dentro de una misma transacción. Al
 * estar todo bajo @Transactional, las entidades quedan "managed" y Hibernate
 * detecta los cambios (dirty checking) sin necesidad de llamar a merge().
 */
@Service
public class ProductoService {
	
	final private ProductoRepository productoRepository;
	
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
	@Transactional
	public Producto crearProducto(Producto producto) {
		return productoRepository.save(producto);
	}
	
	public List<Producto> buscarPorTexto(String texto) {
		return productoRepository.findByNombreContainingIgnoreCase(texto);
	}
	
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	@Transactional
	public Producto guardar(Producto producto) {
		return productoRepository.save(producto);
	}

	public Integer contarProductos() {
		return (int) productoRepository.count();
	}

	public List<Producto> findByNombreContainingIgnoreCase(String string) {
		
		return productoRepository.findByNombreContainingIgnoreCase(string);
	}

	public List<Producto> findByPrecioGreaterThan(double d) {
		return productoRepository.findByPrecioGreaterThan(d);
	}

	public List<Producto> findByNombre(String string) {
		return productoRepository.findByNombre(string);
	}

	public String existsByNombre(String string) {
		return productoRepository.findByNombre(string).isEmpty() ? "No existe" : "Existe";
	}

	public Optional<Producto> findFirstByOrderByPrecioAsc() {
		return productoRepository.findFirstByOrderByPrecioAsc();
	}

	public List<Producto> findTop3ByOrderByPrecioDesc() {
		return productoRepository.findTop3ByOrderByPrecioDesc();
	}
	
	public Page<Producto> listadorPaginado(int numeroPagina, int tamanioPagina) {
	    Pageable pageable = PageRequest.of(numeroPagina, tamanioPagina, Sort.by("nombre").ascending());
		return productoRepository.findAll(pageable);
	}
	
	public List<Producto> buscarPorNombreDeEmpresa(String nombreEmpresa) {
		return productoRepository.buscarPorNombreDeEmpresa(nombreEmpresa);
	}
	
	public Producto masCaro(Double minimo) {
		return productoRepository.masCaro(minimo);
	}

}
