/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.notas;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.transaction.UserTransaction;

import jsf.usuarios.Sesiones;
import ejb.AnosacademicosFacade;
import ejb.ConfiguracionesFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.LogrosFacade;
import ejb.NotascalificablesFacade;
import ejb.PeriodosFacade;
import ejb.RecuperacionesFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionlogrosdimensionesFacade;
import ejb.RelacionlogrosnotasdimensionFacade;
import ejb.RelacionlogrosrecuperacionesFacade;
import ejb.RelacionnotasdimensionFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import entities.Anosacademicos;
import entities.Configuraciones;
import entities.Cursos;
import entities.Dimensiones;
import entities.Logros;
import entities.Notascalificables;
import entities.Periodos;
import entities.Recuperaciones;
import entities.Registromatriculas;
import entities.Relacionasignaturaperiodos;
import entities.Relaciondimensionesasignaturasano;
import entities.Relacionlogrosdimensiones;
import entities.Relacionlogrosnotasdimension;
import entities.Relacionlogrosrecuperaciones;
import entities.Relacionnotasdimension;
import entities.Relacionnotaslogrosdimensionboletin;
import entities.Relacionprofesoresasignaturaperiodo;

/**
 * 
 * @author juannoguera
 */
@ManagedBean(name = "notasProfesores")
@ViewScoped
public class NotasProfesores implements Serializable {

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
	private LogrosFacade logrosFacade;
	@EJB
	private RelacionlogrosdimensionesFacade relacionlogrosdimensionesFacade;
	@EJB
	private RelacionlogrosrecuperacionesFacade relacionlogrosrecuperacionesFacade;
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	@EJB
	private RelacionnotaslogrosdimensionboletinFacade relacionnotaslogrosdimensionboletinFacade;
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private NotascalificablesFacade notascalificablesFacade;
	@EJB
	private RelacionnotasdimensionFacade relacionnotasdimensionFacade;
	@EJB
	private RelacionlogrosnotasdimensionFacade relacionlogrosnotasdimensionFacade;
	@EJB
	private RecuperacionesFacade recuperacionesFacade;
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
	// Dimension editar
	private Dimensiones dimensionEditar;
	// ###PERIODOS
	// Lista de los periodos
	private List<Periodos> datalistPeriodos;
	// Periodo Seleccionado
	private Periodos periodoSeleccionado;
	// /###LOGROS
	// Lista de los logros
	private List<Relacionlogrosdimensiones> datalistLogros;
	// Logros Seleccionado
	private Relacionlogrosdimensiones relacionLogrosDimensionesSeleccionado;
	// Logro a editar
	private Relacionlogrosdimensiones relacionlogrosdimensioneseditar;
	// ##NOTAS
	// Lista de las notas por Logro
	private List<Relacionnotasdimension> dataListRelacionnotasdimension;
	// Notas a Editar
	private Relacionnotasdimension notasEditar;
	// Notas Seleccionada
	private Relacionnotasdimension relacionNotasDimensionSeleccionada;
	// ##ACTIVIDADES
	// Lista de las notas que cada profesor escoge por categoria de notas
	private List<Relacionnotaslogrosdimensionboletin> dataListdataListRelacionnotaslogrosdimensionboletin;
	// Nota Calificable seleccionable
	private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinSeleccionada;
	// Nota Calificable editar
	private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinEditar;
	// ###ESTUDIANTES
	private List<Registromatriculas> dataListRegistroMatricula;
	// Metodo utilizado para darle commit y begin a la transaccion con el fin de
	// que no se queden transacciones por la mitad
	// ###Lista de las recuperaciones
	private List<Recuperaciones> dataListRecuperaciones;
	// ###Bandera para saber si han seleccionado las recuperaciones
	private boolean recuperacionesSeleccionado = false;
	@Resource
	private UserTransaction userTransaction;

