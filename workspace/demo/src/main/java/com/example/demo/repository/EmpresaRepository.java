package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Empresa findByNombre(String empresaNombre);

	boolean existsByNombre(String nombre);

	List<Empresa> findByActivoTrue();

	@Query("SELECT e FROM Empresa e JOIN FETCH e.productos WHERE e.id = :id")
	Optional<Empresa> findByIdConProductos(@Param("id") Long id);

	@Modifying
	@Query("UPDATE Empresa e SET e.activo = false WHERE e.id = :id")
	void softDelete(@Param("id") Long id);

}
