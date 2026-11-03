package com.tienda.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class ClienteDAO {

    // Coleccion de MOngoDB con la que trabajamos
    private MongoCollection<Document> coleccionClientes;

    // Recibimos la base de datos desde le Main
    public ClienteDAO(MongoDatabase db){
        this.coleccionClientes = db.getCollection("clientes");
    }


    // Crear nuevo cliente
    public void crearCliente(String nombre, String email, String telefono, String direccion){

        // 1. Comprobar si existe ya el email
        Document existente = coleccionClientes.find(eq("email",email)).first();

        if (existente !=null){
            System.out.println("Error, ya existe cliente con ese mail");
            return;
        }

        // 2. Si no existe, cremos el doc
        Document nuevoCliente =new Document("nombre", nombre).append("email",email).append("telefono",telefono).append("direccion",direccion);

        // 3. Insertar en la coleccion
        coleccionClientes.insertOne(nuevoCliente);

        System.out.println("Cleinte creado correctamente con email: "+email);
    }




}
