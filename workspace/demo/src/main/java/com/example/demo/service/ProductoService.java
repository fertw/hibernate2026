package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.mapper.ProductoMapper;
import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.ProductoRepository;

import org.springframework.transaction.annotation.Transactional; // ⚠️ SPRING, no jakarta

/**
 * Capa de servicio: orquesta varios DAOs dentro de una misma transacción. Al
 * estar todo bajo @Transactional, las entidades quedan "managed" y Hibernate
 * detecta los cambios (dirty checking) sin necesidad de llamar a merge().
 */
@Service
@Transactional(readOnly = true)
public class ProductoService {

	final private ProductoRepository productoRepository;
	final private EmpresaRepository empresaRepository;

	public ProductoService(ProductoRepository productoRepository, EmpresaRepository empresaRepository) {
		this.productoRepository = productoRepository;
		this.empresaRepository = empresaRepository;
	}

	@Transactional
	public ProductoDTO crearProducto(ProductoDTO producto) {
		Producto productoEntity = ProductoMapper.toEntity(producto);

		if (producto.getEmpresaNombre() != null) {
			// Aquí deberías buscar la empresa por nombre y asignarla al productoEntity
			// Por ejemplo:
			Empresa empresa = empresaRepository.findByNombre(producto.getEmpresaNombre());
			productoEntity.setEmpresa(empresa);
		}
		return ProductoMapper.toDTO(productoRepository.save(productoEntity));
	}

	public List<ProductoDTO> buscarPorTexto(String texto) {
		return ProductoMapper.toDTOList(productoRepository.findByNombreContainingIgnoreCase(texto));
	}

	public List<ProductoDTO> buscarPorNombre(String nombre) {
		return ProductoMapper.toDTOList(productoRepository.findByNombre(nombre));
	}

	@Transactional
	public ProductoDTO guardar(Producto producto) {

		return ProductoMapper.toDTO(productoRepository.save(producto));
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

	public List<ProductoDTO> buscarPorNombreDeEmpresa(String nombreEmpresa) {
		return ProductoMapper.toDTOList(productoRepository.buscarPorNombreDeEmpresa(nombreEmpresa));
	}

	public ProductoDTO masCaro(Double minimo) {
		return ProductoMapper.toDTO(productoRepository.masCaro(minimo));
	}

	public List<ProductoDTO> buscarPorPrecioMayorA(double d) {
		// TODO Auto-generated method stub
		return ProductoMapper.toDTOList(productoRepository.findByPrecioGreaterThan(d));
	}

	public Optional<ProductoDTO> masBarato() {
		Optional<Producto> productoOpt = productoRepository.findFirstByOrderByPrecioAsc();
		return productoOpt.map(ProductoMapper::toDTO);
	}

	public List<ProductoDTO> top3MasCaros() {
		return ProductoMapper.toDTOList(productoRepository.findTop3ByOrderByPrecioDesc());
	}

	public Page<ProductoDTO> listadoPaginado(int i, int j) {
		return productoRepository.findAll(PageRequest.of(i, j, Sort.by("nombre").ascending()))
				.map(ProductoMapper::toDTO);
	}

}
