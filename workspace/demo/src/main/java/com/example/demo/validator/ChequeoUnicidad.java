package com.example.demo.validator;

// Grupo de validación separado del Default: agrupa constraints que consultan
// la base de datos (como @NombreEmpresaUnico). Hibernate solo valida el grupo
// Default en el flush automático, así que estos chequeos SOLO se ejecutan
// cuando se piden explícitamente (ver EmpresaService.guardarValidando).
// Un ConstraintValidator que consulta la base nunca es seguro de correr
// durante el flush: dispara un flush reentrante sobre la misma Session.
public interface ChequeoUnicidad {
}
