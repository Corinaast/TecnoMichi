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
public interface IRepository <T>{
 /**
     * Guarda una nueva entidad en el repositorio.
     * @param entity La entidad a guardar.
     * @return true si la entidad fue guardada exitosamente, false en caso contrario.
     */
    boolean save(T entity);
    /**
     * Busca una entidad por su ID.
     * @param id El ID de la entidad a buscar.
     * @return La entidad encontrada, o null si no existe.
     */
    T findById(String id); // Asumiendo que los IDs son Strings (ej. ObjectId de MongoDB)
    /**
     * Recupera todas las entidades del repositorio.
     * @return Una lista de todas las entidades.
     */
    List<T> findAll();
    /**
     * Actualiza una entidad existente en el repositorio.
     * @param entity La entidad a actualizar.
     * @return true si la entidad fue actualizada exitosamente, false en caso contrario.
     */
    boolean update(T entity);
    /**
     * Elimina una entidad por su ID.
     * @param id El ID de la entidad a eliminar.
     * @return true si la entidad fue eliminada exitosamente, false en caso contrario.
     */
    boolean delete(String id);
}