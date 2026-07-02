package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Categoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CategoriaDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void guardar(Categoria categoria) {
		em.persist(categoria);
	}

	public Categoria buscarPorId(Long id) {
		return em.find(Categoria.class, id);
	}

	public List<Categoria> listar() {
		return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
	}

	public Categoria buscarPorNombre(String nombre) {
		return em.createQuery("SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class)
				.setParameter("nombre", nombre)
				.getSingleResult();
	}

	@Transactional
	public Categoria actualizar(Categoria categoria) {
		return em.merge(categoria);
	}

}
