package com.tienda;

import com.mongodb.client.*;
import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ProductoDAO;
import org.bson.Document;
import org.basex.core.Context;
import org.basex.core.StaticOptions;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //1. inicilizar conexion
        ConexionDB conexion = new ConexionDB();
        conexion.conectar();

        //2. Crear DAOss pasando conexion
        ProductoDAO productoDAO = new ProductoDAO(conexion.getBaseXCtx());
        ClienteDAO clienteDAO = new ClienteDAO(conexion.getMongoDb());

        //3. Arrancamos el meenu
        Menu menu = new Menu(productoDAO,clienteDAO);
        menu.mostrarMenu();

        conexion.desconectar();
    }
}