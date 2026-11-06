package Modelo;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Compra {
    private String id;
    private String nombreUsuario;
    private Celular celular;
    private int cantidad;
    private double precio;
    private double preciototal;

    // ✅ Constructor vacío (obligatorio para frameworks y MongoDB)
    public Compra() {}

    // ✅ Constructor normal
    public Compra(String nombreUsuario, Celular celular, int cantidad, double precio, double preciototal) {
        this.nombreUsuario = nombreUsuario;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precio = precio;
        this.preciototal = preciototal;
    }

    // ✅ Constructor desde un Document de MongoDB
    public Compra(Document doc) {
        if (doc.containsKey("_id")) {
            this.id = doc.getObjectId("_id").toString();
        }
        this.nombreUsuario = doc.getString("usuario");

        // 📌 Reconstruimos el celular desde el subdocumento
        Document celularDoc = (Document) doc.get("celular");
        if (celularDoc != null) {
            this.celular = Celular.fromDocument(celularDoc);
        }

        this.cantidad = doc.getInteger("cantidad", 0);
        this.precio = doc.getDouble("precio");
        this.preciototal = doc.getDouble("Total");
    }

    // ✅ Convertir Compra a Document para MongoDB
    public Document toDocument() {
        Document doc = new Document("usuario", nombreUsuario)
                .append("celular", celular != null ? celular.toDocument() : null)
                .append("cantidad", cantidad)
                .append("precio", precio)
                .append("Total", preciototal);

        // Solo agregar _id si ya existe (para updates)
        if (id != null) {
            doc.append("_id", new ObjectId(id));
        }
        return doc;
    }

    // ✅ Método estático para reconstruir Compra desde Document
    public static Compra fromDocument(Document doc) {
        return new Compra(doc);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id='" + id + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", celular=" + (celular != null ? celular.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", preciototal=" + preciototal +
                '}';
    }

    // ✅ Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Celular getCelular() { return celular; }
    public void setCelular(Celular celular) { this.celular = celular; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getPreciototal() { return preciototal; }
    public void setPreciototal(double preciototal) { this.preciototal = preciototal; }
}
