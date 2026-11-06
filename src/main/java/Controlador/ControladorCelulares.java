package Controlador;

import Modelo.Celular;
import Modelo.Mongodb;
import Vista.Celulares;
import Vista.VistaCompra; 
import Vista.Inicio;
import Vista.VistaUsuario; 

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorCelulares implements ActionListener {

    private Celulares vistaCelulares;
    private Mongodb mongoDB;
    private MongoCollection<Document> productosCollection;

    public ControladorCelulares(Celulares vistaCelulares, Mongodb mongoDB) {
        this.vistaCelulares = vistaCelulares;
        this.mongoDB = mongoDB;
        this.productosCollection = mongoDB.getProductosCollection();

        // Asignar listeners a los botones y tabla de la vista de Celulares (Catálogo)
        this.vistaCelulares.ButtonComprarCatal.addActionListener(this);
        this.vistaCelulares.ButtonBuscarCatal.addActionListener(this);
        this.vistaCelulares.ButtonRegresoIni.addActionListener(this);
        this.vistaCelulares.ButtonRegresoUsu.addActionListener(this);

        // Listener para la selección de filas en la tabla del catálogo
        this.vistaCelulares.TableCatalogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCelularSeleccionadoCatalogo();
            }
        });

        // Cargar los celulares en la tabla al iniciar la vista del catálogo
        cargarCatalogoCelulares();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCelulares.ButtonComprarCatal) {
            abrirVentanaCompra();
        } else if (e.getSource() == vistaCelulares.ButtonBuscarCatal) {
            buscarCelularesCatalogo();
        } else if (e.getSource() == vistaCelulares.ButtonRegresoIni) {
            regresarInicio();
        } else if (e.getSource() == vistaCelulares.ButtonRegresoUsu) {
            regresarUsuario();
        }
    }

    private void cargarCatalogoCelulares() {
        DefaultTableModel modelo = (DefaultTableModel) vistaCelulares.TableCatalogo.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

        try (MongoCursor<Document> cursor = productosCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Asegurarse de que el orden de las columnas coincida con el DefaultTableModel en Celulares.java
                modelo.addRow(new Object[]{
                        doc.getString("nombre"),
                        doc.getString("marca"),
                        doc.getDouble("precio"),
                        doc.getString("ram"),
                        doc.getString("almacenamiento"),
                        doc.getString("color"),
                        doc.getString("sim"),
                        doc.getInteger("cantidad", 0) // Mostrar la cantidad disponible
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaCelulares, "Error al cargar el catálogo de celulares: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCelularesCatalogo() {
        DefaultTableModel modelo = (DefaultTableModel) vistaCelulares.TableCatalogo.getModel();
        modelo.setRowCount(0);

        Document filtro = new Document();

        String nombre = vistaCelulares.TextNombreCatal.getText().trim();
        String marca = vistaCelulares.TextMarcaCatal.getText().trim();
        String precioStr = vistaCelulares.TextPrecioCatal.getText().trim();
        String ram = (String) vistaCelulares.BoxRamCatal.getSelectedItem();
        String almacenamiento = (String) vistaCelulares.BoxAlmacenCatal.getSelectedItem();
        String color = (String) vistaCelulares.BoxColorCatal.getSelectedItem();
        String sim = "";

        if (vistaCelulares.RadioButtSim1Catal.isSelected()) {
            sim = "Sim 1";
        } else if (vistaCelulares.RadioButtSim2Catal.isSelected()) {
            sim = "Sim 2";
        }

        // Construir el filtro dinámicamente
        if (!nombre.isEmpty()) {
            filtro.append("nombre", new Document("$regex", nombre).append("$options", "i")); // Búsqueda insensible a mayúsculas/minúsculas
        }
        if (!marca.isEmpty()) {
            filtro.append("marca", new Document("$regex", marca).append("$options", "i"));
        }
        if (!precioStr.isEmpty()) {
            try {
                double precio = Double.parseDouble(precioStr);
                filtro.append("precio", precio);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vistaCelulares, "El precio debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // Modificación: Solo añadir al filtro si no es el valor por defecto o nulo
        if (ram != null && !ram.isEmpty() && !ram.equals("1GB") && !ram.equals("2GB") && !ram.equals("4GB") && !ram.equals("8GB") && !ram.equals("12GB")) {
            // Si el valor del combobox es el valor por defecto o no es un valor real, no se añade al filtro
        } else if (ram != null && !ram.isEmpty()) {
            filtro.append("ram", ram);
        }

        if (almacenamiento != null && !almacenamiento.isEmpty() && !almacenamiento.equals("32GB") && !almacenamiento.equals("64GB") && !almacenamiento.equals("128GB") && !almacenamiento.equals("256GB")) {
            // Si el valor del combobox es el valor por defecto o no es un valor real, no se añade al filtro
        } else if (almacenamiento != null && !almacenamiento.isEmpty()) {
            filtro.append("almacenamiento", almacenamiento);
        }

        if (color != null && !color.isEmpty() && !color.equals("Negro") && !color.equals("Blanco") && !color.equals("Gris") && !color.equals("Dorado")) {
            // Si el valor del combobox es el valor por defecto o no es un valor real, no se añade al filtro
        } else if (color != null && !color.isEmpty()) {
            filtro.append("color", color);
        }

        if (!sim.isEmpty()) {
            filtro.append("sim", sim);
        }

        try (MongoCursor<Document> cursor = productosCollection.find(filtro).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                modelo.addRow(new Object[]{
                        doc.getString("nombre"),
                        doc.getString("marca"),
                        doc.getDouble("precio"),
                        doc.getString("ram"),
                        doc.getString("almacenamiento"),
                        doc.getString("color"),
                        doc.getString("sim"),
                        doc.getInteger("cantidad", 0)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaCelulares, "Error al buscar celulares: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCelularSeleccionadoCatalogo() {
        int fila = vistaCelulares.TableCatalogo.getSelectedRow();
        if (fila >= 0) {
            vistaCelulares.TextNombreCatal.setText(vistaCelulares.TableCatalogo.getValueAt(fila, 0).toString());
            vistaCelulares.TextMarcaCatal.setText(vistaCelulares.TableCatalogo.getValueAt(fila, 1).toString());
            vistaCelulares.TextPrecioCatal.setText(vistaCelulares.TableCatalogo.getValueAt(fila, 2).toString());
            vistaCelulares.BoxRamCatal.setSelectedItem(vistaCelulares.TableCatalogo.getValueAt(fila, 3).toString());
            vistaCelulares.BoxAlmacenCatal.setSelectedItem(vistaCelulares.TableCatalogo.getValueAt(fila, 4).toString());
            vistaCelulares.BoxColorCatal.setSelectedItem(vistaCelulares.TableCatalogo.getValueAt(fila, 5).toString());

            String sim = vistaCelulares.TableCatalogo.getValueAt(fila, 6).toString();
            vistaCelulares.RadioButtSim1Catal.setSelected("Sim 1".equalsIgnoreCase(sim));
            vistaCelulares.RadioButtSim2Catal.setSelected("Sim 2".equalsIgnoreCase(sim));

            // La cantidad en el catálogo no se edita, solo se muestra.
            // Si hubiera un campo de cantidad para comprar, se usaría aquí.
            vistaCelulares.TextCantidadCatal.setText(vistaCelulares.TableCatalogo.getValueAt(fila, 7).toString());
        }
    }

    private void abrirVentanaCompra() {
        int filaSeleccionada = vistaCelulares.TableCatalogo.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(vistaCelulares, "Por favor, seleccione un celular para comprar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener los datos del celular seleccionado
        String nombreCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 0).toString();
        String marcaCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 1).toString();
        double precioCelular = (Double) vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 2);
        String ramCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 3).toString();
        String almacenamientoCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 4).toString();
        String colorCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 5).toString();
        String simCelular = vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 6).toString();
        int cantidadDisponible = (Integer) vistaCelulares.TableCatalogo.getValueAt(filaSeleccionada, 7);


        // Solicitar la cantidad al usuario
        String cantidadStr = JOptionPane.showInputDialog(vistaCelulares, "Ingrese la cantidad que desea comprar de " + nombreCelular + " (Disponible: " + cantidadDisponible + "):");
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return; // El usuario canceló o no ingresó nada
        }

        int cantidadComprar;
        try {
            cantidadComprar = Integer.parseInt(cantidadStr.trim());
            if (cantidadComprar <= 0) {
                JOptionPane.showMessageDialog(vistaCelulares, "La cantidad debe ser un número positivo.", "Error de Cantidad", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cantidadComprar > cantidadDisponible) {
                JOptionPane.showMessageDialog(vistaCelulares, "No hay suficiente stock. Cantidad disponible: " + cantidadDisponible, "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaCelulares, "Cantidad inválida. Por favor, ingrese un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Celular con los datos seleccionados
        Celular celularSeleccionado = new Celular(nombreCelular, marcaCelular, precioCelular, ramCelular, almacenamientoCelular, colorCelular, simCelular);

        // Abrir la vista de Compra y pasar los datos
        VistaCompra vistaCompra = new VistaCompra(); // Usar la clase Compra directamente
        ControladorCompra controladorCompra = new ControladorCompra(vistaCompra, mongoDB);
        controladorCompra.agregarProductoAlCarrito(celularSeleccionado, cantidadComprar); // Método para añadir al carrito
        vistaCompra.setVisible(true);
        vistaCelulares.dispose(); // Cerrar la ventana de catálogo
    }


    private void regresarInicio() {
        Inicio vistaInicio = new Inicio();
        ControladorInicio controladorInicio = new ControladorInicio(vistaInicio);
        vistaInicio.setVisible(true);
        vistaCelulares.dispose(); // Cerrar la ventana de catálogo
    }

    private void regresarUsuario() {
        VistaUsuario vistaUsuario = new VistaUsuario(); // Instanciar la clase VistaUsuario de Vista
        ControladorUsuario controladorUsuario = new ControladorUsuario(vistaUsuario);
        vistaUsuario.setVisible(true);
        vistaCelulares.dispose(); // Cerrar la ventana de catálogo
    }

    // Método para limpiar los campos de búsqueda/selección en el catálogo
    private void limpiarCamposCatalogo() {
        vistaCelulares.TextNombreCatal.setText("");
        vistaCelulares.TextMarcaCatal.setText("");
        vistaCelulares.TextPrecioCatal.setText("");
        vistaCelulares.TextCantidadCatal.setText("");
        vistaCelulares.BoxRamCatal.setSelectedIndex(0);
        vistaCelulares.BoxAlmacenCatal.setSelectedIndex(0);
        vistaCelulares.BoxColorCatal.setSelectedIndex(0);
        vistaCelulares.buttonGroup1.clearSelection(); // Deseleccionar los RadioButtons
    }
}
