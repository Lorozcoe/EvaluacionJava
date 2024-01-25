package com.evaluacion.java.controllers;

import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Login;
import com.evaluacion.java.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public SesionUsuario login(@RequestBody Login login) {
        SesionUsuario sesion = loginService.validarUsuario(login);
        return sesion;
    }

    @PostMapping(value = "/logout")
    public SesionUsuario logout(@RequestBody Login login) {
        SesionUsuario sesion = loginService.logout(login);
        return sesion;
    }
}
