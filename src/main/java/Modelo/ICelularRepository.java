package Modelo;

import java.util.List;

public interface ICelularRepository extends IRepository<Celular> {
    boolean updateQuantity(String celularId, int newQuantity);
    Celular findByNombre(String nombre);
    List<Celular> searchCelulares(String nombre, String marca, String precio, 
                                String ram, String almacenamiento, String color, String sim);
}