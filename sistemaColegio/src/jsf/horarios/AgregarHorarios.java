/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.horarios;

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
import org.primefaces.context.RequestContext;

import com.sun.faces.facelets.component.UIRepeat;

import ejb.AnosacademicosFacade;
import ejb.BloquesFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.HorariosFacade;
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
import entities.Bloques;
import entities.Bloqueshorarios;
import entities.Cursos;
import entities.Dimensiones;
import entities.Estudiantes;
import entities.Horarios;
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
import entities.Relacionprofesoresasignaturaperiodo;
import entities.Usuarios;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class AgregarHorarios implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
    private CursosFacade cursosFacade;
	@EJB
    private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private BloquesFacade bloquesFacade;
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	@EJB
	private HorariosFacade horariosFacade;
    //##ANOSACADEMICOS
    //Anoacademico actual
    private Anosacademicos anosacademicosActual;
    //###CURSOS
    //Lista de los cursos
    private List<Cursos> dataListCursos;
    //Curso seleccionado
    private Cursos cursoSeleccionado;
    //Lista de las asignaturasPorCurso
    private List<Relacionasignaturaperiodos> dataListRelacionasignaturaperiodos;
    //##Bloques
    private List<Object[]> dataListBloquesHorariosCurso;
    //##MATERIAS
    private List<Relacionprofesoresasignaturaperiodo> dataListMaterias;
    
    
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of ConfiguracionNotas
     */
    public  AgregarHorarios() {
		// TODO Auto-generated constructor stub
	}

    public Anosacademicos getCurrentYear() {
        if (anosacademicosActual == null) {
            anosacademicosActual = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true").get(0);
        }

        return anosacademicosActual;
    }

    //###PROPIEDADES DE LA LISTA DE CURSOS
    public List<Cursos> getDataListCursos() {
        if (dataListCursos == null || dataListCursos.isEmpty()) {
            dataListCursos = cursosFacade.findByLikeAll("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = 'true' ORDER BY C.grados.numero ");
        }
        return dataListCursos;
    }
    
    //Metodo para escoger el curso
    public void escogerCurso(Cursos cursos){
    	cursoSeleccionado = cursos;
    	
    	String query = "select h.idhorarios, h.lunes, h.martes, h.miercoles, h.jueves, h.viernes, b.horainicio, b.horafin, b.idbloqueshorarios, c.idcursos "
    			+ "from bloqueshorarios b  "
    			+ "left join cursos c on c.idbloques = b.bloques "
    			+ "left join horarios h on h.idbloqueshorarios = b.idbloqueshorarios and h.idcursos = c.idcursos "
    			+ "where c.idcursos  = " + cursos.getIdcursos() + " "
    			+ "order by horainicio";
    	
    	dataListBloquesHorariosCurso = bloquesFacade.findByNative(query);
    	String queryMaterias =  "SELECT R FROM Relacionprofesoresasignaturaperiodo R WHERE R.cursos.idcursos = " + cursos.getIdcursos();
    	dataListMaterias  = relacionprofesoresasignaturaperiodoFacade.findByLikeAll(queryMaterias);    	
    }

    //Propiedades de los horarios
	public List<Object[]> getDataListBloquesHorariosCurso() {
		return dataListBloquesHorariosCurso;
	}

	public void setDataListBloquesHorariosCurso(
			List<Object[]> dataListBloquesHorariosCurso) {
		this.dataListBloquesHorariosCurso = dataListBloquesHorariosCurso;
	}
	
	//Propiedades de las materias
	public List<Relacionprofesoresasignaturaperiodo> getDataListMaterias() {
		return dataListMaterias;
	}

	public void setDataListMaterias(
			List<Relacionprofesoresasignaturaperiodo> dataListMaterias) {
		this.dataListMaterias = dataListMaterias;
	}
	
	//Metodo encargado de guardar los horarios
	public void guardarHorarios(AjaxBehaviorEvent event){
		FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        DataTable htd = (DataTable) root.findComponent("formPrincipal").findComponent("tblAsignaturas");
        Object[] object = (Object[]) htd.getRowData();
        String query = "SELECT H FROM Horarios H WHERE H.cursos.idcursos = " + object[9] + " "
    			+ "AND H.bloqueshorarios.idbloqueshorarios = " + object[8];
    	List<Horarios> dataLisTmp = horariosFacade.findByLike(query);
    	Horarios horarios = new Horarios();
    	if(dataLisTmp.isEmpty()){
    		horarios.setIdhorarios(new Long(0));
    		Long id  = new Long(object[9].toString());
    		horarios.setCursos(new Cursos(new Long(id)));
    		
    		Long idBloquesHorarios = new Long(object[8].toString());
    		Bloqueshorarios bloqueshorarios = new Bloqueshorarios(idBloquesHorarios);
    		horarios.setBloqueshorarios(bloqueshorarios);
    		
    		horariosFacade.create(horarios);
    	}else{
    		horarios = dataLisTmp.get(0); 
    	}
        
    	if(object[1]!=null){
    		Long idlunes = new Long(object[1].toString());
    		Relacionprofesoresasignaturaperiodo lunes  =  new Relacionprofesoresasignaturaperiodo(idlunes);
    		horarios.setLunes(lunes);
		}else{
			horarios.setLunes(null);
		}
		if(object[2]!=null){
			Long idMartes = new Long(object[2].toString());
    		Relacionprofesoresasignaturaperiodo martes  =  new Relacionprofesoresasignaturaperiodo(idMartes);
    		horarios.setMartes(martes);
		}else{
			horarios.setMartes(null);
		}
			
		
		if(object[3]!=null){
    		Long idMiercoles = new Long(object[3].toString());
    		Relacionprofesoresasignaturaperiodo miercoles  =  new Relacionprofesoresasignaturaperiodo(idMiercoles);
    		horarios.setMiercoles(miercoles);
		}else{
			horarios.setMiercoles(null);
		}

		if(object[4]!=null){
			Long idJueves = new Long(object[4].toString());
    		Relacionprofesoresasignaturaperiodo jueves  =  new Relacionprofesoresasignaturaperiodo(idJueves);
    		horarios.setJueves(jueves);
		}else{
			horarios.setJueves(null);
		}
		
		if(object[5]!=null){
    		Long idViernes = new Long(object[5].toString());
    		Relacionprofesoresasignaturaperiodo viernes  =  new Relacionprofesoresasignaturaperiodo(idViernes);
    		horarios.setViernes(viernes);
		}else{
			horarios.setViernes(null);	
		}
		
		horariosFacade.edit(horarios);
		
	}
	
}
