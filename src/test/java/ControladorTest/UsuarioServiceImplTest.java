package ControladorTest;

import Modelo.IUsuarioRepository;
import Modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class UsuarioServiceImplTest {
    @Mock
    private IUsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        String username = "testUser";
        String password = "testPass";
        Usuario mockUser = new Usuario(username, "test@example.com", password, "COMPRADOR");
        
        when(usuarioRepository.findByUsernameAndPassword(username, password))
            .thenReturn(mockUser);
        
        Usuario result = usuarioService.login(username, password);
        
        assertNotNull(result);
        assertEquals(username, result.getNombre());
    }
}
