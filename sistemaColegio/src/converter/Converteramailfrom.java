package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "mailfrom")
public class Converteramailfrom implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return " pruebota ";
	}

	//Metodo del get
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		if(arg2==null || arg2.toString().length() == 0){
			return  "Sin Asunto";
		}
		if(arg2!=null && arg2.toString().length() > 30){
			return arg2.toString().substring(0, 30) + "...";
		}
		
		return arg2.toString();
		
	}

}
