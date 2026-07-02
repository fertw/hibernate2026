package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Direccion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class DireccionDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void guardar(Direccion direccion) {
		em.persist(direccion);
	}

	public Direccion buscarPorId(Long id) {
		return em.find(Direccion.class, id);
	}

	public List<Direccion> listar() {
		return em.createQuery("SELECT d FROM Direccion d", Direccion.class).getResultList();
	}

}
