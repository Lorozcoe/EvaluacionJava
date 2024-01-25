package com.evaluacion.java.services;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.utils.JWTUtil;
import com.evaluacion.java.model.Login;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.LoginRepository;
import com.evaluacion.java.repository.RespuestaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginServicesImpl implements LoginService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public SesionUsuario validarUsuario(Login login) {
        try {
            if (validarFormatoCorreo(login.getEmail()) == true){
                Usuario usuario = new Usuario();
                SesionUsuario saveSesionUsuario = new SesionUsuario();
                usuario.setEmail(login.getEmail());
                usuario = loginRepository.findByEmail(login.getEmail());
                if (usuario!= null){
                    Optional<SesionUsuario> respuesta = Optional.of(new SesionUsuario());
                    if (usuario.getContraseña().equals(login.getPassword())){
                        respuesta = respuestaRepository.findById(usuario.getId());
                        String token = respuesta.map(SesionUsuario::getToken).orElse(null);
                        String activo = validarActivo(token, usuario);
                        if(activo.equals(Constantes.ACTIVO)){
                            SesionUsuario sesionUsuarioExistente = respuesta.get();
                            sesionUsuarioExistente.setId(sesionUsuarioExistente.getId());
                            sesionUsuarioExistente.setCreado(sesionUsuarioExistente.getCreado());
                            sesionUsuarioExistente.setModificado(sesionUsuarioExistente.getModificado());
                            sesionUsuarioExistente.setUltimoLogin(fechaUltimoLogin());
                            sesionUsuarioExistente.setToken(token);
                            sesionUsuarioExistente.setActivo(activo);
                            if (sesionUsuarioExistente.getSesion() == false){
                                sesionUsuarioExistente.setSesion(true);
                                saveSesionUsuario = respuestaRepository.save(sesionUsuarioExistente);
                                return saveSesionUsuario;
                            }else {
                                throw new UsuarioException(Constantes.VALIDAR_SESION);
                            }
                        }else {
                            throw new UsuarioException(Constantes.ERROR_TOKEN_INVALIDO);
                        }
                    }else {
                        throw new UsuarioException(Constantes.ERROR_CLAVE_INCORRECTA);
                    }
                }else {
                    throw new UsuarioException(Constantes.MSJ_ERROR_USUARIO_NOEXISTE);
                }
            }else{
                throw new UsuarioException(Constantes.MSJ_ERROR_FORMATO_EMAIL);
            }
        }catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getMessage(), e);
            throw new UsuarioException(Constantes.MSJ_ERROR_INESPERADO);
        }

    }

    @Override
    public SesionUsuario logout(Login login) {
        try {
            Usuario usuario = new Usuario();
            SesionUsuario saveSesionUsuario = new SesionUsuario();
            usuario.setEmail(login.getEmail());
            usuario = loginRepository.findByEmail(login.getEmail());
            Optional<SesionUsuario> respuesta = Optional.of(new SesionUsuario());
            respuesta = respuestaRepository.findById(usuario.getId());
            Boolean sesion = respuesta.map(SesionUsuario::getSesion).orElse(null);
            if (sesion.equals(true)){
                String token = respuesta.map(SesionUsuario::getToken).orElse(null);
                String activo = validarActivo(token, usuario);
                if(activo == Constantes.ACTIVO){
                    SesionUsuario sesionUsuarioExistente = respuesta.get();
                    sesionUsuarioExistente.setId(sesionUsuarioExistente.getId());
                    sesionUsuarioExistente.setCreado(sesionUsuarioExistente.getCreado());
                    sesionUsuarioExistente.setModificado(sesionUsuarioExistente.getModificado());
                    sesionUsuarioExistente.setUltimoLogin(fechaUltimoLogin());
                    sesionUsuarioExistente.setToken(token);
                    sesionUsuarioExistente.setActivo(activo);
                    sesionUsuarioExistente.setSesion(false);
                    saveSesionUsuario = respuestaRepository.save(sesionUsuarioExistente);
                }
                return saveSesionUsuario;
            }else {
                throw new UsuarioException(Constantes.MSJ_ERROR_USUARIO_DUPLICADO);
            }
        }catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getMessage(), e);
            throw new UsuarioException(Constantes.MSJ_ERROR_INESPERADO);
        }
    }

    private String fechaUltimoLogin(){
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA);
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }

    private String validarActivo(String token, Usuario usuario) {
        try {
            boolean esValido = jwtUtil.isTokenValid(token,usuario );
            String respuesta ;
            if (esValido == true){
                return respuesta = Constantes.ACTIVO;
            }else {
                return respuesta = Constantes.NO_ACTIVO;
            }
        }catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getMessage(), e);
            throw new UsuarioException(Constantes.ERROR_TOKEN_INVALIDO);
        }
    }

    private boolean validarFormatoCorreo(String correo) {
        String formatoCorreo = Constantes.REGGEX_EMAIL;

        Pattern pattern = Pattern.compile(formatoCorreo);
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }
}
