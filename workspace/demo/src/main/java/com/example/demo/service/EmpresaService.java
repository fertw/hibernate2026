package com.example.demo.service;

import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.validator.ChequeoUnicidad;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Service;
// SIEMPRE de Spring — nunca jakarta.transaction (Moraleja de Clase 5)
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)   // lectura por defecto en toda la clase
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    // jakarta.validation.Validator — lo provee Spring como LocalValidatorFactoryBean.
    // Cuando Spring crea los ConstraintValidator, los trata como beans:
    // por eso NombreEmpresaUnicoValidator recibe el EmpresaRepository inyectado.
    private final Validator validator;

    // Inyección por constructor — sin @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, Validator validator) {
        this.empresaRepository = empresaRepository;
        this.validator = validator;
    }

    // ── Lectura ────────────────────────────────────────────────────────────

    public List<Empresa> listarActivas() {
        return empresaRepository.findByActivoTrue();
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> buscarConProductos(Long id) {
        return empresaRepository.findByIdConProductos(id);
    }

    // ── Escritura ──────────────────────────────────────────────────────────

    /**
     * Guarda validando ANTES del flush.
     *
     * Ventaja: el feedback llega antes de tocar la base de datos,
     * con un mensaje claro por campo. Sin esta validación previa,
     * Hibernate lanza ConstraintViolationException en el flush
     * con un mensaje menos amigable.
     *
     * También es el método que habilita @NombreEmpresaUnico:
     * como el Validator lo crea Spring, el ConstraintValidator
     * sí recibe el EmpresaRepository inyectado.
     */
    @Transactional
    public Empresa guardarValidando(Empresa empresa) {
        // Default: constraints normales (@NotBlank, @Size, @Pattern, cascada @Valid).
        // ChequeoUnicidad: constraints que consultan la base (@NombreEmpresaUnico).
        // Hibernate solo revisa Default en el flush automático — por eso hay que
        // pedir ambos grupos acá explícitamente, ANTES de llegar al save().
        Set<ConstraintViolation<Empresa>> violaciones = validator.validate(empresa, Default.class, ChequeoUnicidad.class);

        if (!violaciones.isEmpty()) {
            // Construir mensaje detallado: campo → mensaje
            String detalle = violaciones.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(" | "));
            throw new IllegalArgumentException("Empresa inválida → " + detalle);
        }

        return empresaRepository.save(empresa);
    }

    @Transactional
    public void softDelete(Long id) {
        empresaRepository.softDelete(id);
    }
}