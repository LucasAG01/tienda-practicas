package com.tienda.dao;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.XQuery;
import org.basex.core.cmd.Flush;

public class ProductoDAO {

    private Context ctx;

    public ProductoDAO(Context ctx) {
        this.ctx = ctx;
    }

    // --OPCIÓN 1: Modificar precio--
    public void modificarPrecio(String id, String nuevoPrecio){

    }

    public void eliminarProducto(String id){
    }

    public void listarProductosAlfabeticamente(){

    }





    // HELPERS

    private void ejecuatrConulta(String query){
        try{
            String resultado = new XQuery(query).execute(ctx);

            if(resultado.trim().isEmpty()){
                System.out.println("Nohay resultado");
            }else{
                System.out.println(resultado);
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta BaseX: " + e.getMessage());
        }
    }



    private void ejecutarActualizacion(String query, String mensajeExito){
        try{
            new XQuery(query).execute(ctx);
            new Flush().execute(ctx);
            System.out.println(mensajeExito);

        } catch (Exception e) {
            System.out.println("Error al actualizar en BaseX: " + e.getMessage());
        }
    }






}
