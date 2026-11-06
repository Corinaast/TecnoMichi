package Controlador;

import Modelo.Celular;
import Modelo.Mongodb;
import Vista.Registro;
import Vista.VistaUsuario; // Para regresar a la pantalla de login

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorRegistro implements ActionListener {

    private Registro vistaRegistro;
    private Mongodb mongoDB;
    private MongoCollection<Document> productosCollection;

    public ControladorRegistro(Registro vistaRegistro) {
        this.vistaRegistro = vistaRegistro;
        this.mongoDB = new Mongodb();
        this.productosCollection = mongoDB.getProductosCollection();

        // Asignar listeners a los botones y tabla de la vista de Registro
        this.vistaRegistro.ButtonAgregarRegi.addActionListener(this);
        this.vistaRegistro.ButtonEditarReg.addActionListener(this);
        this.vistaRegistro.ButtonEliminarReg.addActionListener(this);
        this.vistaRegistro.ButtonBuscarReg.addActionListener(this);
        this.vistaRegistro.ButtonCerraSesion.addActionListener(this);

        // Listener para la selección de filas en la tabla
        this.vistaRegistro.TableRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCelularSeleccionado();
            }
        });

        // Cargar los celulares en la tabla al iniciar la vista
        cargarCelularesEnTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistro.ButtonAgregarRegi) {
            agregarCelular();
        } else if (e.getSource() == vistaRegistro.ButtonEditarReg) {
            modificarCelular();
        } else if (e.getSource() == vistaRegistro.ButtonEliminarReg) {
            eliminarCelular();
        } else if (e.getSource() == vistaRegistro.ButtonBuscarReg) {
            buscarCelulares();
        } else if (e.getSource() == vistaRegistro.ButtonCerraSesion) {
            cerrarSesion();
        }
    }

    private void cargarCelularesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vistaRegistro.TableRegistro.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

        try (MongoCursor<Document> cursor = productosCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Asegurarse de que el orden de las columnas coincida con el DefaultTableModel en Registro.java
                modelo.addRow(new Object[]{
                        doc.getObjectId("_id"), // ID de MongoDB para edición/eliminación
                        doc.getString("nombre"),
                        doc.getString("marca"),
                        doc.getDouble("precio"),
                        doc.getString("ram"),
                        doc.getString("almacenamiento"),
                        doc.getString("color"),
                        doc.getString("sim"),
                        doc.getInteger("cantidad", 0) // Asumiendo que la cantidad es un Integer
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Error al cargar celulares: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCelular() {
        String nombre = vistaRegistro.TextNombre.getText().trim();
        String marca = vistaRegistro.TextMarca.getText().trim();
        String precioStr = vistaRegistro.TextPrecio1.getText().trim();
        String cantidadStr = vistaRegistro.TextCantidad.getText().trim(); // Obtener la cantidad
        String ram = (String) vistaRegistro.BoxRamRegistro.getSelectedItem();
        String almacenamiento = (String) vistaRegistro.BoxAlmacenRegi.getSelectedItem();
        String color = (String) vistaRegistro.BoxColorRegi.getSelectedItem();
        String sim = "";

        if (vistaRegistro.RadioButtSin1Regi.isSelected()) {
            sim = "Sim 1";
        } else if (vistaRegistro.RadioButtSim2Regi.isSelected()) {
            sim = "Sim 2";
        }

        // Validaciones
        if (nombre.isEmpty() || marca.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty() ||
            ram == null || ram.isEmpty() || almacenamiento == null || almacenamiento.isEmpty() ||
            color == null || color.isEmpty() || sim.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistro, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(precioStr);
            cantidad = Integer.parseInt(cantidadStr);
            if (precio < 0 || cantidad < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Precio y Cantidad deben ser números válidos y positivos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto Celular y guardarlo en MongoDB
        Celular nuevoCelular = new Celular(nombre, marca, precio, ram, almacenamiento, color, sim);
        // La clase Celular no tiene un campo 'cantidad' en tu modelo, pero lo agregamos al Documento de MongoDB
        Document celularDoc = nuevoCelular.toDocument().append("cantidad", cantidad);

        try {
            productosCollection.insertOne(celularDoc);
            JOptionPane.showMessageDialog(vistaRegistro, "Celular agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposCelular();
            cargarCelularesEnTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Error al agregar celular: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCelularSeleccionado() {
        int fila = vistaRegistro.TableRegistro.getSelectedRow();
        if (fila >= 0) {
            // El ID de MongoDB está en la primera columna (columna 0)
            // vistaRegistro.TextCantidad.setText(vistaRegistro.TableRegistro.getValueAt(fila, 0).toString()); // Esto es el ID, no la cantidad
            vistaRegistro.TextNombre.setText(vistaRegistro.TableRegistro.getValueAt(fila, 1).toString());
            vistaRegistro.TextMarca.setText(vistaRegistro.TableRegistro.getValueAt(fila, 2).toString());
            vistaRegistro.TextPrecio1.setText(vistaRegistro.TableRegistro.getValueAt(fila, 3).toString());
            vistaRegistro.BoxRamRegistro.setSelectedItem(vistaRegistro.TableRegistro.getValueAt(fila, 4).toString());
            vistaRegistro.BoxAlmacenRegi.setSelectedItem(vistaRegistro.TableRegistro.getValueAt(fila, 5).toString());
            vistaRegistro.BoxColorRegi.setSelectedItem(vistaRegistro.TableRegistro.getValueAt(fila, 6).toString());

            String sim = vistaRegistro.TableRegistro.getValueAt(fila, 7).toString();
            vistaRegistro.RadioButtSin1Regi.setSelected("Sim 1".equalsIgnoreCase(sim));
            vistaRegistro.RadioButtSim2Regi.setSelected("Sim 2".equalsIgnoreCase(sim));

            // Cargar la cantidad. Asegúrate de que la columna 8 sea la cantidad.
            vistaRegistro.TextCantidad.setText(vistaRegistro.TableRegistro.getValueAt(fila, 8).toString());
        }
    }

    private void modificarCelular() {
        int fila = vistaRegistro.TableRegistro.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vistaRegistro, "Seleccione un celular para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ObjectId del celular seleccionado
        Object idObj = vistaRegistro.TableRegistro.getValueAt(fila, 0);
        ObjectId id;
        try {
            id = (ObjectId) idObj;
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Error al obtener el ID del celular. Por favor, seleccione una fila válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = vistaRegistro.TextNombre.getText().trim();
        String marca = vistaRegistro.TextMarca.getText().trim();
        String precioStr = vistaRegistro.TextPrecio1.getText().trim();
        String cantidadStr = vistaRegistro.TextCantidad.getText().trim();
        String ram = (String) vistaRegistro.BoxRamRegistro.getSelectedItem();
        String almacenamiento = (String) vistaRegistro.BoxAlmacenRegi.getSelectedItem();
        String color = (String) vistaRegistro.BoxColorRegi.getSelectedItem();
        String sim = "";

        if (vistaRegistro.RadioButtSin1Regi.isSelected()) {
            sim = "Sim 1";
        } else if (vistaRegistro.RadioButtSim2Regi.isSelected()) {
            sim = "Sim 2";
        }

        // Validaciones
        if (nombre.isEmpty() || marca.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty() ||
            ram == null || ram.isEmpty() || almacenamiento == null || almacenamiento.isEmpty() ||
            color == null || color.isEmpty() || sim.isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistro, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(precioStr);
            cantidad = Integer.parseInt(cantidadStr);
            if (precio < 0 || cantidad < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Precio y Cantidad deben ser números válidos y positivos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el documento de actualización
        Document updateDoc = new Document("$set", new Document()
                .append("nombre", nombre)
                .append("marca", marca)
                .append("precio", precio)
                .append("ram", ram)
                .append("almacenamiento", almacenamiento)
                .append("color", color)
                .append("sim", sim)
                .append("cantidad", cantidad)); // Actualizar también la cantidad

        try {
            productosCollection.updateOne(Filters.eq("_id", id), updateDoc);
            JOptionPane.showMessageDialog(vistaRegistro, "Celular modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposCelular();
            cargarCelularesEnTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaRegistro, "Error al modificar celular: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCelular() {
        int fila = vistaRegistro.TableRegistro.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vistaRegistro, "Seleccione un celular para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vistaRegistro, "¿Está seguro de que desea eliminar este celular?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = vistaRegistro.TableRegistro.getValueAt(fila, 0);
            ObjectId id;
            try {
                id = (ObjectId) idObj;
            } catch (ClassCastException e) {
                JOptionPane.showMessageDialog(vistaRegistro, "Error al obtener el ID del celular. Por favor, seleccione una fila válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                productosCollection.deleteOne(Filters.eq("_id", id));
                JOptionPane.showMessageDialog(vistaRegistro, "Celular eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposCelular();
                cargarCelularesEnTabla();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vistaRegistro, "Error al eliminar celular: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarCelulares() {
        DefaultTableModel modelo = (DefaultTableModel) vistaRegistro.TableRegistro.getModel();
        modelo.setRowCount(0);

        String nombreBusqueda = vistaRegistro.TextBuscarNombreReg.getText().trim();
        Document filtro = new Document();

        if (!nombreBusqueda.isEmpty()) {
            // Búsqueda insensible a mayúsculas/minúsculas y parcial
            filtro.append("nombre", new Document("$regex", nombreBusqueda).append("$options", "i"));
        }

        try (MongoCursor<Document> cursor = productosCollection.find(filtro).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                modelo.addRow(new Object[]{
                        doc.getObjectId("_id"),
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
            JOptionPane.showMessageDialog(vistaRegistro, "Error al buscar celulares: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposCelular() {
        vistaRegistro.TextCantidad.setText("");
        vistaRegistro.TextNombre.setText("");
        vistaRegistro.TextMarca.setText("");
        vistaRegistro.TextPrecio1.setText("");
        vistaRegistro.BoxRamRegistro.setSelectedIndex(0);
        vistaRegistro.BoxAlmacenRegi.setSelectedIndex(0);
        vistaRegistro.BoxColorRegi.setSelectedIndex(0);
        vistaRegistro.RadioButtSin1Regi.setSelected(false);
        vistaRegistro.RadioButtSim2Regi.setSelected(false);
        vistaRegistro.TextBuscarNombreReg.setText(""); // Limpiar campo de búsqueda
    }

    private void cerrarSesion() {
        vistaRegistro.dispose(); // Cerrar la ventana de registro
        VistaUsuario vistaUsuario = new VistaUsuario();
        ControladorUsuario controladorUsuario = new ControladorUsuario(vistaUsuario);
        vistaUsuario.setVisible(true);
    }
}
