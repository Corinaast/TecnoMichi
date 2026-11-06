/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;
/**
 * Interfaz específica para operaciones de repositorio de la entidad Usuario.
 * Extiende IRepository para heredar las operaciones CRUD básicas.
 * Ahora incluye métodos específicos para búsquedas de usuario.
 */
public interface IUsuarioRepository extends IRepository<Usuario> {
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
     * Busca un usuario por su nombre de usuario y contraseña.
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return El usuario encontrado, o null si no existe.
     */
    Usuario findByUsernameAndPassword(String username, String password);
}
