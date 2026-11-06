/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;
import java.util.List;
/**
 * Interfaz para los servicios de negocio relacionados con la entidad Usuario.
 * Define las operaciones de alto nivel que los controladores pueden invocar.
 * Aplica el Principio de Inversión de Dependencias (DIP) al ser una abstracción
 * de la lógica de negocio de usuario.
 */
public interface IUsuarioService {
    /**
     * Intenta iniciar sesión de un usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return El objeto Usuario si las credenciales son válidas, o null en caso contrario.
     */
    Usuario login(String username, String password);

    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario El objeto Usuario a registrar.
     * @return true si el registro fue exitoso, false en caso contrario (ej. usuario ya existe).
     */
    boolean registerUser(Usuario usuario);
    /**
     * Verifica y crea usuarios predeterminados si la base de datos de usuarios está vacía.
     */
    void checkAndCreateInitialUsers();
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return El usuario encontrado, o null si no existe.
     */
    Usuario findByUsername(String username);

    /**
     * Busca un usuario por su correo electrónico.
     * @param email El correo electrónico a buscar.
     * @return El usuario encontrado, o null si no existe.
     */
    Usuario findByEmail(String email);

    /**
     * Obtiene una lista de todos los usuarios.
     * @return Una lista de objetos Usuario.
     */
    List<Usuario> getAllUsers();
}
