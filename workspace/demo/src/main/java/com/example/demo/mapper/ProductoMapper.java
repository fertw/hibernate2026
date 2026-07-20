package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.model.Empresa;
import com.example.demo.model.Producto;
import com.example.demo.repository.EmpresaRepository;

public class ProductoMapper {
	
	private ProductoMapper() {
		// Constructor privado para evitar instanciación
	}
	
	public static ProductoDTO toDTO(Producto producto) {
		if (producto == null) {
			return null;
		}
		String empresaNombre = producto.getEmpresa() != null ? producto.getEmpresa().getNombre() : null;
		return new ProductoDTO(producto.getNombre(), empresaNombre, producto.getPrecio(), empresaNombre);
	}
	
	public static Producto toEntity(ProductoDTO productoDTO) {
		if (productoDTO == null) {
			return null;
		}
		Producto producto = new Producto();
		producto.setNombre(productoDTO.getNombre());
		producto.setPrecio(productoDTO.getPrecio());
		// La empresa se debe establecer en otro lugar, ya que el DTO no contiene la entidad Empresa
		return producto;
	}

	public static List<ProductoDTO> toDTOList(List<Producto> productos) {
		return productos.stream().map(ProductoMapper::toDTO).toList();
	}

	public static Producto toEntity(ProductoDTO dto, EmpresaRepository empresaRepository) {
		if (dto == null) {
			return null;
		}
		Producto producto = new Producto();
		producto.setNombre(dto.getNombre());
		producto.setPrecio(dto.getPrecio());
		if (dto.getEmpresaNombre() != null) {
			Empresa empresa = empresaRepository.findByNombre(dto.getEmpresaNombre());
			producto.setEmpresa(empresa);
		}
		return producto;
	}

}
