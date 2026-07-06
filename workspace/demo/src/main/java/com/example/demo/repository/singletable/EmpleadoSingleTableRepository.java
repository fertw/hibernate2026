package com.example.demo.repository.singletable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.singletable.Empleado;

public interface EmpleadoSingleTableRepository extends JpaRepository<Empleado, Long> {
}