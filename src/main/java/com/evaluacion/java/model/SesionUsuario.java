package com.evaluacion.java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SesionUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creado;
    private String modificado;
    private String ultimoLogin;
    private String token;
    private String activo;
    private Boolean sesion;


    public SesionUsuario() {

    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }

    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
        this.modificado = modificado;
    }

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Boolean getSesion() {
        return sesion;
    }

    public void setSesion(Boolean sesion) {
        this.sesion = sesion;
    }
}
