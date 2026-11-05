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

        // Preparar Texto
        String query = "replace value of node //producto[id='" + id + "']/precio with "+ nuevoPrecio;

        // Decimos al motor que ejectute
        ejecutarActualizacion(query, "precio modificado correctamente");

    }

    public void eliminarProducto(String id){
        String query = "delete node //producto[ id = '" + id + "']";

        ejecutarActualizacion(query, "Poroducto eliminado correctamente");
    }

    public void listarProductosAlfabeticamente(){

        // For (para cada), order by (ordenado por), return (devuelve)
        String query = "for $p in //producto order by $p/nombre  return concat($p/id, ' | ',$p/nombre, ' | ',$p/precio, ' | ',$p/disponibilidad,' | ',$p/categoria)";

        ejecutarConulta(query);
    }


    public void listarPorStock(int stockMinimo){
        //
        String query = "for $p in //producto where $p/disponibilidad > "+stockMinimo + " order by $p/nombre return concat($p/id,' | ',$p/nombre,' | ',$p/precio,' | ',$p/disponibilidad,' | ',$p/categoria)";

        ejecutarConulta(query);
    }



    public void productoMasCaroPorCategoria(){
        String query = "for $cat in distinct-values(//producto/categoria) let $prods :=//producto[categoria=$cat] let $maxP := max($prods/precio) let $top := $prods[precio=$maxP][1] return concat($cat,' | ',$top/nombre,' | ',$top/precio)";
        ejecutarConulta(query);
    }

    public void buscarPorDescripcion(String subcadena){
        String query= "for $p in //producto where contains($p/descripcion,'" + subcadena + "') order by $p/fabricante descending return concat($p/nombre, ' | ',$p/fabricante)";
        ejecutarConulta(query);

    }

    public void porcentajeStockPorCategoria(){
        String query = "let $total := sum(//producto/disponibilidad) for $cat in distinct-values(//producto/categoria) let $sub := sum(//producto[categoria=$cat]/disponibilidad) return concat($cat, ' | ', $sub, ' | ', format-number($sub div $total * 100, '0.00'), '%')";
        ejecutarConulta(query);
    }


    // HELPERS

    private void ejecutarConulta(String query){
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





    //METODOS PARA EL CARRITO
    public String obtenerProductoPorId(String id){
        String query  = "let $p := //producto[id='"+id+"'] "+"return if ($p) then concat($p/nombre,'|',$p/precio) else ''";
        try{
            return new XQuery(query).execute(ctx).trim();
        }catch (Exception e){
            return "";
        }
    }


}
