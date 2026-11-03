package com.tienda;

import com.tienda.dao.ClienteDAO;
import com.tienda.dao.ProductoDAO;

import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;

    public Menu(ProductoDAO productoDAO, ClienteDAO clienteDAO) {
        this.productoDAO = productoDAO;
        this.clienteDAO = clienteDAO;
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

                        break;
                    case 10:

                        break;
                    case 11:
                        break;

                    case 12:

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
}

