package com.evaluacion.java.services;

import com.evaluacion.java.model.Login;
import com.evaluacion.java.model.SesionUsuario;

public interface LoginService {
    SesionUsuario validarUsuario(Login login);

    SesionUsuario logout(Login login);
}
