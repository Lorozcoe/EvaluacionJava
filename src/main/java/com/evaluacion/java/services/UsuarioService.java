package com.evaluacion.java.services;

import com.evaluacion.java.model.Usuario;

import java.util.List;


public interface UsuarioService {
    List<Usuario> listaUsuarios();

    String EliminarUsuario(Long id);

    Usuario save(Usuario usuario);
}
