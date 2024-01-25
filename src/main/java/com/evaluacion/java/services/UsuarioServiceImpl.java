package com.evaluacion.java.services;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.UsuarioRepository;
import com.evaluacion.java.utils.ValidacionesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioException(Constantes.MSJ_ERROR_USUARIO_NOEXISTE);
        }
        usuarioRepository.deleteById(id);
        return "Usuario Eliminado";
    }

    @Override
    public Usuario save(Usuario usuario) {
        List<Usuario> listUsuario;
        listUsuario = usuarioRepository.findAll();
        try {
            ValidacionesUtil.validarFormatoCorreo(usuario.getEmail());
            if (listUsuario.isEmpty() || !validarUsuario(listUsuario, usuario)) {
                return usuarioRepository.save(usuario);
            } else {
                throw new UsuarioException(Constantes.MSJ_ERROR_USUARIO_DUPLICADO);
            }
        } catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getCause(), e);
            throw new UsuarioException(Constantes.MSJ_ERROR_INESPERADO);

        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getCause(), e);
            throw e;
        }
    }

    public boolean validarUsuario(List<Usuario> listUsuario, Usuario usuario) {
        boolean respuesta = false;
        for (Usuario user : listUsuario) {
            respuesta = user.getEmail().equals(usuario.getEmail());
        }
        return respuesta;
    }

    public Usuario validaEmail(Usuario usuario) {
        Usuario usuariosEmailList;
        try {
            usuariosEmailList = usuarioRepository.findByEmail(usuario.getEmail());
        } catch (Exception e) {
            throw new UsuarioException(Constantes.ERROR_EMAIL);
        }

        Usuario usuarioCreado = save(usuario);

        if (usuariosEmailList != null && usuariosEmailList.getEmail().equals(usuarioCreado.getEmail())) {
            throw new UsuarioException(Constantes.ERROR_EMAIL);
        }
        return usuarioCreado;
    }
}
