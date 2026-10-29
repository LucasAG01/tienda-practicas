package com.tienda;

import com.mongodb.client.*;
import org.basex.core.*;
import org.basex.core.cmd.Open;

public class ConexionDB {

    private Context baseXCtx;
    private MongoClient mongoClient;
    private MongoDatabase mongoDb;

    // Abrir conexiones

    public void conectar() {
        try {
            // 1.Conexion BaseeX
            baseXCtx = new Context();
            baseXCtx.soptions.set(StaticOptions.DBPATH, System.getProperty("user.home") + "/BaseX/data");
            new Open("productos").execute(baseXCtx);
            System.out.println("✅ Conectado a BaseX (productos)");

            // 2. Conexión MongoDB
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            mongoDb = mongoClient.getDatabase("tienda");
            System.out.println("✅ Conectado a MongoDB (tienda)");

        } catch (Exception e) {
            System.err.println("❌ Error crítico al conectar: " + e.getMessage());
            System.exit(1); // Si no hay conexión, cerramos la app
        }
    }


    public void desconectar() {
        if (baseXCtx != null) baseXCtx.close();
        if (mongoClient != null) mongoClient.close();
        System.out.println(" Conexiones cerradas.");
    }

    // Métodos para que los dao puedan usar las conexiones
    public Context getBaseXCtx() {
        return baseXCtx;
    }

    public MongoDatabase getMongoDb() {
        return mongoDb;
    }
}
