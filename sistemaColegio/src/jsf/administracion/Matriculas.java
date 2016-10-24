/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.ShipmentIdentificationNumberProvider;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import com.sun.faces.facelets.component.UIRepeat;

import ejb.AnosacademicosFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.EstudiantesFacade;
import ejb.GradosFacade;
import ejb.LogrosFacade;
import ejb.NotascalificablesFacade;
import ejb.PeriodosFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionlogrosdimensionesFacade;
import ejb.RelacionlogrosnotasdimensionFacade;
import ejb.RelacionnotasdimensionFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Dimensiones;
import entities.Estudiantes;
import entities.Grados;
import entities.Logros;
import entities.Notascalificables;
import entities.Periodos;
import entities.Registromatriculas;
import entities.Relacionasignaturaperiodos;
import entities.Relaciondimensionesasignaturasano;
import entities.Relacionlogrosdimensiones;
import entities.Relacionlogrosnotasdimension;
import entities.Relacionnotasdimension;
import entities.Relacionnotaslogrosdimensionboletin;
import entities.Usuarios;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class Matriculas implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
    private CursosFacade cursosFacade;
	@EJB
    private GradosFacade gradosFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private EstudiantesFacade estudiantesFacade;
	
    //##ANOSACADEMICOS
    //Anoacademico actual
    private Anosacademicos anosacademicosActual;
    //###CURSOS
    //Lista de los cursos
    private List<Cursos> dataListCursos;
    
    //Lista de los cursos
    private List<Grados> dataListGrados;
    
    //Curso seleccionado
    private Cursos cursoSeleccionado;
    //###ESTUDIANTES
    private List<Registromatriculas> dataListRegistroMatriculas;
    
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of ConfiguracionNotas
     */
    public Matriculas() {
    }

    public Anosacademicos getCurrentYear() {
        if (anosacademicosActual == null) {
            anosacademicosActual = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true").get(0);
        }

        return anosacademicosActual;
    }
    

    public List<Grados> getDataListGrados() {
    	if(dataListGrados == null){
    		dataListGrados = gradosFacade.findByLikeAll("SELECT G FROM Grados G ORDER BY g.numero");
    	}
		return dataListGrados;
	}

	public void setDataListGrados(List<Grados> dataListGrados) {
		this.dataListGrados = dataListGrados;
	}

	//###PROPIEDADES DE LA LISTA DE CURSOS
    public List<Cursos> getDataListCursos() {
        if (dataListCursos == null || dataListCursos.isEmpty()) {
            dataListCursos = cursosFacade.findByLikeAll("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = 'true' ORDER BY C.grados.numero ");
        }
        return dataListCursos;
    }

    public void setDataListCursos(List<Cursos> dataListCursos) {
        this.dataListCursos = dataListCursos;
    }

    //Metodo para saber cuál es el curso seleccionado
    public boolean cursoSeleccionado(Cursos cursos) {
        if (cursoSeleccionado != null && cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
            return true;
        }
        return false;
    }

    //Metodo para colocar el grado
    public void escogerCurso(Cursos cursos) {
        this.cursoSeleccionado = cursos;
        String query = "SELECT R FROM Registromatriculas R WHERE R.grados.idgrados = " + cursos.getGrados().getIdgrados() + " AND R.fecharetiro is null"
        		+ " ORDER BY R.estudiantes.usuarios.apellidos";
        dataListRegistroMatriculas = registromatriculasFacade.findByLikeAll(query);
        for(Registromatriculas r:dataListRegistroMatriculas){
        	r.setGrados(r.getGrados().getProximo());
        }
        
    }

    //Propiedades de la lista que tiene los registromatriculas
	public List<Registromatriculas> getDataListRegistroMatriculas() {
		return dataListRegistroMatriculas;
	}

	public void setDataListRegistroMatriculas(
			List<Registromatriculas> dataListRegistroMatriculas) {
		this.dataListRegistroMatriculas = dataListRegistroMatriculas;
	}
	
	public void escogerFacturaAlbaran(AjaxBehaviorEvent event){
		UIData data = (UIData) event.getComponent().getParent().getParent();
		Registromatriculas reg = (Registromatriculas) data.getRowData();
		Grados grados_1 = gradosFacade.find(((SelectOneMenu) event.getSource()).getValue());
		
//		
//		Grados grados_2 = gradosFacade.find(reg.getGrados().getIdgrados());
//		grados_2.setProximo(grados_1);
//		
		
		dataListRegistroMatriculas.get(data.getRowIndex()).setGrados(grados_1);
//		RequestContext.getCurrentInstance().update("formPrincipal:tblEstudiantes");
		System.out.println();
	}
	
	public void escogerEstadoActivo(AjaxBehaviorEvent event){
		boolean valor = (Boolean) ((SelectBooleanCheckbox) event.getSource()).getValue();
		UIData data = (UIData) event.getComponent().getParent().getParent();
		dataListRegistroMatriculas.get(data.getRowIndex()).getEstudiantes().getUsuarios().setEstado_activo(valor);
	}
	
	//metodo para guardar la edicion de registromatriculas
	public void guardarRegistroMatriculas(){
		try{
			userTransaction.begin();
		}catch(Exception e){
			
		}
		
		for (Registromatriculas r : dataListRegistroMatriculas) {
			List<Cursos> tmp = cursosFacade
					.findByLikeAll("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = 'true' and C.grados.idgrados = "
							+ r.getGrados().getIdgrados());
			if (!tmp.isEmpty()) {
				Registromatriculas regtmp = registromatriculasFacade.find(r.getIdregistromatriculas());
				r.setFecharetiro(new Date());
				registromatriculasFacade.edit(r);
				if (r.getEstudiantes().getUsuarios().isEstado_activo()) {
					Cursos c = cursosFacade.find(r.getCursos().getIdcursos());
					// Colocamos al estudiante en el proximo grado que haya
					// escogido
					r.getEstudiantes().setGrados(tmp.get(0).getGrados());
					estudiantesFacade.edit(r.getEstudiantes());
					// Creamos el registromatricula
					Registromatriculas registromatriculas = new Registromatriculas(
							new Long(0));
					registromatriculas.setFechamatricula(new Date());
					registromatriculas.setCursos(tmp.get(0));
					registromatriculas.setGrados(tmp.get(0).getGrados());
					registromatriculas.setEstudiantes(r.getEstudiantes());
					registromatriculas.setAnosacademicos(getCurrentYear());
					registromatriculasFacade.create(registromatriculas);
				}
			}
		}
		try{
			userTransaction.commit();
			
			String query = "SELECT R FROM Registromatriculas R WHERE R.grados.idgrados = " + cursoSeleccionado.getGrados().getIdgrados() + " AND R.fecharetiro is null"
	        		+ " ORDER BY R.estudiantes.usuarios.apellidos";
	        dataListRegistroMatriculas = registromatriculasFacade.findByLikeAll(query);
	        
	        RequestContext.getCurrentInstance().update("formPrincipal:tblEstudiantes");
		}catch(Exception e){
			
		}
	}
}