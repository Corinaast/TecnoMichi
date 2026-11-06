/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase responsable de gestionar la conexión a la base de datos MongoDB.
 * Aplica el Principio de Responsabilidad Única (SRP) al centralizar
 * la lógica de conexión.
 */
public class MongoDBConnection {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final String DATABASE_NAME = "TecnoMichi"; // Nombre de tu base de datos

    // Deshabilita los logs ruidosos de MongoDB si no son necesarios para depuración
    static {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
    }

    /**
     * Constructor privado para asegurar una única instancia (Singleton si se desea,
     * aunque para la conexión, MongoDBClient ya maneja el pooling).
     * Aquí se inicializa la conexión.
     */
    private MongoDBConnection() {
        try {
            // Asegúrate de que la URI de conexión sea correcta para tu configuración de MongoDB
            // Si MongoDB está corriendo localmente en el puerto por defecto, esta URI es común.
            // Si usas MongoDB Atlas o una configuración diferente, actualiza esta URI.
            String connectionString = "mongodb://localhost:27017";
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Conexión a MongoDB establecida exitosamente a la base de datos: " + DATABASE_NAME);
            // Puedes eliminar el JOptionPane en un entorno de producción
            // JOptionPane.showMessageDialog(null, "Conexión a MongoDB establecida exitosamente.", "Conexión DB", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos MongoDB: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            // Considera terminar la aplicación o manejar el error de forma más robusta si la DB es crítica
            // System.exit(1);
        }
    }

    /**
     * Método estático para obtener la instancia de la base de datos.
     * Si la conexión no existe, la inicializa.
     * @return La instancia de MongoDatabase.
     */
    public static MongoDatabase getDatabase() {
        if (database == null) {
            new MongoDBConnection(); // Inicializa la conexión si aún no lo ha hecho
        }
        return database;
    }

    /**
     * Cierra la conexión con MongoDB.
     */
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}
