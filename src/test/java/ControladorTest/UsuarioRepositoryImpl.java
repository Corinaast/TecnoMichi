/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorTest;

import Controlador.MongoDBConnection;
import Modelo.IUsuarioRepository;
import Modelo.Usuario;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de IUsuarioRepository para MongoDB.
 * Aplica el Principio de Responsabilidad Única (SRP) al manejar solo operaciones CRUD de Usuario.
 * Aplica el Principio de Inversión de Dependencias (DIP) al depender de la abstracción IUsuarioRepository
 * y usar MongoDBConnection para la base de datos.
 */
public class UsuarioRepositoryImpl implements IUsuarioRepository { // ¡CORRECCIÓN CLAVE: implements IUsuarioRepository!

    private final MongoCollection<Document> usuariosCollection;

    /**
     * Constructor que obtiene la colección de usuarios de la base de datos.
     * Depende de MongoDBConnection para obtener la instancia de la base de datos.
     * Ya no maneja su propia conexión a MongoDB.
     */
    public UsuarioRepositoryImpl() {
        MongoDatabase database = MongoDBConnection.getDatabase(); // Usa la conexión centralizada
        this.usuariosCollection = database.getCollection("usuarios"); // Asegúrate de que el nombre de tu colección sea "usuarios"
    }

    @Override
    public boolean save(Usuario usuario) {
        try {
            Document doc = usuario.toDocument();
            // Si el usuario ya tiene un ID (ej. al actualizar), MongoDB lo usará para upsert o error si no se maneja
            // Para inserción, si _id no está en el documento, MongoDB lo genera automáticamente.
            usuariosCollection.insertOne(doc);
            // Si necesitas el ID generado, puedes obtenerlo de doc.getObjectId("_id").toHexString();
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario findById(String id) {
        try {
            Document doc = usuariosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return Usuario.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario findByUsername(String username) {
        try {
            Document doc = usuariosCollection.find(Filters.eq("nombre", username)).first();
            if (doc != null) {
                return Usuario.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar usuario por nombre de usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario findByEmail(String email) {
        try {
            Document doc = usuariosCollection.find(Filters.eq("correo", email)).first();
            if (doc != null) {
                return Usuario.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar usuario por correo electrónico: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario findByUsernameAndPassword(String username, String password) {
        try {
            Document doc = usuariosCollection.find(Filters.and(
                Filters.eq("nombre", username),
                Filters.eq("password", password)
            )).first();
            if (doc != null) {
                return Usuario.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar usuario por credenciales: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            for (Document doc : usuariosCollection.find()) {
                usuarios.add(Usuario.fromDocument(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar todos los usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public boolean update(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            System.err.println("Error al actualizar usuario: ID es nulo o vacío.");
            return false;
        }
        try {
            // Crear un documento de actualización sin el _id, ya que el _id se usa en el filtro
            Document updatedDoc = usuario.toDocument();
            updatedDoc.remove("_id"); // Asegurarse de que el _id no esté en el $set

            usuariosCollection.updateOne(Filters.eq("_id", new ObjectId(usuario.getId())), new Document("$set", updatedDoc));
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            usuariosCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
