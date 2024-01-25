package com.evaluacion.java.controller;

import com.evaluacion.java.model.SesionUsuario;
import com.evaluacion.java.model.Telefono;
import com.evaluacion.java.controllers.UsuarioController;
import com.evaluacion.java.model.Login;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.services.RespuestaServicesImpl;

import com.evaluacion.java.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static  org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class controllerUsuarioTest {

    @InjectMocks
    private UsuarioController usuarioController = new UsuarioController();

    @Mock
    private UsuarioServiceImpl service;
    @Mock
    private RespuestaServicesImpl respuestaServices;

    private Usuario usuariotest;
    private SesionUsuario respuestatest;
    private Telefono telefonotest;
    private Login logintest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        usuariotest = mock(Usuario.class);
        respuestatest = mock(SesionUsuario.class);
        telefonotest = mock(Telefono.class);
        logintest = mock(Login.class);
    }

    @Test
    public void testcrearUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1l);
        usuario.setNombre("test");
        usuario.setEmail("test@email.com");
        usuario.setContraseña("1");
        // Act
        doReturn(usuariotest).when(service).save(any(Usuario.class));
        usuarioController.crearUsuario(usuario);

        // Assert
        verify(service, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Act
        doNothing().when(service).EliminarUsuario(usuario.getId());
        usuarioController.eliminar(usuario.getId());

        // Assert
        verify(service, times(1)).EliminarUsuario(usuario.getId());
    }

    @Test
    public void testusuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1l);

        when(service.listaUsuarios()).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioController.usuarios();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(service, times(1)).listaUsuarios();
    }



}
