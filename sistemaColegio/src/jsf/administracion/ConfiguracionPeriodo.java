/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.administracion;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;

import service.ProfesoresService;
import ejb.AnosacademicosFacade;
import ejb.AsignaturasFacade;
import ejb.AsignaturasgrupalesFacade;
import ejb.BloquesFacade;
import ejb.CursosFacade;
import ejb.GradosFacade;
import ejb.PeriodosFacade;
import ejb.ProfesoresFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import entities.Anosacademicos;
import entities.Asignaturas;
import entities.Asignaturasgrupales;
import entities.Bloques;
import entities.Cursos;
import entities.Grados;
import entities.Periodos;
import entities.Profesores;
import entities.Relacionasignaturaperiodos;
import entities.Relacionprofesoresasignaturaperiodo;

/**
 *
 * @author ismael
 */
@ManagedBean(name = "configuracionPeriodo")
@ViewScoped
public class ConfiguracionPeriodo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
     * Creates a new instance of configuracionPeriodo
     */
    public ConfiguracionPeriodo() {
    }
    @EJB
    private AnosacademicosFacade anosacademicosFacade;
    @EJB
    private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
    @EJB
    private AsignaturasFacade asignaturasFacade;
    @EJB
    private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
    @EJB
    private CursosFacade cursosFacade;
    @EJB
    private ProfesoresFacade profesoresFacade;
    @EJB
    private GradosFacade gradosFacade;
    @EJB
    private PeriodosFacade periodosFacade;
    @EJB
    private AsignaturasgrupalesFacade asignaturasgrupalesFacade;
    
    //###ANOSACADEMICOS
    //Lista de anosacademicos
    private List<Anosacademicos> dataListAnosAcademicos;
    //Anoacademico seleccionaddo para editar o para agregar
    private Anosacademicos anosacademicoEditarSeleccionado;
    //Anoacademico seleccionaddo para editar o para agregar
    private Anosacademicos anosacademicoSeleccionado;
    
    //###PERIODOS
    //Lista de periodos disponibles
    private List<Periodos> dataListPeriodos;
    //Anoacademico seleccionaddo para editar o para agregar
    private Periodos periodoEditarSeleccionado;
    //Anoacademico seleccionaddo para editar o para agregar
    private Periodos periodoSeleccionado;
    
    //###Grados
    //Lista de los cursos disponibles
    private List<Grados> dataListGrados;
    //Curso seleccionado para editar
    private Cursos cursoSeleccionadoEditar;
    //Cursos seleccionado para el resto
    private Cursos cursoSeleccionado;
    
    
    //###Asignaturas
    //Lista de las asignaturas por curso
    private List<Object[]> dataListAsignaturas;
    //Asignatura seleccionada para editar
    private Relacionprofesoresasignaturaperiodo relacionprofesoresasignaturaperiodoEditar;
    //Cursos seleccionado para el resto
    private Asignaturas asignaturaSeleccionada;
    //Indice de la asignatura seleccionada
    private int indexAsignaturaSeleccionada = -1;
    //Asignaturas para agrupar
    private List<Object[]> dataListAsignaturasGrupales;
    
    
    
    //Propiedades para obtener los anosacademicos
    public List<Anosacademicos> getDataListAnosAcademicos() {
    	if(dataListAnosAcademicos == null){
    		dataListAnosAcademicos = anosacademicosFacade.findByLike("SELECT A FROM Anosacademicos A ORDER BY A.fechainicio");
    	}
		return dataListAnosAcademicos;
	}

	public void setDataListAnosAcademicos(
			List<Anosacademicos> dataListAnosAcademicos) {
		this.dataListAnosAcademicos = dataListAnosAcademicos;
	}

	//Metodo para verificar si es el ano academico seleccionado
	public boolean isSelectetAnoAcademico(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaAnosAcademicos = (UIData) root
				.findComponent("formPrincipal").findComponent(
						"tablaAnosAcademicos");
		Anosacademicos anosacademicos = (Anosacademicos) tablaAnosAcademicos.getRowData();
		anosacademicos = anosacademicosFacade.find(anosacademicos.getIdanosacademicos());
		return anosacademicos.getEstadoactivo();
	}
	
	public void setSelectetAnoAcademico(boolean bandera){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaAnosAcademicos = (UIData) root
				.findComponent("formPrincipal").findComponent(
						"tablaAnosAcademicos");
		Anosacademicos anosacademicos = (Anosacademicos) tablaAnosAcademicos.getRowData();
		String query = "UPDATE Anosacademicos A SET A.estadoactivo = false";
		anosacademicosFacade.metodo(query);
		query = "UPDATE Anosacademicos A SET A.estadoactivo = true WHERE A.idanosacademicos = " + anosacademicos.getIdanosacademicos();
		anosacademicosFacade.metodo(query);
		dataListAnosAcademicos = null;
		RequestContext.getCurrentInstance().update("formPrincipa:tablaAnosAcademicos");
	}
	
	//Metodo para seleccinoar un anoacademico
	public void seleccionarAnoAcademicoEditar(Anosacademicos anosacademicos){
		if(anosacademicos == null){
			anosacademicoEditarSeleccionado = new Anosacademicos(new Long(0));
		}
	}
	
	//Propiedades de los anosacademicos
	public Anosacademicos getAnosacademicoEditarSeleccionado() {
		return anosacademicoEditarSeleccionado;
	}

	public void setAnosacademicoEditarSeleccionado(
			Anosacademicos anosacademicoEditarSeleccionado) {
		this.anosacademicoEditarSeleccionado = anosacademicoEditarSeleccionado;
	}
	
	//Metodo para guardar la edición o creación de un anoacademico
	public void guardarAnoAcademicoSeleccionado(){
		if(anosacademicoEditarSeleccionado != null){
			if(anosacademicoEditarSeleccionado.getIdanosacademicos() > 0){
				anosacademicosFacade.edit(anosacademicoEditarSeleccionado);
			}else{
				anosacademicosFacade.create(anosacademicoEditarSeleccionado);
				dataListAnosAcademicos = null;
			}
		}
	}
	//Metodo para seleccionar un anoacademico para mostrar los periodos de este 
	public void seleccionarAnoAcademico(Anosacademicos anosacademicos){
		anosacademicoSeleccionado = anosacademicos;
		dataListPeriodos = null;		
	}
	
	//###Periodos
	//Propiedades de la lista de periodos segun el anoacademico seleccionado
	public List<Periodos> getDataListPeriodos() {
		if(anosacademicoSeleccionado == null){
			return null;
		}
		if(dataListPeriodos == null){
			dataListPeriodos = periodosFacade.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos()
					+ " ORDER BY P.fechafin");
		}
		return dataListPeriodos;
	}

	public void setDataListPeriodos(List<Periodos> dataListPeriodos) {
		this.dataListPeriodos = dataListPeriodos;
	}
	
	//Seleccionar un anoacademico para editar o para crear
	public void seleccionarPeriodoAcademicoEditar(Periodos periodo){
		if(periodo == null){
			periodoEditarSeleccionado = new Periodos(new Long(0));
			periodoEditarSeleccionado.setAnoacademicos(anosacademicoSeleccionado);
		}else{
			periodoEditarSeleccionado = periodo;
		}
	}
	
	public Periodos getPeriodoEditarSeleccionado() {
		return periodoEditarSeleccionado;
	}

	public void setPeriodoEditarSeleccionado(Periodos periodoEditarSeleccionado) {
		this.periodoEditarSeleccionado = periodoEditarSeleccionado;
	}
	
	//Metodo para guardar un periodo
	public void guardarPeriodoSeleccionado(){
		if(periodoEditarSeleccionado!=null){
			if(periodoEditarSeleccionado.getFechafin() != null
				&& periodoEditarSeleccionado.getFechainicio() != null
				&& periodoEditarSeleccionado.getNombre()!= null
				&& periodoEditarSeleccionado.getNombre().trim().length() > 0
				&& (periodoEditarSeleccionado.getTipo() == 1 || periodoEditarSeleccionado.getTipo() == 0)){
				if(periodoEditarSeleccionado.getIdperiodos() == 0){
					periodoEditarSeleccionado.setAnoacademicos(anosacademicoSeleccionado);
					periodoEditarSeleccionado.setIdperiodos(new Long(0));
					periodoEditarSeleccionado.setFechanotas(new Date());
					periodosFacade.create(periodoEditarSeleccionado);
				}else{
					periodosFacade.edit(periodoEditarSeleccionado);
				}
				dataListPeriodos = null;
				
			}else{
				
			}
		}
	}

	//###GRADOS
	public List<Grados> getDataListGrados() {
		if(anosacademicoSeleccionado == null){
			return null;
		}
		if(dataListGrados == null){
			String query = "SELECT G FROM Grados G order by G.numero";
			dataListGrados = gradosFacade.findByLikeAll(query);
		}
		return dataListGrados;
	}

	public void setDataListGrados(List<Grados> dataListGrados) {
		this.dataListGrados = dataListGrados;
	}
	
	//Metodo para seleccionar el curso que vamos a crear
	public void seleccionarEditarGrados(Grados grados){
		if(grados == null){
			
		}else{
			List<Cursos> dataListCursos = cursosFacade.findByLikeAll("SELECT C FROM Cursos C "
					+ "WHERE C.anosacademicos.idanosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos() + " "
					+ "AND C.grados.idgrados = " + grados.getIdgrados());
			if(dataListCursos == null || dataListCursos.isEmpty()){
				cursoSeleccionadoEditar = new Cursos(new Long(0));
				cursoSeleccionadoEditar.setAnosacademicos(anosacademicoSeleccionado);
				cursoSeleccionadoEditar.setGrados(grados);
				cursoSeleccionadoEditar.setNombre("A");
				if(cursoSeleccionadoEditar.getBloques() == null){
					Bloques bloques = new Bloques(new Long(0));
					cursoSeleccionadoEditar.setBloques(bloques);
				}
			}else{
				cursoSeleccionadoEditar = dataListCursos.get(0);
				if(cursoSeleccionadoEditar.getBloques() == null){
					Bloques bloques = new Bloques(new Long(0));
					cursoSeleccionadoEditar.setBloques(bloques);
				}
			}
		}
	}
	
	//Propiedas del curso a crear
	public Cursos getCursoSeleccionadoEditar() {
		return cursoSeleccionadoEditar;
	}

	public void setCursoSeleccionadoEditar(Cursos cursoSeleccionadoEditar) {
		this.cursoSeleccionadoEditar = cursoSeleccionadoEditar;
	}
	
	// Metodo para seleccionar un curso y agregarle materias
	public void seleccionarGrado(Grados grados) {
		List<Cursos> dataListCursos = cursosFacade
				.findByLikeAll("SELECT C FROM Cursos C "
						+ "WHERE C.anosacademicos.idanosacademicos = "
						+ anosacademicoSeleccionado.getIdanosacademicos() + " "
						+ "AND C.grados.idgrados = " + grados.getIdgrados()
						+ " ");
		dataListAsignaturas = null;
		
		if (dataListCursos != null && !dataListCursos.isEmpty()) {
			cursoSeleccionado = dataListCursos.get(0);
		}else{
//			dataListAsignaturas = new ArrayList<Object[]>();
		}
	}	
	
	// Empleado service
	@ManagedProperty("#{profesoresService}")
	private ProfesoresService serviceEmpleados;
	
	private Profesores them1Profesores;

	public Profesores getTheme1Profesores() {
		return them1Profesores;
	}

	public void setTheme1Profesores(Profesores theme1Empleados) {
		this.them1Profesores = theme1Empleados;
	}

	public List<Profesores> completeThemeProfesores(String query) {
		List<Profesores> allThemes = serviceEmpleados.getThemes();
		List<Profesores> filteredThemes = new ArrayList<Profesores>();
		for (int i = 0; i < allThemes.size(); i++) {
			Profesores skin = allThemes.get(i);
			if (skin.getUsuarios().getNombres().toLowerCase().contains(query.toLowerCase())
				|| skin.getUsuarios().getApellidos().toLowerCase().contains(query.toLowerCase())) {
				filteredThemes.add(skin);
			}
		}
		return filteredThemes;
	}

	public ProfesoresService getServiceEmpleados() {
		return serviceEmpleados;
	}

	public void setServiceEmpleados(ProfesoresService serviceEmpleados) {
		this.serviceEmpleados = serviceEmpleados;
	}
	
	//Metodo encargado de guardar la edicion de un curso
	public void guardarEdicionCurso(){
		if(cursoSeleccionadoEditar != null){
			if(cursoSeleccionadoEditar.getIdcursos() == 0){
				Profesores p = profesoresFacade.find(cursoSeleccionadoEditar.getProfesor().getIdprofesores());
				Bloques bloques = bloquesFacade.find(cursoSeleccionadoEditar.getBloques().getIdbloques());
				cursoSeleccionadoEditar.setBloques(bloques);
				cursoSeleccionadoEditar.setProfesor(p);
				cursosFacade.create(cursoSeleccionadoEditar);
			}else{
				Profesores p = profesoresFacade.find(cursoSeleccionadoEditar.getProfesor().getIdprofesores());
				Bloques bloques = bloquesFacade.find(cursoSeleccionadoEditar.getBloques().getIdbloques());
				cursoSeleccionadoEditar.setBloques(bloques);
				cursoSeleccionadoEditar.setProfesor(p);
				cursosFacade.edit(cursoSeleccionadoEditar);
			}
		}
	}
	
	//###ASIGNATURAS
	//Metodo para seleccionar una asginatura  y editarla
	public void seleccionarAsignataturasEditar(Long idasignaturas){
		if(idasignaturas != null){
			String query = "SELECT R FROM Relacionasignaturaperiodos R WHERE R.asignaturas.idasignaturas = " + idasignaturas + " "
					+ "AND R.anosacademicos.idanosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos() ;
			List<Relacionasignaturaperiodos> dataListRAP = relacionasignaturaperiodosFacade.findByLikeAll(query);
			if(dataListRAP == null || dataListRAP.isEmpty()){
				Relacionasignaturaperiodos relacionasignaturaperiodos =  new Relacionasignaturaperiodos(new Long(0));
				relacionasignaturaperiodos.setAnosacademicos(anosacademicoSeleccionado);
				relacionasignaturaperiodos.setGrados(cursoSeleccionado.getGrados());
				Asignaturas asignaturas = asignaturasFacade.find(idasignaturas);
				relacionasignaturaperiodos.setAsignaturas(asignaturas);
				relacionasignaturaperiodosFacade.create(relacionasignaturaperiodos);
				
				relacionprofesoresasignaturaperiodoEditar = new Relacionprofesoresasignaturaperiodo(new Long(0));
				relacionprofesoresasignaturaperiodoEditar.setCursos(cursoSeleccionado);
				relacionprofesoresasignaturaperiodoEditar.setRelacionasignaturaperiodos(relacionasignaturaperiodos);
			}else{
				query = "SELECT R FROM Relacionprofesoresasignaturaperiodo R WHERE R.cursos.idcursos =" + cursoSeleccionado.getIdcursos() + " "
						+"AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + dataListRAP.get(0).getIdrelacionasignaturaperiodos();
				List<Relacionprofesoresasignaturaperiodo> dataListTmp = relacionprofesoresasignaturaperiodoFacade.findByLikeAll(query);
				if(dataListTmp == null || dataListTmp.isEmpty()){
					relacionprofesoresasignaturaperiodoEditar = new Relacionprofesoresasignaturaperiodo(new Long(0));relacionprofesoresasignaturaperiodoEditar = new Relacionprofesoresasignaturaperiodo(new Long(0));
					relacionprofesoresasignaturaperiodoEditar.setCursos(cursoSeleccionado);
					relacionprofesoresasignaturaperiodoEditar.setRelacionasignaturaperiodos(dataListRAP.get(0));
					relacionprofesoresasignaturaperiodoEditar.setProfesores(null);
					relacionprofesoresasignaturaperiodoEditar.setCantidadhorasporsemana(0);
					
				}else{
					relacionprofesoresasignaturaperiodoEditar = dataListTmp.get(0);
				}
			}
		}else{
			relacionprofesoresasignaturaperiodoEditar = new Relacionprofesoresasignaturaperiodo();
			relacionprofesoresasignaturaperiodoEditar = new Relacionprofesoresasignaturaperiodo(new Long(0));
			relacionprofesoresasignaturaperiodoEditar.setCursos(cursoSeleccionado);
			Relacionasignaturaperiodos relacionasignaturaperiodos =  new Relacionasignaturaperiodos(new Long(0));
			relacionasignaturaperiodos.setAnosacademicos(anosacademicoSeleccionado);
			relacionasignaturaperiodos.setGrados(cursoSeleccionado.getGrados());
			Asignaturas asignaturasTMP = new Asignaturas(new Long(0));
			relacionasignaturaperiodos.setAsignaturas(asignaturasTMP);
			relacionprofesoresasignaturaperiodoEditar.setRelacionasignaturaperiodos(relacionasignaturaperiodos);
		}
	}
	
	//Public void seleccionarEditarNombre
	public void seleccionarAsignaturaEditarNombre(Long idasignaturas){
		if(idasignaturas != null){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot root = facesContext.getViewRoot();
			UIData tablaAnosAcademicos = (UIData) root
					.findComponent("formPrincipal").findComponent(
							"tblAsignaturas");
			indexAsignaturaSeleccionada = tablaAnosAcademicos.getRowIndex();
			asignaturaSeleccionada = asignaturasFacade.find(idasignaturas);
		}
	}
	
	//Metodo para guardar la asignatura editada
	public void guardarAsignaturaSeleccionada(){
		if(asignaturaSeleccionada != null){
			asignaturasFacade.edit(asignaturaSeleccionada);
			dataListAsignaturas.get(indexAsignaturaSeleccionada)[1] = asignaturaSeleccionada.getNombre();
			RequestContext.getCurrentInstance().update("formPrincipal:tblAsignaturas");
		}
	}
	
	public Asignaturas getAsignaturaSeleccionada() {
		if(asignaturaSeleccionada == null){
			return new Asignaturas();
		}
		return asignaturaSeleccionada;
	}

	public void setAsignaturaSeleccionada(Asignaturas asignaturaSeleccionada) {
		this.asignaturaSeleccionada = asignaturaSeleccionada;
	}

	public List<Object[]> getDataListAsignaturas() {
		if(cursoSeleccionado == null){
			return null;
		}
		if(dataListAsignaturas == null){
			String query = "select idasignaturas, nombre,  "
					+ "case when idrelacionprofesoresasignaturaperiodo is not null "
					+ "then idrelacionprofesoresasignaturaperiodo else -1 end from asignaturas a "
					+ "left join relacionasignaturaperiodos r on r.asignaturas = a.idasignaturas "
					+ "and anosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos() + " "
					+ "left join relacionprofesoresasignaturaperiodo rr "
					+ "on rr.relacionasignaturaperiodos = r.idrelacionasignaturaperiodos "
					+ "and rr.cursos  = " + cursoSeleccionado.getIdcursos() + " "
					+ "order by 2";
			dataListAsignaturas = asignaturasFacade.findByNative(query);
		}
		return dataListAsignaturas;
	}

	public void setDataListAsignaturas(List<Object[]> dataListAsignaturas) {
		this.dataListAsignaturas = dataListAsignaturas;
	}

	//Propiedades de la Relacionprofesoresasignaturaperiodo
	public Relacionprofesoresasignaturaperiodo getRelacionprofesoresasignaturaperiodoEditar() {
		return relacionprofesoresasignaturaperiodoEditar;
	}

	public void setRelacionprofesoresasignaturaperiodoEditar(
			Relacionprofesoresasignaturaperiodo relacionprofesoresasignaturaperiodoEditar) {
		this.relacionprofesoresasignaturaperiodoEditar = relacionprofesoresasignaturaperiodoEditar;
	}	

	// metodo para guardar una relacionprofesoresasignaturaperiodo
	public void guardarEdcionRelacionprofesoresasignaturaperiodoEditar() {
		try{
			if (relacionprofesoresasignaturaperiodoEditar != null) {
				if (relacionprofesoresasignaturaperiodoEditar
						.getRelacionasignaturaperiodos().getAsignaturas()
						.getIdasignaturas() != 0) {
					if(relacionprofesoresasignaturaperiodoEditar
							.getProfesores() != null)
					{
						Profesores p = profesoresFacade
								.find(relacionprofesoresasignaturaperiodoEditar
										.getProfesores().getIdprofesores());
						relacionprofesoresasignaturaperiodoEditar.setProfesores(p);
					}
					asignaturasFacade
							.edit(relacionprofesoresasignaturaperiodoEditar
									.getRelacionasignaturaperiodos()
									.getAsignaturas());
					if (relacionprofesoresasignaturaperiodoEditar
							.getIdrelacionprofesoresasignaturaperiodo() == 0) {
						relacionprofesoresasignaturaperiodoFacade
								.create(relacionprofesoresasignaturaperiodoEditar);
					} else {
						relacionprofesoresasignaturaperiodoFacade
								.edit(relacionprofesoresasignaturaperiodoEditar);
					}
				} else {
					// Creamos la asignatura
					asignaturasFacade
							.create(relacionprofesoresasignaturaperiodoEditar
									.getRelacionasignaturaperiodos()
									.getAsignaturas());
					// Creamos la asignatura para este año
					relacionasignaturaperiodosFacade
							.create(relacionprofesoresasignaturaperiodoEditar
									.getRelacionasignaturaperiodos());
					// Creamos la relación entre el curso, la asignatura, el año y
					// el profesor
					relacionprofesoresasignaturaperiodoFacade
							.create(relacionprofesoresasignaturaperiodoEditar);
				}
				
				dataListAsignaturas = null;
			}
		}catch(Exception e){
			System.out.println("Erro");
		}
	}
	
	@EJB
	private BloquesFacade bloquesFacade;
	
	///###BLOQUES
	public List<Bloques> dataListBloques(){
		if(cursoSeleccionadoEditar != null){
			return bloquesFacade.findByLike("SELECT B FROM Bloques B ORDER BY B.nombre");
		}
		return null;
	}
	
	//####LISTA DE LAS ASIGNATURAS GRUPALES
	public List<Object[]> getDataListAsignaturasGrupales() {
		if(asignaturaSeleccionada != null){
			String query = "select idasignaturas, "
					+ "nombre, "
					+ "case when idrelacionprofesoresasignaturaperiodo is not null "
					+ "then idrelacionprofesoresasignaturaperiodo else -1 end, "
					+ "case when ag.idasignaturasgrupales is not null then true else false end "
					+ "from asignaturas a "
					+ "left join relacionasignaturaperiodos r on r.asignaturas = a.idasignaturas "
					+ "and anosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos() + " "
					+ "join relacionprofesoresasignaturaperiodo rr "
					+ "on rr.relacionasignaturaperiodos = r.idrelacionasignaturaperiodos "
					+ "and rr.cursos  = " + cursoSeleccionado.getIdcursos() + " "
					+ "left join asignaturasgrupales ag on ag.asignatura1 =  " + asignaturaSeleccionada.getIdasignaturas() + " "
					+ "and ag.idanosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos()  + " "
					+ "and ag.asignatura2 = r.asignaturas "
					+ "and ag.grado = " + cursoSeleccionado.getGrados().getIdgrados() + " "
					+ "order by 2";
			dataListAsignaturasGrupales = asignaturasFacade.findByNative(query);
		}
		return dataListAsignaturasGrupales;
	}

	public void setDataListAsignaturasGrupales(
			List<Object[]> dataListAsignaturasGrupales) {
		this.dataListAsignaturasGrupales = dataListAsignaturasGrupales;
	}
	
	//Metodo para agrupar o desagrupar asignaturas
	public void modificarRelacionAgrupa(AjaxBehaviorEvent event){
		
		String tmp = (String) ((UISelectBoolean)event.getSource()).getValue();
		tmp = tmp.replaceAll("\\s","");
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaAnosAcademicos = (UIData) root
				.findComponent("formPrincipal").findComponent(
						"tablaAnosAcademicos");
	}
	
	public boolean getRelacionAgrupa(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaAgrupaAsignaturas = (UIData) root
				.findComponent("edicionAsignaturasCreadas").findComponent(
						"tblAgrupaAsignaturas");
		Object[] o = (Object[]) tablaAgrupaAsignaturas.getRowData();
		
		return (Boolean) o[3];
		
	}
	
	public void setRelacionAgrupa(boolean valor){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaAgrupaAsignaturas = (UIData) root
				.findComponent("edicionAsignaturasCreadas").findComponent(
						"tblAgrupaAsignaturas");
		Object[] o = (Object[]) tablaAgrupaAsignaturas.getRowData();
		
		String query =
				"SELECT A FROM Asignaturasgrupales A WHERE A.asignatura1.idasignaturas  = "
				+ asignaturaSeleccionada.getIdasignaturas()
				+ " AND A.asignatura2.idasignaturas = " + o[0].toString()
				+ " AND A.grado.idgrados = " + cursoSeleccionado.getGrados().getIdgrados()
				+ " AND A.anosacademicos.idanosacademicos = " + anosacademicoSeleccionado.getIdanosacademicos();
		List<Asignaturasgrupales> dataListAsignaturasgrupales = 
				asignaturasgrupalesFacade.findByLike(query);
		
		
		if(!valor){
			if(!dataListAsignaturasgrupales.isEmpty()){
				asignaturasgrupalesFacade.remove(dataListAsignaturasgrupales.get(0));
			}
		}else{
			if(dataListAsignaturasgrupales.isEmpty()){
				Asignaturasgrupales asignaturasgrupales = new Asignaturasgrupales();
				asignaturasgrupales.setIdasignaturasgrupales(new Long(0));
				asignaturasgrupales.setAsignatura1(new Asignaturas(asignaturaSeleccionada.getIdasignaturas()));
				asignaturasgrupales.setAsignatura2(new Asignaturas(new Long(o[0].toString())));
				asignaturasgrupales.setGrado(cursoSeleccionado.getGrados());
				asignaturasgrupales.setAnosacademicos(anosacademicoSeleccionado);
				asignaturasgrupalesFacade.create(asignaturasgrupales);
			}
		}
		
		
	}
}
