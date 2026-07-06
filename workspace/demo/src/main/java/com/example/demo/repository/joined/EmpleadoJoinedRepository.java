package com.example.demo.repository.joined;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.joined.Empleado;

public interface EmpleadoJoinedRepository extends JpaRepository<Empleado, Long> {
}