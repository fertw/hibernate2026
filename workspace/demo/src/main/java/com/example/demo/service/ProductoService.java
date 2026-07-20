package com.example.demo.service;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.mapper.ProductoMapper;
import com.example.demo.model.Producto;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           EmpresaRepository empresaRepository) {
        this.productoRepository = productoRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

    public Optional<ProductoDTO> buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toDTO);
    }

    public Page<ProductoDTO> listarPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(ProductoMapper::toDTO);
    }

    public List<ProductoDTO> buscarPorTexto(String texto) {
        return productoRepository.findByNombreContainingIgnoreCase(texto)
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

    @Transactional
    public ProductoDTO guardar(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto, empresaRepository);
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }

    @Transactional
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}