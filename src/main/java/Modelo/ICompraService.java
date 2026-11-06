/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.List;
import java.util.Map;

/**
 * Interfaz para los servicios de negocio relacionados con la entidad Compra.
 * Define las operaciones de alto nivel para la gestión de compras.
 * Aplica el Principio de Inversión de Dependencias (DIP).
 */
public interface ICompraService {

    /**
     * Procesa una compra para un usuario.
     * @param username El nombre de usuario que realiza la compra.
     * @param productosEnCarrito Un mapa donde la clave es el Celular y el valor es la cantidad comprada.
     * @return true si la compra fue exitosa, false en caso contrario.
     */
    boolean processPurchase(String username, Map<Celular, Integer> productosEnCarrito);

    /**
     * Obtiene una lista de todas las compras realizadas.
     * @return Una lista de objetos Compra.
     */
    List<Compra> getAllCompras();
}