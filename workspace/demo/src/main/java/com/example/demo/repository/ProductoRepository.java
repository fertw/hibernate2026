package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	List<Producto> findByNombreContainingIgnoreCase(String nombre);
	
	List<Producto> findByNombre(String nombre);

	List<Producto> findByPrecioGreaterThan(double d);

	Optional<Producto> findFirstByOrderByPrecioAsc();

	List<Producto> findTop3ByOrderByPrecioDesc();
	
	 @Query("SELECT p FROM Producto p WHERE p.empresa.nombre = :nombreEmpresa")
	 List<Producto> buscarPorNombreDeEmpresa(@Param("nombreEmpresa") String nombreEmpresa);
	 
	 @Query(value = "SELECT * FROM productos WHERE precio > ?1 ORDER BY precio DESC LIMIT 1", nativeQuery = true)
	 Producto masCaro(Double minimo);
	 
	 
}