	/**
	 * Creates a new instance of NotasEstudiantes
	 */
	public NotasProfesores() {
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
			Relacionprofesoresasignaturaperiodo tmp;
			dataListCursos = cursosFacade
					.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R WHERE r.cursos.anosacademicos.estadoactivo = 'true' AND R.profesores.usuarios.idusuarios = "
							+ sesiones.getUsuarios().getIdusuarios()
							+ " ORDER BY R.cursos.grados.numero");
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
		dataListRelacionasignaturaperiodos = relacionasignaturaperiodosFacade
				.findByLike("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.anosacademicos.estadoactivo = 'true' AND  RPA.cursos.grados.idgrados = "
						+ cursos.getGrados().getIdgrados()
						+ " AND RPA.profesores.usuarios.idusuarios = "
						+ sesiones.getUsuarios().getIdusuarios()
						+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		relacionasignaturaperiodosAsignado = null;
		periodoSeleccionado = null;
		relaciondimensionesasignaturasanoAsignada = null;
		datalistDimensionesasignaturasano = null;
		datalistLogros = null;
		datalistPeriodos = null;

		// ##Reiniciar las actividades
		dataListdataListRelacionnotaslogrosdimensionboletin = null;

		// Reiniciar las notas
		relacionLogrosDimensionesSeleccionado = null;
		dataListRelacionnotasdimension = null;

		// Reiniciamos los otros logros
		otrosLogros = null;

		// reiniciamos los otros
		relacionNotasDimensionSeleccionada = null;

		// Colocamos la lista de las recuperaciones en nulo
		dataListRecuperaciones = null;
		// Colocamos la bandera para decir que no estamos seleccionando ninguna
		// recuperacion
		recuperacionesSeleccionado = false;
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
		datalistLogros = null;
		datalistPeriodos = null;

		// ##Reiniciar las actividades
		dataListdataListRelacionnotaslogrosdimensionboletin = null;
		// relacionnotasrelacionlogrosdimensionSeleccionada = null;

		// Reiniciar las notas
		relacionLogrosDimensionesSeleccionado = null;

		dataListRelacionnotasdimension = null;

		// Reiniciamos los otros logros
		otrosLogros = null;

		// reiniciamos los otros
		relacionNotasDimensionSeleccionada = null;

		// Colocamos la lista de las recuperaciones en nulo
		dataListRecuperaciones = null;
		// Colocamos la bandera para decir que no estamos seleccionando ninguna
		// recuperacion
		recuperacionesSeleccionado = false;

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

			// Reiniciar la lista de los logros
			datalistLogros = null;

			// Reiniciamos la lista de las notas
			dataListRelacionnotasdimension = null;

			relacionLogrosDimensionesSeleccionado = null;
			// relacionnotasrelacionlogrosdimensionSeleccionada = null;

			// ##Reiniciar las actividades
			dataListdataListRelacionnotaslogrosdimensionboletin = null;
			// relacionnotasrelacionlogrosdimensionSeleccionada = null;

			// Reiniciar las notas
			relacionLogrosDimensionesSeleccionado = null;
			relacionNotasDimensionSeleccionada = null;
			// dataListNotasRelacionLogrosDimension = null;

			// Reiniciamos
			otrosLogros = null;

			// Colocamos la lista de las recuperaciones en nulo
			dataListRecuperaciones = null;
			// Colocamos la bandera para decir que no estamos seleccionando
			// ninguna recuperacion
			recuperacionesSeleccionado = false;

		}
	}

	// Metodo para eliminar las dimensiones
	public void eliminarDimensiones(Dimensiones dimensiones) {
		// Eliminar las dimensiones
		dimensionesFacade.remove(dimensiones);

		datalistDimensionesasignaturasano = null;
	}

	// Metodo para editar las dimensiones
	public void seleccionarEditarDimension(Dimensiones dimensiones) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlOutputText titulo = (HtmlOutputText) root.findComponent(
				"dimensiones").findComponent("tituloPopUp");
		titulo.setValue("Agregar Nueva Habilidad");
		if (dimensiones == null) {
			this.dimensionEditar = null;
		} else {
			titulo.setValue("Editar Habilidad");

			HtmlInputText nombreDimensionNueva = (HtmlInputText) root
					.findComponent("dimensiones").findComponent(
							"nombreDimension");
			this.dimensionEditar = dimensiones;
			nombreDimensionNueva.setValue(dimensiones.getNombre());
		}
	}

	// Metodo para agregar una nueva dimension
	public void agregarDimension() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputText nombreDimensionNueva = (HtmlInputText) root
				.findComponent("dimensiones").findComponent("nombreDimension");
		datalistDimensionesasignaturasano = null;
		//
		if (nombreDimensionNueva == null
				|| nombreDimensionNueva.getValue() == null
				|| nombreDimensionNueva.getValue().toString().trim().length() == 0) {
			throw new ValidatorException(new FacesMessage(
					"El nombre de la dimension no puede estar vacio"));
		}

		if (dimensionEditar != null) {
			dimensionEditar.setNombre(nombreDimensionNueva.getValue()
					.toString());
			dimensionesFacade.edit(dimensionEditar);
		} else {
			Dimensiones dimensionNueva = new Dimensiones(new Long(0));
			dimensionNueva
					.setNombre(nombreDimensionNueva.getValue().toString());
			dimensionesFacade.create(dimensionNueva);
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
		// if (datalistPeriodos == null) {
		// //Llenamos la lista de los periodos
		// datalistPeriodos =
		// periodosFacade.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = "
		// + getCurrentYear().getIdanosacademicos());
		// }
		return datalistPeriodos;
	}

	public void setDatalistPeriodos(List<Periodos> datalistPeriodos) {
		this.datalistPeriodos = datalistPeriodos;
	}

	// Metodo para seleccionar el Periodo
	public void seleccionarPeriodos(Periodos periodo) {
		this.periodoSeleccionado = periodo;
		datalistLogros = relacionlogrosdimensionesFacade
				.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano());

		// Relaciondimensionesasignaturasano
		otrosLogros = relacionlogrosdimensionesFacade
				.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = "
						+ relaciondimensionesasignaturasanoAsignada.getCursos()
								.getGrados().getIdgrados()
						+ " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodosAsignado
								.getIdrelacionasignaturaperiodos()
						+ " AND NOT EXISTS (SELECT RR FROM Relacionlogrosdimensiones RR WHERE RR.idrelacionlogrosdimensiones = R.idrelacionlogrosdimensiones AND RR.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND RR.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano() + ")");
		
		
		dataListRelacionnotasdimension = new ArrayList<Relacionnotasdimension>();
		
		List<Configuraciones> dataListConfiguraciones;
		if(relaciondimensionesasignaturasanoAsignada.getDimensiones().getNombre().equals("Saber")){
			dataListConfiguraciones = configuracionesFacade.findByLike("SELECT C FROM Configuraciones C WHERE C.propiedad = 'actividadSaber'");
			for(Configuraciones c:dataListConfiguraciones){
				String nombreActividad = c.getValor().split("-")[0];
				Short valor = new Short(c.getValor().split("-")[1]);
				if(relacionnotasdimensionFacade
						.findByLike("SELECT R FROM Relacionnotasdimension R WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano= "
								+ relaciondimensionesasignaturasanoAsignada
										.getIdrelaciondimensionesasignaturasano()
								+ " AND R.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos()
								+ " AND R.nombrenotas = '" +nombreActividad + "'").isEmpty()){
					Relacionnotasdimension notaNueva = new Relacionnotasdimension(new Long(0));
					notaNueva.setNombrenotas(nombreActividad);
					notaNueva.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
					notaNueva.setPorcentaje(valor);
					notaNueva.setPeriodos(periodoSeleccionado);
					relacionnotasdimensionFacade.create(notaNueva);
					dataListRelacionnotasdimension.add(notaNueva);
				}
				
			}
		}
		
		if(relaciondimensionesasignaturasanoAsignada.getDimensiones().getNombre().equals("Social")){
			dataListConfiguraciones = configuracionesFacade.findByLike("SELECT C FROM Configuraciones C WHERE C.propiedad = 'actividadSocial'");
			for(Configuraciones c:dataListConfiguraciones){
				String nombreActividad = c.getValor().split("-")[0];
				Short valor = new Short(c.getValor().split("-")[1]);
				if(relacionnotasdimensionFacade
						.findByLike("SELECT R FROM Relacionnotasdimension R WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano= "
								+ relaciondimensionesasignaturasanoAsignada
										.getIdrelaciondimensionesasignaturasano()
								+ " AND R.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos()
								+ " AND R.nombrenotas = '" +nombreActividad + "'").isEmpty()){
					Relacionnotasdimension notaNueva = new Relacionnotasdimension(new Long(0));
					notaNueva.setNombrenotas(nombreActividad);
					notaNueva.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
					notaNueva.setPorcentaje(valor);
					notaNueva.setPeriodos(periodoSeleccionado);
					relacionnotasdimensionFacade.create(notaNueva);
					dataListRelacionnotasdimension.add(notaNueva);
				}
				
			}
		}
		
		
		
		if(relaciondimensionesasignaturasanoAsignada.getDimensiones().getNombre().equals("Hacer")){
			dataListConfiguraciones = configuracionesFacade.findByLike("SELECT C FROM Configuraciones C WHERE C.propiedad = 'actividadHacer'");
			for(Configuraciones c:dataListConfiguraciones){
				String nombreActividad = c.getValor().split("-")[0];
				Short valor = new Short(c.getValor().split("-")[1]);
				if(relacionnotasdimensionFacade
						.findByLike("SELECT R FROM Relacionnotasdimension R WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano= "
								+ relaciondimensionesasignaturasanoAsignada
										.getIdrelaciondimensionesasignaturasano()
								+ " AND R.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos()
								+ " AND R.nombrenotas = '" +nombreActividad + "'").isEmpty()){
					Relacionnotasdimension notaNueva = new Relacionnotasdimension(new Long(0));
					notaNueva.setNombrenotas(nombreActividad);
					notaNueva.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
					notaNueva.setPorcentaje(valor);
					notaNueva.setPeriodos(periodoSeleccionado);
					relacionnotasdimensionFacade.create(notaNueva);
					dataListRelacionnotasdimension.add(notaNueva);
				}
				
			}
		}
		
		
		

		dataListRelacionnotasdimension = relacionnotasdimensionFacade
				.findByLike("SELECT R FROM Relacionnotasdimension R WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano= "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano()
						+ " AND R.periodos.idperiodos = "
						+ periodoSeleccionado.getIdperiodos());

		// relacionnotasrelacionlogrosdimensionSeleccionada = null;

		// ##Reiniciar las actividades
		dataListdataListRelacionnotaslogrosdimensionboletin = null;
		// relacionnotasrelacionlogrosdimensionSeleccionada = null;

		// Reiniciar las notas
		relacionLogrosDimensionesSeleccionado = null;
		relacionNotasDimensionSeleccionada = null;
		// dataListNotasRelacionLogrosDimension = null;

		// Colocamos la lista de las recuperaciones en nulo
		dataListRecuperaciones = null;
		// Colocamos la bandera para decir que no estamos seleccionando ninguna
		// recuperacion
		recuperacionesSeleccionado = false;

	}

	// Metodo para saber cu��l es el periodo seleccionado
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
	public List<Relacionlogrosdimensiones> getDatalistLogros() {
		return datalistLogros;
	}

	//

	public void setDatalistLogros(List<Relacionlogrosdimensiones> datalistLogros) {
		this.datalistLogros = datalistLogros;
	}

	// Propieades de los otros logros
	private List<Relacionlogrosdimensiones> otrosLogros;

	public List<Relacionlogrosdimensiones> getOtrosLogros() {
		return otrosLogros;
	}

	public void setOtrosLogros(List<Relacionlogrosdimensiones> otrosLogros) {
		this.otrosLogros = otrosLogros;
	}

	// Propiadades del checkbox del logro en el periodo
	public void setValorLogroPeriodo(boolean valorLogro) {
		if (periodoSeleccionado != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot root = facesContext.getViewRoot();
			HtmlDataTable htd = (HtmlDataTable) root.findComponent(
					"formPrincipal").findComponent("tablaLogros");
			Logros logrosTmp = (Logros) htd.getRowData();

			if (relacionlogrosdimensionesFacade
					.findByLike(
							"SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
									+ relaciondimensionesasignaturasanoAsignada
											.getIdrelaciondimensionesasignaturasano()
									+ " AND RLD.periodos.idperiodos = "
									+ periodoSeleccionado.getIdperiodos()
									+ " AND RLD.logros.idlogros = "
									+ logrosTmp.getIdlogros()).isEmpty()) {
				Relacionlogrosdimensiones tmp = new Relacionlogrosdimensiones(
						new Long(0));
				tmp.setLogros(logrosTmp);
				tmp.setPeriodos(periodoSeleccionado);
				tmp.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
				relacionlogrosdimensionesFacade.create(tmp);
			} else {
				relacionlogrosdimensionesFacade
						.remove(relacionlogrosdimensionesFacade
								.findByLike(
										"SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
												+ relaciondimensionesasignaturasanoAsignada
														.getIdrelaciondimensionesasignaturasano()
												+ " AND RLD.periodos.idperiodos = "
												+ periodoSeleccionado
														.getIdperiodos()
												+ " AND RLD.logros.idlogros = "
												+ logrosTmp.getIdlogros()).get(
										0));
			}
		}
	}

	public boolean getValorLogroPeriodo() {
		if (periodoSeleccionado == null) {
			return false;
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlDataTable htd = (HtmlDataTable) root.findComponent("formPrincipal")
				.findComponent("tablaLogros");
		Relacionlogrosdimensiones logrosTmp = (Relacionlogrosdimensiones) htd
				.getRowData();

		if (relacionlogrosdimensionesFacade
				.findByLike(
						"SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
								+ relaciondimensionesasignaturasanoAsignada
										.getIdrelaciondimensionesasignaturasano()
								+ " AND RLD.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos()
								+ " AND RLD.logros.idlogros = "
								+ logrosTmp.getLogros().getIdlogros())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	//
	// Metodo para seleccionar un logro

	public void seleccionarLogro(Relacionlogrosdimensiones logro) {
		// Colocamos el logro seleccionado
		// this.relacionLogrosDimensionesSeleccionado =
		// relacionlogrosdimensionesFacade.findByLike("SELECT RNGD FROM Relacionlogrosdimensiones RNGD WHERE RNGD.logros.idlogros = "
		// + logro.getLogr
		// +
		// " AND RNGD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
		// +
		// relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano()
		// + " AND RNGD.periodos.idperiodos = " +
		// periodoSeleccionado.getIdperiodos()).get(0);
		this.relacionLogrosDimensionesSeleccionado = logro;

		// dataListNotasRelacionLogrosDimension =
		// relacionnotasrelacionlogrosdimensionFacade.findByLike("SELECT RN FROM Relacionnotasrelacionlogrosdimension RN WHERE RN.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
		// +
		// relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones()
		// + " ORDER BY RN.nombrenotas");
		// datalistNotas =
		// notasFacade.findByLike("SELECT R.notas FROM Relacionnotasrelacionlogrosdimension R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
		// +
		// relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones());
		// //Llenamos la lista de las notas
		// datalistNotas = notasFacade.findAll();

		// ##Reiniciar las actividades
		dataListdataListRelacionnotaslogrosdimensionboletin = null;
		// relacionnotasrelacionlogrosdimensionSeleccionada = null;

		// Reiniciar las notas
		// relacionLogrosDimensionesSeleccionado = null;
		// dataListNotasRelacionLogrosDimension = null;
	}

	// M�todo para mostrar el historial de logros de esta asignatura en esta
	// dimensi�n
	public List<Relacionlogrosdimensiones> dataListRelacionLogrosDimensiones() {
		if (periodoSeleccionado != null) {
			List<Relacionlogrosdimensiones> tmp = relacionlogrosdimensionesFacade
					.findByLike("SELECT R FROM Relacionlogrosdimensiones "
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
							+ " ORDER BY R.logros.logro");
			return tmp;
		}
		return null;
	}

	// M�todo para seleccionar
	public void seleccionarLogroAntiguo(Relacionlogrosdimensiones logroAntiguo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputTextarea nombreLogroNuevo = (HtmlInputTextarea) root
				.findComponent("logros").findComponent("textoLogro");
		nombreLogroNuevo.setValue(logroAntiguo.getLogros().getLogro());
	}

	//
	// Metodo para agregar una nueva dimension
	public void agregarLogro() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputTextarea nombreLogroNuevo = (HtmlInputTextarea) root
				.findComponent("logros").findComponent("textoLogro");

		if (periodoSeleccionado == null) {
			throw new ValidatorException(new FacesMessage(
					"No hay ningun periodo seleccionado"));
		}

		if (nombreLogroNuevo == null || nombreLogroNuevo.getValue() == null
				|| nombreLogroNuevo.getValue().toString().trim().length() == 0) {
			throw new ValidatorException(new FacesMessage(
					"El LOGRO no puede estar vacio"));
		}

		if (relacionlogrosdimensioneseditar != null) {
			relacionlogrosdimensioneseditar.getLogros().setLogro(
					nombreLogroNuevo.getValue().toString().replaceAll("�", ""));
			logrosFacade.edit(relacionlogrosdimensioneseditar.getLogros());
			// relacionlogrosdimensionesFacade.edit(relacionlogrosdimensioneseditar);
		} else {
			Logros logroNuevo = new Logros(new Long(0));
			logroNuevo.setLogro(nombreLogroNuevo.getValue().toString()
					.replaceAll("�", ""));
			logrosFacade.create(logroNuevo);

			Relacionlogrosdimensiones tmp = new Relacionlogrosdimensiones(
					new Long(0));
			tmp.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
			tmp.setPeriodos(periodoSeleccionado);
			tmp.setLogros(logroNuevo);
			relacionlogrosdimensionesFacade.create(tmp);

			datalistLogros.add(tmp);
		}

		// datalistLogros =
		// rel.findByLike("SELECT R.logros FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
		// + periodoSeleccionado.getIdperiodos() +
		// " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
		// +
		// relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano());
		// datalistLogros = null;
	}

	//
	// Metodo para eliminar un logro

	public void eliminarLogro(Relacionlogrosdimensiones logros) {

		relacionlogrosdimensionesFacade.remove(logros);

		logrosFacade.remove(logros.getLogros());
		datalistLogros.remove(logros);
	}

	//
	// Metodo para editar las dimensiones

	public void seleccionarEditarLogro(Relacionlogrosdimensiones logros) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlOutputText titulo = (HtmlOutputText) root.findComponent("logros")
				.findComponent("tituloPopUp");
		HtmlInputTextarea nombreLogroNuevo = (HtmlInputTextarea) root
				.findComponent("logros").findComponent("textoLogro");

		if (logros == null) {
			titulo.setValue("Agregar Nuevo Logro");
			nombreLogroNuevo.setValue("");
			this.relacionlogrosdimensioneseditar = null;
		} else {

			titulo.setValue("Editar Logro");
			this.relacionlogrosdimensioneseditar = logros;
			nombreLogroNuevo.setValue(logros.getLogros().getLogro());
		}
	}

	//
	// Metodo para saber si el logro ha sido o no seleccinado

	public boolean logroSeleccionado(Relacionlogrosdimensiones logro) {
		if (relacionLogrosDimensionesSeleccionado != null
				&& relacionLogrosDimensionesSeleccionado.getLogros()
						.getIdlogros() == logro.getLogros().getIdlogros()) {
			return true;
		}
		return false;
	}

	// ###NOTAS
	// Propiedades de la lista de las notas
	public List<Relacionnotasdimension> getDataListRelacionnotasdimension() {
		return dataListRelacionnotasdimension;
	}

	public void setDataListRelacionnotasdimension(
			List<Relacionnotasdimension> dataListRelacionnotasdimension) {
		this.dataListRelacionnotasdimension = dataListRelacionnotasdimension;
	}

	//
	// Metodo para agregar una nueva nota
	public void agregarNotaGlobal() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputText nombreNotaGlobalNueva = (HtmlInputText) root
				.findComponent("notasGlobales").findComponent(
						"nombreNotaGlobal");

		if (periodoSeleccionado == null) {
			throw new ValidatorException(new FacesMessage(
					"No hay ningun periodo seleccionado"));
		}

		if (nombreNotaGlobalNueva == null
				|| nombreNotaGlobalNueva.getValue() == null
				|| nombreNotaGlobalNueva.getValue().toString().trim().length() == 0) {
			throw new ValidatorException(new FacesMessage(
					"El nombre de la NOTA no puede quedar vacio"));
		}

		if (notasEditar != null) {
			notasEditar.setNombrenotas(nombreNotaGlobalNueva.getValue()
					.toString());
			relacionnotasdimensionFacade.edit(notasEditar);
		} else {
			Relacionnotasdimension notaNueva = new Relacionnotasdimension(
					new Long(0));
			notaNueva.setNombrenotas(nombreNotaGlobalNueva.getValue()
					.toString());
			notaNueva
					.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
			// notaNueva.setRelacionlogrosdimensiones(relacionLogrosDimensionesSeleccionado);
			notaNueva.setPeriodos(periodoSeleccionado);
			notaNueva.setPorcentaje(new Short("0"));
			relacionnotasdimensionFacade.create(notaNueva);
			dataListRelacionnotasdimension.add(notaNueva);
		}
	}

	//
	// Metodo para seleccionar una nota global

	public void seleccionarNotaGlobal(
			Relacionnotasdimension relacionnotasrelacionlogrosdimension) {
		this.relacionNotasDimensionSeleccionada = relacionnotasrelacionlogrosdimension;

		dataListdataListRelacionnotaslogrosdimensionboletin = null;

		// Colocamos la lista de las recuperaciones en nulo
		dataListRecuperaciones = null;
		// Colocamos la bandera para decir que no estamos seleccionando ninguna
		// recuperacion
		recuperacionesSeleccionado = false;

		// ##Reiniciar las actividades
		// dataListdataListRelacionnotaslogrosdimensionboletin = null;
		// relacionnotasrelacionlogrosdimensionSeleccionada = null;

		// Reiniciar las notas
		// relacionLogrosDimensionesSeleccionado = null;
		// dataListNotasRelacionLogrosDimension = null;
	}

	// Metodo para eliminar las notas
	public void eliminarNotas(Relacionnotasdimension notas) {
		try {
			userTransaction.begin();
		} catch (Exception e) {
		}
		// Eliminamos de la base de datos esta nota
		relacionnotasdimensionFacade.remove(notas);
		try {
			userTransaction.commit();
			// Quitamos de la lista las notas
			dataListRelacionnotasdimension.remove(notas);
		} catch (Exception e) {
			throw new ValidatorException(
					new FacesMessage(
							"Esta nota no se puede eliminar porque hay actividades asociadas a esta"));
		}

	}

	//
	// //Metodo para saber si es la nota est�� seleccionada
	public boolean notaSeleccionada(Relacionnotasdimension notas) {
		if (relacionNotasDimensionSeleccionada != null
				&& relacionNotasDimensionSeleccionada
						.getIdrelacionnotasdimesion() == notas
						.getIdrelacionnotasdimesion()) {
			return true;
		}
		return false;
	}

	//
	// //Metodo para seleccionar Editar las notas

	public void seleccionarEditarNotas(Relacionnotasdimension notas) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlOutputText titulo = (HtmlOutputText) root.findComponent(
				"notasGlobales").findComponent("tituloPopUp");
		HtmlInputText nombreNotaNueva = (HtmlInputText) root.findComponent(
				"notasGlobales").findComponent("nombreNotaGlobal");
		if (notas == null) {
			titulo.setValue("Agregar Nuevo Item");
			nombreNotaNueva.setValue("");
			this.notasEditar = null;
		} else {
			titulo.setValue("Editar Item");
			this.notasEditar = notas;
			nombreNotaNueva.setValue(notasEditar.getNombrenotas());
		}
	}

	//
	// short valorPorcentajeNota;
	// +++Propiedades del valor en porcentaje de la nota en el logro

	public short getValorPorcentajeNota() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData titulo = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaNotas");
		Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo
				.getRowData();
		if (notaTmp.getPorcentaje() == null) {
			return 0;
		}
		return notaTmp.getPorcentaje();
	}

	public void setValorPorcentajeNota(short nota) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData titulo = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaNotas");
		Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo
				.getRowData();

		// Si coincide colocamos este valor y lo editamos
		if (notaTmp != null) {
			notaTmp.setPorcentaje(nota);

			relacionnotasdimensionFacade.edit(notaTmp);
		}
	}

	// Metodo para obtener la nota en la que estamos trabajando
	public Relacionnotasdimension getRelacionnotasdimension() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData titulo = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaNotas");
		Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo
				.getRowData();
		return notaTmp;
	}

	// Metodo para agregar o eliminar la relacion relacionnotaslgrosdimension
	// public boolean getLogroNotaDimension() {
	// if (relacionLogrosDimensionesSeleccionado == null) {
	// return false;
	// }
	// List<Relacionlogrosnotasdimension> dataListTmp;
	//
	// dataListTmp =
	// relacionlogrosnotasdimensionFacade.findByLike("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = "
	// + relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones()
	// + " AND R.relacionnotasdimension.idrelacionnotasdimesion = " +
	// getRelacionnotasdimension().getIdrelacionnotasdimesion());
	// if (!dataListTmp.isEmpty()) {
	// return true;
	// }
	//
	// return false;
	// }
	//
	// public void setLogroNotaDimension(boolean estado) {
	// if (relacionLogrosDimensionesSeleccionado != null) {
	// if (estado) {
	// Relacionlogrosnotasdimension relacionlogrosnotasdimensionNuevo = new
	// Relacionlogrosnotasdimension(new Long(0));
	// relacionlogrosnotasdimensionNuevo.setRelacionlogrosdimension(relacionLogrosDimensionesSeleccionado);
	// relacionlogrosnotasdimensionNuevo.setRelacionnotasdimension(getRelacionnotasdimension());
	// relacionlogrosnotasdimensionFacade.create(relacionlogrosnotasdimensionNuevo);
	// } else {
	// List<Relacionlogrosnotasdimension> dataListTmp;
	// dataListTmp =
	// relacionlogrosnotasdimensionFacade.findByLike("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = "
	// + relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones()
	// + " AND R.relacionnotasdimension.idrelacionnotasdimesion = " +
	// getRelacionnotasdimension().getIdrelacionnotasdimesion());
	// if (!dataListTmp.isEmpty()) {
	// relacionlogrosnotasdimensionFacade.remove(dataListTmp.get(0));
	// }
	// }
	// }
	// }
	//
	// ###ACTIVIDADES
	// ###dataListRelacionnotaslogrosdimensionboletin
	public void setDataListdataListRelacionnotaslogrosdimensionboletin(
			List<Relacionnotaslogrosdimensionboletin> dataListdataListRelacionnotaslogrosdimensionboletin) {
		this.dataListdataListRelacionnotaslogrosdimensionboletin = dataListdataListRelacionnotaslogrosdimensionboletin;

	}

	// Metodo para escoger la notaCalificable y calificar los alumnos
	public void seleccionarNotaCalificable(
			Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin) {
		dataListNotasCalificables = new ArrayList<NotasCalificablesNueva>();
		this.relacionnotaslogrosdimensionboletinSeleccionada = relacionnotaslogrosdimensionboletin;
		dataListRegistroMatricula = registromatriculasFacade
				.findByLikeAll("SELECT RM FROM Registromatriculas RM WHERE RM.cursos.idcursos = "
						+ cursoSeleccionado.getIdcursos()
						+ " AND RM.fecharetiro is null AND RM.anosacademicos.idanosacademicos = "
						+ periodoSeleccionado.getAnoacademicos()
								.getIdanosacademicos()
						+ " ORDER BY RM.estudiantes.usuarios.apellidos");
		for (Registromatriculas g : dataListRegistroMatricula) {
			NotasCalificablesNueva notasCalificables = new NotasCalificablesNueva();
			notasCalificables.setNota(0);
			notasCalificables.setRegistromatriculas(g);
			List<Notascalificables> tmp = notascalificablesFacade
					.findByLike("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
							+ g.getIdregistromatriculas()
							+ " AND N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
							+ relacionnotaslogrosdimensionboletinSeleccionada
									.getIdrelacionnotaslogrosdimensionboletin());
			if (tmp != null && !tmp.isEmpty()) {
				notasCalificables.setNotascalificables(tmp.get(0));
				notasCalificables.setNota(tmp.get(0).getValor());
			} else {
				Notascalificables notascalificablesTMP = new Notascalificables(
						new Long(0));
				notascalificablesTMP.setRegistromatriculas(g);
				notascalificablesTMP.setValor(new Short("0"));
				notascalificablesTMP.setFecharegistro(new Date());
				notascalificablesTMP.setRelacionnotaslogrosdimensionboletin(relacionnotaslogrosdimensionboletin);
				notasCalificables.setNotascalificables(notascalificablesTMP);
			}
			dataListNotasCalificables.add(notasCalificables);
		}

	}

	private List<NotasCalificablesNueva> dataListNotasCalificables;

	public List<NotasCalificablesNueva> getDataListNotasCalificables() {
		return dataListNotasCalificables;
	}

	public void setDataListNotasCalificables(
			List<NotasCalificablesNueva> dataListNotasCalificables) {
		this.dataListNotasCalificables = dataListNotasCalificables;
	}

	public void guardarNotas() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage();
		boolean tmp = false;
		try {
			for (NotasCalificablesNueva nc : dataListNotasCalificables) {
				if (nc.getNota() <= 100 && nc.getNota() >-1) {
					nc.getNotascalificables().setValor(new Short(nc.getNota() + ""));
					if (nc.getNotascalificables().getIdnotascalificables() == 0) {
						try {
							notascalificablesFacade.create(nc.getNotascalificables());
						} catch (Exception e) {
							System.out.print("paila" + e.getMessage());
						}
					} else {
						notascalificablesFacade.edit(nc.getNotascalificables());
					}
					tmp = true;
				} else {
					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Eror",
									"Las notas mayores a 100 o menores a 0 no se cargaron"));
				}
			}
			if (tmp) {
				context.addMessage(null, new FacesMessage("Exitoso",
						"Se subieron las notas correctamente"));
			}
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("Error",
					"No se pudieron cargar las notas, inténtalo de nuevo"));
		}
	}

	// Metodo para eliminar una actividad
	public void eliminarNota(
			Relacionnotaslogrosdimensionboletin relacionnotasrelacionlogrosdimension) {
		try {
			userTransaction.begin();
		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		}
		try {
			Notascalificables sd;
			notascalificablesFacade
					.metodo("DELETE FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
							+ relacionnotasrelacionlogrosdimension
									.getIdrelacionnotaslogrosdimensionboletin());
			notascalificablesFacade
					.metodo("DELETE FROM Notascalificables N WHERE N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
							+ relacionnotasrelacionlogrosdimension
									.getIdrelacionnotaslogrosdimensionboletin());
		} catch (Exception e) {
			System.out.print(e.getStackTrace());

		}
		try {
			relacionnotaslogrosdimensionboletinFacade
					.remove(relacionnotasrelacionlogrosdimension);
			dataListdataListRelacionnotaslogrosdimensionboletin
					.remove(relacionnotasrelacionlogrosdimension);
		} catch (Exception e) {
			System.out.print(e.getStackTrace());
		}
		try {
			userTransaction.commit();
		} catch (Exception e) {
		}
	}

	// Metodo para seleccionar una nota calificable, ya sea para crearla o si ya
	// est�� creada entonces para editarla
	public void seleccionarNotaCalificableEditar(
			Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinEditar) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlOutputText titulo = (HtmlOutputText) root.findComponent(
				"formCrearNotasCalificables").findComponent("tituloPopUp");
		HtmlInputText nombreNotaNueva = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"nombreNotaCalificar");
		HtmlInputText inputFechaInicio = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"fechaNotaCalificarInicio");
		HtmlInputText inputFechaFin = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"fechaNotaCalificarFin");

		DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");

		if (relacionnotaslogrosdimensionboletinEditar == null) {
			titulo.setValue("Agregar Actividad");
			this.relacionnotaslogrosdimensionboletinEditar = null;
			nombreNotaNueva.setValue("");
			inputFechaInicio.setValue("");
			inputFechaFin.setValue("");
		} else {
			titulo.setValue("Editar Actividad");

			this.relacionnotaslogrosdimensionboletinEditar = relacionnotaslogrosdimensionboletinEditar;
			nombreNotaNueva.setValue(relacionnotaslogrosdimensionboletinEditar
					.getNombre());

			if (relacionnotaslogrosdimensionboletinEditar.getFechainicio() != null) {
				inputFechaInicio.setValue(fecha
						.format(relacionnotaslogrosdimensionboletinEditar
								.getFechainicio()));
			} else {
				inputFechaInicio.setValue("");
			}

			if (relacionnotaslogrosdimensionboletinEditar.getFechafin() != null) {
				inputFechaFin.setValue(fecha
						.format(relacionnotaslogrosdimensionboletinEditar
								.getFechafin()));
			} else {
				inputFechaFin.setValue("");
			}

		}
	}

	// Metodo para agregar una nota nueva
	public void agregarNotaCalificar() throws ParseException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlInputText input = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"nombreNotaCalificar");
		HtmlInputText inputFechaInicio = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"fechaNotaCalificarInicio");
		HtmlInputText inputFechaFin = (HtmlInputText) root.findComponent(
				"formCrearNotasCalificables").findComponent(
				"fechaNotaCalificarFin");

		if (relacionNotasDimensionSeleccionada == null) {
			throw new ValidatorException(new FacesMessage(
					"No hay ninguna nota seleccionada"));
		}

		DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
		fecha.setTimeZone(TimeZone.getTimeZone("GMT-5"));
		//
		if (relacionnotaslogrosdimensionboletinEditar != null) {
			relacionnotaslogrosdimensionboletinEditar.setNombre(input
					.getValue().toString());
			// Validamos si hay fecha de inicio
			if (inputFechaFin.getValue() != null
					&& inputFechaFin.getValue().toString().trim()
							.replaceAll(" ", "").length() > 0) {
				relacionnotaslogrosdimensionboletinEditar.setFechafin(fecha
						.parse(inputFechaFin.getValue().toString()));
			}
			// Validamos si hay fecha de fin
			if (inputFechaInicio.getValue() != null
					&& inputFechaInicio.getValue().toString().trim()
							.replaceAll(" ", "").length() > 0) {
				relacionnotaslogrosdimensionboletinEditar.setFechainicio(fecha
						.parse(inputFechaInicio.getValue().toString()));
			}
			relacionnotaslogrosdimensionboletinEditar.setNombre(input
					.getValue().toString());
			relacionnotaslogrosdimensionboletinFacade
					.edit(relacionnotaslogrosdimensionboletinEditar);
		} else {

			// Creamos la Relacionnotaslogrosdimensionboletin
			Relacionnotaslogrosdimensionboletin tmp = new Relacionnotaslogrosdimensionboletin(
					new Long(0));
			tmp.setRelacionnotasdimension(relacionNotasDimensionSeleccionada);
			tmp.setNombre(input.getValue().toString());

			// Validamos si hay fecha de inicio
			if (inputFechaFin.getValue() != null
					&& inputFechaFin.getValue().toString().trim()
							.replaceAll(" ", "").length() > 0) {
				tmp.setFechafin(fecha
						.parse(inputFechaFin.getValue().toString()));
			}

			// Validamos si hay fecha de fin
			if (inputFechaInicio.getValue() != null
					&& inputFechaInicio.getValue().toString().trim()
							.replaceAll(" ", "").length() > 0) {
				tmp.setFechainicio(fecha.parse(inputFechaInicio.getValue()
						.toString()));
			}

			tmp.setFechacreacion(new Date());

			relacionnotaslogrosdimensionboletinFacade.create(tmp);
			dataListdataListRelacionnotaslogrosdimensionboletin = null;
		}
	}

	// ##ESTUDIANTES NOTAS
	// Propiedades de la lista de dataListRelacionnotaslogrosdimensionboletin
	public List<Relacionnotaslogrosdimensionboletin> getDataListRelacionnotaslogrosdimensionboletin() {
		if (dataListdataListRelacionnotaslogrosdimensionboletin == null) {
			if (relacionNotasDimensionSeleccionada != null) {
				dataListdataListRelacionnotaslogrosdimensionboletin = relacionnotaslogrosdimensionboletinFacade
						.findByLike("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
								+ relacionNotasDimensionSeleccionada
										.getIdrelacionnotasdimesion()
								+ " ORDER BY R.nombre");
			}
		}
		return dataListdataListRelacionnotaslogrosdimensionboletin;
	}

	// ###Recuperaciones
	// Metodo para saber si podemos seleccionar las recuperaciones
	public boolean isPeriodoSeleccionado() {
		if (periodoSeleccionado == null) {
			return false;
		}

		return true;
	}

	// Metodo para decir que vamos a seleccionar una recuperacion
	public void seleccionaRecuperaciones() {
		dataListdataListRelacionnotaslogrosdimensionboletin = null;
		relacionNotasDimensionSeleccionada = null;
		recuperacionesSeleccionado = true;
	}

	// Lista de las recuperaciones
	public List<Recuperaciones> getDataListRecuperaciones() {
		if (dataListRecuperaciones == null && periodoSeleccionado != null
				&& recuperacionesSeleccionado) {
			dataListRecuperaciones = recuperacionesFacade
					.findByLike("SELECT R from Recuperaciones R ORDER BY 1");
		}
		return dataListRecuperaciones;
	}

	// ###USUARIOS DE LOS CURSOS
	// Lista de los estudiantes por curso
	public List<Registromatriculas> getDataListRegistroMatricula() {
		return dataListRegistroMatricula;
	}

	public void setDataListRegistroMatricula(
			List<Registromatriculas> dataListRegistroMatricula) {
		this.dataListRegistroMatricula = dataListRegistroMatricula;
	}

