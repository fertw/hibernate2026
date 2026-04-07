package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmpresaDTO;
import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional
	public void guardarEmpresaConProductos(Empresa empresa) {
		empresaRepository.save(empresa);
	}
	
	public List<Empresa> obtenerTodasLasEmpresas() {
		return empresaRepository.findAll();
	}
	
//	public EmpresaDTO buscarPorCuit(String cuit) {
//		Empresa empresa =  empresaRepository.findByCuit(cuit).orElse(null);
//		//return modelMapper(empresa);
//	}
	
	public List<Empresa> buscarPorNombre(String nombre) {
		return empresaRepository.findByNombre(nombre);
	}
	
	
}
