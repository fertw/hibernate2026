package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Categoria;
import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaDao;
import com.example.demo.repository.EmpresaDao;
import com.example.demo.repository.ProductoDao;

/**
 * Capa de servicio: orquesta varios DAOs dentro de una misma transacción.
 * Al estar todo bajo @Transactional, las entidades quedan "managed" y Hibernate
 * detecta los cambios (dirty checking) sin necesidad de llamar a merge().
 */
@Service
public class ProductoService {

	private final ProductoDao productoDao;
	private final CategoriaDao categoriaDao;
	private final EmpresaDao empresaDao;

	public ProductoService(ProductoDao productoDao, CategoriaDao categoriaDao, EmpresaDao empresaDao) {
		this.productoDao = productoDao;
		this.categoriaDao = categoriaDao;
		this.empresaDao = empresaDao;
	}

	// Alta de un producto asociado a una empresa (1:N). Usa EmpresaDao + ProductoDao.
	@Transactional
	public Producto alta(Long empresaId, String nombre, Double precio) {
		Empresa empresa = empresaDao.buscarPorId(empresaId);

		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		empresa.addProducto(producto); // sincroniza ambos lados de la 1:N

		productoDao.guardar(producto);
		return producto;
	}

	// Asigna una categoría a un producto (N:M). Usa ProductoDao + CategoriaDao.
	@Transactional
	public void asignarCategoria(Long productoId, Long categoriaId) {
		Producto producto = productoDao.buscar(productoId);
		Categoria categoria = categoriaDao.buscarPorId(categoriaId);
		producto.addCategoria(categoria); // dueño de la N:M -> se inserta en producto_categoria al commitear
	}

	// Modifica un campo simple: el dirty checking persiste el cambio al cerrar la transacción.
	@Transactional
	public void actualizarPrecio(Long productoId, Double nuevoPrecio) {
		Producto producto = productoDao.buscar(productoId);
		producto.setPrecio(nuevoPrecio);
	}

	@Transactional(readOnly = true)
	public List<Producto> listarPorEmpresa(Long empresaId) {
		return productoDao.buscarPorEmpresa(empresaId);
	}

	@Transactional(readOnly = true)
	public List<Producto> listarPorCategoria(String nombreCategoria) {
		return productoDao.buscarPorCategorias(nombreCategoria);
	}

}
