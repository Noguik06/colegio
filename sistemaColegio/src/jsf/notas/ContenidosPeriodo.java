/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.notas;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.transaction.UserTransaction;

import ejb.AnosacademicosFacade;
import ejb.ConfiguracionesFacade;
import ejb.ContenidosFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.PeriodosFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelacioncontenidosdimensionesFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionlogrosnotasdimensionFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import entities.Anosacademicos;
import entities.Contenidos;
import entities.Cursos;
import entities.Dimensiones;
import entities.Periodos;
import entities.Relacionasignaturaperiodos;
import entities.Relacioncontenidosdimensiones;
import entities.Relaciondimensionesasignaturasano;
import jsf.usuarios.Sesiones;

/**
 * 
 * @author juannoguera
 */
@ManagedBean(name = "contenidosPeriodo")
@ViewScoped
public class ContenidosPeriodo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
	@EJB
	private DimensionesFacade dimensionesFacade;
	@EJB
	private RelaciondimensionesasignaturasanoFacade relaciondimensionesasignaturasanoFacade;
	@EJB
	private PeriodosFacade periodosFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private ContenidosFacade contenidosFacade;
	@EJB
	private RelacioncontenidosdimensionesFacade relacioncontenidosdimensionesFacade;
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	@EJB
	private RelacionlogrosnotasdimensionFacade relacionlogrosnotasdimensionFacade;
	@EJB
	private ConfiguracionesFacade configuracionesFacade;
	// ##ANOSACADEMICOS
	// Anoacademico actual
	private Anosacademicos anosacademicosActual;
	// ###CURSOS
	// Lista de los cursos
	private List<Cursos> dataListCursos;
	// Curso seleccionado
	private Cursos cursoSeleccionado;
	// Lista de las asignaturasPorCurso
	private List<Relacionasignaturaperiodos> dataListRelacionasignaturaperiodos;
	// Asignatura escogida
	private Relacionasignaturaperiodos relacionasignaturaperiodosAsignado;
	// ##DIMENSIONES
	// Lista de las dimensiones
	private List<Relaciondimensionesasignaturasano> datalistDimensionesasignaturasano;
	// Dimension seleccionada
	private Relaciondimensionesasignaturasano relaciondimensionesasignaturasanoAsignada;
	// ###PERIODOS
	// Lista de los periodos
	private List<Periodos> datalistPeriodos;
	// Periodo Seleccionado
	private Periodos periodoSeleccionado;
	// /###LOGROS
	// Lista de los logros
	private List<Relacioncontenidosdimensiones> datalistContenidos;
	// Logros Seleccionado
	private Relacioncontenidosdimensiones relacionContenidosDimensionesSeleccionado;
	// Logro a editar
	private Relacioncontenidosdimensiones relacionContenidosdimensioneseditar;

	// Propieades de los otros logros
	private List<Relacioncontenidosdimensiones> otrosContenidos;
		
	@Resource
	private UserTransaction userTransaction;

	/**
	 * Creates a new instance of NotasEstudiantes
	 */
	public ContenidosPeriodo() {
	}

	// ###PROPIEDADES DE LA LISTA DE CURSOS
	// public List<Cursos> getDataListCursos() {
	// List<Cursos> tmp = cursosFacade.findAll();
	// return tmp;
	// // return new ArrayList<Cursos>();
	// }
	public Anosacademicos getCurrentYear() {
		if (anosacademicosActual == null) {
			anosacademicosActual = anosacademicosFacade
					.findByLike(
							"SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true")
					.get(0);
		}

		return anosacademicosActual;
	}

	public Sesiones getSesiones() {
		FacesContext FCInstance = FacesContext.getCurrentInstance();
		String theBeanName = "sesiones";
		Object bean = FCInstance.getELContext().getELResolver()
				.getValue(FCInstance.getELContext(), null, theBeanName);
		Sesiones sesiones = (Sesiones) bean;
		if (sesiones != null && sesiones.getUsuarios() != null) {
			return sesiones;
		}

		return null;
	}

	// ###PROPIEDADES DE LA LISTA DE CURSOS
	public List<Cursos> getDataListCursos() {
		Sesiones sesiones = getSesiones();
		if (sesiones == null || sesiones.getUsuarios() == null) {
			return null;
		}
		if (dataListCursos == null || dataListCursos.isEmpty()) {
			
			//Si el usuario es tipo administrador va a poder ver todos los cursos
			if(sesiones.isAdministrador()){
				dataListCursos = cursosFacade
						.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R WHERE r.cursos.anosacademicos.estadoactivo = 'true' " 
								+ " ORDER BY R.cursos.grados.numero");
			}else{
				//Si el usuario no es tipo administrador solo va a poder ver los cursos asignados a el
				dataListCursos = cursosFacade
						.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R WHERE r.cursos.anosacademicos.estadoactivo = 'true' AND R.profesores.usuarios.idusuarios = "
								+ sesiones.getUsuarios().getIdusuarios()
								+ " ORDER BY R.cursos.grados.numero");
			}
			
		}
		return dataListCursos;
	}

	public void setDataListCursos(List<Cursos> dataListCursos) {
		this.dataListCursos = dataListCursos;
	}

	// Metodo para saber cu��l es el curso seleccionado
	public boolean cursoSeleccionado(Cursos cursos) {
		if (cursoSeleccionado != null
				&& cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
			return true;
		}
		return false;
	}

	// Metodo para colocar el grado
	public void escogerCurso(Cursos cursos) {
		Sesiones sesiones = getSesiones();
		this.cursoSeleccionado = cursos;
		
		if(sesiones.isAdministrador()){
			dataListRelacionasignaturaperiodos = relacionasignaturaperiodosFacade
					.findByLike("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.anosacademicos.estadoactivo = 'true' AND  RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}else{
			dataListRelacionasignaturaperiodos = relacionasignaturaperiodosFacade
					.findByLike("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.anosacademicos.estadoactivo = 'true' AND  RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " AND RPA.profesores.usuarios.idusuarios = "
							+ sesiones.getUsuarios().getIdusuarios()
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}
		
		
		relacionasignaturaperiodosAsignado = null;
		periodoSeleccionado = null;
		relaciondimensionesasignaturasanoAsignada = null;
		datalistDimensionesasignaturasano = null;
		datalistContenidos = null;
		otrosContenidos = null;
		datalistPeriodos = null;

		
	}

	// ###ASIGNATURAS
	// PROPIEDADES DE LAS ASIGNATURAS
	public List<Relacionasignaturaperiodos> getDataListRelacionasignaturaperiodos() {
		return dataListRelacionasignaturaperiodos;
	}

	public void setDataListRelacionasignaturaperiodos(
			List<Relacionasignaturaperiodos> dataListRelacionasignaturaperiodos) {
		this.dataListRelacionasignaturaperiodos = dataListRelacionasignaturaperiodos;
	}

	// Escoger la asignatura
	public void escogerAsignatura(Relacionasignaturaperiodos asignatura) {
		this.relacionasignaturaperiodosAsignado = asignatura;

		periodoSeleccionado = null;
		relaciondimensionesasignaturasanoAsignada = null;
		datalistDimensionesasignaturasano = null;
		datalistContenidos = null;
		datalistPeriodos = null;

		

		// Reiniciar las notas
		relacionContenidosDimensionesSeleccionado = null;

		

		// Reiniciamos los otros logros
		otrosContenidos = null;

		
	}

	//
	public boolean asignaturaSeleccionada(Relacionasignaturaperiodos asignatura) {
		if (relacionasignaturaperiodosAsignado != null
				&& this.relacionasignaturaperiodosAsignado
						.getIdrelacionasignaturaperiodos() == asignatura
						.getIdrelacionasignaturaperiodos()) {
			return true;
		}
		return false;
	}

	// ####DIMENSIONES
	// PROPIEDADES DE LA LISTA DE DIMENSIONES
	public List<Relaciondimensionesasignaturasano> getDataListRelaciondimensionesasignaturasano() {
		if (datalistDimensionesasignaturasano == null
				&& relacionasignaturaperiodosAsignado != null) {
			
			
			List<Dimensiones> datalistDimensiones = dimensionesFacade.findByLikeAll("SELECT D FROM Dimensiones D ORDER BY D.nombre");
			//Recorremos las dimensiones
	        for(Dimensiones d:datalistDimensiones){
	        	if(d.getNombre().equals("Saber") || d.getNombre().equals("Hacer") || d.getNombre().equals("Social")){
		        	//Validamos si el tipo de asignatura que escogimos es tipo 0 es decir calificable normalmente
		        	if(relacionasignaturaperiodosAsignado.getAsignaturas().getTipoasignatura() == 0){
		        		Short valor = new Short("0");
			        	if(d.getNombre().equals("Saber")){
			        		valor = 40;
			        	}
			        	if(d.getNombre().equals("Hacer")){
			        		valor = 40;
			        	}
			        	if(d.getNombre().equals("Social")){
			        		valor = 20;
			        	}
		        		if (relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT RDAA FROM Relaciondimensionesasignaturasano RDAA WHERE RDAA.dimensiones.iddimensiones = " + d.getIddimensiones() + " AND RDAA.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND + RDAA.cursos.idcursos = " + cursoSeleccionado.getIdcursos()).isEmpty()) {
		                    //Si esta vacio entonces creamos uno nuevo
		                    Relaciondimensionesasignaturasano tmp = new Relaciondimensionesasignaturasano(new Long(0));
		                    tmp.setDimensiones(d);
		                    tmp.setCursos(cursoSeleccionado);
		                    tmp.setRelacionasignaturasperiodos(relacionasignaturaperiodosAsignado);
		                    tmp.setPorcentaje(valor);
		                    relaciondimensionesasignaturasanoFacade.create(tmp);
		                } 
		        	}
	        	}
	        }
			
			
			datalistDimensionesasignaturasano = relaciondimensionesasignaturasanoFacade
					.findByLike("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.cursos.idcursos = "
							+ cursoSeleccionado.getIdcursos()
							+ " AND R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
							+ relacionasignaturaperiodosAsignado
									.getIdrelacionasignaturaperiodos());
		}
		return datalistDimensionesasignaturasano;
	}

	public void SetDataListRelaciondimensionesasignaturasano(
			List<Relaciondimensionesasignaturasano> datalistDimensionesasignaturasano) {
		this.datalistDimensionesasignaturasano = datalistDimensionesasignaturasano;
	}

	// Metodo para escoger las dimensiones
	public void seleccionarRelaciondimensionesasignaturasano(
			Relaciondimensionesasignaturasano relaciondimensionesasignaturasano) {
		if (relacionasignaturaperiodosAsignado != null) {
			// //Damos el valor de la dimension seleccionada
			this.relaciondimensionesasignaturasanoAsignada = relaciondimensionesasignaturasano;

			//
			datalistPeriodos = periodosFacade
					.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = "
							+ getCurrentYear().getIdanosacademicos()
							+ " ORDER BY P.fechainicio");
			//
			periodoSeleccionado = null;

			// Reiniciar la lista de los contenidos
			datalistContenidos = null;

			relacionContenidosDimensionesSeleccionado = null;
			// relacionnotasrelacionlogrosdimensionSeleccionada = null;

			// Reiniciar las notas
			relacionContenidosDimensionesSeleccionado = null;

			// Reiniciamos
			otrosContenidos = null;

		}
	}

	

	// Metodo para hallar los porcentajes de las dimensiones en cada asginatura
	// Propiedades de los porcentajes
	public short getValorPorcentajeAsignatura() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaDimensiones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaDimensiones");
		Dimensiones dimensionValor = (Dimensiones) tablaDimensiones
				.getRowData();
		List<Relaciondimensionesasignaturasano> tmp = relaciondimensionesasignaturasanoFacade
				.findByLike("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodosAsignado
								.getIdrelacionasignaturaperiodos()
						+ " AND R.dimensiones.iddimensiones = "
						+ dimensionValor.getIddimensiones()
						+ " AND R.cursos.idcursos = "
						+ cursoSeleccionado.getIdcursos());

		if (tmp == null || tmp.isEmpty()) {
			return 0;
		}

		if (tmp.get(0) == null || tmp.get(0).getPorcentaje() == null) {
			return 0;
		}

		return tmp.get(0).getPorcentaje();
	}

	public void setValorPorcentajeAsignatura(short porcentajeAsigntaura) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaDimensiones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaDimensiones");
		Dimensiones dimensionValor = (Dimensiones) tablaDimensiones
				.getRowData();
		List<Relaciondimensionesasignaturasano> tmp = relaciondimensionesasignaturasanoFacade
				.findByLike("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodosAsignado
								.getIdrelacionasignaturaperiodos()
						+ " AND R.dimensiones.iddimensiones = "
						+ dimensionValor.getIddimensiones()
						+ " AND R.cursos.idcursos = "
						+ cursoSeleccionado.getIdcursos());

		if (tmp != null && !tmp.isEmpty()) {
			tmp.get(0).setPorcentaje(porcentajeAsigntaura);
			relaciondimensionesasignaturasanoFacade.edit(tmp.get(0));
		}
	}

	// Metodo para saber cu��l es la dimension seleccionado
	public boolean dimensionSeleccionada(
			Relaciondimensionesasignaturasano relaciondimensionesasignaturasano) {
		if (relaciondimensionesasignaturasanoAsignada != null
				&& relaciondimensionesasignaturasanoAsignada
						.getIdrelaciondimensionesasignaturasano() == relaciondimensionesasignaturasano
						.getIdrelaciondimensionesasignaturasano()) {
			return true;
		}
		return false;
	}

	// ###PERIODOS
	// PROPIEDADES DE LA LISTA DE LOS PERIODOS
	public List<Periodos> getDatalistPeriodos() {
		return datalistPeriodos;
	}

	public void setDatalistPeriodos(List<Periodos> datalistPeriodos) {
		this.datalistPeriodos = datalistPeriodos;
	}

	// Metodo para seleccionar el Periodo
	public void seleccionarPeriodos(Periodos periodo) {
		this.periodoSeleccionado = periodo;
		datalistContenidos = relacioncontenidosdimensionesFacade
				.findByLike("SELECT R FROM Relacioncontenidosdimensiones R WHERE R.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano());

		// Relaciondimensionesasignaturasano
		otrosContenidos = relacioncontenidosdimensionesFacade
				.findByLike("SELECT R FROM Relacioncontenidosdimensiones R WHERE R.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = "
						+ relaciondimensionesasignaturasanoAsignada.getCursos()
								.getGrados().getIdgrados()
						+ " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodosAsignado
								.getIdrelacionasignaturaperiodos()
						+ " AND NOT EXISTS (SELECT RR FROM Relacioncontenidosdimensiones RR WHERE RR.idrelacioncontenidosdimensiones = R.idrelacioncontenidosdimensiones AND RR.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND RR.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano() + ")");
	
		// Reiniciar las notas
		relacionContenidosDimensionesSeleccionado = null;
	}

	// Metodo para saber cual es el periodo seleccionado
	public boolean periodoSeleccionado(Periodos periodos) {
		if (periodoSeleccionado != null
				&& periodoSeleccionado.getIdperiodos() == periodos
						.getIdperiodos()) {
			return true;
		}
		return false;
	}

	// ###LOGROS
	// Propiedades de la lista de los logros
	public List<Relacioncontenidosdimensiones> getDatalistContenidos() {
		return datalistContenidos;
	}

	//

	public void setDatalistContenidos(List<Relacioncontenidosdimensiones> datalistContenidos) {
		this.datalistContenidos = datalistContenidos;
	}

	public List<Relacioncontenidosdimensiones> getOtrosContenidos() {
		return otrosContenidos;
	}

	public void setOtrosContenidos(List<Relacioncontenidosdimensiones> otrosContenidos) {
		this.otrosContenidos = otrosContenidos;
	}



	// Metodo para mostrar el historial de logros de esta asignatura en esta
	// dimension
	public List<Relacioncontenidosdimensiones> dataListRelacionContenidosDimensiones() {
		if (periodoSeleccionado != null) {
			List<Relacioncontenidosdimensiones> tmp = relacioncontenidosdimensionesFacade
					.findByLike("SELECT R FROM Relacioncontenidosdimensiones "
							+ "R WHERE R.periodos.idperiodos != "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND "
							+ "R.relaciondimensionesasignaturasano.dimensiones.iddimensiones = "
							+ relaciondimensionesasignaturasanoAsignada
									.getDimensiones().getIddimensiones()
							+ "AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.idasignaturas = "
							+ relacionasignaturaperiodosAsignado
									.getAsignaturas().getIdasignaturas()
							+ " "
							+ "AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = "
							+ cursoSeleccionado.getGrados().getIdgrados()
							+ " ORDER BY R.contenidos.contenido");
			return tmp;
		}
		return null;
	}
	
	// Metodo para agregar un nuevo contenido
	public void agregarContenido() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputTextarea nombreContenidoNuevo = (HtmlInputTextarea) root
				.findComponent("contenidos").findComponent("textoContenido");

		if (periodoSeleccionado == null) {
			throw new ValidatorException(new FacesMessage(
					"No hay ningun periodo seleccionado"));
		}

		if (nombreContenidoNuevo == null || nombreContenidoNuevo.getValue() == null
				|| nombreContenidoNuevo.getValue().toString().trim().length() == 0) {
			throw new ValidatorException(new FacesMessage(
					"El texto del Contenido no puede estar vacio"));
		}

		if (relacionContenidosdimensioneseditar != null) {
			relacionContenidosdimensioneseditar.getContenidos().setContenido(
					nombreContenidoNuevo.getValue().toString().replaceAll("�", ""));
			contenidosFacade.edit(relacionContenidosdimensioneseditar.getContenidos());
			// relacionlogrosdimensionesFacade.edit(relacionlogrosdimensioneseditar);
		} else {
			Contenidos contenidoNuevo = new Contenidos(new Long(0));
			contenidoNuevo.setContenido((nombreContenidoNuevo.getValue().toString()
					.replaceAll("�", "")));
			contenidosFacade.create(contenidoNuevo);

			Relacioncontenidosdimensiones tmp = new Relacioncontenidosdimensiones(
					new Long(0));
			tmp.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
			tmp.setPeriodos(periodoSeleccionado);
			tmp.setContenidos(contenidoNuevo);
			relacioncontenidosdimensionesFacade.create(tmp);

			datalistContenidos.add(tmp);
		}
	}

	// Metodo para eliminar un logro
	public void eliminarContenido(Relacioncontenidosdimensiones contenido) {

		relacioncontenidosdimensionesFacade.remove(contenido);

		contenidosFacade.remove(contenido.getContenidos());
		datalistContenidos.remove(contenido);
	}

	// Metodo para editar los contenidos
	public void seleccionarEditarContenido(Relacioncontenidosdimensiones contenido) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlOutputText titulo = (HtmlOutputText) root.findComponent("contenidos")
				.findComponent("tituloPopUp");
		HtmlInputTextarea nombreContenidoNuevo = (HtmlInputTextarea) root
				.findComponent("contenidos").findComponent("textoContenido");

		if (contenido == null) {
			titulo.setValue("Agregar Nuevo Contenido");
			nombreContenidoNuevo.setValue("");
			this.relacionContenidosdimensioneseditar = null;
		} else {

			titulo.setValue("Editar Contenido");
			this.relacionContenidosdimensioneseditar = contenido;
			nombreContenidoNuevo.setValue(contenido.getContenidos().getContenido());
		}
	}

	// Metodo para saber si el logro ha sido o no seleccinado
	public boolean contenidoSeleccionado(Relacioncontenidosdimensiones contenido) {
		if (relacionContenidosDimensionesSeleccionado != null
				&& relacionContenidosDimensionesSeleccionado.getContenidos()
						.getIdcontenidos() == contenido.getContenidos().getIdcontenidos()) {
			return true;
		}
		return false;
	}
	
	//Metodo para validar si puede o no realizar modificaciones sobre las fechas
	public boolean administrador() {
		try{
			Sesiones sesiones = getSesiones();
			if (sesiones == null || sesiones.getUsuarios() == null) {
				return false;
			}
			if(sesiones.isAdministrador()){
				return true;
			}
		}catch(Exception e){
		}
		return false;
	}

	// ###Recuperaciones
	// Metodo para saber si podemos seleccionar las recuperaciones
	public boolean isPeriodoSeleccionado() {
		if (periodoSeleccionado == null) {
			return false;
		}

		return true;
	}
}
