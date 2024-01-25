package com.evaluacion.java.services;

import com.evaluacion.java.constantes.Constantes;
import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.utils.JWTUtil;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.RespuestaRepository;
import com.evaluacion.java.repository.UsuarioRepository;
import com.evaluacion.java.utils.ValidacionesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class RespuestaServicesImpl implements RespuestaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public SesionUsuario respuesta(Usuario usuarioCreado) {
        SesionUsuario sesionUsuario = new SesionUsuario();
        SesionUsuario saveSesionUsuario;
        String token;
        try {
            if (usuarioCreado != null) {
                token = jwtUtil.generateToken(usuarioCreado);
            } else {
                throw new UsuarioException(Constantes.ERROR_CREACION_TOKEN);
            }
            String fecha = fechaCreacionUsuario();
            String modificado = fechaModificacionUsuario();
            String ultimoLogin = fechaUltimoLogin();
            String activo = validarActivo(token, usuarioCreado);

            sesionUsuario.setId(usuarioCreado.getId());
            sesionUsuario.setCreado(fecha);
            sesionUsuario.setModificado(modificado);
            sesionUsuario.setUltimoLogin(ultimoLogin);
            sesionUsuario.setToken(token);
            sesionUsuario.setActivo(activo);
            sesionUsuario.setSesion(false);
            saveSesionUsuario = respuestaRepository.save(sesionUsuario);
            return saveSesionUsuario;

        } catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getCause(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getCause(), e);
            throw new UsuarioException(Constantes.MSJ_ERROR_INESPERADO);
        }

    }

    @Override
    public SesionUsuario actualizarUsuario(Usuario usuario, Long id) {
        Optional<SesionUsuario> respuesta = Optional.of(new SesionUsuario());
        SesionUsuario saveSesionUsuario = new SesionUsuario();
        Optional<Usuario> usuarioRespuesta;
        usuarioRespuesta = usuarioRepository.findById(id);
        ValidacionesUtil.validarEmpty(usuarioRespuesta);
        Usuario usuarioExistente = usuarioRespuesta.get();
        respuesta = respuestaRepository.findById(usuarioExistente.getId());
        String token = respuesta.map(SesionUsuario::getToken).orElse(null);
        String activo = validarActivo(token, usuarioExistente);
        ValidacionesUtil.validaSesion(respuesta);
        if (activo.equals(Constantes.ACTIVO)) {
            Usuario usuario1 = actualizarDatosUsuario(usuario, usuarioExistente);
            token = jwtUtil.generateToken(usuario1);
            SesionUsuario sesionUsuarioExistente = respuesta.get();
            sesionUsuarioExistente.setId(sesionUsuarioExistente.getId());
            sesionUsuarioExistente.setCreado(sesionUsuarioExistente.getCreado());
            sesionUsuarioExistente.setModificado(fechaModificacionUsuario());
            sesionUsuarioExistente.setUltimoLogin(sesionUsuarioExistente.getUltimoLogin());
            sesionUsuarioExistente.setToken(token);
            sesionUsuarioExistente.setActivo(activo);
            sesionUsuarioExistente.setSesion(sesionUsuarioExistente.getSesion());
            saveSesionUsuario = respuestaRepository.save(sesionUsuarioExistente);
        }
        return saveSesionUsuario;
    }


    private Usuario actualizarDatosUsuario(Usuario usuario, Usuario usuarioExistente) {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(usuarioExistente.getId());
        usuarioActualizado.setNombre(usuario.getNombre());
        usuarioActualizado.setEmail(usuario.getEmail());
        usuarioActualizado.setContraseña(usuario.getContraseña());
        usuarioActualizado.setTelefonos(usuario.getTelefonos());
        usuarioRepository.save(usuarioActualizado);
        return usuarioActualizado;
    }

    @Override
    public SesionUsuario actualizarContraseña(Long id, Map<String, String> request) {
        Optional<SesionUsuario> respuesta = Optional.of(new SesionUsuario());
        SesionUsuario saveSesionUsuario = new SesionUsuario();
        Optional<Usuario> usuarioRespuesta = Optional.of(new Usuario());
        usuarioRespuesta = usuarioRepository.findById(id);
        Usuario usuarioExistente = usuarioRespuesta.get();
        respuesta = respuestaRepository.findById(usuarioExistente.getId());
        String token = respuesta.map(SesionUsuario::getToken).orElse(null);
        String activo = validarActivo(token, usuarioExistente);
        ValidacionesUtil.validaSesion(respuesta);
        if (activo.equals(Constantes.ACTIVO)) {
            Usuario usuario1 = actualizarPasswordUsuario(request, usuarioExistente);
            token = jwtUtil.generateToken(usuario1);
            SesionUsuario sesionUsuarioExistente = respuesta.get();
            sesionUsuarioExistente.setId(sesionUsuarioExistente.getId());
            sesionUsuarioExistente.setCreado(sesionUsuarioExistente.getCreado());
            sesionUsuarioExistente.setModificado(fechaModificacionUsuario());
            sesionUsuarioExistente.setUltimoLogin(sesionUsuarioExistente.getUltimoLogin());
            sesionUsuarioExistente.setToken(token);
            sesionUsuarioExistente.setActivo(activo);
            sesionUsuarioExistente.setSesion(sesionUsuarioExistente.getSesion());
            saveSesionUsuario = respuestaRepository.save(sesionUsuarioExistente);
        }
        return saveSesionUsuario;
    }

    private Usuario actualizarPasswordUsuario(Map<String, String> request, Usuario usuarioExistente) {
        Usuario usuarioActualizado = new Usuario();
        String nuevaContraseña = request.get("contraseña");
        usuarioActualizado.setId(usuarioExistente.getId());
        usuarioActualizado.setNombre(usuarioExistente.getNombre());
        usuarioActualizado.setEmail(usuarioExistente.getEmail());
        usuarioActualizado.setContraseña(nuevaContraseña);
        usuarioActualizado.setTelefonos(usuarioExistente.getTelefonos());
        usuarioRepository.save(usuarioActualizado);
        return usuarioActualizado;
    }


    private String validarActivo(String token, Usuario usuarioExistente) {
        try {
            boolean esValido = jwtUtil.isTokenValid(token, usuarioExistente);
            String respuesta;
            if (esValido == true) {
                return respuesta = Constantes.ACTIVO;
            } else {
                return respuesta = Constantes.NO_ACTIVO;
            }
        } catch (UsuarioException e) {
            LOGGER.error(Constantes.MSJ_ERROR_VALIDACION_USUARIO, e.getCause(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(Constantes.MENSAJE_DE_ERROR, e.getCause(), e);
            throw new UsuarioException(Constantes.ERROR_TOKEN_INVALIDO);
        }
    }

    private String fechaCreacionUsuario() {

        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_CORTO);
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }

    private String fechaModificacionUsuario() {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA);
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }

    private String fechaUltimoLogin() {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA);
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }
}
