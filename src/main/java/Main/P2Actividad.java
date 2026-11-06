package Main;

import Controlador.ControladorUsuario; // Importar el controlador específico para la vista de usuario
import Vista.VistaUsuario; // Importar la vista de usuario

public class P2Actividad {
    public static void main(String[] args) {
        // Crear una instancia de la vista de usuario
        VistaUsuario vistaUsuario = new VistaUsuario();

        // Crear una instancia del controlador de usuario, pasándole la vista
        ControladorUsuario controladorUsuario = new ControladorUsuario(vistaUsuario);

        // Hacer visible la ventana de inicio de sesión
        vistaUsuario.setVisible(true);

        // Centrar la ventana en la pantalla
        vistaUsuario.setLocationRelativeTo(null);
    }
}
