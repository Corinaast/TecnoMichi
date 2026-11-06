package Controlador;

import Modelo.Mongodb;
import Modelo.Usuario;
import Vista.Inicio;
import Vista.NewUsuario;
import Vista.VistaUsuario; // Alias para evitar conflicto de nombres

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorUsuario implements ActionListener {

    private VistaUsuario vistaUsuario; // Vista de inicio de sesión
    private NewUsuario vistaNewUsuario; // Vista de nuevo usuario
    private Mongodb mongoDB;

    // Constructor para la vista de inicio de sesión (VistaUsuario.java)
    public ControladorUsuario(VistaUsuario vistaUsuario) {
        this.vistaUsuario = vistaUsuario;
        this.mongoDB = new Mongodb();
        // Asignar listeners a los botones de la vista de inicio de sesión
        this.vistaUsuario.ButtonIngresar.addActionListener(this);
        this.vistaUsuario.ButtonSinRegistro.addActionListener(this);
        this.vistaUsuario.buttonOlviPasswor.addActionListener(this);

        // Verificar y crear usuarios iniciales al iniciar el controlador
        checkAndCreateInitialUsers();
    }

    // Constructor para la vista de nuevo usuario (NewUsuario.java)
    public ControladorUsuario(NewUsuario vistaNewUsuario) {
        this.vistaNewUsuario = vistaNewUsuario;
        this.mongoDB = new Mongodb();
        // Asignar listeners a los botones de la vista de nuevo usuario
        this.vistaNewUsuario.ButtonNewRegistro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica para la vista de inicio de sesión (VistaUsuario.java)
        if (vistaUsuario != null) {
            if (e.getSource() == vistaUsuario.ButtonIngresar) {
                iniciarSesion();
            } else if (e.getSource() == vistaUsuario.ButtonSinRegistro) {
                abrirVentanaRegistro();
            } else if (e.getSource() == vistaUsuario.buttonOlviPasswor) {
                JOptionPane.showMessageDialog(vistaUsuario, "Funcionalidad de recuperar contraseña no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        // Lógica para la vista de nuevo usuario (NewUsuario.java)
        else if (vistaNewUsuario != null) {
            if (e.getSource() == vistaNewUsuario.ButtonNewRegistro) {
                registrarNuevoUsuario();
            }
        }
    }

    /**
     * Verifica si la colección de usuarios está vacía y, si es así, crea usuarios predeterminados.
     */
    private void checkAndCreateInitialUsers() {
        MongoCollection<Document> collection = mongoDB.getUsuariosCollection();
        long userCount = collection.countDocuments();

        if (userCount == 0) {
            // Crear usuario administrador
            Usuario adminUser = new Usuario("admin", "30", "admin@tecnomichi.com", "admin123");
            Document adminDoc = adminUser.toDocument();
            adminDoc.append("rol", "ADMIN"); // Añadir el rol para el administrador
            collection.insertOne(adminDoc);

            // Crear usuario comprador
            Usuario buyerUser = new Usuario("comprador", "25", "comprador@tecnomichi.com", "comp123");
            Document buyerDoc = buyerUser.toDocument();
            buyerDoc.append("rol", "COMPRADOR"); // Añadir el rol para el comprador
            collection.insertOne(buyerDoc);

            JOptionPane.showMessageDialog(null, "Usuarios iniciales creados:\n- admin (admin123)\n- comprador (comp123)", "Usuarios Iniciales", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void iniciarSesion() {
        String nombre = vistaUsuario.TextNombre.getText();
        String password = new String(vistaUsuario.Password.getPassword());

        if (nombre.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Por favor, ingrese nombre y contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MongoCollection<Document> collection = mongoDB.getUsuariosCollection();
        Document userDoc = collection.find(Filters.and(
                Filters.eq("nombre", nombre),
                Filters.eq("password", password)
        )).first();

        if (userDoc != null) {
            JOptionPane.showMessageDialog(vistaUsuario, "Inicio de sesión exitoso. ¡Bienvenido " + nombre + "!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            String rol = userDoc.getString("rol"); // Obtener el rol del usuario

            if ("ADMIN".equalsIgnoreCase(rol)) {
                // Abrir vista de Registro (para admin)
                Vista.Registro vistaRegistro = new Vista.Registro();
                ControladorRegistro controladorRegistro = new ControladorRegistro(vistaRegistro);
                vistaRegistro.setVisible(true);
            } else if ("COMPRADOR".equalsIgnoreCase(rol)) {
                // Abrir vista de Inicio (para usuario normal)
                Inicio vistaInicio = new Inicio();
                ControladorInicio controladorInicio = new ControladorInicio(vistaInicio);
                vistaInicio.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vistaUsuario, "Rol de usuario no reconocido.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
                return; // Mantener la ventana de login abierta si el rol no es válido
            }
            vistaUsuario.dispose(); // Cerrar la ventana de inicio de sesión
        } else {
            JOptionPane.showMessageDialog(vistaUsuario, "Nombre de usuario o contraseña incorrectos.", "Error de Credenciales", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVentanaRegistro() {
        NewUsuario newUsuario = new NewUsuario();
        ControladorUsuario controladorNewUsuario = new ControladorUsuario(newUsuario); // Reutilizar este controlador para NewUsuario
        newUsuario.setVisible(true);
        vistaUsuario.dispose(); // Cerrar la ventana de inicio de sesión
    }

    private void registrarNuevoUsuario() {
        String nombre = vistaNewUsuario.TextNombre1.getText();
        String edad = vistaNewUsuario.TextEdad1.getText();
        String correo = vistaNewUsuario.TextCorreo.getText();
        String password = new String(vistaNewUsuario.NewPassword.getPassword());

        if (nombre.isEmpty() || edad.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vistaNewUsuario, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MongoCollection<Document> collection = mongoDB.getUsuariosCollection();
        
        // Verificar si el nombre de usuario ya existe
        Document existingUserByName = collection.find(Filters.eq("nombre", nombre)).first();
        if (existingUserByName != null) {
            JOptionPane.showMessageDialog(vistaNewUsuario, "El nombre de usuario ya existe. Por favor, elija otro.", "Usuario Existente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar si el correo ya existe
        Document existingUserByEmail = collection.find(Filters.eq("correo", correo)).first();
        if (existingUserByEmail != null) {
            JOptionPane.showMessageDialog(vistaNewUsuario, "El correo electrónico ya está registrado.", "Correo Existente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, edad, correo, password);
        // Por defecto, los nuevos usuarios registrados serán "COMPRADOR"
        Document newUserDoc = nuevoUsuario.toDocument();
        newUserDoc.append("rol", "COMPRADOR");
        
        try {
            collection.insertOne(newUserDoc);
            JOptionPane.showMessageDialog(vistaNewUsuario, "Usuario registrado con éxito. Ahora puede iniciar sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            // Después de registrar, redirigir al usuario a la pantalla de inicio de sesión
            VistaUsuario loginView = new VistaUsuario();
            ControladorUsuario loginController = new ControladorUsuario(loginView);
            loginView.setVisible(true);
            vistaNewUsuario.dispose(); // Cerrar la ventana de nuevo usuario
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaNewUsuario, "Error al registrar usuario: " + e.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
