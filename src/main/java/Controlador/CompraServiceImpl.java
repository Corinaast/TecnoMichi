package Controlador;

import Modelo.Celular;
import Modelo.ICompraRepository;
import Modelo.ICelularRepository;
import Modelo.Compra;
import java.util.Map;

public class CompraServiceImpl {

    private final ICelularRepository celularRepository;
    private final ICompraRepository compraRepository;

    public CompraServiceImpl(ICelularRepository celularRepository, ICompraRepository compraRepository) {
        this.celularRepository = celularRepository;
        this.compraRepository = compraRepository;
    }

    public boolean processPurchase(String username, Map<Celular, Integer> productos) {
        try {
            for (Map.Entry<Celular, Integer> entry : productos.entrySet()) {
                // Buscar el celular por ID
                Celular celularBuscado = celularRepository.findById(entry.getKey().getId());

                if (celularBuscado == null) {
                    System.out.println("Celular no encontrado: " + entry.getKey().getId());
                    return false;
                }

                int cantidad = entry.getValue();
                double precioUnitario = celularBuscado.getPrecio();
                double precioTotal = precioUnitario * cantidad;

                // Crear una compra por cada celular
                Compra compra = new Compra(username, celularBuscado, cantidad, precioUnitario, precioTotal);

                // Guardar la compra
                compraRepository.save(compra);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al procesar la compra: " + e.getMessage());
            return false;
        }
    }
}
