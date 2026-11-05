package com.tienda;

import com.tienda.dao.CarritoDAO;
import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ProductoDAO;

import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private CarritoDAO carritoDAO;


    //Giardar al cliente logueado
    private org.bson.types.ObjectId clienteActualId = null;

    public Menu(ProductoDAO productoDAO, ClienteDAO clienteDAO, CarritoDAO carritoDAO) {
        this.productoDAO = productoDAO;
        this.clienteDAO = clienteDAO;
        this.carritoDAO = carritoDAO;
        this.sc = new Scanner(System.in);
    }


    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n===========================================");
            System.out.println("   SISTEMA DE COMERCIO ELECTRÓNICO");
            System.out.println("===========================================");
            System.out.println("1. Modificar precio de un producto (BaseX)");
            System.out.println("2. Eliminar un producto por ID (BaseX)");
            System.out.println("3. Listar productos alfabéticamente (BaseX)");
            System.out.println("4. Listar productos con stock > X (BaseX)");
            System.out.println("5. Producto más caro por categoría (BaseX)");
            System.out.println("6. Buscar por descripción (BaseX)");
            System.out.println("7. Porcentaje de stock por categoría (BaseX)");
            // Aquí irán las opciones de MongoDB (8 al 17)
            System.out.println("--- CONSULTAS MONGODB ---");
            System.out.println("8. Crear nuevo cliente");
            System.out.println("9. Iniciar sesion");
            System.out.println("10. Eliminar cliente");
            System.out.println("11. Modificar Campo");
            System.out.println("12. Añadir producto al carrito");
            System.out.println("13. Modificar Campo");
            System.out.println("14. Modificar Campo");
            System.out.println("0. Salir");
            System.out.println("===========================================");
            System.out.print("Seleccione una opción: ");


            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        opcionMOdificarPrecio();
                        break;

                    case 2:
                        opcionEliminarProducto();
                        break;

                    case 3:
                        productoDAO.listarProductosAlfabeticamente();
                        break;
                    case 4:
                        opcionOrdenarPorStock();
                        break;

                    case 5:
                        productoDAO.productoMasCaroPorCategoria();
                        break;

                    case 6:
                        opcionBuscarPorDescripcion();
                        break;
                    case 7:
                        productoDAO.porcentajeStockPorCategoria();
                        break;
                    case 8:
                        opcionCrearCliente();
                        break;
                    case 9:
                        opcionIdentificarCliente();
                        break;
                    case 10:
                        if(clienteActualId == null){
                            System.out.println("Debes loguearte primero");
                        }else {
                            clienteDAO.eliminarCliente(clienteActualId);
                            clienteActualId = null;
                        }
                        break;
                    case 11:
                        if(clienteActualId == null){
                            System.out.println("Debes loguearte primero");
                        }else {
                            opcionModificarCliente();
                        }

                        break;

                    case 12:
                        if(clienteActualId == null){
                            System.out.println("Debes loguearte primero");
                        }else {

                        }
                        break;
                    case 13:

                        break;
                    case 14:

                        break;
                    case 15:

                        break;
                    case 16:

                        break;
                    case 17:

                        break;
                    case 0:
                        System.out.println("SAliendo");
                        break;
                    default:
                        System.out.println("Opcion no valida");

                }
            } catch (NumberFormatException e) {
                System.err.println("Debe introducir un numero");
            }

        } while (opcion != 0);
    }


    private void opcionMOdificarPrecio() {
        System.out.print("Introduce el ID del producto: ");
        String id = sc.nextLine();
        System.out.print("Introduce el nuevo precio: ");
        String precio = sc.nextLine();

        productoDAO.modificarPrecio(id, precio);

    }

    private void opcionEliminarProducto() {
        System.out.print("Introduce el ID del producto: ");
        String id = sc.nextLine();

        productoDAO.eliminarProducto(id);

    }

    private void opcionOrdenarPorStock(){
        try {
            System.out.println("Introduce stock minimo:");
            int stockMinimo = Integer.parseInt(sc.nextLine());

            productoDAO.listarPorStock(stockMinimo);
        }catch (NumberFormatException e){
            System.err.println("debes introducir un número");
        }
    }

    private void opcionBuscarPorDescripcion(){

        System.out.println("Introduce una subcadeena");
        String subcadena = sc.nextLine();
        productoDAO.buscarPorDescripcion(subcadena);

    }


    private void opcionCrearCliente(){
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        //lamada DAO
        clienteDAO.crearCliente(nombre,email,telefono,direccion);
    }


    private void opcionIdentificarCliente(){
        System.out.println("Introduce email del cliente");
        String email = sc.nextLine();

        //Llamamso al dao
        org.bson.types.ObjectId idEncontrado = clienteDAO.identiifcarCliente(email);

        if(idEncontrado !=null){
            clienteActualId = idEncontrado;

            System.out.println("Cliente identificado correctamente, sesion iniciada");
        }else{
            System.out.println("Error, no hay cliente con ese mail");
        }
    }

    private void opcionModificarCliente(){

        System.out.println("¿Qué campo quieres modificar? (nombre/telefono/direccion): ");
        String campo = sc.nextLine();
        System.out.println("Intriduce nuevo valor");
        String valor = sc.nextLine();

        clienteDAO.modificarCampoCliente(clienteActualId, campo, valor);

    }


    private void opcionAgregarAlCarrito(){
        boolean seguir = true;
        while (seguir){
            System.out.println("Introduce el ID de BASEX");
            String idStr = sc.nextLine();

            // 1. Preguntamos a BAseX
            String datosProducto = productoDAO.obtenerProductoPorId(idStr);

            if(datosProducto.isEmpty()){
                System.out.println("Error Producto no encontrado");
            }else {
                // 2.- Separamos nombre y precio
                String[] partes = datosProducto.split("\\|");
                String nombre = partes[0];
                double precio = Double.parseDouble(partes[1]);

                System.out.println("Introduce cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());

                // 3.- Guardamos en MOngo
                carritoDAO.agregarProducto(clienteActualId,Integer.parseInt(idStr),nombre,precio,cantidad);
                System.out.println("Producto añadido al carrito");
            }

            System.out.print("¿Quieres añadir otro producto? (s/n): ");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("n")) {
                seguir = false;
            }
        }
    }

}

