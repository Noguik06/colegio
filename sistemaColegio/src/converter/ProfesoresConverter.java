package converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import entities.Profesores;


@FacesConverter("profesoresConverter")
public class ProfesoresConverter implements Converter {

   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
           try {
//               ProveedoresService service = (ProveedoresService) fc.getExternalContext().getApplicationMap().get("proveedoresService");
               return new Profesores(new Long(value));
           } catch(NumberFormatException e) {
               return null;
           }
       }
       else {
           return null;
       }
   }

   public String getAsString(FacesContext fc, UIComponent uic, Object object) {
       if(object != null) {
           return String.valueOf(((Profesores) object).getIdprofesores());
       }
       else {
           return null;
       }
   }   
}     