package Controlador;

import Modelo.Celular;
import Modelo.Compra; // Asegúrate de que esta sea la clase Modelo.Compra
import Modelo.Mongodb;
import Vista.VistaCompra; // Alias para evitar conflicto de nombres
import Vista.Celulares; // Para regresar al catálogo
import Vista.Inicio; // Posiblemente para regresar al inicio después de la compra

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControladorCompra implements ActionListener {

    private VistaCompra vistaCompra;
    private Mongodb mongoDB;
    private MongoCollection<Document> comprasCollection;
    private MongoCollection<Document> productosCollection; // Necesario para actualizar stock
    private List<Celular> carritoProductos; // Para mantener los productos en el carrito
    private String usuarioActual; // Para saber qué usuario está comprando

    public ControladorCompra(VistaCompra vistaCompra, Mongodb mongoDB) {
        this.vistaCompra = vistaCompra;
        this.mongoDB = mongoDB;
        this.comprasCollection = mongoDB.getComprasCollection();
        this.productosCollection = mongoDB.getProductosCollection();
        this.carritoProductos = new ArrayList<>(); // Inicializar el carrito
        this.usuarioActual = "UsuarioGenerico"; // TODO: Implementar paso de usuario logueado

        // Asignar listeners a los botones de la vista de Compra
        this.vistaCompra.ButtonComprar.addActionListener(this);
        this.vistaCompra.ButtonRegresoIni.addActionListener(this);

        // Inicializar la tabla de compras
        inicializarTablaCompra();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == vistaCompra.ButtonRegresoIni) {
          regresarInicio();
    }
        if (e.getSource() == vistaCompra.ButtonComprar) {
            finalizarCompra();
   
    }
}
    

    /**
     * Inicializa el modelo de la tabla de compras.
     */
    private void inicializarTablaCompra() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre Cliente", "Nombre Celular", "Precio Unitario", "Cantidad", "Precio Total"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas de la tabla no son editables
            }
        };
        vistaCompra.TableCompra.setModel(modelo);
    }

    /**
     * Agrega un producto al carrito de compras y lo muestra en la tabla.
     * @param celular El objeto Celular a agregar.
     * @param cantidad La cantidad del celular a agregar.
     */
    public void agregarProductoAlCarrito(Celular celular, int cantidad) {
        DefaultTableModel modelo = (DefaultTableModel) vistaCompra.TableCompra.getModel();
        double precioTotalProducto = celular.getPrecio() * cantidad;

        // Añadir el producto a la lista interna del carrito
        // Nota: Para simplificar, no estamos manejando duplicados en el carrito en esta versión.
        // Si un usuario añade el mismo producto varias veces, aparecerá como líneas separadas.
        // Para una gestión más robusta, se podría buscar el producto en carritoProductos y actualizar la cantidad.
        carritoProductos.add(celular); // Podrías necesitar una lista de objetos que incluyan cantidad

        modelo.addRow(new Object[]{
                usuarioActual, // Se podría obtener el nombre del usuario logueado
                celular.getNombre(),
                celular.getPrecio(),
                cantidad,
                precioTotalProducto
        });

        JOptionPane.showMessageDialog(vistaCompra, "Producto '" + celular.getNombre() + "' agregado al carrito.", "Carrito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Finaliza la compra, registra cada item en la base de datos y actualiza el stock.
     */
    private void finalizarCompra() {
        DefaultTableModel modelo = (DefaultTableModel) vistaCompra.TableCompra.getModel();
        int filas = modelo.getRowCount();

        if (filas == 0) {
            JOptionPane.showMessageDialog(vistaCompra, "No hay productos en el carrito para finalizar la compra.", "Carrito Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vistaCompra, "¿Confirmar la compra de " + filas + " productos?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }

        try {
            for (int i = 0; i < filas; i++) {
                String nombreCelular = modelo.getValueAt(i, 1).toString();
                double precioUnitario = (Double) modelo.getValueAt(i, 2);
                int cantidadComprada = (Integer) modelo.getValueAt(i, 3);
                double precioTotalItem = (Double) modelo.getValueAt(i, 4);

                // Buscar el celular en la colección de productos para obtener sus detalles completos
                Document celularDoc = productosCollection.find(Filters.eq("nombre", nombreCelular)).first();

                if (celularDoc == null) {
                    JOptionPane.showMessageDialog(vistaCompra, "Error: Celular '" + nombreCelular + "' no encontrado en el inventario.", "Error de Inventario", JOptionPane.ERROR_MESSAGE);
                    continue; // Saltar a la siguiente fila si el celular no se encuentra
                }

                // Crear un objeto Celular a partir del documento de MongoDB
                Celular celularComprado = new Celular(
                        celularDoc.getString("nombre"),
                        celularDoc.getString("marca"),
                        celularDoc.getDouble("precio"),
                        celularDoc.getString("ram"),
                        celularDoc.getString("almacenamiento"),
                        celularDoc.getString("color"),
                        celularDoc.getString("sim")
                );

                // Registrar la compra en la colección de compras
                Compra nuevaCompra = new Compra(usuarioActual, celularComprado, cantidadComprada, precioUnitario, precioTotalItem);
                comprasCollection.insertOne(nuevaCompra.toDocument());

                // Actualizar la cantidad en la colección de productos (restar del stock)
                int cantidadActual = celularDoc.getInteger("cantidad", 0);
                int nuevaCantidad = cantidadActual - cantidadComprada;

                productosCollection.updateOne(
                        Filters.eq("_id", celularDoc.getObjectId("_id")),
                        Updates.set("cantidad", nuevaCantidad)
                );
            }

            JOptionPane.showMessageDialog(vistaCompra, "Compra realizada exitosamente. ¡Gracias por su compra!", "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
            modelo.setRowCount(0); // Limpiar el carrito después de la compra
            carritoProductos.clear(); // Limpiar la lista de productos en el carrito

            // Regresar a la pantalla de inicio o catálogo después de la compra
            Celulares vistaCelulares = new Celulares(); // O Inicio vistaInicio = new Inicio();
            ControladorCelulares controladorCelulares = new ControladorCelulares(vistaCelulares, mongoDB); // O ControladorInicio
            vistaCelulares.setVisible(true);
            vistaCompra.dispose(); // Cerrar la ventana de compra

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaCompra, "Error al procesar la compra: " + ex.getMessage(), "Error de Compra", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Imprimir el stack trace para depuración
        }
    }
    
     private void regresarInicio() {
        Inicio vistaInicio = new Inicio();
        ControladorInicio controladorInicio = new ControladorInicio(vistaInicio);
        vistaInicio.setVisible(true);
        vistaCompra.dispose(); // Cerrar la ventana de catálogo
    }
}
