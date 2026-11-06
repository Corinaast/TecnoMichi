/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Controlador.MongoDBConnection;
import Modelo.Celular;
import Modelo.ICelularRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de ICelularRepository para MongoDB.
 * Aplica el Principio de Responsabilidad Única (SRP) al manejar solo operaciones CRUD de Celular.
 * Aplica el Principio de Inversión de Dependencias (DIP) al depender de la abstracción ICelularRepository
 * y usar MongoDBConnection para la base de datos.
 */
public class CelularRepositoryImpl implements ICelularRepository {

    private final MongoCollection<Document> productosCollection; // Asumiendo que los celulares están en la colección "productos"

    /**
     * Constructor que obtiene la colección de productos de la base de datos.
     * Depende de MongoDBConnection para obtener la instancia de la base de datos.
     */
    public CelularRepositoryImpl() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.productosCollection = database.getCollection("productos"); // Asegúrate de que el nombre de tu colección sea "productos"
    }

    @Override
    public boolean save(Celular celular) {
        try {
            Document doc = celular.toDocument();
            // Si tu modelo Celular no tiene cantidad, pero la DB sí, puedes añadirla aquí
            // doc.append("cantidad", 0); // O el valor inicial que desees
            productosCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar celular: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Celular findById(String id) {
        try {
            Document doc = productosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return Celular.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar celular por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Celular> findAll() {
        List<Celular> celulares = new ArrayList<>();
        try {
            for (Document doc : productosCollection.find()) {
                celulares.add(Celular.fromDocument(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al listar celulares: " + e.getMessage());
        }
        return celulares;
    }

    @Override
    public Celular findByNombre(String nombre) {
        try {
            Document doc = productosCollection.find(Filters.eq("nombre", nombre)).first();
            if (doc != null) {
                return Celular.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar celular por nombre: " + e.getMessage());
        }
        return null;
    }

@Override
public List<Celular> searchCelulares(String nombre, String marca, String ram,
                                     String almacenamiento, String color, String sim, String precio) {
    List<Celular> celulares = new ArrayList<>();
    try {
        for (Document doc : productosCollection.find()) {
            celulares.add(Celular.fromDocument(doc));
        }
    } catch (Exception e) {
        System.err.println("Error en búsqueda de celulares: " + e.getMessage());
    }
    return celulares;
}


    @Override
    public boolean update(Celular celular) {
        if (celular.getId() == null) {
            System.err.println("Error al actualizar celular: ID es nulo.");
            return false;
        }
        try {
            Document updatedDoc = celular.toDocument();
            updatedDoc.remove("_id"); // Eliminar el _id del documento de actualización
            productosCollection.updateOne(Filters.eq("_id", new ObjectId(celular.getId())), new Document("$set", updatedDoc));
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar celular: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la cantidad de un celular en la base de datos.
     * Este método es específico y no está en IRepository, pero es útil para el stock.
     * Podrías añadirlo a ICelularRepository si se considera una operación CRUD de inventario.
     * @param celularId El ID del celular a actualizar.
     * @param newQuantity La nueva cantidad.
     * @return true si la cantidad fue actualizada, false en caso contrario.
     */
    public boolean updateQuantity(String celularId, int newQuantity) {
        try {
            productosCollection.updateOne(
                Filters.eq("_id", new ObjectId(celularId)),
                Updates.set("cantidad", newQuantity)
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar cantidad del celular: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            productosCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar celular: " + e.getMessage());
            return false;
        }
    }
}
