package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	// Guarda un producto nuevo o actualiza uno existente (si tiene id).
	public Producto guardarProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	// Retorna el producto con el id dado, o Optional vacío si no existe.
	public Optional<Producto> obtenerProductoPorId(Long id) {
		return productoRepository.findById(id);
	}

	// Retorna todos los productos sin filtro.
	public List<Producto> obtenerTodosLosProductos() {
		return productoRepository.findAll();
	}

	// Elimina el producto con el id dado. No hace nada si no existe.
	public void eliminarProducto(Long id) {
		productoRepository.deleteById(id);
	}

	// Elimina todos los productos que tengan exactamente ese nombre.
	public void eliminarProductoPorNombre(String nombre) {
		productoRepository.deleteByNombre(nombre);
	}

	// Elimina todos los productos asociados a la empresa dada.
	public void eliminarProductosPorEmpresa(Long empresaId) {
		productoRepository.deleteByEmpresaId(empresaId);
	}

	// Actualiza solo los campos nombre, descripcion y precio del producto.
	// Lanza IllegalArgumentException si el producto no existe.
	public Producto actualizarProducto(Long id, Producto datos) {
		Producto existente = productoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + id));
		existente.setNombre(datos.getNombre());
		existente.setDescripcion(datos.getDescripcion());
		existente.setPrecio(datos.getPrecio());
		return productoRepository.save(existente);
	}

	// Busca el primer producto con ese nombre exacto.
	public Optional<Producto> buscarPorNombre(String nombre) {
		return productoRepository.findFirstByNombre(nombre);
	}

	// Busca productos cuyo nombre contenga el texto dado (sin distinguir mayúsculas).
	public List<Producto> buscarPorNombreContiene(String texto) {
		return productoRepository.findByNombreContainingIgnoreCase(texto);
	}

	// Busca productos cuya descripción contenga el texto dado (sin distinguir mayúsculas).
	public List<Producto> buscarPorDescripcionContiene(String texto) {
		return productoRepository.findByDescripcionContainingIgnoreCase(texto);
	}

	// Retorna productos con precio mayor al valor dado.
	public List<Producto> buscarPorPrecioMayorA(Double precio) {
		return productoRepository.findByPrecioGreaterThan(precio);
	}

	// Retorna productos con precio mayor al valor dado, paginados.
	public Page<Producto> buscarPorPrecioMayorAPaginado(Double precio, Pageable pageable) {
		return productoRepository.findByPrecioGreaterThan(precio, pageable);
	}

	// Retorna productos con precio menor al valor dado.
	public List<Producto> buscarPorPrecioMenorA(Double precio) {
		return productoRepository.findByPrecioLessThan(precio);
	}

	// Retorna productos cuyo precio esté entre min y max (inclusive).
	public List<Producto> buscarPorRangoDePrecio(Double min, Double max) {
		return productoRepository.findByPrecioBetween(min, max);
	}

	// Retorna todos los productos de una empresa, ordenados por precio descendente.
	public List<Producto> buscarPorEmpresa(Long empresaId) {
		return productoRepository.findByEmpresaIdOrderByPrecioDesc(empresaId);
	}

	// Retorna todos los productos ordenados de menor a mayor precio.
	public List<Producto> obtenerOrdenadosPorPrecioAsc() {
		return productoRepository.findAllByOrderByPrecioAsc();
	}

	// Retorna todos los productos ordenados de mayor a menor precio.
	public List<Producto> obtenerOrdenadosPorPrecioDesc() {
		return productoRepository.findAllByOrderByPrecioDesc();
	}

	// Retorna todos los productos ordenados alfabéticamente por nombre.
	public List<Producto> obtenerOrdenadosPorNombre() {
		return productoRepository.findAllByOrderByNombreAsc();
	}

	// Retorna el producto más caro, o Optional vacío si no hay productos.
	public Optional<Producto> obtenerMasCaro() {
		return productoRepository.findMasCaro();
	}

	// Retorna el producto más barato, o Optional vacío si no hay productos.
	public Optional<Producto> obtenerMasBarato() {
		return productoRepository.findMasBarato();
	}

	// Cuenta cuántos productos tiene una empresa.
	public long contarProductosPorEmpresa(Long empresaId) {
		return productoRepository.countByEmpresaId(empresaId);
	}

	// Verifica si ya existe un producto con ese nombre exacto.
	public boolean existeProductoConNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}

	// Aplica un aumento o descuento de precio a todos los productos de una empresa.
	// factor > 1 es aumento (ej: 1.1 = +10%), factor < 1 es descuento (ej: 0.9 = -10%).
	// Retorna la cantidad de productos actualizados.
	public int actualizarPreciosPorEmpresa(Long empresaId, Double factor) {
		return productoRepository.actualizarPreciosPorEmpresa(factor, empresaId);
	}

}
