package ControladorTest;

import Controlador.MongoDBConnection;
import Modelo.Compra;
import Modelo.ICompraRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de ICompraRepository para MongoDB.
 * Aplica el Principio de Responsabilidad Única (SRP) al manejar solo operaciones CRUD de Compra.
 * Aplica el Principio de Inversión de Dependencias (DIP) al depender de la abstracción ICompraRepository
 * y usar MongoDBConnection para la base de datos.
 */
public class CompraRepositoryImpl implements ICompraRepository {

    private final MongoCollection<Document> comprasCollection;

    /**
     * Constructor que obtiene la colección de compras de la base de datos.
     */
    public CompraRepositoryImpl() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.comprasCollection = database.getCollection("compras"); // Verifica que la colección se llame "compras"
    }

    @Override
    public boolean save(Compra compra) {
        try {
            Document doc = compra.toDocument();
            comprasCollection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar compra: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Compra findById(String id) {
        try {
            Document doc = comprasCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return Compra.fromDocument(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar compra por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Compra> findAll() {
        List<Compra> compras = new ArrayList<>();
        try {
            for (Document doc : comprasCollection.find()) {
                compras.add(Compra.fromDocument(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar todas las compras: " + e.getMessage());
        }
        return compras;
    }

    @Override
    public boolean update(Compra compra) {
        if (compra.getId() == null) {
            System.err.println("Error al actualizar compra: ID es nulo.");
            return false;
        }
        try {
            Document updatedDoc = compra.toDocument();
            updatedDoc.remove("_id"); // No permitir cambiar el _id
            comprasCollection.updateOne(
                    Filters.eq("_id", new ObjectId(compra.getId())),
                    new Document("$set", updatedDoc)
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar compra: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            comprasCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar compra: " + e.getMessage());
            return false;
        }
    }

    /**
     * 🔥 MÉTODO QUE FALTABA (de ICompraRepository)
     * Busca todas las compras asociadas a un usuario.
     */
    @Override
    public List<Compra> findByUsuario(String usuarioId) {
        List<Compra> compras = new ArrayList<>();
        try {
            for (Document doc : comprasCollection.find(Filters.eq("usuarioId", usuarioId))) {
                compras.add(Compra.fromDocument(doc));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar compras por usuario: " + e.getMessage());
        }
        return compras;
    }
}
