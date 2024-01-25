package com.evaluacion.java.service;

import com.evaluacion.java.exception.UsuarioException;
import com.evaluacion.java.model.Usuario;
import com.evaluacion.java.repository.UsuarioRepository;
import com.evaluacion.java.services.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static  org.mockito.Mockito.when;

public class UsuarioServiceImplTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveNuevoUsuario() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail("nuevo@example.com");

        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        // Act
        Usuario resultado = usuarioService.save(nuevoUsuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("nuevo@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testSaveUsuarioExistente() {
        // Arrange
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@example.com");

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuarioExistente);

        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        // Act and Assert
        assertThrows(UsuarioException.class, () -> {
            usuarioService.save(usuarioExistente);
        });

        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    public void testSaveFormatoEmailInvalido() {
        // Arrange
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setEmail("emailInvalido");

        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(UsuarioException.class, () -> {
            usuarioService.save(usuarioInvalido);
        });

        // Verifica que se lance la excepción, pero no verifica llamadas a findAll()
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
