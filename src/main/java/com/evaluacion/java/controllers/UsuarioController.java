package com.evaluacion.java.controllers;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.LoginRepository;
import com.evaluacion.java.repository.UsuarioRepository;
import com.evaluacion.java.services.RespuestaService;
import com.evaluacion.java.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RespuestaService respuestaService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping(value = "/crearUsuario")
    public SesionUsuario crearUsuario(@RequestBody Usuario usuario) {
        Usuario usuariosEmailList;
        try {
            usuariosEmailList = usuarioRepository.findByEmail(usuario.getEmail());
        } catch (Exception e) {
            throw new UsuarioException(Constantes.ERROR_EMAIL);
        }

        Usuario usuarioCreado = usuarioService.save(usuario);

        if(usuariosEmailList != null && usuariosEmailList.getEmail().equals(usuarioCreado.getEmail())){
            throw new UsuarioException(Constantes.ERROR_EMAIL);
        }
        SesionUsuario sesionUsuario = respuestaService.respuesta(usuarioCreado);

        return sesionUsuario;
    }

    @GetMapping(value = "/MostrarUsuarios")
    public List<Usuario> usuarios(){
        return usuarioService.listaUsuarios();
    }

    @RequestMapping(value = "/eliminarUsuario/{id}")
    public String eliminar(@PathVariable Long id){
        return usuarioService.EliminarUsuario(id);
    }

    @PutMapping(value = "/actualizarUsuario/{id}")
    public SesionUsuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        SesionUsuario sesionUsuario = respuestaService.actualizarUsuario(usuario,id);
        return sesionUsuario;
    }
    @PatchMapping("/actualizarContraseña/{id}")
    public SesionUsuario updatePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return respuestaService.actualizarContraseña(id, request);
    }

}
