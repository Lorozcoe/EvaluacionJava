package com.evaluacion.java.services;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public List<Usuario> listaUsuarios() {
        List<Usuario> listaUsuario;
        listaUsuario = usuarioRepository.findAll();
        return listaUsuario;
    }

    @Override
    public String EliminarUsuario(Long id) {
        if(usuarioRepository.findById(id) == null || usuarioRepository.findById(id).isEmpty()){
            throw  new UsuarioException(Constantes.MSJ_ERROR_USUARIO_NOEXISTE);
        }
        usuarioRepository.deleteById(id);
        return "Usuario Eliminado";
    }

    @Override
    public Usuario save(Usuario usuario) {
        List<Usuario> listUsuario;
        Usuario usuario1 = new Usuario();
        boolean validacion;
        listUsuario = usuarioRepository.findAll();
        try {
            if (validarFormatoCorreo(usuario.getEmail())){
                if (listUsuario.isEmpty()){
                    return usuario1 = usuarioRepository.save(usuario);
                }else{
                    validacion = validarUsuario(listUsuario, usuario);
                    if (!validacion){
                        return usuario1 = usuarioRepository.save(usuario);
                    }
                }
                throw new UsuarioException(Constantes.MSJ_ERROR_USUARIO_DUPLICADO);
            }else {
                throw new UsuarioException(Constantes.MSJ_ERROR_FORMATO_EMAIL);
            }
        } catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getMessage(), e);
            throw new UsuarioException(Constantes.MSJ_ERROR_INESPERADO);
        }
    }

    public boolean validarUsuario (List<Usuario> listUsuario, Usuario usuario){
        boolean respuesta = false;
        for (Usuario user : listUsuario){
            respuesta = user.getEmail().equals(usuario.getEmail());
        }
        return respuesta;
    }

    private boolean validarFormatoCorreo(String correo) {
        String formatoCorreo = Constantes.REGGEX_EMAIL;
        Pattern pattern = Pattern.compile(formatoCorreo);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}
