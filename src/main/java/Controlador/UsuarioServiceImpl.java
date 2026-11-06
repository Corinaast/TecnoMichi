package Controlador;

import Modelo.IUsuarioRepository;
import Modelo.Usuario;

public class UsuarioServiceImpl {
    private final IUsuarioRepository usuarioRepository;

    // Constructor para inyección
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }
}
