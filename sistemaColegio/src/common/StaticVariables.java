package common;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ejb.CursosFacade;
import entities.Cursos;
@ManagedBean
@ApplicationScoped
public class StaticVariables {
	@EJB
	private CursosFacade cursosFacade;
	
	//Lista total de los cursos
	public static List<Cursos> dataListCursosTotales;
	
	//Métodos de los cursos
	public  List<Cursos> getDataListCursosTotatales(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if(dataListCursosTotales == null){
//			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			System.out.print("olitas");
			dataListCursosTotales = cursosFacade.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R "
	                    + " WHERE R.cursos.anosacademicos.estadoactivo = true"
	                    + " ORDER BY R.cursos.grados.numero");
		}
		return dataListCursosTotales;
	}

	public void setDataListCursosTotatales(List<Cursos> dataListCursosTotales){
		this.dataListCursosTotales = dataListCursosTotales;;
	}
}
