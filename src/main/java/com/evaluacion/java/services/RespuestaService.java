package com.evaluacion.java.services;

import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Usuario;

import java.util.Map;

public interface RespuestaService {
    SesionUsuario respuesta(Usuario usuarioCreado);

    SesionUsuario actualizarUsuario(Usuario usuario, Long id);

    SesionUsuario actualizarContraseña(Long id, Map<String, String> request);
}
