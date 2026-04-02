package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	List<Empresa> findByNombre(String nombre);
	Optional<Empresa> findByCuit(String cuit);
}
