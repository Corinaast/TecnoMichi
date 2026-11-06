package Modelo;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Celular {
    private String id;
    private String nombre;
    private String marca;
    private double precio;
    private String ram;
    private String almacenamiento;
    private String color;
    private String sim;
    private int cantidad;  

    public Celular() {}

    public Celular(String nombre, String marca, double precio, String ram,
                   String almacenamiento, String color, String sim) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.ram = ram;
        this.almacenamiento = almacenamiento;
        this.color = color;
        this.sim = sim;
    }

    public Celular(String id, String nombre, String marca, double precio,
                   String ram, String almacenamiento, String color, String sim, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.ram = ram;
        this.almacenamiento = almacenamiento;
        this.color = color;
        this.sim = sim;
        this.cantidad = cantidad;
    }

    // Constructor para crear desde MongoDB
    public Celular(Document doc) {
        if (doc.containsKey("_id")) {
            this.id = doc.getObjectId("_id").toString();
        }
        this.nombre = doc.getString("nombre");
        this.marca = doc.getString("marca");
        this.precio = doc.getDouble("precio");
        this.ram = doc.getString("ram");
        this.almacenamiento = doc.getString("almacenamiento");
        this.color = doc.getString("color");
        this.sim = doc.getString("sim");
        this.cantidad = doc.getInteger("cantidad", 0); // ✅ Extraer cantidad si existe
    }

    // Convertir a documento para MongoDB
    public Document toDocument() {
        Document doc = new Document("nombre", nombre)
                .append("marca", marca)
                .append("precio", precio)
                .append("ram", ram)
                .append("almacenamiento", almacenamiento)
                .append("color", color)
                .append("sim", sim)
                .append("cantidad", cantidad);

        if (id != null) {
            doc.append("_id", new ObjectId(id));
        }
        return doc;
    }

    public static Celular fromDocument(Document doc) {
        return new Celular(doc);
    }

    @Override
    public String toString() {
        return nombre + " - " + marca + " - $" + precio + " (Stock: " + cantidad + ")";
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getAlmacenamiento() { return almacenamiento; }
    public void setAlmacenamiento(String almacenamiento) { this.almacenamiento = almacenamiento; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getSim() { return sim; }
    public void setSim(String sim) { this.sim = sim; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
