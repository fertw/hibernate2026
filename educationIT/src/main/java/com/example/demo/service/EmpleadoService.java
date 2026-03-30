package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.joined.EmpleadoB;
import com.example.demo.model.singletable.EmpleadoA;
import com.example.demo.model.tableperclass.EmpleadoC;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EmpleadoService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void guardarEmpleadoA(EmpleadoA empleado) {
		em.persist(empleado);
	}
	
	@Transactional
	public void guardarEmpleadoB(EmpleadoB empleado) {
		em.persist(empleado);
	}
	
	@Transactional
	public void guardarEmpleadoC(EmpleadoC empleado) {
		em.persist(empleado);
	}
	
	

}
