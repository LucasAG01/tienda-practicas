package com.tienda.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

public class CarritoDAO {

    private MongoCollection<Document> coleccionCarrito;


    public CarritoDAO(MongoDatabase db) {
        this.coleccionCarrito = db.getCollection("carrito");
    }

    public void agregarProducto(ObjectId clienteId, int prodId, String nombre, double precio, int cantidad){
        // 1.- Preparamos el rpoducto con un doc Json
        Document nuevoItem = new Document("id_producto", prodId).append("nombre",nombre).append("precio_unitario",precio).append("cantidad",cantidad);

        // 2.- CLeinte tien ya carrito?=¿
        Document carritoExistente = coleccionCarrito.find(eq("cliente_id",clienteId)).first();

        if(carritoExistente == null){
            // 3.- Si no tiene, creamos
            coleccionCarrito.insertOne(new Document("cliente_id",clienteId).append("productos", List.of(nuevoItem)));
        }else{
            // Si ya tiene hacer push
            coleccionCarrito.updateOne(eq("cliente_id",clienteId),push("productos",nuevoItem));
        }
    }
}
