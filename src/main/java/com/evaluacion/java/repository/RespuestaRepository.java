package com.evaluacion.java.repository;

import com.evaluacion.java.model.SesionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<SesionUsuario, Long>  {
    Optional<SesionUsuario> findById(Long id);

}
