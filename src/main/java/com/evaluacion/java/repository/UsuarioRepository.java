package com.evaluacion.java.repository;

import com.evaluacion.java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario save(Usuario usuario);
    Usuario findByEmail(String email);

    Optional<Usuario> findById(Long id);

}
