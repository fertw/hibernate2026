package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Empresa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class EmpresaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void guardar(Empresa empresa) {
		em.persist(empresa);
	}
	
	public Empresa buscarPorId(Long id) {
		return em.find(Empresa.class, id);
	}
	
	
	@Transactional
	public void actualizar(Empresa empresa) {
		em.merge(empresa);
	}
	
	@Transactional
	public void eliminar(Empresa empresa) {
		em.remove(em.contains(empresa) ? empresa : em.merge(empresa));
	}
	
	// Todos los productos de una empresa por nombre
	public List<Empresa> buscarPorNombre(String nombre) {
		return em.createQuery("SELECT e FROM Empresa e WHERE e.nombre = :nombre", Empresa.class)
				.setParameter("nombre", nombre)
				.getResultList();
	}
	public Empresa traerConProductos(Long id) {
		return em.createQuery("SELECT e FROM Empresa e JOIN FETCH e.productos WHERE e.id = :id", Empresa.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
