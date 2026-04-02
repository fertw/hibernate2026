package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Producto;

import jakarta.transaction.Transactional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	Optional<Producto> finFirstByNombre(String nombre);
	Optional<Producto> findTopByNombre(String nombre);
	
	List<Producto> findByPrecioGreaterThan(Double precio);
	
	@Transactional
	void deleteByNombre(String nombre);
	
	Page<Producto> findByPrecioGreaterThan(Double precio, Pageable pageable);
	

}
