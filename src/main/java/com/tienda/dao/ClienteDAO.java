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



    // Identificar el cliente por email
    public org.bson.types.ObjectId identiifcarCliente(String email){

        //Buscar primer cliente que coincida
        Document cliente = coleccionClientes.find(eq("email",email)).first();

        if(cliente != null){
            //Si existe sacamos _id y devolvemos
            return cliente.getObjectId("_id");
        }else {
            return null;
        }

    }



    // Borrar cliente
    public void eliminarCliente(org.bson.types.ObjectId id){
        //Borramos el documento que tenga ese _id
        coleccionClientes.deleteOne(eq("_id",id));

        System.out.println("CLiente eliminado correctamnte");
    }

    // MOdificar un campo
    public void modificarCampoCliente(org.bson.types.ObjectId id, String campo, String nuevoValor){

        // $set sirve para actualizar solo el campo indicado
        Document actualizacion = new Document("$set", new Document(campo, nuevoValor));

        coleccionClientes.updateOne(eq("_id",id),actualizacion);
        System.out.println("Campo '"+campo+"' modificado a: " + nuevoValor);

    }


    //
}
