/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter for Google Key.
 *
 * @author Joel.Weight
 */
@FacesConverter(value = "minusculasjava")
public class Convertermayuminus implements Converter {

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

        }
        return resultado;
    }
}
