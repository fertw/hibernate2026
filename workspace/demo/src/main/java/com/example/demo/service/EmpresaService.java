package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	final private EmpresaRepository empresaRepository;
	
	public EmpresaService(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}
	
	public Empresa guardar(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	public Integer contarEmpresas() {
		return (int) empresaRepository.count();
	}
	
	

}
