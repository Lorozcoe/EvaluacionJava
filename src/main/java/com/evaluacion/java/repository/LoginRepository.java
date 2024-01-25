package com.evaluacion.java.repository;

import com.evaluacion.java.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
