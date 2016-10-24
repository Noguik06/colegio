package clasesAyuda;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import ejb.EstudiantesFacade;
import ejb.ProfesoresFacade;
import entities.Usuarios;

public class Utility{

	public static boolean validarEstado(Usuarios usuarios) {
		
		Utility utility = new Utility();
		
//		if (!utility.profesoresFacade.findByLike("SELECT P FROM Profesores P WHERE P.usuarios.idusuarios = " + usuarios.getIdusuarios())
//				.isEmpty()) {
//		}
		return true;
	}
	
	public static String mes(int month){
		String result = "";
		switch(month){
		  case 0:
		    {
		      result="ENERO";
		      break;
		    }
		  case 1:
		    {
		      result="FEBRERO";
		      break;
		    }
		  case 2:
		    {
		      result="MARZO";
		      break;
		    }
		  case 3:
		    {
		      result="ABRIL";
		      break;
		    }
		  case 4:
		    {
		      result="MAYO";
		      break;
		    }
		  case 5:
		    {
		      result="JUNIO";
		      break;
		    }
		  case 6:
		    {
		      result="JULIO";
		      break;
		    }
		  case 7:
		    {
		      result="AGOSTO";
		      break;
		    }
		  case 8:
		    {
		      result="SEPTIEMBRE";
		      break;
		    }
		  case 9:
		    {
		      result="OCTUBRE";
		      break;
		    }
		  case 10:
		    {
		      result="NOVIEMBRE";
		      break;
		    }
		  case 11:
		    {
		      result="DICIEMBRE";
		      break;
		    }
		  default:
		    {
		      result="Error";
		      break;
		    }
		}
		return result;
	}
}
