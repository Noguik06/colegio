/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesAyuda;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author juannoguera
 */
@FacesConverter(value = "converterCapitalLetter")
public class converterCapitalLetter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        String[] valor = value.split(" ");
        String resultado = "";
        for (int i = 0; i < valor.length; i++) {
            if (i + 1 == valor.length && valor[i].trim().length() == 0) {
                break;
            }

            if (valor[i].trim().length() > 0) {
                resultado += valor[i].substring(0, 1).toUpperCase();
                if (valor[i].length() > 1) {
                    resultado += valor[i].substring(1, valor[i].length()).toLowerCase();
                }

                resultado = resultado + " ";
            }

        }

        return resultado;
//        String apellidos = e.getUsuarios().getApellidos().toLowerCase().substring(0, 1).toUpperCase()
//                + //                    e.getUsuarios().getApellidos().substring(1,e.getUsuarios().getApellidos().length()).toLowerCase();
        //            
        //            
        //            String nombres = e.getUsuarios().getNombres() e.getUsuarios().getApellidos().toLowerCase().substring(0, 1).toUpperCase() + +
        //                    e.getUsuarios().getNombres().substring(1,e.getUsuarios().getNombres().length()).toLowerCase();

//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String[] valor = ((String) value).split(" ");
        String resultado = "";
        for (int i = 0; i < valor.length; i++) {
            if (i + 1 == valor.length && valor[i].trim().length() == 0) {
                break;
            }

            if (valor[i].trim().length() > 0) {
                resultado += valor[i].substring(0, 1).toUpperCase();
                if (valor[i].length() > 1) {
                    resultado += valor[i].substring(1, valor[i].length()).toLowerCase();
                }

                resultado = resultado + " ";
            }

//            if (i != 0) {
//                System.out.print("Ola");

//            }
        }

        return resultado;
//        return value.toString().toLowerCase();
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
