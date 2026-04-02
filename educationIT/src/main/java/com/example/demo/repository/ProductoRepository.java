package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Producto;

import jakarta.transaction.Transactional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	// --- Búsqueda por nombre ---

	// Retorna el primer producto que coincida exactamente con el nombre dado.
	// Devuelve Optional vacío si no existe.
	Optional<Producto> findFirstByNombre(String nombre);

	// Equivalente a findFirstByNombre. Retorna el primer resultado usando TOP 1 en la query generada.
	Optional<Producto> findTopByNombre(String nombre);

	// Busca productos cuyo nombre contenga el texto dado, sin distinguir mayúsculas/minúsculas.
	// Ejemplo: "cam" encuentra "Camisa", "CAMARA", etc.
	List<Producto> findByNombreContainingIgnoreCase(String nombre);

	// Verifica si existe al menos un producto con ese nombre exacto.
	// Más eficiente que buscar y chequear isEmpty() porque no carga la entidad.
	boolean existsByNombre(String nombre);

	// --- Búsqueda por precio ---

	// Retorna todos los productos con precio mayor al valor indicado.
	List<Producto> findByPrecioGreaterThan(Double precio);

	// Igual que el anterior pero con soporte de paginación.
	// Útil para mostrar resultados en páginas (ej: 10 productos por página).
	Page<Producto> findByPrecioGreaterThan(Double precio, Pageable pageable);

	// Retorna todos los productos con precio menor al valor indicado.
	List<Producto> findByPrecioLessThan(Double precio);

	// Retorna productos cuyo precio esté entre precioMin y precioMax (inclusive).
	List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

	// --- Búsqueda por descripción ---

	// Busca productos cuya descripción contenga el texto dado, ignorando mayúsculas/minúsculas.
	List<Producto> findByDescripcionContainingIgnoreCase(String descripcion);

	// --- Búsqueda por empresa ---

	// Retorna todos los productos pertenecientes a la empresa con el id dado.
	// Spring Data navega automáticamente la relación @ManyToOne hacia Empresa.
	List<Producto> findByEmpresaId(Long empresaId);

	// Retorna todos los productos cuya empresa tenga el nombre exacto dado.
	// Spring Data navega la relación: Producto -> empresa -> nombre.
	List<Producto> findByEmpresaNombre(String empresaNombre);

	// --- Ordenamiento ---

	// Retorna todos los productos ordenados por precio de menor a mayor.
	List<Producto> findAllByOrderByPrecioAsc();

	// Retorna todos los productos ordenados por precio de mayor a menor.
	List<Producto> findAllByOrderByPrecioDesc();

	// Retorna todos los productos ordenados alfabéticamente por nombre (A → Z).
	List<Producto> findAllByOrderByNombreAsc();

	// --- Conteo ---

	// Cuenta cuántos productos pertenecen a la empresa con el id dado.
	long countByEmpresaId(Long empresaId);

	// Cuenta cuántos productos tienen un precio mayor al valor dado.
	long countByPrecioGreaterThan(Double precio);

	// --- Eliminación ---

	// Elimina todos los productos con el nombre exacto dado.
	// @Transactional es obligatorio en operaciones de escritura derivadas (derived delete).
	@Transactional
	void deleteByNombre(String nombre);

	// Elimina todos los productos asociados a la empresa con el id dado.
	@Transactional
	void deleteByEmpresaId(Long empresaId);

	// --- JPQL personalizado ---

	// Busca el producto más caro. Retorna lista porque puede haber varios con el mismo precio máximo.
	// El service toma el primero con stream().findFirst().
	@Query("SELECT p FROM Producto p WHERE p.precio = (SELECT MAX(p2.precio) FROM Producto p2)")
	List<Producto> findMasCaro();

	// Busca el producto más barato. Retorna lista por la misma razón que findMasCaro.
	@Query("SELECT p FROM Producto p WHERE p.precio = (SELECT MIN(p2.precio) FROM Producto p2)")
	List<Producto> findMasBarato();

	// Retorna los productos de una empresa ordenados por precio descendente.
	// Usa @Param para mapear el parámetro :empresaId al argumento del método.
	@Query("SELECT p FROM Producto p WHERE p.empresa.id = :empresaId ORDER BY p.precio DESC")
	List<Producto> findByEmpresaIdOrderByPrecioDesc(@Param("empresaId") Long empresaId);

	// Actualiza el precio de todos los productos de una empresa multiplicándolo por un factor.
	// Ejemplo: factor=1.1 aplica un aumento del 10%.
	// @Modifying indica que es una query de escritura (UPDATE/DELETE), no de lectura.
	// Retorna la cantidad de filas afectadas.
	@Transactional
	@Modifying
	@Query("UPDATE Producto p SET p.precio = p.precio * :factor WHERE p.empresa.id = :empresaId")
	int actualizarPreciosPorEmpresa(@Param("factor") Double factor, @Param("empresaId") Long empresaId);

}
