package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProductoDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void guardar(Producto producto) {
		em.persist(producto);
	}
	
	public Producto buscar(Long id) {
		return em.find(Producto.class, id);
	}
	
	@Transactional
	public Producto actualizar(Producto producto) {
		return em.merge(producto);
	}
	
	@Transactional
	public void eliminar(Long id) {
		Producto producto = em.find(Producto.class, id);
		if (producto != null) {
			em.remove(producto);
		}
	}
	
    // JOIN sobre la relación ManyToMany con Categoria
	public List<Producto> buscarPorCategorias(String nombreCategoria) {
		return em.createQuery("SELECT p FROM Producto p JOIN p.categorias c WHERE c.nombre = :nom", Producto.class)
				.setParameter("nom", nombreCategoria)
				.getResultList();
	}

	// JOIN sobre la relación ManyToOne con Empresa (1:N visto desde Producto)
	public List<Producto> buscarPorEmpresa(Long empresaId) {
		return em.createQuery("SELECT p FROM Producto p JOIN p.empresa e WHERE e.id = :id", Producto.class)
				.setParameter("id", empresaId)
				.getResultList();
	}

	// JOIN FETCH: trae el producto con sus categorías ya inicializadas (evita LazyInitializationException)
	public Producto traerConCategorias(Long id) {
		return em.createQuery("SELECT p FROM Producto p LEFT JOIN FETCH p.categorias WHERE p.id = :id", Producto.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
