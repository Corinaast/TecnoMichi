package Modelo;

import java.util.List;

public interface ICompraRepository extends IRepository<Compra> {
    List<Compra> findByUsuario(String usuario);
}