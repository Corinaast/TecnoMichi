package Modelo;

 import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.JOptionPane;

public class Mongodb {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public Mongodb() {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("P2ProyectoTecnoMichi");  // Nombre de tu base de datos
            System.out.println(" Conexion MongoDB exitosa a base: P2Proyecto");
        } catch (MongoException e) {
            System.out.println(" La conexion a MongoDB fallo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en la conexion a MongoDB.");
        }
    }

    public MongoCollection<Document> getUsuariosCollection() {
        return database.getCollection("usuarios"); // colección para admin y compradores
    }

    public MongoCollection<Document> getProductosCollection() {
        return database.getCollection("productos"); // colección de celulares
    }

    public MongoCollection<Document> getComprasCollection() {
        return database.getCollection("compras"); // carrito o compras
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    public void insertarCelular(Celular celular) {
    try {
        getProductosCollection().insertOne(celular.toDocument());
        JOptionPane.showMessageDialog(null, "Celular guardado exitosamente.");
    } catch (MongoException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar celular: " + e.getMessage());
    }
}

public void insertarUsuario(Usuario usuario) {
    try {
        getUsuariosCollection().insertOne(usuario.toDocument());
        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
    } catch (MongoException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
    }
}
}
