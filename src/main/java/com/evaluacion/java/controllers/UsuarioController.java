package com.evaluacion.java.controllers;


import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.services.RespuestaService;
import com.evaluacion.java.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RespuestaService respuestaService;


    @PostMapping(value = "/Usuario")
    public SesionUsuario crearUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.validaEmail(usuario);
        return respuestaService.respuesta(usuarioCreado);
    }

    @GetMapping(value = "/Usuarios")
    public List<Usuario> usuarios() {
        return usuarioService.listaUsuarios();
    }

    @DeleteMapping(value = "/Usuario/{id}")
    public String eliminar(@PathVariable Long id) {
        return usuarioService.EliminarUsuario(id);
    }

    @PutMapping(value = "/Usuario/{id}")
    public SesionUsuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return  respuestaService.actualizarUsuario(usuario, id);
    }

    @PatchMapping("/Contraseña/{id}")
    public SesionUsuario updatePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return respuestaService.actualizarContraseña(id, request);
    }

}