//	 Propiedades de la calificacion del estudiantes por actividad
//	 public void setNotaCalificable(Short valorNota) {
//	 FacesContext facesContext = FacesContext.getCurrentInstance();
//	 UIViewRoot root = facesContext.getViewRoot();
//	 UIData tablaEstudiantes = (UIData)
//	 root.findComponent("formRegistroMatricula").findComponent("tablaEstudiantes");
//	
//	 Registromatriculas registromatriculas = (Registromatriculas)
//	 tablaEstudiantes.getRowData();
//	 //
//	 List<Notascalificables> tmp =
//	 notascalificablesFacade.findByLike("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
//	 + registromatriculas.getIdregistromatriculas() +
//	 " AND N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
//	 +
//	 relacionnotaslogrosdimensionboletinSeleccionada.getIdrelacionnotaslogrosdimensionboletin());
//	
//	 if (valorNota != null && valorNota > 100) {
//	 FacesMessage msg = new
//	 FacesMessage("La nota no puede ser mayor que 100");
//	 msg.setSeverity(msg.SEVERITY_ERROR);
//	 FacesContext.getCurrentInstance().addMessage(null, msg);
//	 } else {
//	 if (valorNota != null) {
//	 if (tmp != null && !tmp.isEmpty()) {
//	 tmp.get(0).setValor(valorNota);
//	 tmp.get(0).setFecharegistro(new Date());
//	 notascalificablesFacade.edit(tmp.get(0));
//	 } else {
//	 Notascalificables notascalificables = new Notascalificables(new Long(0));
//	 notascalificables.setRelacionnotaslogrosdimensionboletin(relacionnotaslogrosdimensionboletinSeleccionada);
//	 notascalificables.setRegistromatriculas(registromatriculas);
//	 notascalificables.setValor(valorNota);
//	 notascalificables.setFecharegistro(new Date());
//	
//	 notascalificablesFacade.create(notascalificables);
//	 }
//	
//	
//	 FacesMessage msg = new FacesMessage("Se ha actualizado la nota");
//	 FacesContext.getCurrentInstance().addMessage(null, msg);
//	 }
//	 }
//	 }
//	
//	 public Short getNotaCalificable() {
//	 FacesContext facesContext = FacesContext.getCurrentInstance();
//	 UIViewRoot root = facesContext.getViewRoot();
//	 UIData tablaEstudiantes = (UIData)
//	 root.findComponent("formRegistroMatricula").findComponent("tablaEstudiantes");
//	
//	 Registromatriculas registromatriculas = (Registromatriculas)
//	 tablaEstudiantes.getRowData();
//	
//	 List<Notascalificables> tmp =
//	 notascalificablesFacade.findByLike("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
//	 + registromatriculas.getIdregistromatriculas() +
//	 " AND N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
//	 +
//	 relacionnotaslogrosdimensionboletinSeleccionada.getIdrelacionnotaslogrosdimensionboletin());
//	
//	 if (tmp != null && !tmp.isEmpty()) {
//	 return tmp.get(0).getValor();
//	 }
//	
//	 return 0;
//	
//	
//	 // return 0;
//	 }

	// ###valorCheckLogroActividad
	public boolean isValorCheckLogroActividad() {
		// Vemos si se ha seleccionado el logro
		if (relacionLogrosDimensionesSeleccionado == null) {
			return false;
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData actividades = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaRelacionlogrosdimensionboletin");
		Relacionnotaslogrosdimensionboletin rnlTMP = (Relacionnotaslogrosdimensionboletin) actividades
				.getRowData();

		List<Relacionlogrosdimensiones> tmp = relacionlogrosdimensionesFacade
				.findByLike("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = "
						+ relacionLogrosDimensionesSeleccionado
								.getIdrelacionlogrosdimensiones()
						+ " AND R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
						+ rnlTMP.getIdrelacionnotaslogrosdimensionboletin());
		if (tmp != null && !tmp.isEmpty()) {
			return true;
		}

		return false;
	}

	public void setValorCheckLogroActividad(boolean valorCheckLogroActividad) {

		// Vemos si se puede guardar el registro
		if (relacionLogrosDimensionesSeleccionado != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot root = facesContext.getViewRoot();
			UIData actividades = (UIData) root.findComponent("formPrincipal")
					.findComponent("tablaRelacionlogrosdimensionboletin");
			Relacionnotaslogrosdimensionboletin rnlTMP = (Relacionnotaslogrosdimensionboletin) actividades
					.getRowData();

			// relacionLogrosDimensionesSeleccionado
			List<Relacionlogrosdimensiones> tmp = relacionlogrosdimensionesFacade
					.findByLike("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = "
							+ relacionLogrosDimensionesSeleccionado
									.getIdrelacionlogrosdimensiones()
							+ " AND R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
							+ rnlTMP.getIdrelacionnotaslogrosdimensionboletin());

			if (tmp != null && !tmp.isEmpty()) {
				relacionlogrosdimensionesFacade
						.metodo("DELETE FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = "
								+ relacionLogrosDimensionesSeleccionado
										.getIdrelacionlogrosdimensiones()
								+ " AND R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = "
								+ rnlTMP.getIdrelacionnotaslogrosdimensionboletin());
			} else {
				Relacionlogrosnotasdimension relacionlogrosnotasdimension = new Relacionlogrosnotasdimension(
						new Long(0));
				relacionlogrosnotasdimension
						.setRelacionnotaslogrosdimensionboletin(rnlTMP);
				relacionlogrosnotasdimension
						.setRelacionlogrosdimension(relacionLogrosDimensionesSeleccionado);
				relacionlogrosnotasdimensionFacade
						.create(relacionlogrosnotasdimension);
			}
		}
		// this.valorCheckLogroActividad = valorCheckLogroActividad;
	}

	// ###valorCheckLogroActividad
	public boolean isValorCheckLogroRecuperacion() {
		// Vemos si se ha seleccionado el logro
		if (relacionLogrosDimensionesSeleccionado == null
				|| periodoSeleccionado == null) {
			return false;
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData recuperaciones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tablaRecuperaciones");
		Recuperaciones rnlTMP = (Recuperaciones) recuperaciones.getRowData();

		List<Relacionlogrosrecuperaciones> tmp = relacionlogrosrecuperacionesFacade
				.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
						+ relacionLogrosDimensionesSeleccionado
								.getIdrelacionlogrosdimensiones()
						+ " AND R.recuperaciones.idrecuperaciones = "
						+ rnlTMP.getIdrecuperaciones()
						+ " AND R.periodos.idperiodos = "
						+ periodoSeleccionado.getIdperiodos());

		// System.out.print(tmp.size() + " TAMA��O de la LISTA ");

		if (tmp != null && !tmp.isEmpty()) {
			return true;
		}

		return false;
	}

	public void setValorCheckLogroRecuperacion(boolean valorCheckLogroActividad) {

		// Vemos si se puede guardar el registro
		if (relacionLogrosDimensionesSeleccionado != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot root = facesContext.getViewRoot();
			UIData recuperaciones = (UIData) root
					.findComponent("formPrincipal").findComponent(
							"tablaRecuperaciones");
			Recuperaciones rnlTMP = (Recuperaciones) recuperaciones
					.getRowData();

			// relacionLogrosDimensionesSeleccionado
			List<Relacionlogrosrecuperaciones> tmp = relacionlogrosrecuperacionesFacade
					.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
							+ relacionLogrosDimensionesSeleccionado
									.getIdrelacionlogrosdimensiones()
							+ " AND R.recuperaciones.idrecuperaciones = "
							+ rnlTMP.getIdrecuperaciones()
							+ " AND R.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos());

			if (tmp != null && !tmp.isEmpty()) {
				relacionlogrosrecuperacionesFacade
						.metodo("DELETE FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
								+ relacionLogrosDimensionesSeleccionado
										.getIdrelacionlogrosdimensiones()
								+ " AND R.recuperaciones.idrecuperaciones = "
								+ rnlTMP.getIdrecuperaciones()
								+ " AND R.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos());
			} else {
				Relacionlogrosrecuperaciones relacionlogrosnotasdimension = new Relacionlogrosrecuperaciones(
						new Long(0));
				relacionlogrosnotasdimension
						.setRelacionlogrosdimensiones(relacionLogrosDimensionesSeleccionado);
				relacionlogrosnotasdimension.setPeriodos(periodoSeleccionado);
				relacionlogrosnotasdimension.setRecuperaciones(rnlTMP);
				relacionlogrosrecuperacionesFacade
						.create(relacionlogrosnotasdimension);
			}
		}
	}
}
