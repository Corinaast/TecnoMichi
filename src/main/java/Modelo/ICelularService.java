/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author caaco
 */
public interface ICelularService {

    /**
     * Agrega un nuevo celular al inventario.
     * @param celular El objeto Celular a agregar.
     * @param cantidad La cantidad inicial del celular.
     * @return true si el celular fue agregado exitosamente, false en caso contrario.
     */
    boolean addCelular(Celular celular, int cantidad);

    /**
     * Modifica un celular existente en el inventario.
     * @param celular El objeto Celular con los datos actualizados.
     * @param id El ID del celular a modificar.
     * @return true si el celular fue modificado exitosamente, false en caso contrario.
     */
    boolean updateCelular(Celular celular, String id);

    /**
     * Elimina un celular del inventario por su ID.
     * @param id El ID del celular a eliminar.
     * @return true si el celular fue eliminado exitosamente, false en caso contrario.
     */
    boolean deleteCelular(String id);

    /**
     * Busca celulares en el catálogo según un filtro.
     * @param nombre Nombre del celular (parcial o completo).
     * @param marca Marca del celular.
     * @param precio Precio del celular.
     * @param ram RAM del celular.
     * @param almacenamiento Almacenamiento del celular.
     * @param color Color del celular.
     * @param sim Tipo de SIM del celular.
     * @return Una lista de celulares que coinciden con el filtro.
     */
    List<Celular> searchCelulares(String nombre, String marca, String precio, String ram, String almacenamiento, String color, String sim);

    /**
     * Obtiene una lista de todos los celulares disponibles en el catálogo.
     * @return Una lista de todos los objetos Celular.
     */
    List<Celular> getAllCelulares();

    /**
     * Obtiene un celular por su ID.
     * @param id El ID del celular.
     * @return El objeto Celular si se encuentra, o null.
     */
    Celular getCelularById(String id);

    /**
     * Actualiza la cantidad (stock) de un celular.
     * @param celularId El ID del celular.
     * @param newQuantity La nueva cantidad.
     * @return true si la cantidad fue actualizada, false en caso contrario.
     */
    boolean updateCelularQuantity(String celularId, int newQuantity);
}
