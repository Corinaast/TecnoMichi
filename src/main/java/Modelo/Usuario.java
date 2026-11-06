package Modelo;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;

    // ✅ Constructor vacío (MongoDB y frameworks lo necesitan)
    public Usuario() {}

    // ✅ Constructor normal
    public Usuario(String nombre, String correo, String password, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    // ✅ Constructor desde un Document (MongoDB → Java)
    public Usuario(Document doc) {
        if (doc.containsKey("_id")) {
            this.id = doc.getObjectId("_id").toString();
        }
        this.nombre = doc.getString("nombre");
        this.correo = doc.getString("correo");
        this.password = doc.getString("password");
        this.rol = doc.getString("rol");
    }

    // ✅ Convertir objeto Usuario → Document para MongoDB
    public Document toDocument() {
        Document doc = new Document("nombre", nombre)
                .append("correo", correo)
                .append("password", password)
                .append("rol", rol);

        if (id != null) { // Solo agrega _id si existe (para updates)
            doc.append("_id", new ObjectId(id));
        }
        return doc;
    }

    // ✅ Método estático para reconstruir Usuario desde Document
    public static Usuario fromDocument(Document doc) {
        return new Usuario(doc);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    // ✅ Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
