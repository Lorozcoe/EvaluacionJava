package com.evaluacion.java.model;

import org.junit.jupiter.api.Test;

public class modelTest {
    Login login = new Login();
    SesionUsuario sesionUsuario = new SesionUsuario();
    Telefono telefono = new Telefono();
    Usuario usuario = new Usuario();

    @Test
    public void modelTest(){
        login.getPassword();
        login.getPassword();
        login.setEmail("test@email.com");
        login.setPassword("1");
        sesionUsuario.getId();
        sesionUsuario.getCreado();
        sesionUsuario.getModificado();
        sesionUsuario.getUltimoLogin();
        sesionUsuario.getToken();
        sesionUsuario.getActivo();
        sesionUsuario.getSesion();
        sesionUsuario.setId(1l);
        sesionUsuario.setCreado("1");
        sesionUsuario.setModificado("1");
        sesionUsuario.setUltimoLogin("1");
        sesionUsuario.setToken("1");
        sesionUsuario.setActivo("1");
        sesionUsuario.setSesion(true);
        telefono.getId();
        telefono.getNumero();
        telefono.getCodigoCiudad();
        telefono.getCodigoPais();
        telefono.setId(1l);
        telefono.setNumero("1");
        telefono.setCodigoCiudad("1");
        telefono.setCodigoPais("1");
        usuario.getId();
        usuario.getNombre();
        usuario.getEmail();
        usuario.getContraseña();
        usuario.setId(1l);
        usuario.setNombre("test");
        usuario.setEmail("test@email.com");
        usuario.setContraseña("1");

    }
}
