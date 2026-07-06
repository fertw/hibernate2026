package com.example.demo.repository.tablaporclase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.tablaporclase.Empleado;

public interface EmpleadoTablaPorClaseRepository extends JpaRepository<Empleado, Long> {
}