package com.example.demo.validator;

import com.example.demo.repository.EmpresaRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NombreEmpresaUnicoValidator implements ConstraintValidator<NombreEmpresaUnico, String> {

    private final EmpresaRepository empresaRepository;
    
    public NombreEmpresaUnicoValidator(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	@Override
	public boolean isValid(String nombre, ConstraintValidatorContext context) {
		if (nombre == null || nombre.isEmpty()) {
			return true; // No validamos nulos o vacíos aquí
		}
		return !empresaRepository.existsByNombre(nombre);
	}

}
