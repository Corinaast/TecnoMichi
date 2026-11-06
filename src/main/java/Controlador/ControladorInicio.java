package Controlador;

import Vista.Inicio;
import Vista.Celulares;
import Vista.VistaCompra;
import Vista.VistaUsuario; // Para regresar a la pantalla de login
import Modelo.Mongodb; // Aunque no interactúa directamente, podría necesitarse para cerrar la conexión si se cierra la app desde aquí.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorInicio implements ActionListener {

    private Inicio vistaInicio;
    // No instanciamos las otras vistas aquí para evitar acoplamiento y crear instancias innecesarias.
    // Las crearemos y pasaremos a sus respectivos controladores cuando sea necesario.

    public ControladorInicio(Inicio vistaInicio) {
        this.vistaInicio = vistaInicio;
        // Asignar listeners a los botones de la vista de Inicio
        this.vistaInicio.ButtonCatalogo.addActionListener(this);
        this.vistaInicio.ButtonCompra.addActionListener(this);
        this.vistaInicio.ButtonCerrarSesionIni.addActionListener(this);
        this.vistaInicio.ButtonSalirUsu.addActionListener(this); // Asumo que este es para salir de la app
        this.vistaInicio.ButtonRegresoUsu.addActionListener(this); // Para regresar al login
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaInicio.ButtonCatalogo) {
            abrirCatalogo();
        } else if (e.getSource() == vistaInicio.ButtonCompra) {
            abrirCompras();
        } else if (e.getSource() == vistaInicio.ButtonCerrarSesionIni || e.getSource() == vistaInicio.ButtonRegresoUsu) {
            cerrarSesion();
        } else if (e.getSource() == vistaInicio.ButtonSalirUsu) {
            salirAplicacion();
        }
    }

    private void abrirCatalogo() {
        // Crear la vista de Celulares y su controlador
        Celulares vistaCelulares = new Celulares();
        // Pasamos la instancia de MongoDB si es necesaria en el controlador de Celulares
        ControladorCelulares controladorCelulares = new ControladorCelulares(vistaCelulares, new Mongodb());
        vistaCelulares.setVisible(true);
        vistaInicio.dispose(); // Cerrar la ventana de inicio
    }

    private void abrirCompras() {
        // Crear la vista de VistaCompra y su controlador
        VistaCompra vistaCompra = new VistaCompra();
        // Pasamos la instancia de MongoDB si es necesaria en el controlador de VistaCompra
        ControladorCompra controladorCompra = new ControladorCompra(vistaCompra, new Mongodb());
        vistaCompra.setVisible(true);
        vistaInicio.dispose(); // Cerrar la ventana de inicio
    }

    private void cerrarSesion() {
        // Regresar a la pantalla de inicio de sesión
        VistaUsuario vistaUsuario = new VistaUsuario();
        ControladorUsuario controladorUsuario = new ControladorUsuario(vistaUsuario); // Reutilizar el controlador de usuario
        vistaUsuario.setVisible(true);
        vistaInicio.dispose(); // Cerrar la ventana de inicio
    }

    private void salirAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(vistaInicio, "¿Estás seguro de que quieres salir de la aplicación?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Aquí podrías cerrar la conexión a la base de datos si fuera necesario
            // new Mongodb().close(); // Descomentar si quieres cerrar la conexión al salir
            System.exit(0); // Terminar la aplicación
        }
    }
}
