package com.evaluacion.java.utils;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.UsuarioRepository;
import com.evaluacion.java.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidacionesUtil {

    public static void validaSesion(Optional<SesionUsuario> respuesta){
        if(respuesta != null && !respuesta.get().getSesion()){
            throw new UsuarioException(Constantes.ERROR_USUARIO_NO_LOGEADO);
        }
    }
    public static void validarFormatoCorreo(String correo) {
        String formatoCorreo = Constantes.REGGEX_EMAIL;
        Pattern pattern = Pattern.compile(formatoCorreo);
        Matcher matcher = pattern.matcher(correo);
        if(!matcher.matches()){
            throw new UsuarioException(Constantes.MSJ_ERROR_FORMATO_EMAIL);
        }
    }
    public static void validarEmpty(Optional<Usuario> usuario){
        if (usuario.isEmpty() || usuario == null) {
            throw new UsuarioException(Constantes.MSJ_ERROR_ID_NOEXISTE);
        }
    }


}
