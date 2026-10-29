package com.tienda;

import com.tienda.dao.ProductoDAO;

import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private ProductoDAO productoDAO;

    public Menu(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
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
                        opcionEliminarPrecio();
                        break;

                    case 3:
                        break;
                    case 4:
                        System.out.println("aun no");
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

}

