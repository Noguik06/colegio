/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.notas;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import sun.reflect.generics.visitor.Reifier;
import jsf.administracion.rolesManager;
import jsf.usuarios.Sesiones;
import clasesAyuda.converterCapitalLetter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import ejb.AnosacademicosFacade;
import ejb.AsignaturasFacade;
import ejb.ConfiguracionesFacade;
import ejb.CursosFacade;
import ejb.DefinitivasasignaturasperiodosFacade;
import ejb.DimensionesFacade;
import ejb.EstudiantesFacade;
import ejb.FallasFacade;
import ejb.NotascalificablesFacade;
import ejb.ObservacionesboletinesFacade;
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
import ejb.RelacionrecuperacionregistromatriculasFacade;
import ejb.RetardosFacade;
import entities.Anosacademicos;
import entities.Asignaturas;
import entities.Cursos;
import entities.Definitivasasignaturasperiodos;
import entities.Dimensiones;
import entities.Estudiantes;
import entities.Fallas;
import entities.Logros;
import entities.Notascalificables;
import entities.Observacionesboletines;
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
import entities.Relacionrecuperacionregistromatriculas;
import entities.Retardos;

/**
 * 
 * @author juannoguera
 */
@ManagedBean(name = "notasEstudiantes")
@ViewScoped
public class NotasEstudiantes implements Serializable {

	/**
	 * Creates a new instance of NewJSFManagedBean
	 */
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private RelaciondimensionesasignaturasanoFacade relaciondimensionesasignaturasanoFacade;
	@EJB
	private RelacionnotasdimensionFacade relacionnotasdimensionFacade;
	@EJB
	private AsignaturasFacade asignaturasFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private EstudiantesFacade estudiantesFacade;
	@EJB
	private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
	@EJB
	private RelacionnotaslogrosdimensionboletinFacade relacionnotaslogrosdimensionboletinFacade;
	@EJB
	private NotascalificablesFacade notascalificablesFacade;
	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private PeriodosFacade periodosFacade;
	@EJB
	private RecuperacionesFacade recuperacionesFacade;
	@EJB
	private RelacionrecuperacionregistromatriculasFacade relacionrecuperacionregistromatriculasFacade;
	@EJB
	private RelacionlogrosdimensionesFacade relacionlogrosdimensionesFacade;
	@EJB
	private RelacionlogrosnotasdimensionFacade relacionlogrosnotasdimensionFacade;
	@EJB
	private ObservacionesboletinesFacade observacionesboletinesFacade;
	@EJB
	private FallasFacade fallasFacade;
	@EJB
	private RetardosFacade retardosFacade;
	@EJB
	private RelacionlogrosrecuperacionesFacade relacionlogrosrecuperacionesFacade;
	@EJB
	private DefinitivasasignaturasperiodosFacade definitivasasignaturasperiodosFacade;
	@EJB
	private ConfiguracionesFacade configuracionesFacade;
	@EJB
	private DimensionesFacade dimensionesFacade;
	// ##ANOSACADEMICOS
	// Anoacademico actual
	private Anosacademicos anosacademicosActual;
	// Estudiante seleccionado
	private Estudiantes estudiantesSeleccionado;
	// ##REGISTRO MATRICULA
	private Registromatriculas registromatriculas;
	// ###LISTA DE LAS ASIGNATURAS
	private List<Relacionasignaturaperiodos> dataListAsignaturas;
	// ##LISTA DE LAS ASIGNATURAS REAL
	// private List<AsignaturasEstudiantes> dataListAsignturasEstudiantes;
	private List<AsignaturasEstudiantes> dataListAsignturasEstudiantes;
	//##LISTA ANOS ACADEMICOS
	//Lista de los anos academicos
	private List<Anosacademicos> dataListAnosAcademicos;
	//Objeto para saber si queremos ver los informes de un año en especial o cualquiera
	private Anosacademicos anosacademicosManual;
	// ##LISTA DE LOS CURSOS
	// Lista de los cursos que este profesor tiene acceso
	private List<Cursos> dataListCursos;
	// Curso seleccionado
	private Cursos cursoSeleccionado;
	// ##LISTA DE LOS REGISTROS DE MATRICULAS
	private List<Registromatriculas> dataListRegistroMatriculas;
	// ##Lista de los registros de las matricular
	// private List<RegistroMatriculasEstudiantes>
	// dataListRegistroMatriculasEstudiantes;
	private List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasEstudiantes;
	// ##LISTA PARA LOS HEADERS
	private List<PeriodosEstudiantes> dataListPeriodosEstudiantesHeader;
	// ##PERIODOS
	// Bandera para ver los periodos
	private boolean banderaPeriodos = false;
	// Lista de los periodos
	private List<Periodos> dataListPeriodos;
	// Periodos escogido
	private Periodos periodoSeleccionado;
	// ##ASIGNATURA SELECCIONADA
	private Relacionasignaturaperiodos asignaturaSeleccionada;
	//##Definitivas
	//Lista para guardar las definitivas y mostrarlas
	private List<Definitivas> dataListDefinitivas;
	//Objeto para mostrar los encabezados
	private Definitivas definitivasHeader;
	//Variables para saber si el usuario logueado es administrador
	private Boolean administrador; 
	//Convertidor
	private converterCapitalLetter capitalLetter= new converterCapitalLetter();
	//Lista de las recuperaciones
	List<Recuperaciones> dataListRecuperaciones;

	public NotasEstudiantes() {
	}

	public Anosacademicos getCurrentYear() {
		if (anosacademicosActual == null) {
			anosacademicosActual = anosacademicosFacade
					.findByLikeAll(
							"SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true")
					.get(0);
		}

		return anosacademicosActual;
	}

	// Funcion utilizada para devolver la sesión que estamos manejando
	public Sesiones getSesion() {
		FacesContext FCInstance = FacesContext.getCurrentInstance();
		String theBeanName = "sesiones";
		Object bean = FCInstance.getELContext().getELResolver()
				.getValue(FCInstance.getELContext(), null, theBeanName);
		Sesiones sesiones = (Sesiones) bean;

		if (sesiones.getUsuarios() == null) {
			return null;
		}

		List<Estudiantes> tmp = estudiantesFacade
				.findByLikeAll("SELECT E FROM Estudiantes E WHERE E.usuarios.idusuarios = "
						+ sesiones.getUsuarios().getIdusuarios());
		if (!tmp.isEmpty()) {
			estudiantesSeleccionado = tmp.get(0);
			List<Registromatriculas> tmp2 = registromatriculasFacade
					.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.anosacademicos.idanosacademicos ="
							+ getCurrentYear().getIdanosacademicos()
							+ " AND R.estudiantes.idestudiantes = "
							+ estudiantesSeleccionado.getIdestudiantes());
			if (!tmp2.isEmpty()) {
				registromatriculas = tmp2.get(0);
			}
		}
		return sesiones;
	}

	private void calcular() {
		if (estudiantesSeleccionado != null && dataListAsignaturas == null
				&& registromatriculas != null && periodoSeleccionado != null
				&& periodoSeleccionado.getTipo() == 0) {
			dataListAsignaturas = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
							+ registromatriculas.getCursos().getIdcursos()
							+ " AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
							+ getCurrentYear().getIdanosacademicos()
							+ " ORDER BY "
							+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");

			for (Relacionasignaturaperiodos rap : dataListAsignaturas) {
				List<Periodos> dataListPeriodosTmp = periodosFacade
						.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = "
								+ anosacademicosActual.getIdanosacademicos()
								+ " ORDER BY P.fechafin");
				double promedioAsignatura = 0;

				// Creamos un nuevo objeto tipo AsignaturasEstudiantes
				AsignaturasEstudiantes asignaturasEstudiantesTmp = new AsignaturasEstudiantes();
				// Le agregamos la asignatura que estamos calculando
				asignaturasEstudiantesTmp.setAsignaturas(rap.getAsignaturas());
				// Creamos una lista nuevo para los periodos
				List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();
				asignaturasEstudiantesTmp
						.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

				// Validamos si no hay ningún periodo
				if (dataListPeriodosTmp.isEmpty()) {
					asignaturasEstudiantesTmp.setValor(100);
				}
				// Ahora vamos a recorrer los periodos y a agregarles la s
				// asignaturas
				for (Periodos p : dataListPeriodosTmp) {
					if(p.getTipo() == 0){
						List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
								.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
										+ rap.getIdrelacionasignaturaperiodos()
										+ " AND R.cursos.idcursos = "
										+ registromatriculas.getCursos()
												.getIdcursos()
										+ " ORDER BY R.dimensiones.iddimensiones");

						double promedioPeriodo = 0;
	
						PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
						// Agregamos el periodo
						periodosEstudiantes.setPeriodos(p);
						// Creamos una lista de las dimensiones que le vamos a
						// agregar a este periodo
						List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();
						// Agregamos esta lista al periodo
						periodosEstudiantes
								.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);
	
						// Agregamos a la lisa de los periodos esta dimensión
						dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);
	
						if (datalistDimensionesTmp.isEmpty()) {
							periodosEstudiantes.setValor(0);
						}
	
						// Recorremos la lista de las dimensiones para averiguar el
						// promedio de la dimensión en este periodo
						for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {
							// Cremos un objeto temporal tipo DimensionesEstudiantes
							DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
							// Le agregmos la dimensión
							dimensionesEstudiantesTmp.setDimensiones(rdaa
									.getDimensiones());
							// Agregamos a la lista de las dimensiones que creamos
							// antes esta nuevo dimensión
							dataListDimensionesEstudiantestmp
									.add(dimensionesEstudiantesTmp);
	
							// Hacemos una lista temporal de las dimensiones
							List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
							// Agregamos la lista que acabamos de crear
							dimensionesEstudiantesTmp
									.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);
	
							// Sacamos las notas que tiene que cada dimesion
							List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
									.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
											+ rdaa.getIdrelaciondimensionesasignaturasano()
											+ " AND R.periodos.idperiodos = "
											+ p.getIdperiodos());
							double promedioDimension = 0;
	
							if (tmp.isEmpty()) {
								dimensionesEstudiantesTmp.setValor(0);
							}
	
							// Recogemos la lista de logros
							dimensionesEstudiantesTmp
									.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
											+ p.getIdperiodos()
											+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
											+ rdaa.getIdrelaciondimensionesasignaturasano()));
	
							// Recorremos las notas de esta dimensión
							for (Relacionnotasdimension rnd : tmp) {
								// Hacemos un objeto temporal tipo
								// NotasDimensionesEstudiantes
								NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
								// Le agregamos la dimensión
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnd);
								// A la lista de las dimensiones le agregamos las
								// dimensiones
								dataListNotasDimensionesEstudiantes
										.add(notasDimensionesEstudiantes);
								// Creamos una lista temporal de actividades
								List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
								// Agregamos esta lista de actividades al objeto
								// creado anteriormente
								notasDimensionesEstudiantes
										.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);
	
								double promedioActividades = 0;
								List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
										.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
												+ rnd.getIdrelacionnotasdimesion());
	
								// Recorremos la lista de las actividades
								for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {
									ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
									dataListActividadesNotasEstudiantes
											.add(actividadesNotasEstudiantes);
	
									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getIdrelacionnotaslogrosdimensionboletin());
	
									if (!tmpNC.isEmpty()) {
										actividadesNotasEstudiantes
												.setNotascalificables(tmpNC.get(0));
										actividadesNotasEstudiantes.setValor(tmpNC
												.get(0).getValor());
										promedioActividades += tmpNC.get(0)
												.getValor();
									} else {
										Notascalificables notasCalificablesTmp = new Notascalificables();
										notasCalificablesTmp
												.setRelacionnotaslogrosdimensionboletin(rnld);
										actividadesNotasEstudiantes
												.setNotascalificables(notasCalificablesTmp);
										actividadesNotasEstudiantes.setValor(0);
									}
								}
	
								// Validamos que el tamaño de la lista no está
								// vcía
								if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
									promedioActividades = 100;
								} else {
									if (tmpRNLDB != null) {
										promedioActividades = new BigDecimal(
												promedioActividades
														/ tmpRNLDB.size())
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									} else {
										promedioActividades = 100;
									}
								}
	
								notasDimensionesEstudiantes
										.setValor(promedioActividades);
	
								promedioDimension = promedioDimension
										+ new BigDecimal((promedioActividades
												* rnd.getPorcentaje() / 100))
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
								dimensionesEstudiantesTmp
										.setValor(promedioDimension);
							}
	
							if (rdaa.getPorcentaje() != null) {
								promedioDimension = new BigDecimal(
										promedioDimension * rdaa.getPorcentaje()
												/ 100).setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								promedioPeriodo += promedioDimension;
								periodosEstudiantes.setValor(promedioPeriodo);
							} else {
								periodosEstudiantes.setValor(100);
	
							}
						}
						List<Recuperaciones> datalistRecuperacionesTMP = recuperacionesFacade
								.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");
	
						if (periodosEstudiantes.getValor() < 80) {
							List<Relacionrecuperacionregistromatriculas> tmpRM2 = new ArrayList<Relacionrecuperacionregistromatriculas>();
							for (Recuperaciones r : datalistRecuperacionesTMP) {
								List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
										.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.periodos.idperiodos = "
												+ p.getIdperiodos()
												+ " AND R.registromatriculas.idregistromatriculas = "
												+ registromatriculas
														.getIdregistromatriculas()
												+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
												+ rap.getIdrelacionasignaturaperiodos()
												+ " AND R.recuperaciones.idrecuperaciones = "
												+ r.getIdrecuperaciones());
								if (!tmpRM.isEmpty()) {
									periodosEstudiantes
											.setDataListRelacionrecuperacionregistromatriculas(tmpRM);
									if (tmpRM.get(0).getValor() > periodosEstudiantes
											.getValor()
											&& periodosEstudiantes.getValor() == 0) {
										periodosEstudiantes
												.setValor(new BigDecimal(
														(periodosEstudiantes
																.getValor() + tmpRM
																.get(0).getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue());
									} else {
										if (periodosEstudiantes.getValor() == 0) {
											periodosEstudiantes.setValor(tmpRM.get(
													0).getValor());
										}
									}
									tmpRM2.add(tmpRM.get(0));
								} else {
									Recuperaciones recuperaciones = new Recuperaciones(
											new Long(0));
									recuperaciones.setNombre("Recuperación");
									Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
									relacionrecuperacionregistromatriculas
											.setPeriodos(p);
									relacionrecuperacionregistromatriculas
											.setRelacionasignaturaperiodos(rap);
									relacionrecuperacionregistromatriculas
											.setValor(0.0);
									relacionrecuperacionregistromatriculas
											.setRecuperaciones(recuperaciones);
									tmpRM2.add(relacionrecuperacionregistromatriculas);
								}
								periodosEstudiantes
										.setDataListRelacionrecuperacionregistromatriculas(tmpRM2);
							}
						} else {
							List<Relacionrecuperacionregistromatriculas> tmpRM = new ArrayList<Relacionrecuperacionregistromatriculas>();
							for (Recuperaciones r : datalistRecuperacionesTMP) {
	
								Recuperaciones recuperaciones = new Recuperaciones(
										new Long(0));
								recuperaciones.setNombre("Recuperación");
								Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
								relacionrecuperacionregistromatriculas
										.setPeriodos(p);
								relacionrecuperacionregistromatriculas
										.setRelacionasignaturaperiodos(rap);
								relacionrecuperacionregistromatriculas
										.setValor(0.0);
								relacionrecuperacionregistromatriculas
										.setRecuperaciones(recuperaciones);
								tmpRM.add(relacionrecuperacionregistromatriculas);
							}
							periodosEstudiantes
									.setDataListRelacionrecuperacionregistromatriculas(tmpRM);
						}	
						promedioAsignatura += periodosEstudiantes.getValor();

					}else{
						List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
								.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
										+ rap.getIdrelacionasignaturaperiodos()
										+ " AND R.cursos.idcursos = "
										+ registromatriculas.getCursos().getIdcursos()
										+ " ORDER BY R.dimensiones.iddimensiones");

						double promedioPeriodo = 0;

						PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
						// Agregamos el periodo
						periodosEstudiantes.setPeriodos(p);
						// Creamos una lista de las dimensiones que le vamos a
						// agregar a este periodo
						List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();
						// Agregamos esta lista al periodo
						periodosEstudiantes
								.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);

						// Agregamos a la lisa de los periodos esta dimensión
						dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);

						if (datalistDimensionesTmp.isEmpty()) {
							periodosEstudiantes.setValor(0);
						}
		
						String query = "select sum(round(pepe.valordndos)),  "
								+ "pepe.relaciondimensionesasignaturasano, "
								+ "rdaa.porcentaje, "
								+ "round(cast(rdaa.porcentaje * ((case when sum(round(pepe.valordndos)) is null "
								+ "then 0 "
								+ "else cast(sum(round(pepe.valordndos)) as numeric) end)) as numeric)/100), "
								+ "rdaa.dimensiones "
								+ "from ("
								+ "select round(cast(rnd.porcentaje * ( cast ( sum(case when valor is null then 0 else valor end) as numeric)/count(1)) as numeric)/100) valordndos,  "
								+ "rnldb.relacionnotasdimension, "
								+ "rnd.relaciondimensionesasignaturasano "
								+ "from notascalificables nc "
								+ "right join relacionnotaslogrosdimensionboletin rnldb  on rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
								+ "and nc.registromatriculas =   " + registromatriculas.getIdregistromatriculas()  + " "
								+ "join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
								+ "and rnd.periodos = " + p.getIdperiodos() + " "
								+ "group by rnldb.relacionnotasdimension, rnd.porcentaje, rnd.relaciondimensionesasignaturasano) pepe "
								+ "join relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = pepe.relaciondimensionesasignaturasano "
								+ "where rdaa.relacionasignaturasperiodos =  " + rap.getIdrelacionasignaturaperiodos() + " "
								+ "and rdaa.cursos =  " + registromatriculas.getCursos().getIdcursos() + " "
								+ "group by relaciondimensionesasignaturasano, rdaa.porcentaje, rdaa.dimensiones "
								+ "order by rdaa.dimensiones ";
					
							List<Object[]> dataListDimensionesValor = relacionlogrosdimensionesFacade.findoAll(query);
							double tmp = 0;
							for(Object[] o:dataListDimensionesValor){
								DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
								
								dimensionesEstudiantesTmp
								.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
										+ p.getIdperiodos()
										+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
										+ Long.parseLong(o[1].toString())));
								
								dimensionesEstudiantesTmp.setDimensiones(dimensionesFacade.find(Long.parseLong(o[4].toString())));
								dataListDimensionesEstudiantestmp.add(dimensionesEstudiantesTmp);
								dimensionesEstudiantesTmp.setValor(Double.parseDouble(o[0].toString()));
								
								tmp += Double.parseDouble(o[3].toString());
							}
							if(tmp > 100){
								tmp = 100;
							}
							periodosEstudiantes.setValor(new BigDecimal(tmp)
							.setScale(0,
									BigDecimal.ROUND_UP)
							.doubleValue());
							
					}
	
					promedioAsignatura = new BigDecimal(promedioAsignatura
							/ dataListPeriodosEstudiantesTmp.size()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue();
					asignaturasEstudiantesTmp.setValor(promedioAsignatura);
	
					dataListAsignturasEstudiantes.add(asignaturasEstudiantesTmp);
				}
			}

			if (!dataListAsignturasEstudiantes.isEmpty()) {
				dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();
				for (AsignaturasEstudiantes as : dataListAsignturasEstudiantes) {
					for (PeriodosEstudiantes pe : as
							.getDataListPeriodosEstudiantes()) {
						dataListPeriodosEstudiantesHeader.add(pe);
					}
					break;
				}
			}
		}

	}

	public static class RegistroMatriculasEstudiantes {

		private Registromatriculas registromatriculas;
		private double valor = 0;
		private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
		private List<PeriodosEstudiantes> dataListPeriodosEstudiantes;

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
			return dataListDimensionesEstudiantes;
		}

		public void setDataListDimensionesEstudiantes(
				List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
			this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
		}

		public Registromatriculas getRegistromatriculas() {
			return registromatriculas;
		}

		public void setRegistromatriculas(Registromatriculas registromatriculas) {
			this.registromatriculas = registromatriculas;
		}

		public List<PeriodosEstudiantes> getDataListPeriodosEstudiantes() {
			return dataListPeriodosEstudiantes;
		}

		public void setDataListPeriodosEstudiantes(
				List<PeriodosEstudiantes> dataListPeriodosEstudiantes) {
			this.dataListPeriodosEstudiantes = dataListPeriodosEstudiantes;
		}
	}

	public static class AsignaturasEstudiantes {

		private Asignaturas asignaturas;
		private double valor = 0;
		private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
		private List<PeriodosEstudiantes> dataListPeriodosEstudiantes;

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
			return dataListDimensionesEstudiantes;
		}

		public void setDataListDimensionesEstudiantes(
				List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
			this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
		}

		public Asignaturas getAsignaturas() {
			return asignaturas;
		}

		public void setAsignaturas(Asignaturas asignaturas) {
			this.asignaturas = asignaturas;
		}

		public List<PeriodosEstudiantes> getDataListPeriodosEstudiantes() {
			return dataListPeriodosEstudiantes;
		}

		public void setDataListPeriodosEstudiantes(
				List<PeriodosEstudiantes> dataListPeriodosEstudiantes) {
			this.dataListPeriodosEstudiantes = dataListPeriodosEstudiantes;
		}
	}

	public class PeriodosEstudiantes {

		private double valor = 0;
		private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
		private Periodos periodos;
		private List<Relacionrecuperacionregistromatriculas> dataListRelacionrecuperacionregistromatriculas;

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
			return dataListDimensionesEstudiantes;
		}

		public void setDataListDimensionesEstudiantes(
				List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
			this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
		}

		public Periodos getPeriodos() {
			return periodos;
		}

		public void setPeriodos(Periodos periodos) {
			this.periodos = periodos;
		}

		public List<Relacionrecuperacionregistromatriculas> getDataListRelacionrecuperacionregistromatriculas() {
			return dataListRelacionrecuperacionregistromatriculas;
		}

		public void setDataListRelacionrecuperacionregistromatriculas(
				List<Relacionrecuperacionregistromatriculas> dataListRelacionrecuperacionregistromatriculas) {
			this.dataListRelacionrecuperacionregistromatriculas = dataListRelacionrecuperacionregistromatriculas;
		}
	}

	public class DimensionesEstudiantes {

		private double valor = 0;
		private Dimensiones dimensiones;
		private List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes;
		private List<Relacionlogrosdimensiones> dataListRelacionlogrosdimensiones;

		public Dimensiones getDimensiones() {
			return dimensiones;
		}

		public void setDimensiones(Dimensiones dimensiones) {
			this.dimensiones = dimensiones;
		}

		public List<NotasDimensionesEstudiantes> getDataListNotasDimensionesEstudiantes() {
			return dataListNotasDimensionesEstudiantes;
		}

		public void setDataListNotasDimensionesEstudiantes(
				List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes) {
			this.dataListNotasDimensionesEstudiantes = dataListNotasDimensionesEstudiantes;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public List<Relacionlogrosdimensiones> getDataListRelacionlogrosdimensiones() {
			return dataListRelacionlogrosdimensiones;
		}

		public void setDataListRelacionlogrosdimensiones(
				List<Relacionlogrosdimensiones> dataListRelacionlogrosdimensiones) {
			this.dataListRelacionlogrosdimensiones = dataListRelacionlogrosdimensiones;
		}
	}

	public class NotasDimensionesEstudiantes {

		private Relacionnotasdimension relacionnotasdimension;
		private double valor = 0;
		private List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes;

		public Relacionnotasdimension getRelacionnotasdimension() {
			return relacionnotasdimension;
		}

		public void setRelacionnotasdimension(
				Relacionnotasdimension relacionnotasdimension) {
			this.relacionnotasdimension = relacionnotasdimension;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

		public List<ActividadesNotasEstudiantes> getDataListActividadesNotasEstudiantes() {
			return dataListActividadesNotasEstudiantes;
		}

		public void setDataListActividadesNotasEstudiantes(
				List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes) {
			this.dataListActividadesNotasEstudiantes = dataListActividadesNotasEstudiantes;
		}
	}

	public class ActividadesNotasEstudiantes {

		private Notascalificables notascalificables;
		private double valor = 0;

		public Notascalificables getNotascalificables() {
			return notascalificables;
		}

		public void setNotascalificables(Notascalificables notascalificables) {
			this.notascalificables = notascalificables;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}
	}

	public List<AsignaturasEstudiantes> getDataListAsignturasEstudiantes() {
		if (periodoSeleccionado != null && periodoSeleccionado.getTipo() == 1) {
			return null;
		}
		if (dataListAsignturasEstudiantes == null) {
			getSesion();
			if(estudiantesSeleccionado != null){
				periodoSeleccionado  = new Periodos();
				periodoSeleccionado.setTipo(0);
			}
			dataListAsignturasEstudiantes = new ArrayList<AsignaturasEstudiantes>();
			calcular();
		}

		return dataListAsignturasEstudiantes;
	}

	// Metodo para obtener la sesión de la persona que se ha logueado
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

	// ##Método para que los profesores vean las notas de los alumnos
	// ###PROPIEDADES DE LA LISTA DE CURSOS
	public List<Cursos> getDataListCursos() {
		Sesiones sesiones = getSesiones();
		if (sesiones == null || sesiones.getUsuarios() == null) {
			return null;
		}
		if (dataListCursos == null || dataListCursos.isEmpty()) {
			//Se valida si son administradores
			if(getSesiones().isAdministrador()){
				dataListCursos = cursosFacade
						.findByLikeAll("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R"
								+ " WHERE " 
								+ " R.cursos.anosacademicos.estadoactivo = true"
								+ " ORDER BY R.cursos.grados.numero");
			}else{
				//Se validan si son profesores
				dataListCursos = cursosFacade
						.findByLikeAll("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R"
								+ " WHERE "
								+ " ((R.profesores.usuarios.idusuarios = " + sesiones.getUsuarios().getIdusuarios()  
								+ " AND  R.cursos.anosacademicos.estadoactivo = true ) OR R.cursos.profesor.usuarios.idusuarios = "
										+ sesiones.getUsuarios().getIdusuarios()
										+ ") AND  R.cursos.anosacademicos.estadoactivo = true"
										+ " "
										+ "ORDER BY R.cursos.grados.numero");
			}
			
			
		}
		return dataListCursos;
	}

	public void escogerCurso(Cursos cursos) {

		banderaPeriodos = false;

		this.periodoSeleccionado = null;

		this.cursoSeleccionado = cursos;
		if(getSesiones().isAdministrador()){
			dataListAsignaturas = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " AND RPA.cursos.anosacademicos.estadoactivo = true"
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}else{
			//Validamos si es profesor
			dataListAsignaturas = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA "
							+ "WHERE RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " AND "
							+ "(RPA.profesores.usuarios.idusuarios = "
							+ getSesiones().getUsuarios().getIdusuarios()
							+ " OR RPA.cursos.profesor.usuarios.idusuarios = " + getSesiones().getUsuarios().getIdusuarios() + " "
							+ ") AND RPA.cursos.anosacademicos.estadoactivo = true"
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}
		// registromatriculas.get
		this.dataListRegistroMatriculas = registromatriculasFacade
				.findByLikeAll("SELECT R FROM Registromatriculas R "
						+ "WHERE R.cursos.idcursos = "
						+ cursos.getIdcursos()
						+ " AND R.fecharetiro is null "
						+ " ORDER BY R.estudiantes.usuarios.apellidos");

		this.dataListRegistroMatriculasEstudiantes = null;

	}

	// ##PERIODOS
	// Método para saber si los periodos se renderizan o no
	public boolean isBanderaPeriodos() {
		return banderaPeriodos;
	}

	// Método para escoger el curso
	public void escogerPeriodo(Periodos periodos) {
		periodoSeleccionado = periodos;
		dataListRegistroMatriculasEstudiantes = null;
		//Validamos si es un periodo normal o es el final
		if (periodos.getTipo() == 0){
			dataListDefinitivas = null;
			//Validamos si la forma como se hace el calculo es nuevo o es antigua
			if(periodos.getCalculo() == 1){
				dataListRegistroMatriculasEstudiantes = getDataListRegistroMatriculasEstudiantes();
			}else{
				dataListRegistroMatriculasEstudiantes = getDataListRegistroMatriculasEstudiantes_antiguo();
			}
		}else{
			dataListRegistroMatriculasEstudiantes = null;
			calcularDefinitivasPorAsignatura(null, null, null);
		}

	}

	// Lista de los cursos
	public List<Periodos> getDataListPeriodos() {
		try{
		if (dataListPeriodos == null 
				&& cursoSeleccionado != null) {
			
			if(anosacademicosManual == null){
				dataListPeriodos = periodosFacade
						.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.estadoactivo = true"
								+ " ORDER BY P.fechainicio");
			}else{
				dataListPeriodos = periodosFacade
						.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + anosacademicosManual.getIdanosacademicos()
								+ " ORDER BY P.fechainicio");
			}
		}
		}catch(Exception e){
			System.out.print("Error al consultar la lista de los periodos " + e);
		}
		return dataListPeriodos;
	}

	//Metodos para obtener las asignaturas del profesor
	public List<Relacionasignaturaperiodos> getDataListAsignaturas() {
		return dataListAsignaturas;
	}

	public void setDataListAsignaturas(
			List<Relacionasignaturaperiodos> dataListAsignaturas) {
		this.dataListAsignaturas = dataListAsignaturas;
	}

	//Metodo para escoger la asignatura
	public void escogerAsignatura(Relacionasignaturaperiodos rap) {
		asignaturaSeleccionada = rap;
		periodoSeleccionado = null;
		banderaPeriodos = true;
		this.dataListRegistroMatriculasEstudiantes = null;
	}
	
	//Propiedades para mostrar los encabezados de las notas de la asignatura en el periodo
	public List<PeriodosEstudiantes> getDataListPeriodosEstudiantesHeader() {
		return dataListPeriodosEstudiantesHeader;
	}

	public void setDataListPeriodosEstudiantesHeader(
			List<PeriodosEstudiantes> dataListPeriodosEstudiantesHeader) {
		this.dataListPeriodosEstudiantesHeader = dataListPeriodosEstudiantesHeader;
	}
	
	//Metodo para validar el indice de la columna
	public int validarEstiloColumna(PeriodosEstudiantes periodosEstudiantes){
		return dataListPeriodosEstudiantesHeader.indexOf(periodosEstudiantes)%2;
	}
	
	 
	
	//Método para calcular las recuperaciones
	public List<Recuperaciones> getDataListRecuperaciones(){
		if(dataListRecuperaciones == null){
			 dataListRecuperaciones = recuperacionesFacade
						.findByLike("SELECT R FROM Recuperaciones R");
		}
		return dataListRecuperaciones;
	}
	
	//Metodo para calcular la definitiva de la asignatura de todo el año
	public void calcularDefinitivasPorAsignatura(List<Registromatriculas> dataListRegMat, Relacionasignaturaperiodos relacionasignaturaperiodos, Cursos curso){
		dataListDefinitivas = new ArrayList<Definitivas>();
		//Verificamos si 
		if(dataListRegMat != null && relacionasignaturaperiodos != null){
			dataListRegistroMatriculas = dataListRegMat;
			asignaturaSeleccionada = relacionasignaturaperiodos;
			this.cursoSeleccionado = curso;
		}
		
		for (Registromatriculas rm : dataListRegistroMatriculas) {
			//Hacemos una variable tipo de la clase estática
			Definitivas definitivas = new Definitivas();
			//Hacemos una lista para empezar a colocar la Información de cada periodo
			List<Definitivasasignaturasperiodos> dataListDefinitivasasignaturasperiodos = new ArrayList<Definitivasasignaturasperiodos>();
			
			
			
			
			// Recorremos la lista de los peeriodos			
			for (Periodos p : getDataListPeriodos()){
				if(p.getTipo() == 0){
					List<Definitivasasignaturasperiodos> tmp = definitivasasignaturasperiodosFacade
					.findByLike("SELECT D FROM Definitivasasignaturasperiodos D WHERE "
							+ "D.periodos.idperiodos = "
							+ p.getIdperiodos()
							+ " AND D.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
							+ asignaturaSeleccionada.getIdrelacionasignaturaperiodos()
							+ " AND D.registromatricula.idregistromatriculas = "
							+ rm
									.getIdregistromatriculas());
					
					if(!tmp.isEmpty()){
						dataListDefinitivasasignaturasperiodos.add(tmp.get(0));
					}
				}
			}
			//Agregamos la lista de las definitivas
			definitivas.setDataListDefinitivasasignaturasperiodos(dataListDefinitivasasignaturasperiodos);
			//Agregamos el estudiante
			definitivas.setEstudiantes(rm.getEstudiantes());
			//Agregamos el registromatriculas
			definitivas.setRegistromatriculas(rm);
			//Calculamos la nota final
			int contador = 0;
			double promedioFinal = 0;
			for(Definitivasasignaturasperiodos def:dataListDefinitivasasignaturasperiodos){
				if(def.getPeriodos().getTipo() == 0){
					contador++;
					promedioFinal += (def.getValor() * def.getPeriodos().getValor()* 0.01);
				}
			}
			if (contador > 0) {
				definitivas.setDefinitiva(new BigDecimal((promedioFinal)).setScale(0, BigDecimal.ROUND_HALF_UP)
						.intValue());
			} else {
				definitivas.setDefinitiva(0);
			}
			
			//Nos aseguramos que si el Método que llama es el de recuperacines conteste sin haber incluído las recuperaciones
			if(dataListRegMat == null && relacionasignaturaperiodos == null){
				//Promediamos con las recuperaciones finales
				boolean bandera = true;
				List<Relacionrecuperacionregistromatriculas> tmpRReg = new ArrayList<Relacionrecuperacionregistromatriculas>();
				for (Recuperaciones r : getDataListRecuperaciones()) {
					List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
							.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
									+ r.getIdrecuperaciones()
									+ " AND R.periodos.idperiodos = "
									+ periodoSeleccionado.getIdperiodos()
									+ " AND R.registromatriculas.idregistromatriculas = "
									+ rm.getIdregistromatriculas()
									+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
									+ asignaturaSeleccionada
											.getIdrelacionasignaturaperiodos());
					if (bandera && definitivas.definitiva > 0 && !tmpRM.isEmpty() && definitivas.definitiva < tmpRM.get(0).getValor() 
							&& definitivas.definitiva < 65) {
						definitivas.setDefinitiva(new BigDecimal((definitivas.getDefinitiva() + tmpRM.get(0).getValor()) / 2).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
						if (bandera && definitivas.getDefinitiva() > 64) {
							bandera = false;
						}
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
						relacionrecuperacionregistromatriculas.setValor(tmpRM.get(0).getValor());
						tmpRReg.add(relacionrecuperacionregistromatriculas);
						definitivas.setDataListRelacionrecuperacionregistromatriculas(tmpRReg);
					}else{
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
						relacionrecuperacionregistromatriculas.setValor(0.0);
						tmpRReg.add(relacionrecuperacionregistromatriculas);
						definitivas.setDataListRelacionrecuperacionregistromatriculas(tmpRReg);
					}
				}
			}
			dataListDefinitivas.add(definitivas);
		}
		//
//		dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();

//		if (!dataListRegistroMatriculasEstudiantes.isEmpty()) {
//			for (PeriodosEstudiantes pe : dataListRegistroMatriculasEstudiantes
//					.get(0).getDataListPeriodosEstudiantes()) {
//				dataListPeriodosEstudiantesHeader.add(pe);
//			}
//		}
	}
	
	// Método para calcular las definitivas del periodo nuevo
	public List<RegistroMatriculasEstudiantes> getDataListRegistroMatriculasEstudiantes() {
		if (dataListRegistroMatriculasEstudiantes == null
				&& periodoSeleccionado != null && periodoSeleccionado.getTipo() == 0
				&& periodoSeleccionado.getCalculo() == 1) {

			List<Recuperaciones> dataListRecuperaciones = new ArrayList<Recuperaciones>();
			dataListRecuperaciones = recuperacionesFacade
					.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

			dataListRegistroMatriculasEstudiantes = new ArrayList<RegistroMatriculasEstudiantes>();

			// Sacamos la lista de los periodos
			List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
			dataListPeriodosTmp.add(periodoSeleccionado);

			for (Registromatriculas rm : dataListRegistroMatriculas) {

				// Creamos un nuevo objeto
				RegistroMatriculasEstudiantes registroMatriculasEstudiantes = new RegistroMatriculasEstudiantes();

				// Agregamos el nuevo objeto a la lista de registros ya creada
				// antes
				dataListRegistroMatriculasEstudiantes
						.add(registroMatriculasEstudiantes);

				// Agregamos al nuevo objeto la rm
				registroMatriculasEstudiantes.setRegistromatriculas(rm);

				// Creamos una lista tipo periodosEstudiantes
				List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();

				// Agregamos al registro de matrículas la nueva lista de los
				// periodos
				registroMatriculasEstudiantes
						.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

				// Recorremos la lista de los peeriodos
				for (Periodos p : dataListPeriodosTmp) {
					double promedioPeriodos = 0;

					// Sacamos la lista de dimensiones de esta asignatura
					List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
							.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
									+ asignaturaSeleccionada
											.getIdrelacionasignaturaperiodos()
									+ " AND R.cursos.idcursos = "
									+ rm.getCursos().getIdcursos());
					List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();

					PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
					dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);
					periodosEstudiantes
							.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);
					periodosEstudiantes.setPeriodos(p);

					registroMatriculasEstudiantes
							.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);
					
					String query = "select sum(round(pepe.valordndos)),  "
							+ "pepe.relaciondimensionesasignaturasano, "
							+ "rdaa.porcentaje, "
							+ "round(cast(rdaa.porcentaje * ((case when sum(round(pepe.valordndos)) is null "
							+ "then 0 "
							+ "else cast(sum(round(pepe.valordndos)) as numeric) end)) as numeric)/100), "
							+ "rdaa.dimensiones "
							+ "from ("
							+ ""
							+ "select round(cast(rnd.porcentaje * (  cast(sum(case when valor is null then 0 else valor end) as numeric )/count(1)) as numeric)/100) valordndos,  "
							+ "rnldb.relacionnotasdimension, "
							+ "rnd.relaciondimensionesasignaturasano "
							+ "from notascalificables nc "
							+ "right join relacionnotaslogrosdimensionboletin rnldb  on rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
							+ "and nc.registromatriculas =   " + rm.getIdregistromatriculas() + " "
							+ "join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
							+ "and rnd.periodos = " + p.getIdperiodos() + " "
							+ "group by rnldb.relacionnotasdimension, rnd.porcentaje, rnd.relaciondimensionesasignaturasano"
							+ ""
							+ ") pepe "
							+ "join relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = pepe.relaciondimensionesasignaturasano "
							+ "where rdaa.relacionasignaturasperiodos =  " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()  + " "
							+ "and rdaa.cursos =  " + cursoSeleccionado.getIdcursos() + " "
							+ "group by relaciondimensionesasignaturasano, rdaa.porcentaje, rdaa.dimensiones "
							+ "order by rdaa.dimensiones ";
					
					
					List<Object[]> dataListDimensionesValor = relacionlogrosdimensionesFacade.findoAll(query);
					double tmp = 0;
					for(Object[] o:dataListDimensionesValor){
						DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
						dimensionesEstudiantesTmp.setDimensiones(dimensionesFacade.find(Long.parseLong(o[4].toString())));
						dataListDimensionesEstudiantestmp.add(dimensionesEstudiantesTmp);
						dimensionesEstudiantesTmp.setValor(Double.parseDouble(o[0].toString()));
						tmp += Double.parseDouble(o[3].toString());
					}
					if(tmp > 100){
						tmp = 100;
					}
					periodosEstudiantes.setValor(tmp);
					
					
					periodosEstudiantes
							.setDataListRelacionrecuperacionregistromatriculas(new ArrayList<Relacionrecuperacionregistromatriculas>());

					boolean bandera = true;
					for (Recuperaciones r : dataListRecuperaciones) {
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTMp = new Relacionrecuperacionregistromatriculas();
						List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
								.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
										+ r.getIdrecuperaciones()
										+ " AND R.periodos.idperiodos = "
										+ periodoSeleccionado.getIdperiodos()
										+ " AND R.registromatriculas.idregistromatriculas = "
										+ rm.getIdregistromatriculas()
										+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
										+ asignaturaSeleccionada
												.getIdrelacionasignaturaperiodos());
						if (bandera) {

							if (tmpRM.isEmpty()) {
								relacionrecuperacionregistromatriculasTMp
										.setIdrelacionrecuperacionregistromatriculas(new Long(
												0));
								relacionrecuperacionregistromatriculasTMp
										.setPeriodos(periodoSeleccionado);
								relacionrecuperacionregistromatriculasTMp
										.setRecuperaciones(r);
								relacionrecuperacionregistromatriculasTMp
										.setRegistromatriculas(rm);
								relacionrecuperacionregistromatriculasTMp
										.setValor(new Double(0));
							} else {
								relacionrecuperacionregistromatriculasTMp = tmpRM
										.get(0);
							}

							// Miramos si este estudiante es nuevo
							if (periodosEstudiantes.getValor() > 0) {

								if (periodosEstudiantes.getValor() < relacionrecuperacionregistromatriculasTMp
										.getValor()
										&& periodosEstudiantes.getValor() < 80) {
									periodosEstudiantes
											.setValor(new BigDecimal(
													(periodosEstudiantes
															.getValor() + relacionrecuperacionregistromatriculasTMp
															.getValor()) / 2)
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue());

									if (bandera
											&& periodosEstudiantes.getValor() > 80) {
										bandera = false;
									}
								}
							} else {
								periodosEstudiantes
										.setValor(relacionrecuperacionregistromatriculasTMp
												.getValor());
							}
						}
						periodosEstudiantes
								.getDataListRelacionrecuperacionregistromatriculas()
								.add(relacionrecuperacionregistromatriculasTMp);
					}
					// Vamos a guardar la nota definitiva
					guardarNotaDefinitiva(p, asignaturaSeleccionada, rm,
							periodosEstudiantes.getValor());
				}
			}

			//
			dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();

			if (!dataListRegistroMatriculasEstudiantes.isEmpty()) {
				for (PeriodosEstudiantes pe : dataListRegistroMatriculasEstudiantes
						.get(0).getDataListPeriodosEstudiantes()) {
					dataListPeriodosEstudiantesHeader.add(pe);
				}
			}
		}else{
			if(dataListRegistroMatriculasEstudiantes == null
				&& periodoSeleccionado != null && periodoSeleccionado.getTipo() == 0){
			List<Recuperaciones> dataListRecuperaciones = new ArrayList<Recuperaciones>();
			dataListRecuperaciones = recuperacionesFacade
					.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

			dataListRegistroMatriculasEstudiantes = new ArrayList<RegistroMatriculasEstudiantes>();

			// Sacamos la lista de los periodos
			List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
			dataListPeriodosTmp.add(periodoSeleccionado);

			for (Registromatriculas rm : dataListRegistroMatriculas) {

				// Creamos un nuevo objeto
				RegistroMatriculasEstudiantes registroMatriculasEstudiantes = new RegistroMatriculasEstudiantes();

				// Agregamos el nuevo objeto a la lista de registros ya creada
				// antes
				dataListRegistroMatriculasEstudiantes
						.add(registroMatriculasEstudiantes);

				// Agregamos al nuevo objeto la rm
				registroMatriculasEstudiantes.setRegistromatriculas(rm);

				// Creamos una lista tipo periodosEstudiantes
				List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();

				// Agregamos al registro de matrículas la nueva lista de los
				// periodos
				registroMatriculasEstudiantes
						.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

				// Recorremos la lista de los peeriodos
				for (Periodos p : dataListPeriodosTmp) {
					double promedioPeriodos = 0;

					// Sacamos la lista de dimensiones de esta asignatura
					List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
							.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
									+ asignaturaSeleccionada
											.getIdrelacionasignaturaperiodos()
									+ " AND R.cursos.idcursos = "
									+ rm.getCursos().getIdcursos());
					List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();

					PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
					dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);
					periodosEstudiantes
							.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);
					periodosEstudiantes.setPeriodos(p);

					registroMatriculasEstudiantes
							.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);
					
//					 Recorremos la lista de las dimensiones
					for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {

						DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
						dimensionesEstudiantesTmp.setDimensiones(rdaa
								.getDimensiones());

						dataListDimensionesEstudiantestmp
								.add(dimensionesEstudiantesTmp);

						List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
						dimensionesEstudiantesTmp
								.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);

						// Colocamos la lista de los logros por dimensión
						dimensionesEstudiantesTmp
								.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
										+ p.getIdperiodos()
										+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
										+ rdaa.getIdrelaciondimensionesasignaturasano()));

						// Sacamos las notas que tiene que cada dimesion
						List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
										+ rdaa.getIdrelaciondimensionesasignaturasano()
										+ " AND R.periodos.idperiodos = "
										+ p.getIdperiodos());
						double promedioDimension = 0;

						// Validamos si no hay notas asociadas a esta dimensión
						if (tmp.isEmpty()) {
							dimensionesEstudiantesTmp.setValor(0);
							promedioDimension = 0;
						}

						for (Relacionnotasdimension rnd : tmp) {
							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
							notasDimensionesEstudiantes
									.setRelacionnotasdimension(rnd);
							dataListNotasDimensionesEstudiantes
									.add(notasDimensionesEstudiantes);

							List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
							notasDimensionesEstudiantes
									.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);

							double promedioActividades = 0;
							List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
									.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
											+ rnd.getIdrelacionnotasdimesion());
							
//							
							// Recorremos las actividades de las notas
							for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {

								ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
								dataListActividadesNotasEstudiantes
										.add(actividadesNotasEstudiantes);

								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ rm.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getIdrelacionnotaslogrosdimensionboletin());

								if (!tmpNC.isEmpty()) {
									actividadesNotasEstudiantes
											.setNotascalificables(tmpNC.get(0));
									actividadesNotasEstudiantes.setValor(tmpNC
											.get(0).getValor());
									promedioActividades += tmpNC.get(0)
											.getValor();
								} else {
									Notascalificables notasCalificablesTmp = new Notascalificables();
									notasCalificablesTmp
											.setRelacionnotaslogrosdimensionboletin(rnld);
									actividadesNotasEstudiantes
											.setNotascalificables(notasCalificablesTmp);
									actividadesNotasEstudiantes.setValor(0);
								}
							}

							// Validamos que el tamaño de la lista no está
							// vacía
							if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
								promedioActividades = 0;
							} else {
								if (tmpRNLDB != null) {
									// System.out.print(promedioActividades +
									// " ENTRANDO PROMEDIO ACTI ");
									promedioActividades = new BigDecimal(
											promedioActividades
													/ tmpRNLDB.size())
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
								}
							}
//							
							
//							String query = "select (((sum(case when valor is null then 0 else valor end)/count(1)))) valordn "
//									+ "from notascalificables nc "
//									+ "right join relacionnotaslogrosdimensionboletin rnldb "
//									+ "on rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
//									+ "and nc.registromatriculas = " + rm.getIdregistromatriculas() + " "
//									//+ "join relacionnotasdimension rnd  on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
//									//+ "and rnd.periodos =  23 "
//									+ "where "
//									+ "rnldb.relacionnotasdimension = " + rnd.getIdrelacionnotasdimesion()  + " ";
//							
//							Object otmp = relacionnotaslogrosdimensionboletinFacade.retornarValorObjectNativo(query);
							
//							promedioActividades = (Long) otmp;
//							
//							
							notasDimensionesEstudiantes
									.setValor(promedioActividades);
							promedioDimension = promedioDimension
									+ new BigDecimal((promedioActividades
											* rnd.getPorcentaje() / 100))
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
							dimensionesEstudiantesTmp
									.setValor(promedioDimension);

						}

						if (rdaa.getPorcentaje() != null) {
							promedioDimension = new BigDecimal(
									promedioDimension * rdaa.getPorcentaje()
											/ 100).setScale(0,
									BigDecimal.ROUND_HALF_UP).doubleValue();
							promedioPeriodos += promedioDimension;
							periodosEstudiantes.setValor(promedioPeriodos);
						} else {
							periodosEstudiantes.setValor(0);
						}
					}
//
//					if (periodosEstudiantes.getValor() < 80) {
					periodosEstudiantes
							.setDataListRelacionrecuperacionregistromatriculas(new ArrayList<Relacionrecuperacionregistromatriculas>());

					boolean bandera = true;
					for (Recuperaciones r : dataListRecuperaciones) {
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTMp = new Relacionrecuperacionregistromatriculas();
						List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
								.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
										+ r.getIdrecuperaciones()
										+ " AND R.periodos.idperiodos = "
										+ periodoSeleccionado.getIdperiodos()
										+ " AND R.registromatriculas.idregistromatriculas = "
										+ rm.getIdregistromatriculas()
										+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
										+ asignaturaSeleccionada
												.getIdrelacionasignaturaperiodos());
						if (bandera) {

							if (tmpRM.isEmpty()) {
								relacionrecuperacionregistromatriculasTMp
										.setIdrelacionrecuperacionregistromatriculas(new Long(
												0));
								relacionrecuperacionregistromatriculasTMp
										.setPeriodos(periodoSeleccionado);
								relacionrecuperacionregistromatriculasTMp
										.setRecuperaciones(r);
								relacionrecuperacionregistromatriculasTMp
										.setRegistromatriculas(rm);
								relacionrecuperacionregistromatriculasTMp
										.setValor(new Double(0));
							} else {
								relacionrecuperacionregistromatriculasTMp = tmpRM
										.get(0);
							}

							// Miramos si este estudiante es nuevo
							if (periodosEstudiantes.getValor() > 0) {

								if (periodosEstudiantes.getValor() < relacionrecuperacionregistromatriculasTMp
										.getValor()
										&& periodosEstudiantes.getValor() < 80) {
									periodosEstudiantes
											.setValor(new BigDecimal(
													(periodosEstudiantes
															.getValor() + relacionrecuperacionregistromatriculasTMp
															.getValor()) / 2)
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue());

									if (bandera
											&& periodosEstudiantes.getValor() > 80) {
										bandera = false;
									}
								}
							} else {
								periodosEstudiantes
										.setValor(relacionrecuperacionregistromatriculasTMp
												.getValor());
							}
						}
						periodosEstudiantes
								.getDataListRelacionrecuperacionregistromatriculas()
								.add(relacionrecuperacionregistromatriculasTMp);
					}
					// Vamos a guardar la nota definitiva
					guardarNotaDefinitiva(p, asignaturaSeleccionada, rm,
							periodosEstudiantes.getValor());
				}
			}

			//
			dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();

			if (!dataListRegistroMatriculasEstudiantes.isEmpty()) {
				for (PeriodosEstudiantes pe : dataListRegistroMatriculasEstudiantes
						.get(0).getDataListPeriodosEstudiantes()) {
					dataListPeriodosEstudiantesHeader.add(pe);
				}
			}
		}
			
		}
		return dataListRegistroMatriculasEstudiantes;
	}
	
	
	
	// Método para calcular las definitivas del periodo nuevo
		public List<RegistroMatriculasEstudiantes> getDataListRegistroMatriculasEstudiantes_antiguo() {
			if (dataListRegistroMatriculasEstudiantes == null
					&& periodoSeleccionado != null && periodoSeleccionado.getTipo() == 0) {

				List<Recuperaciones> dataListRecuperaciones = new ArrayList<Recuperaciones>();
				dataListRecuperaciones = recuperacionesFacade
						.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

				dataListRegistroMatriculasEstudiantes = new ArrayList<RegistroMatriculasEstudiantes>();

				// Sacamos la lista de los periodos
				List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
				dataListPeriodosTmp.add(periodoSeleccionado);

				for (Registromatriculas rm : dataListRegistroMatriculas) {

					// Creamos un nuevo objeto
					RegistroMatriculasEstudiantes registroMatriculasEstudiantes = new RegistroMatriculasEstudiantes();

					// Agregamos el nuevo objeto a la lista de registros ya creada
					// antes
					dataListRegistroMatriculasEstudiantes
							.add(registroMatriculasEstudiantes);

					// Agregamos al nuevo objeto la rm
					registroMatriculasEstudiantes.setRegistromatriculas(rm);

					// Creamos una lista tipo periodosEstudiantes
					List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();

					// Agregamos al registro de matrículas la nueva lista de los
					// periodos
					registroMatriculasEstudiantes
							.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

					// Recorremos la lista de los peeriodos
					for (Periodos p : dataListPeriodosTmp) {
						double promedioPeriodos = 0;

						// Sacamos la lista de dimensiones de esta asignatura
						List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
								.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
										+ asignaturaSeleccionada
												.getIdrelacionasignaturaperiodos()
										+ " AND R.cursos.idcursos = "
										+ rm.getCursos().getIdcursos());
						List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();

						PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
						dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);
						periodosEstudiantes
								.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);
						periodosEstudiantes.setPeriodos(p);

						registroMatriculasEstudiantes
								.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);
						
						
						
						

						 //Recorremos la lista de las dimensiones
						for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {
	
							DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
							dimensionesEstudiantesTmp.setDimensiones(rdaa
									.getDimensiones());
	
							dataListDimensionesEstudiantestmp
									.add(dimensionesEstudiantesTmp);
	
							List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
							dimensionesEstudiantesTmp
									.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);
	
							// Colocamos la lista de los logros por dimensión
							dimensionesEstudiantesTmp
									.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
											+ p.getIdperiodos()
											+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
											+ rdaa.getIdrelaciondimensionesasignaturasano()));
	
							// Sacamos las notas que tiene que cada dimesion
							List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
									.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
											+ rdaa.getIdrelaciondimensionesasignaturasano()
											+ " AND R.periodos.idperiodos = "
											+ p.getIdperiodos());
							double promedioDimension = 0;
	
							// Validamos si no hay notas asociadas a esta dimensión
							if (tmp.isEmpty()) {
								dimensionesEstudiantesTmp.setValor(0);
								promedioDimension = 0;
							}
	
							for (Relacionnotasdimension rnd : tmp) {
								NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnd);
								dataListNotasDimensionesEstudiantes
										.add(notasDimensionesEstudiantes);
	
								List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
								notasDimensionesEstudiantes
										.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);
	
								double promedioActividades = 0;
								List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
										.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
												+ rnd.getIdrelacionnotasdimesion());
								
//								
								// Recorremos las actividades de las notas
								for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {
	
									ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
									dataListActividadesNotasEstudiantes
											.add(actividadesNotasEstudiantes);
	
									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ rm.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getIdrelacionnotaslogrosdimensionboletin());
	
									if (!tmpNC.isEmpty()) {
										actividadesNotasEstudiantes
												.setNotascalificables(tmpNC.get(0));
										actividadesNotasEstudiantes.setValor(tmpNC
												.get(0).getValor());
										promedioActividades += tmpNC.get(0)
												.getValor();
									} else {
										Notascalificables notasCalificablesTmp = new Notascalificables();
										notasCalificablesTmp
												.setRelacionnotaslogrosdimensionboletin(rnld);
										actividadesNotasEstudiantes
												.setNotascalificables(notasCalificablesTmp);
										actividadesNotasEstudiantes.setValor(0);
									}
								}
	
								// Validamos que el tamaño de la lista no está
								// vacía
								if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
									promedioActividades = 0;
								} else {
									if (tmpRNLDB != null) {
										// System.out.print(promedioActividades +
										// " ENTRANDO PROMEDIO ACTI ");
										promedioActividades = new BigDecimal(
												promedioActividades
														/ tmpRNLDB.size())
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}
								}
//									
								notasDimensionesEstudiantes
										.setValor(promedioActividades);
								promedioDimension = promedioDimension
										+ new BigDecimal((promedioActividades
												* rnd.getPorcentaje() / 100))
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
								dimensionesEstudiantesTmp
										.setValor(promedioDimension);
	
							}
	
							if (rdaa.getPorcentaje() != null) {
								promedioDimension = new BigDecimal(
										promedioDimension * rdaa.getPorcentaje()
												/ 100).setScale(0,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								promedioPeriodos += promedioDimension;
								periodosEstudiantes.setValor(promedioPeriodos);
							} else {
								periodosEstudiantes.setValor(0);
							}
						}
	
//						if (periodosEstudiantes.getValor() < 80) {
						periodosEstudiantes
								.setDataListRelacionrecuperacionregistromatriculas(new ArrayList<Relacionrecuperacionregistromatriculas>());

						boolean bandera = true;
						for (Recuperaciones r : dataListRecuperaciones) {
							Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTMp = new Relacionrecuperacionregistromatriculas();
							List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
									.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
											+ r.getIdrecuperaciones()
											+ " AND R.periodos.idperiodos = "
											+ periodoSeleccionado.getIdperiodos()
											+ " AND R.registromatriculas.idregistromatriculas = "
											+ rm.getIdregistromatriculas()
											+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
											+ asignaturaSeleccionada
													.getIdrelacionasignaturaperiodos());
							if (bandera) {

								if (tmpRM.isEmpty()) {
									relacionrecuperacionregistromatriculasTMp
											.setIdrelacionrecuperacionregistromatriculas(new Long(
													0));
									relacionrecuperacionregistromatriculasTMp
											.setPeriodos(periodoSeleccionado);
									relacionrecuperacionregistromatriculasTMp
											.setRecuperaciones(r);
									relacionrecuperacionregistromatriculasTMp
											.setRegistromatriculas(rm);
									relacionrecuperacionregistromatriculasTMp
											.setValor(new Double(0));
								} else {
									relacionrecuperacionregistromatriculasTMp = tmpRM
											.get(0);
								}

								// Miramos si este estudiante es nuevo
								if (periodosEstudiantes.getValor() > 0) {

									if (periodosEstudiantes.getValor() < relacionrecuperacionregistromatriculasTMp
											.getValor()
											&& periodosEstudiantes.getValor() < 80) {
										periodosEstudiantes
												.setValor(new BigDecimal(
														(periodosEstudiantes
																.getValor() + relacionrecuperacionregistromatriculasTMp
																.getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue());

										if (bandera
												&& periodosEstudiantes.getValor() > 80) {
											bandera = false;
										}
									}
								} else {
									periodosEstudiantes
											.setValor(relacionrecuperacionregistromatriculasTMp
													.getValor());
								}
							}
							periodosEstudiantes
									.getDataListRelacionrecuperacionregistromatriculas()
									.add(relacionrecuperacionregistromatriculasTMp);
						}
						// Vamos a guardar la nota definitiva
						guardarNotaDefinitiva(p, asignaturaSeleccionada, rm,
								periodosEstudiantes.getValor());
					}
				}

				//
				dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();

				if (!dataListRegistroMatriculasEstudiantes.isEmpty()) {
					for (PeriodosEstudiantes pe : dataListRegistroMatriculasEstudiantes
							.get(0).getDataListPeriodosEstudiantes()) {
						dataListPeriodosEstudiantesHeader.add(pe);
					}
				}
			}
			return dataListRegistroMatriculasEstudiantes;
		}
	
	public void setDataListRegistroMatriculasEstudiantes(List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasEstudiantes) {
		this.dataListRegistroMatriculasEstudiantes = dataListRegistroMatriculasEstudiantes;
	}
	
	// Método para imprimir un boletín
	// Método para imprimir el curso
	public void imprimirCurso() {
		try {
			Document document = new Document(PageSize.LEGAL, 20, 20, 20, 20);
			// create a PDF writer
			PdfWriter pdf = PdfWriter.getInstance(document,
					new FileOutputStream("primero" + ".pdf"));

			// open the PDF document
			document.open();

			PdfContentByte cb = pdf.getDirectContent();
			//
			Image image3 = Image
					.getInstance(new URL(
							"http://localhost:8080/sistemaColegio/resources/imagenes/Escudo.png"));
			image3.setAbsolutePosition(20f, 938f);
			// image3.scaleAbsolute(10f, 10f);
			// image3.scaleAbsolute(1f, 1f);
			image3.scaleToFit(75f, 75f);
			document.add(image3);

			BaseFont bf = BaseFont.createFont();
			cb.beginText();

			String text = "COLEGIO PEDAGÓGICO CAMPESTRE FLORIDABLANCA";
			String text2 = "Resolución No. 0241 de 9 de Junio de 2004";
			String text3 = "Grado: " + " Primero";
			String nombre = "Nombre: Pedro José Martínez Villamizar";
			cb.setFontAndSize(bf, 15);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
			cb.setFontAndSize(bf, 13);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 270, 918, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 880, 0);
			cb.endText();

			// cell.setRotation(90);
			// asignatura.setBorderColorRight(BaseColor.WHITE);
			// fallas.setBorderWidthLeft(0f);
			// fallas.setBorderColorLeft(BaseColor.WHITE);
			// fallas.setBorderColorBottom(BaseColor.BLACK);

			Paragraph paragraphTable1 = new Paragraph();
			paragraphTable1.setSpacingBefore(120);

			PdfPTable table = new PdfPTable(5); // Code 1
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 5, 2, 2, 1, 1 });

			PdfPCell celda;
			celda = new PdfPCell(new Phrase("Español y Literatura"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Fallas
			celda = new PdfPCell(new Phrase("Fallas: 1"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Jucio valorativo
			celda = new PdfPCell(new Phrase("JV:  80% B"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Fortaleza
			celda = new PdfPCell(new Phrase("F"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Por mejorara
			celda = new PdfPCell(new Phrase("PM"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Agregamos al párrafo la tabla
			paragraphTable1.add(table);

			// Otra tabla
			table = new PdfPTable(3); // Code 1
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 9, 1, 1 });

			celda = new PdfPCell(new Phrase("Español y Literatura"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Fortaleza
			celda = new PdfPCell(new Phrase("F"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Por mejorara
			celda = new PdfPCell(new Phrase("PM"));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);

			// Agregamos al párrafo la tabla
			paragraphTable1.add(table);

			document.add(paragraphTable1);

			//
			//
			// PdfPTable table = new PdfPTable(32); // Code 1
			// table.setWidthPercentage(100);
			//
			// float[] columnWidths = new float[32];
			// columnWidths[0] = 2f;
			// columnWidths[1] = 38f;
			// for (int i = 0; i < 30; i++) {
			// columnWidths[i + 2] = 2f;
			// }
			//
			// table.setWidths(columnWidths);
			//
			// table.addCell("No");
			// table.addCell("Nombre");
			//
			//
			//
			// //
			// for (int i = 0; i < 30; i++) {
			// table.addCell(new Phrase((i + 1) + "",
			// FontFactory.getFont(FontFactory.HELVETICA, 8)));
			// }
			// //
			// // System.out.print(dataListEstudiantes.size() +
			// " tamaño de la lista ");
			//
			//
			// int contador = 0;
			// for (Estudiantes e : dataListEstudiantes) {
			// contador++;
			// table.addCell(contador + "");
			//
			// table.addCell(e.getUsuarios().getApellidos().toUpperCase() + " "
			// + e.getUsuarios().getNombres().toUpperCase());
			// for (int i = 0; i < 30; i++) {
			// table.addCell(" ");
			// }
			// }

			// paragraphTable1.add(table);

			// document.add(paragraphTable1);

			// close the PDF document
			document.close();

			String downloadFile = "primero" + ".pdf";

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext
					.getResponse();

			String contentType = "application/pdf";

			response.setContentType(contentType);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ "primero" + ".pdf" + "\"");
			downloadFile = "primero" + ".pdf";
			byte[] buf = new byte[1024];
			try {
				File file = new File(downloadFile);
				long length = file.length();
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(file));
				ServletOutputStream out = response.getOutputStream();
				response.setContentLength((int) length);
				while ((in != null) && ((length = in.read(buf)) != -1)) {
					out.write(buf, 0, (int) length);
				}
				in.close();
				out.close();
			} catch (Exception exc) {
				exc.printStackTrace();
			}

			File fichero = new File("primero" + ".pdf");
			fichero.delete();

			facesContext.responseComplete();
		} catch (Exception e) {
		}
	}

	// /###BOLETIN DEL ESTUDIANTE
	private List<AsignaturasBoletines> dataListAsignaturasBoletines;

	// Clase estática que maneja los boletines
	public static class AsignaturasBoletines {

		private Registromatriculas registromatriculas;
		private List<LogrosBoletines> dataListLogros;

		public Registromatriculas getRegistromatriculas() {
			return registromatriculas;
		}

		public void setRegistromatriculas(Registromatriculas registromatriculas) {
			this.registromatriculas = registromatriculas;
		}

		public List<LogrosBoletines> getDataListLogros() {
			return dataListLogros;
		}

		public void setDataListLogros(List<LogrosBoletines> dataListLogros) {
			this.dataListLogros = dataListLogros;
		}
	}

	// Clase para los logros
	public static class LogrosBoletines {

		private Logros logro;
		private double valor = 0;

		public Logros getLogro() {
			return logro;
		}

		public void setLogro(Logros logro) {
			this.logro = logro;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}
	}

	// Método para obtener la lista de los boletines
	public void seleccionarEstudiantes(RegistroMatriculasEstudiantes rm) {
		if (periodoSeleccionado != null) {
			// Reiniciamos la lista de los boletines
			dataListAsignaturasBoletines = new ArrayList<AsignaturasBoletines>();
			// Seleccionamos el estudiante
			estudiantesSeleccionado = rm.getRegistromatriculas()
					.getEstudiantes();
			AsignaturasBoletines asignaturasBoletines = new AsignaturasBoletines();
			asignaturasBoletines.setRegistromatriculas(rm
					.getRegistromatriculas());
			List<LogrosBoletines> dataListLogrosBoletines = new ArrayList<LogrosBoletines>();
			asignaturasBoletines.setDataListLogros(dataListLogrosBoletines);

			dataListAsignaturasBoletines.add(asignaturasBoletines);

			for (PeriodosEstudiantes pe : rm.getDataListPeriodosEstudiantes()) {
				for (DimensionesEstudiantes de : pe
						.getDataListDimensionesEstudiantes()) {
					for (Relacionlogrosdimensiones rld : de
							.getDataListRelacionlogrosdimensiones()) {
						// Hacemos un objeto tipo logrosBoletines
						LogrosBoletines logrosBoletines = new LogrosBoletines();
						// Le colocamos el logro a este objeto que habíamos
						// creado con anterioridad
						logrosBoletines.setLogro(rld.getLogros());
						// Colocamos a la lista de los logros el objeto que
						// habíamos creado antes
						dataListLogrosBoletines.add(logrosBoletines);

						List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
										+ rld.getLogros().getIdlogros()
										+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
										+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");

						Long idDimension = null;
						Long idRelacionNotasDimension = null;
						double suma = 0;
						int contador = 0;
						int contador2 = 0;

						ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
						List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

						NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
						List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

						for (Relacionlogrosnotasdimension rnld : tmpList) {
							// Miramos
							if (idRelacionNotasDimension == null) {

								// Sacamos el id de la dimensión que estamos
								// manejando
								idDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getIdrelaciondimensionesasignaturasano();

								// Sacamos el id del item que estamos manejando
								idRelacionNotasDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion();
								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ rm.getRegistromatriculas()
														.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getRelacionnotaslogrosdimensionboletin()
														.getIdrelacionnotaslogrosdimensionboletin());
								if (!tmpNC.isEmpty()) {
									suma += tmpNC.get(0).getValor();
								}
								promedioActividades.setValor(suma);

								// Agregamos el porcenta
								Notascalificables notascalificables = new Notascalificables(
										new Long(0));
								// Agregamos a notas calificables una de las
								// actividades para poder acceder luego al
								// porcentaje del item
								notascalificables
										.setRelacionnotaslogrosdimensionboletin(rnld
												.getRelacionnotaslogrosdimensionboletin());
								// Agregamos notascalificables a la relación
								// con registromatricula
								promedioActividades
										.setNotascalificables(notascalificables);
								// Le agregamos a la dimensión la relación
								// entre la dimensión y el item
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension());

								// Revisamos si llegamos al final de la lista
								if (contador2 + 1 == tmpList.size()) {
									// promedioActividades.setValor(suma);
									dataListNotasEstudiantes
											.add(promedioActividades);
									// Guardamos la lista de los items
									notasDimensionesEstudiantes
											.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

									// Agregamos a la lista grande los items de
									// la dimensión
									tmpDimensionesEstudiantes
											.add(notasDimensionesEstudiantes);
								}

							} else {
								// Miramos si hay cambio del item
								if (rnld.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {

										// Terminamos
										// Si hemos llegado al final entonces lo
										// que hacemos
										suma = new BigDecimal(suma / (contador))
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Miramos si el item ha cambiado
										if (idDimension == rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ rm.getRegistromatriculas()
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

										} else {

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ rm.getRegistromatriculas()
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}

									} else {
										idRelacionNotasDimension = rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getIdrelacionnotasdimesion();
										// aScamos el promedio de las
										// actividades por item
										suma = new BigDecimal(suma / contador)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										// Agregamos este valor un objeto
										promedioActividades.setValor(suma);
										// Agregamos este objeto a una lista
										dataListNotasEstudiantes
												.add(promedioActividades);

										if (idDimension != rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
											// --Sacar valor de la dimensión

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
											// Colocamos el nuevo valor de la
											// dimensión
											idDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano();
										}

										// Reiniciamos el objeto
										promedioActividades = new ActividadesNotasEstudiantes();
										if (promedioActividades
												.getNotascalificables() == null) {
											// Agregamos el porcenta
											Notascalificables notascalificables = new Notascalificables(
													new Long(0));
											// Agregamos a notas calificables
											// una de las actividades para poder
											// acceder luego al porcentaje del
											// item
											notascalificables
													.setRelacionnotaslogrosdimensionboletin(rnld
															.getRelacionnotaslogrosdimensionboletin());
											// Agregamos notascalificables a la
											// relación con registromatricula
											promedioActividades
													.setNotascalificables(notascalificables);
										}
										// Reiniciamos la variable
										suma = 0;

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ rm.getRegistromatriculas()
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());

										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
									}
									contador = 0;

								} else {

									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ rm.getRegistromatriculas()
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									if (contador2 + 1 == tmpList.size()) {
										suma = new BigDecimal(suma
												/ (contador + 1)).setScale(0,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();

										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}
								}
							}
							contador++;
							contador2++;
						}
						//
						// //
						double sumaTotalLogro = -1;
						//
						double sumaDimensiones = 0;
						//
						for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
							sumaDimensiones += nde.getRelacionnotasdimension()
									.getRelaciondimensionesasignaturasano()
									.getPorcentaje();
						}

						if (!tmpDimensionesEstudiantes.isEmpty()) {
							sumaTotalLogro = 0;
							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								double sumaPromedioItem = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaPromedioItem += ane
											.getNotascalificables()
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getPorcentaje();
								}

								double sumaActividades = 0;
								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaActividades += new BigDecimal(
											(ane.getValor() * (ane
													.getNotascalificables()
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
								}

								sumaTotalLogro += new BigDecimal(
										(nde.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getPorcentaje() * 100 / sumaDimensiones)
												* sumaActividades / 100)
										.setScale(0, BigDecimal.ROUND_HALF_UP)
										.doubleValue();

							}

							// if (sumaTotalLogro < 80) {
							// logrodPasado = false;
							// }
						}

						if (sumaTotalLogro < 80) {
							// System.out.print(sumaTotalLogro +
							// "SUMA TOTAL DEL LOGRO ");
							if (sumaTotalLogro <= 0) {

								// Miramos si este niño es nuevo
								List<Relacionlogrosrecuperaciones> tmpRRList = relacionlogrosrecuperacionesFacade
										.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
												+ rld.getIdrelacionlogrosdimensiones());
								if (!tmpRRList.isEmpty()) {
									List<Relacionrecuperacionregistromatriculas> relacionrecuperacionList = relacionrecuperacionregistromatriculasFacade
											.findByLike("SELECT R FROM Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
													+ tmpRRList
															.get(0)
															.getRecuperaciones()
															.getIdrecuperaciones()
													+ " AND"
													+ " R.registromatriculas.idregistromatriculas = "
													+ rm.getRegistromatriculas()
															.getIdregistromatriculas());
									if (!relacionrecuperacionList.isEmpty()) {
										sumaTotalLogro = relacionrecuperacionList
												.get(0).getValor();
									}
								}

							}
						}

						// Finalmente damos el valor a este logro
						logrosBoletines.setValor(sumaTotalLogro);

					}
				}
			}

		}

	}

	// Métodos para acceder a la lista de los logros por boletin
	public List<AsignaturasBoletines> getDataListAsignaturasBoletines() {
		return dataListAsignaturasBoletines;
	}

	public void setDataListAsignaturasBoletines(
			List<AsignaturasBoletines> dataListAsignaturasBoletines) {
		this.dataListAsignaturasBoletines = dataListAsignaturasBoletines;
	}

	// Método para selccionar un estudiante
	public Estudiantes getEstudiantesSeleccionado() {
		return estudiantesSeleccionado;
	}

	public void setEstudiantesSeleccionado(Estudiantes estudiantesSeleccionado) {
		this.estudiantesSeleccionado = estudiantesSeleccionado;
	}

	// /###BOLETINES PROFESORES
	
	//METODO PARA OBTENER LOS ANOS ACADEMICOS
	public List<Anosacademicos> getDataListAnosAcademicos() {
		Sesiones sesiones = getSesiones();
		if (sesiones == null || sesiones.getUsuarios() == null) {
			return null;
		}
		if (dataListAnosAcademicos == null || dataListAnosAcademicos.isEmpty()) {
			dataListAnosAcademicos = anosacademicosFacade
					.findByLike("SELECT A FROM Anosacademicos A order by A.fechainicio");
			return dataListAnosAcademicos;

		}
		return dataListAnosAcademicos;
	}

	// METODO PARA ESCOGER UN ANO ACADEMICO
	public void escogerAnoAcademico(Anosacademicos anosacademicos) {
		this.anosacademicosManual = anosacademicos;		
		dataListCursos = cursosFacade
				.findByLikeAll("SELECT  C FROM Cursos C WHERE C.anosacademicos.idanosacademicos = "
						+ anosacademicos.getIdanosacademicos()
						+ " ORDER BY C.grados.numero");
	}
	
	// ##Método para que los profesores vean las notas de los alumnos
	// PROPIEDADES DE LA LISTA DE CURSOS
	public List<Cursos> getDataListCursosBoletines() {
		Sesiones sesiones = getSesiones();
		if (sesiones == null || sesiones.getUsuarios() == null) {
			return null;
		}
		if (dataListCursos == null || dataListCursos.isEmpty()) {
			if (isAdministrador()) {
				dataListCursos = cursosFacade
						.findByLikeAll("SELECT  C FROM Cursos C  WHERE C.anosacademicos.estadoactivo = true ORDER BY C.grados.numero");
			} else {
				dataListCursos = cursosFacade
						.findByLikeAll("SELECT  C FROM Cursos C WHERE C.profesor.usuarios.idusuarios = "
								+ sesiones.getUsuarios().getIdusuarios()
								+ " AND  C.anosacademicos.estadoactivo = true"
								+ " ORDER BY C.grados.numero");
			}

		}
		return dataListCursos;
	}
	
	//Método para saber si es un administrador o no
	public boolean isAdministrador(){
		if(administrador == null){
			FacesContext FCInstance = FacesContext.getCurrentInstance();
			String theBeanName = "rolesManager";
			Object bean = FCInstance.getELContext().getELResolver()
					.getValue(FCInstance.getELContext(), null, theBeanName);
			rolesManager manager = (rolesManager) bean;
			if (manager.administrarNotas()) {
				administrador = true;
			}else{
				administrador = false;
			}
		}
		return administrador;
	}

	// Método para escoger el curso del cual queremos ver el boletín
	public void escogerCursoBoletin(Cursos cursos) {
		banderaPeriodos = false;
		this.periodoSeleccionado = null;
		this.cursoSeleccionado = cursos;
		if(anosacademicosManual == null){
			this.dataListRegistroMatriculas = registromatriculasFacade
					.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.cursos.idcursos = "
							+ cursos.getIdcursos()
							+ " AND R.fecharetiro is null "
							+ " AND R.cursos.anosacademicos.estadoactivo = true"
							+ " ORDER BY R.estudiantes.usuarios.apellidos");
		}else{
			this.dataListRegistroMatriculas = registromatriculasFacade
					.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " AND ((R.cursos.anosacademicos.estadoactivo = true AND R.fecharetiro is null)" +
							"	OR (R.cursos.anosacademicos.estadoactivo = false))"
							+ " AND R.cursos.anosacademicos.idanosacademicos = " + anosacademicosManual.getIdanosacademicos()
							+ " ORDER BY R.estudiantes.usuarios.apellidos");
		}
	}

	// Método para saber si hemos seleccionado algún curso
	public Cursos getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	// Método para devolver el estudiante seleccionado
	public Registromatriculas getRegistroMatriculas() {
		return registromatriculas;
	}

	// Método para obtener el periodo seleccionado
	public Periodos getPeriodoSeleccionado() {
		return periodoSeleccionado;
	}

	// Método para obtener la lista de estudiantes
	public List<Registromatriculas> getDataListRegistroMatriculas() {
		return dataListRegistroMatriculas;
	}

	// Método para escoger el estudiantes
	public void seleccionarEstudianteBoletin(
			Registromatriculas registromatriculas) {
		this.registromatriculas = registromatriculas;
		dataListAsignaturas = null;
		dataListDefinitivas = null;
		if (periodoSeleccionado != null && periodoSeleccionado.getTipo() == 1) {
			calcularDefinitivas();
		} else {
			calcularBoletin_Imprimir(null);
		}
	}

	// Método para esocoger el periodo
	public void escogerPeriodoBoletin(Periodos periodos) {
		this.periodoSeleccionado = periodos;
		dataListAsignaturas = null;
		dataListDefinitivas = null;
		if (periodos.getTipo() == 1) {
//			calcularDefinitivas();
		} else {
			if(periodos.getCalculo() == 1){
				calcularBoletin(null);
			}else{
				calcularBoletin_Imprimir(null);
			}
		}
	}

	// Método para calcular las definitvas de cada estudiante
	public void calcularDefinitivas() {
		List<Relacionasignaturaperiodos> dataListAsignturasEstudiantesTmp = new ArrayList<Relacionasignaturaperiodos>();
		
		
		if (anosacademicosManual == null) {
			dataListAsignturasEstudiantesTmp = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
							+ registromatriculas.getCursos().getIdcursos()
							+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
							+ getCurrentYear().getIdanosacademicos()
							+ " ORDER BY "
							+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
		} else {
			dataListAsignturasEstudiantesTmp = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
							+ registromatriculas.getCursos().getIdcursos()
							+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
							+ anosacademicosManual.getIdanosacademicos()
							+ " ORDER BY "
							+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
		}
		
		for (Periodos p : getDataListPeriodos()) {
			if (p.getTipo() == 0) {
				for (Relacionasignaturaperiodos r : dataListAsignturasEstudiantesTmp) {
//					getDefinitivaPeriodo(p, r, registromatriculas);

				}
			}
		}
	}

	// Método para hallar la definitiva de una asignatura en un periodo
	public double getDefinitivaPeriodo(Periodos p,
			Relacionasignaturaperiodos rap, Registromatriculas regMat) {

		List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
				.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ rap.getIdrelacionasignaturaperiodos()
						+ " AND R.cursos.idcursos = "
						+ regMat.getCursos().getIdcursos()
						+ " ORDER BY R.dimensiones.iddimensiones");

		double promedioPeriodo = 0;

		PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();

		if (datalistDimensionesTmp.isEmpty()) {
			periodosEstudiantes.setValor(0);
		}

		// Recorremos la lista de las dimensiones para averiguar el
		// promedio de la dimension en este periodo
		for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {
			// Cremos un objeto temporal tipo DimensionesEstudiantes
			DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
			// Le agregmos la dimensión
			dimensionesEstudiantesTmp.setDimensiones(rdaa.getDimensiones());

			// Hacemos una lista temporal de las dimensiones
			List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
			// Agregamos la lista que acabamos de crear
			dimensionesEstudiantesTmp
					.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);

			// Sacamos las notas que tiene que cada dimesion
			List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
					.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
							+ rdaa.getIdrelaciondimensionesasignaturasano()
							+ " AND R.periodos.idperiodos = "
							+ p.getIdperiodos());
			double promedioDimension = 0;

			if (tmp.isEmpty()) {
				dimensionesEstudiantesTmp.setValor(0);
			}

			// Recogemos la lista de logros
			dimensionesEstudiantesTmp
					.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
							+ p.getIdperiodos()
							+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
							+ rdaa.getIdrelaciondimensionesasignaturasano()));

			// Recorremos las notas de esta dimensión
			for (Relacionnotasdimension rnd : tmp) {
				// Hacemos un objeto temporal tipo
				// NotasDimensionesEstudiantes
				NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
				// Le agregamos la dimensión
				notasDimensionesEstudiantes.setRelacionnotasdimension(rnd);
				// A la lista de las dimensiones le agregamos las
				// dimensiones
				dataListNotasDimensionesEstudiantes
						.add(notasDimensionesEstudiantes);
				// Creamos una lista temporal de actividades
				List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
				// Agregamos esta lista de actividades al objeto
				// creado anteriormente
				notasDimensionesEstudiantes
						.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);

				double promedioActividades = 0;
				List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
						.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
								+ rnd.getIdrelacionnotasdimesion());

				// Recorremos la lista de las actividades
				for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {
					ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
					dataListActividadesNotasEstudiantes
							.add(actividadesNotasEstudiantes);

					List<Notascalificables> tmpNC = notascalificablesFacade
							.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
									+ regMat.getIdregistromatriculas()
									+ " AND "
									+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
									+ rnld.getIdrelacionnotaslogrosdimensionboletin());

					if (!tmpNC.isEmpty()) {
						actividadesNotasEstudiantes.setNotascalificables(tmpNC
								.get(0));
						actividadesNotasEstudiantes.setValor(tmpNC.get(0)
								.getValor());
						promedioActividades += tmpNC.get(0).getValor();
					} else {
						Notascalificables notasCalificablesTmp = new Notascalificables();
						notasCalificablesTmp
								.setRelacionnotaslogrosdimensionboletin(rnld);
						actividadesNotasEstudiantes
								.setNotascalificables(notasCalificablesTmp);
						actividadesNotasEstudiantes.setValor(0);
					}
				}

				// Validamos que la longitud de la lista no está
				// vacia
				if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
					promedioActividades = 100;
				} else {
					promedioActividades = new BigDecimal(promedioActividades
							/ tmpRNLDB.size()).setScale(0,
							BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				notasDimensionesEstudiantes.setValor(promedioActividades);

				promedioDimension = promedioDimension
						+ new BigDecimal((promedioActividades
								* rnd.getPorcentaje() / 100)).setScale(0,
								BigDecimal.ROUND_HALF_UP).doubleValue();
				dimensionesEstudiantesTmp.setValor(promedioDimension);
			}

			if (rdaa.getPorcentaje() != null) {
				promedioDimension = new BigDecimal(promedioDimension
						* rdaa.getPorcentaje() / 100).setScale(0,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				promedioPeriodo += promedioDimension;
				periodosEstudiantes.setValor(promedioPeriodo);
			} else {
				periodosEstudiantes.setValor(100);
			}
		}

		List<Recuperaciones> datalistRecuperacionesTMP = recuperacionesFacade
				.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

		if (periodosEstudiantes.getValor() < 80) {
			List<Relacionrecuperacionregistromatriculas> tmpRM2 = new ArrayList<Relacionrecuperacionregistromatriculas>();
			for (Recuperaciones r : datalistRecuperacionesTMP) {
				List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
						.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.periodos.idperiodos = "
								+ p.getIdperiodos()
								+ " AND R.registromatriculas.idregistromatriculas = "
								+ regMat.getIdregistromatriculas()
								+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
								+ rap.getIdrelacionasignaturaperiodos()
								+ " AND R.recuperaciones.idrecuperaciones = "
								+ r.getIdrecuperaciones());
				if (!tmpRM.isEmpty()) {
					periodosEstudiantes
							.setDataListRelacionrecuperacionregistromatriculas(tmpRM);
					// Miramos si el estudiante es nuevo
					if (periodosEstudiantes.getValor() == 0) {
						periodosEstudiantes.setValor(tmpRM.get(0).getValor());
					} else {
						if (tmpRM.get(0).getValor() > periodosEstudiantes
								.getValor()) {
							periodosEstudiantes.setValor(new BigDecimal(
									(periodosEstudiantes.getValor() + tmpRM
											.get(0).getValor()) / 2).setScale(
									0, BigDecimal.ROUND_HALF_UP).doubleValue());
						}
					}
					tmpRM2.add(tmpRM.get(0));
				}
			}
		}

		// Guardamos la nota definitiva del estudiante
//		guardarNotaDefinitiva(p, rap, regMat, periodosEstudiantes.getValor());

		return periodosEstudiantes.getValor();
	}

	// Método para calcular las notas finales del estudiantes
	public List<AsignaturasEstudiantes> calcularBoletin(
			Registromatriculas regMat) {
		if (dataListAsignaturas == null && registromatriculas != null
				&& periodoSeleccionado != null) {

			dataListAsignturasEstudiantes = new ArrayList<AsignaturasEstudiantes>();

			if (regMat == null) {
				dataListAsignaturas = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ registromatriculas.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+   periodoSeleccionado.getAnoacademicos().getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
				regMat = registromatriculas;
			} else {
				dataListAsignaturas = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ regMat.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+ getCurrentYear().getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
			}

			for (Relacionasignaturaperiodos rap : dataListAsignaturas) {
				// Lista de los periodos
				List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
				// Agregamos los periodos a la lista
				dataListPeriodosTmp.add(periodoSeleccionado);

				double promedioAsignatura = 0;

				// Creamos un nuevo objeto tipo AsignaturasEstudiantes
				AsignaturasEstudiantes asignaturasEstudiantesTmp = new AsignaturasEstudiantes();
				// Le agregamos la asignatura que estamos calculando
				asignaturasEstudiantesTmp.setAsignaturas(rap.getAsignaturas());
				// Creamos una lista nuevo para los periodos
				List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();
				asignaturasEstudiantesTmp
						.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

				// Validamos si no hay ningún periodo
				if (dataListPeriodosTmp.isEmpty()) {
					asignaturasEstudiantesTmp.setValor(100);
				}
				// Ahora vamos a recorrer los periodos y a agregarles la s
				// asignaturas
				for (Periodos p : dataListPeriodosTmp) {
					List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
							.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
									+ rap.getIdrelacionasignaturaperiodos()
									+ " AND R.cursos.idcursos = "
									+ regMat.getCursos().getIdcursos()
									+ " ORDER BY R.dimensiones.iddimensiones");

					double promedioPeriodo = 0;

					PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
					// Agregamos el periodo
					periodosEstudiantes.setPeriodos(p);
					// Creamos una lista de las dimensiones que le vamos a
					// agregar a este periodo
					List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();
					// Agregamos esta lista al periodo
					periodosEstudiantes
							.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);

					// Agregamos a la lisa de los periodos esta dimensión
					dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);

					if (datalistDimensionesTmp.isEmpty()) {
						periodosEstudiantes.setValor(0);
					}
	
					String query = "select sum(round(pepe.valordndos)),  "
							+ "pepe.relaciondimensionesasignaturasano, "
							+ "rdaa.porcentaje, "
							+ "round(cast(rdaa.porcentaje * ((case when sum(round(pepe.valordndos)) is null "
							+ "then 0 "
							+ "else cast(sum(round(pepe.valordndos)) as numeric) end)) as numeric)/100), "
							+ "rdaa.dimensiones "
							+ "from ("
							+ "select round(cast(rnd.porcentaje * ( cast ( sum(case when valor is null then 0 else valor end) as numeric)/count(1)) as numeric)/100) valordndos,  "
							+ "rnldb.relacionnotasdimension, "
							+ "rnd.relaciondimensionesasignaturasano "
							+ "from notascalificables nc "
							+ "right join relacionnotaslogrosdimensionboletin rnldb  on rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
							+ "and nc.registromatriculas =   " + regMat.getIdregistromatriculas()  + " "
							+ "join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
							+ "and rnd.periodos = " + p.getIdperiodos() + " "
							+ "group by rnldb.relacionnotasdimension, rnd.porcentaje, rnd.relaciondimensionesasignaturasano) pepe "
							+ "join relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = pepe.relaciondimensionesasignaturasano "
							+ "where rdaa.relacionasignaturasperiodos =  " + rap.getIdrelacionasignaturaperiodos() + " "
							+ "and rdaa.cursos =  " + cursoSeleccionado.getIdcursos() + " "
							+ "group by relaciondimensionesasignaturasano, rdaa.porcentaje, rdaa.dimensiones "
							+ "order by rdaa.dimensiones ";
				
						List<Object[]> dataListDimensionesValor = relacionlogrosdimensionesFacade.findoAll(query);
						double tmp = 0;
						for(Object[] o:dataListDimensionesValor){
							DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
							
							dimensionesEstudiantesTmp
							.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
									+ p.getIdperiodos()
									+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
									+ Long.parseLong(o[1].toString())));
							
							dimensionesEstudiantesTmp.setDimensiones(dimensionesFacade.find(Long.parseLong(o[4].toString())));
							dataListDimensionesEstudiantestmp.add(dimensionesEstudiantesTmp);
							dimensionesEstudiantesTmp.setValor(Double.parseDouble(o[0].toString()));
							
							tmp += Double.parseDouble(o[3].toString());
						}
						if(tmp > 100){
							tmp = 100;
						}
						periodosEstudiantes.setValor(new BigDecimal(tmp)
						.setScale(0,
								BigDecimal.ROUND_UP)
						.doubleValue());
						
						
//						// Cremos un objeto temporal tipo DimensionesEstudiantes
//						DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
//						// Le agregmos la dimensión
//						dimensionesEstudiantesTmp.setDimensiones(rdaa
//								.getDimensiones());
//						// Agregamos a la lista de las dimensiones que creamos
//						// antes esta nuevo dimensión
//						dataListDimensionesEstudiantestmp
//								.add(dimensionesEstudiantesTmp);
//
//						// Hacemos una lista temporal de las dimensiones
//						List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
//						// Agregamos la lista que acabamos de crear
//						dimensionesEstudiantesTmp
//								.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);
//
//						// Sacamos las notas que tiene que cada dimesion
//						List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
//								.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
//										+ rdaa.getIdrelaciondimensionesasignaturasano()
//										+ " AND R.periodos.idperiodos = "
//										+ p.getIdperiodos());
//						double promedioDimension = 0;
//
//						if (tmp.isEmpty()) {
//							dimensionesEstudiantesTmp.setValor(0);
//						}
//
//						// Recogemos la lista de logros
//						dimensionesEstudiantesTmp
//								.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
//										+ p.getIdperiodos()
//										+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
//										+ rdaa.getIdrelaciondimensionesasignaturasano()));
//
//						// Recorremos las notas de esta dimensión
//						for (Relacionnotasdimension rnd : tmp) {
//							// Hacemos un objeto temporal tipo
//							// NotasDimensionesEstudiantes
//							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
//							// Le agregamos la dimensión
//							notasDimensionesEstudiantes
//									.setRelacionnotasdimension(rnd);
//							// A la lista de las dimensiones le agregamos las
//							// dimensiones
//							dataListNotasDimensionesEstudiantes
//									.add(notasDimensionesEstudiantes);
//							// Creamos una lista temporal de actividades
//							List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
//							// Agregamos esta lista de actividades al objeto
//							// creado anteriormente
//							notasDimensionesEstudiantes
//									.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);
//
//							double promedioActividades = 0;
//							List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
//									.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
//											+ rnd.getIdrelacionnotasdimesion());
//
//							// Recorremos la lista de las actividades
//							for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {
//								ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
//								dataListActividadesNotasEstudiantes
//										.add(actividadesNotasEstudiantes);
//
//								List<Notascalificables> tmpNC = notascalificablesFacade
//										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
//												+ regMat.getIdregistromatriculas()
//												+ " AND "
//												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
//												+ rnld.getIdrelacionnotaslogrosdimensionboletin());
//
//								if (!tmpNC.isEmpty()) {
//									actividadesNotasEstudiantes
//											.setNotascalificables(tmpNC.get(0));
//									actividadesNotasEstudiantes.setValor(tmpNC
//											.get(0).getValor());
//									promedioActividades += tmpNC.get(0)
//											.getValor();
//								} else {
//									Notascalificables notasCalificablesTmp = new Notascalificables();
//									notasCalificablesTmp
//											.setRelacionnotaslogrosdimensionboletin(rnld);
//									actividadesNotasEstudiantes
//											.setNotascalificables(notasCalificablesTmp);
//									actividadesNotasEstudiantes.setValor(0);
//								}
//							}
//
//							// Validamos que la longitud de la lista no está
//							// vacia
//							if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
//								promedioActividades = 100;
//							} else {
//								promedioActividades = new BigDecimal(
//										promedioActividades
//												/ tmpRNLDB.size())
//										.setScale(0,
//												BigDecimal.ROUND_HALF_UP)
//										.doubleValue();
//							}
//
//							notasDimensionesEstudiantes
//									.setValor(promedioActividades);
//
//							promedioDimension = promedioDimension
//									+ new BigDecimal((promedioActividades
//											* rnd.getPorcentaje() / 100))
//											.setScale(0,
//													BigDecimal.ROUND_HALF_UP)
//											.doubleValue();
//							dimensionesEstudiantesTmp
//									.setValor(promedioDimension);
//						}
//
//						if (rdaa.getPorcentaje() != null) {
//							promedioDimension = new BigDecimal(
//									promedioDimension * rdaa.getPorcentaje()
//											/ 100).setScale(0,
//									BigDecimal.ROUND_HALF_UP).doubleValue();
//							promedioPeriodo += promedioDimension;
//							periodosEstudiantes.setValor(promedioPeriodo);
//						} else {
//							periodosEstudiantes.setValor(100);
//
//						}
//					}
					
					
					
					List<Recuperaciones> datalistRecuperacionesTMP = recuperacionesFacade
							.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

					if (periodosEstudiantes.getValor() < 80) {
						List<Relacionrecuperacionregistromatriculas> tmpRM2 = new ArrayList<Relacionrecuperacionregistromatriculas>();
						for (Recuperaciones r : datalistRecuperacionesTMP) {
							List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
									.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.periodos.idperiodos = "
											+ p.getIdperiodos()
											+ " AND R.registromatriculas.idregistromatriculas = "
											+ regMat.getIdregistromatriculas()
											+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
											+ rap.getIdrelacionasignaturaperiodos()
											+ " AND R.recuperaciones.idrecuperaciones = "
											+ r.getIdrecuperaciones());
							if (!tmpRM.isEmpty()) {
								periodosEstudiantes
										.setDataListRelacionrecuperacionregistromatriculas(tmpRM);
								// Miramos si el estudiante es nuevo
								if (periodosEstudiantes.getValor() == 0) {
									periodosEstudiantes.setValor(tmpRM.get(0)
											.getValor());
								} else {
									if (tmpRM.get(0).getValor() > periodosEstudiantes
											.getValor()) {
										periodosEstudiantes
												.setValor(new BigDecimal(
														(periodosEstudiantes
																.getValor() + tmpRM
																.get(0)
																.getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue());
									}
								}
								tmpRM2.add(tmpRM.get(0));
							} else {
								Recuperaciones recuperaciones = new Recuperaciones(
										new Long(0));
								recuperaciones.setNombre("Recuperación");
								Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
								relacionrecuperacionregistromatriculas
										.setPeriodos(p);
								relacionrecuperacionregistromatriculas
										.setRelacionasignaturaperiodos(rap);
								relacionrecuperacionregistromatriculas
										.setValor(0.0);
								relacionrecuperacionregistromatriculas
										.setRecuperaciones(recuperaciones);
								tmpRM2.add(relacionrecuperacionregistromatriculas);
							}
							periodosEstudiantes
									.setDataListRelacionrecuperacionregistromatriculas(tmpRM2);
						}
					} else {
						List<Relacionrecuperacionregistromatriculas> tmpRM = new ArrayList<Relacionrecuperacionregistromatriculas>();
						for (Recuperaciones r : datalistRecuperacionesTMP) {

							Recuperaciones recuperaciones = new Recuperaciones(
									new Long(0));
							recuperaciones.setNombre("Recuperación");
							Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
							relacionrecuperacionregistromatriculas
									.setPeriodos(p);
							relacionrecuperacionregistromatriculas
									.setRelacionasignaturaperiodos(rap);
							relacionrecuperacionregistromatriculas
									.setValor(0.0);
							relacionrecuperacionregistromatriculas
									.setRecuperaciones(recuperaciones);
							tmpRM.add(relacionrecuperacionregistromatriculas);
						}

						periodosEstudiantes
								.setDataListRelacionrecuperacionregistromatriculas(tmpRM);

					}

					promedioAsignatura += periodosEstudiantes.getValor();

					// System.out.print(promedioAsignatura + " PROM ASIG " +
					// periodosEstudiantes.getValor() + " PERIODO " +
					// periodosEstudiantes.getPeriodos().getNombre());
					//
					// if (!dataListPeriodosEstudiantesTmp.isEmpty()) {
					// // System.out.print(promedioAsignatura /
					// dataListPeriodosEstudiantesTmp.size() +
					// " PROMEDIO ASIGNA");
					// }

				}

				promedioAsignatura = new BigDecimal(promedioAsignatura
						/ dataListPeriodosEstudiantesTmp.size()).setScale(0,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				asignaturasEstudiantesTmp.setValor(promedioAsignatura);

				dataListAsignturasEstudiantes.add(asignaturasEstudiantesTmp);

				guardarNotaDefinitiva(periodoSeleccionado, rap, regMat,
						promedioAsignatura);

			}

			if (!dataListAsignturasEstudiantes.isEmpty()) {
				dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();
				for (AsignaturasEstudiantes as : dataListAsignturasEstudiantes) {
					for (PeriodosEstudiantes pe : as
							.getDataListPeriodosEstudiantes()) {
						dataListPeriodosEstudiantesHeader.add(pe);
					}
					break;
				}
			}
		}

		return dataListAsignturasEstudiantes;
	}

	// Método para imprimir la lista de las notas de cada estudiante en el
	// boletín
	public List<AsignaturasEstudiantes> getDataListAsignturasEstudiantesBoletin() {
		return dataListAsignturasEstudiantes;
	}

	// Método para colocar las observaciones del boletin
	public String getObservacionesBoletin() {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Observacionesboletines> obTmp = observacionesboletinesFacade
					.findByLike("SELECT O FROM Observacionesboletines O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				return obTmp.get(0).getObservacion();
			}
		}
		return "";
	}

	// Método para colocar las observaciones del boletin en grupo
	public String ObservacionesBoletin(Registromatriculas registromatriculas) {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Observacionesboletines> obTmp = observacionesboletinesFacade
					.findByLike("SELECT O FROM Observacionesboletines O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				System.out.print(obTmp.get(0).getObservacion());
				return obTmp.get(0).getObservacion();
			}
		}
		return "";
	}

	public void setObservacionesBoletin(String Observaciones) throws UnsupportedEncodingException {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Observacionesboletines> obTmp = observacionesboletinesFacade
					.findByLike("SELECT O FROM Observacionesboletines O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			String observaciones = new String(Observaciones.getBytes("ISO-8859-1"), "ISO-8859-1");
			
			if (obTmp.isEmpty()) {
				Observacionesboletines observacionesboletines = new Observacionesboletines(
						new Long(0));
				observacionesboletines.setObservacion(observaciones);
				observacionesboletines.setPeriodos(periodoSeleccionado);
				observacionesboletines
						.setRegistromatriculas(registromatriculas);
				observacionesboletinesFacade.create(observacionesboletines);
			} else {
				obTmp.get(0).setObservacion(observaciones);
				observacionesboletinesFacade.edit(obTmp.get(0));
			}
		}
	}

	// Fallas del boletín
	public int getFallasBoletin() {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Fallas> obTmp = fallasFacade
					.findByLike("SELECT O FROM Fallas O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				return obTmp.get(0).getTotal();
			}
		}
		return 0;
	}

	// Fallas del boletín por grupo
	public int FallasBoletin(Registromatriculas registromatriculas) {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Fallas> obTmp = fallasFacade
					.findByLike("SELECT O FROM Fallas O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				return obTmp.get(0).getTotal();
			}
		}
		return 0;
	}

	public void setFallasBoletin(int Observaciones) {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Fallas> obTmp = fallasFacade
					.findByLike("SELECT O FROM Fallas O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (obTmp.isEmpty()) {
				Fallas observacionesboletines = new Fallas(new Long(0));
				Short total = new Short(Observaciones + "");
				observacionesboletines.setTotal(total);
				observacionesboletines.setPeriodos(periodoSeleccionado);
				observacionesboletines
						.setRegistromatriculas(registromatriculas);
				fallasFacade.edit(observacionesboletines);
			} else {
				Short total = new Short(Observaciones + "");
				obTmp.get(0).setTotal(total);
				fallasFacade.edit(obTmp.get(0));
			}
		}
	}

	// Retardos del boletín por grupo
	public int RetardosBoletin(Registromatriculas registromatriculas) {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Retardos> obTmp = retardosFacade
					.findByLike("SELECT O FROM Retardos O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				return obTmp.get(0).getTotal();
			}
		}
		return 0;
	}

	// Retardos del boletín
	public int getRetardosBoletin() {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Retardos> obTmp = retardosFacade
					.findByLike("SELECT O FROM Retardos O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (!obTmp.isEmpty()) {
				return obTmp.get(0).getTotal();
			}
		}
		return 0;
	}

	public void setRetardosBoletin(int Observaciones) {
		if (periodoSeleccionado != null || registromatriculas != null) {
			List<Retardos> obTmp = retardosFacade
					.findByLike("SELECT O FROM Retardos O WHERE O.periodos.idperiodos = "
							+ periodoSeleccionado.getIdperiodos()
							+ " AND O.registromatriculas.idregistromatriculas = "
							+ registromatriculas.getIdregistromatriculas());
			if (obTmp.isEmpty()) {
				Retardos observacionesboletines = new Retardos(new Long(0));
				Short total = new Short(Observaciones + "");
				observacionesboletines.setTotal(total);
				observacionesboletines.setPeriodos(periodoSeleccionado);
				observacionesboletines
						.setRegistromatriculas(registromatriculas);
				retardosFacade.edit(observacionesboletines);
			} else {
				Short total = new Short(Observaciones + "");
				obTmp.get(0).setTotal(total);
				retardosFacade.edit(obTmp.get(0));
			}
		}
	}
	
	int margenInferior = 10;

	public int getMargenInferior() {
		return margenInferior;
	}

	public void setMargenInferior(int margenInferior) {
		this.margenInferior = margenInferior;
	}

	//
	public void imprimirBoletin() throws DocumentException, FileNotFoundException, BadElementException, MalformedURLException, IOException {
		if(periodoSeleccionado.getTipo() == 0)
			if(registromatriculas.getGrados().getTipo() == 1){
				imprimirBoletinAgrupado();
			}else{
				dataListAsignaturas = null;
//				calcularBoletin_Imprimir(registromatriculas);
				imprimirBoletinPeriodo();
			}
		else{
			//Asi esta bien solo que hubo una gran confusion
			if(registromatriculas.getGrados().getTipo() == 1){
				imprimirBoletinFinal();
			}else{
				imprimirBoletinFinalAgrupado();
			}
		}
	}
	
	//Método para imprimir el boletín final
	public void imprimirBoletinFinal() throws DocumentException, FileNotFoundException, BadElementException, MalformedURLException, IOException {
		if(margenInferior < 10){
			margenInferior = 10;
		}
		Document document = new Document(PageSize.LEGAL, 10, 10, 40f, margenInferior);
		String nombreFichero = registromatriculas.getEstudiantes()
				.getUsuarios().getNombres().toLowerCase().trim()
				.replaceAll(" ", "_")
				+ "_"
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toLowerCase();

		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();

		PdfContentByte cb = pdf.getDirectContent();
		//
		Image image3 = Image
				.getInstance(new URL(
						"http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png"));
		image3.setAbsolutePosition(50, 924f);
		image3.scaleAbsoluteHeight(75f);
		image3.scaleAbsoluteWidth(250f);
		document.add(image3);

		BaseFont bf = BaseFont.createFont();
		cb.beginText();

		// Informacion basica	
		String text3 = "GRADO: " + cursoSeleccionado.getGrados().getNombre().toUpperCase();
		String text31 = "PERIODO: " + periodoSeleccionado.getNombre().toUpperCase();
		DateFormat dia = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy",new Locale("es","ES"));
		
		String text4 = dia.format(periodoSeleccionado.getFechanotas());
		
		// Información del estudiante
		String nombre = "Nombre: "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getNombres().toUpperCase()
				+ " "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toUpperCase();
		cb.setFontAndSize(bf, 15);
		cb.setFontAndSize(bf, 13);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 460, 960f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 460, 975f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 410, 945, 0);
		

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
				+ getFallasBoletin(), 400, 875, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
				+ getRetardosBoletin(), 400, 861, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

		Paragraph paragraphTable1 = new Paragraph();
		paragraphTable1.setSpacingBefore(100);

		// boolean banderaInformacionInicial = true;

		PdfPCell celda;
		PdfPTable table;
		DecimalFormat decimalFormat = new DecimalFormat();

		cb.setFontAndSize(bf, 8);
		cb.endText();

		Font smallfont = new Font(Font.getFamily("HELVETICA"), 9, Font.NORMAL);
		Font materia = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);
	
		table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 7, 1, 1});
		
//		
//		List<Object[]> tmp = asignaturasFacade.findo("SELECT "
//				+ "a.asignatura1, "
//				+ "a.asignatura2, "
//				+ "aa.nombre "
//				+ "from asignaturasgrupales a "
//				+ " join asignaturas aa on aa.idasignaturas = a.asignatura1"
//				//+ " join anosacademicos aac on aac.idanosacademicos = a.idanosacademicos and aac.estadoactivo = 'true'"
//				+ " where a.grado = " +
//				registromatriculas.getGrados().getIdgrados()
//				+ " order by a.asignatura1"
//				);
		
		
		
		Anosacademicos anosacademicosCalcular = anosacademicosManual == null?getCurrentYear():anosacademicosManual;
		List<Object[]> tmp = asignaturasFacade.findo("SELECT "
				+ "a.asignatura1, "
				+ "a.asignatura2, "
				+ "aa.nombre "
				+ "from asignaturasgrupales a "
				+ "join asignaturas aa on aa.idasignaturas = a.asignatura1 "
				+ "where a.grado = " + registromatriculas.getGrados().getIdgrados() + " "
				+ "and a.idanosacademicos = " + anosacademicosCalcular.getIdanosacademicos()
				+ " order by a.asignatura1"
				);

		
		int idmateria = 0;
		int valorAsignatura = 0;
		
		Iterator itMaterias = tmp.iterator();
		while(itMaterias.hasNext()){
			Object[] asigagrup = (Object[]) itMaterias.next();
			if(idmateria != Integer.parseInt(asigagrup[0].toString())){
				idmateria = Integer.parseInt(asigagrup[0].toString());
				Iterator it2 = tmp.iterator();
				int contador = 0;
				while(it2.hasNext()){
					Object[] asigagrup2 = (Object[]) it2.next();
					if(Integer.parseInt(asigagrup2[0].toString()) == idmateria){
						for (Definitivas rm : dataListDefinitivas) {
							if(Integer.parseInt(rm.asignaturas.getIdasignaturas() + "") == Integer.parseInt(asigagrup2[1].toString())){
								valorAsignatura += rm.getDefinitiva();
								contador++;
							}
						}
					}
				}
				if(contador == 0){
					continue;
				}
				
				valorAsignatura = valorAsignatura/contador;
				
				//Hacemos una instancia del convertidor para convertir las materias
				String asignatura = asigagrup[2].toString();
				asignatura = capitalLetter.getAsString(null, null, asigagrup[2]);
				celda = new PdfPCell(new Phrase(asignatura, materia));
				table.addCell(celda);
				celda = new PdfPCell(new Phrase("JV: "+ valorAsignatura + "", smallfont));
				table.addCell(celda);
				celda = new PdfPCell(new Phrase(getJuicioValorativo(valorAsignatura), smallfont));
				table.addCell(celda);
				valorAsignatura = 0;
				it2 = tmp.iterator();
				while(it2.hasNext()){
					Object[] asigagrup2 = (Object[]) it2.next();
					if(Integer.parseInt(asigagrup2[0].toString()) == idmateria){
						for (Definitivas rm : dataListDefinitivas) {
							if(Integer.parseInt(rm.asignaturas.getIdasignaturas() + "") == Integer.parseInt(asigagrup2[1].toString())){
								List<Relacionlogrosdimensiones> dataListRelacionLogrosDimension =
										relacionlogrosdimensionesFacade.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1 AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = " + cursoSeleccionado.getGrados().getIdgrados() + " AND " +
												"R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.idasignaturas = " + asigagrup2[1]);
								Iterator<Relacionlogrosdimensiones> it= dataListRelacionLogrosDimension.iterator();
								while(it.hasNext()){
									String Logro = it.next().getLogros().getLogro();
									celda = new PdfPCell(new Phrase(Logro, smallfont));
									celda.setColspan(3);
									table.addCell(celda);
								}
							}
						}
					}
				}
			}
		}
		
		//Hacemos la tabla de las definitivas del comportamiento
		for(Definitivas def:dataListDefinitivas){
			if(def.getAsignaturas().getTipoasignatura() == 3){
				String asignatura = def.getAsignaturas().getNombre();
				asignatura = capitalLetter.getAsString(null, null, def.getAsignaturas().getNombre());
				celda = new PdfPCell(new Phrase(asignatura, materia));
				table.addCell(celda);
				celda = new PdfPCell(new Phrase(" ", smallfont));
				celda.setBorderWidthRight(0);
				celda.setBorderColorRight(BaseColor.WHITE);
				table.addCell(celda);
				celda = new PdfPCell(new Phrase(getJuicioValorativo(def.getDefinitiva()), smallfont));
				celda.setBorderWidthLeft(0);
				celda.setBorderColorLeft(BaseColor.WHITE);
				table.addCell(celda);
			}
		}
		
		paragraphTable1.add(table);
		
		document.add(paragraphTable1);
		
		
		// Nueva table
		Paragraph paragraphTable2 = new Paragraph();
		paragraphTable2.setSpacingBefore(15);
		PdfPCell celdaTmp;
		PdfPTable tableTmp;

		tableTmp = new PdfPTable(2); // Code 1

		tableTmp.setWidthPercentage(100);
		tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

		// Nombre de la asignatura
		celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		celdaTmp = new PdfPCell(
				new Phrase(ObservacionesBoletin(registromatriculas), smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		paragraphTable2.add(tableTmp);

		document.add(paragraphTable2);

		// Nueva table
		Paragraph paragraphTable3 = new Paragraph();
		paragraphTable3.setSpacingBefore(15);
		PdfPCell celdaTmp2;
		PdfPTable tableTmp2;

		tableTmp2 = new PdfPTable(2); // Code 1

		tableTmp2.setWidthPercentage(100);
		tableTmp2.setWidths(new float[] { 2, 2 });

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
				+ cursoSeleccionado.getProfesor().getUsuarios().getNombres()
				+ " "
				+ cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase(
				"Directora: Rosa Josefina Muñoz Archila", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		// celdaTmp2.setBorder(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// celdaTmp2 = new PdfPCell(new
		// Phrase(cursoSeleccionado.getProfesor().getUsuarios().getNombres()
		// + " " +
		// cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
		// smallfont));
		// celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		// celdaTmp2.setBorder(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		// tableTmp2.addCell(celdaTmp2);

		paragraphTable3.add(tableTmp2);

		document.add(paragraphTable3);
		
		
		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.setContentType("application/pdf; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}
	
	//Método para imprimir el boletín final Agrupado
	public void imprimirBoletinFinalAgrupado() throws DocumentException,
			FileNotFoundException, BadElementException, MalformedURLException,
			IOException {
		if (margenInferior < 10) {
			margenInferior = 10;
		}
		Document document = new Document(PageSize.LEGAL, 10, 10, 40f,
				margenInferior);
		String nombreFichero = registromatriculas.getEstudiantes()
				.getUsuarios().getNombres().toLowerCase().trim()
				.replaceAll(" ", "_")
				+ "_"
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toLowerCase();

		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();

		PdfContentByte cb = pdf.getDirectContent();
		//
		Image image3 = Image
				.getInstance(new URL(
						"http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png"));
		image3.setAbsolutePosition(50, 924f);
		image3.scaleAbsoluteHeight(75f);
		image3.scaleAbsoluteWidth(250f);
		// image3.scaleToFit(3000f, 75f);
		// image3.setWidthPercentage(90);
		document.add(image3);

		BaseFont bf = BaseFont.createFont();
		cb.beginText();

		String text3 = "GRADO: "
				+ cursoSeleccionado.getGrados().getNombre().toUpperCase();
		String text31 = "PERIODO: "
				+ periodoSeleccionado.getNombre().toUpperCase();

		DateFormat dia = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy",
				new Locale("es", "ES"));

		String text4 = dia.format(periodoSeleccionado.getFechanotas());

		// Información del estudiante
		String nombre = "Nombre: "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getNombres().toUpperCase()
				+ " "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toUpperCase();
		cb.setFontAndSize(bf, 15);
		// cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
		cb.setFontAndSize(bf, 13);
		// cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 460, 960f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 460, 975f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 410, 945, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
				+ getFallasBoletin(), 400, 875, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
				+ getRetardosBoletin(), 400, 861, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

		Paragraph paragraphTable1 = new Paragraph();
		paragraphTable1.setSpacingBefore(100);

		// boolean banderaInformacionInicial = true;

		PdfPCell celda;
		PdfPTable table;
		DecimalFormat decimalFormat = new DecimalFormat();

		cb.setFontAndSize(bf, 8);
		cb.endText();

		Font smallfont = new Font(Font.getFamily("HELVETICA"), 9, Font.NORMAL);
		Font materia = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);

		table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 7, 1, 1 });
		// Hacemos la tabla de las definitivas normales
		for (Definitivas def : dataListDefinitivas) {
			if (def.getAsignaturas().getTipoasignatura() == 0) {

				List<Relacionlogrosdimensiones> dataListRelacionLogrosDimension = relacionlogrosdimensionesFacade
						.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
								+ periodoSeleccionado.getIdperiodos()
								+ " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1 AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = "
								+ cursoSeleccionado.getGrados().getIdgrados()
								+ " AND "
								+ "R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.idasignaturas = "
								+ def.getAsignaturas().getIdasignaturas());
				if (!dataListRelacionLogrosDimension.isEmpty()) {
					// Hacemos una instancia del convertidor para convertir las
					// materias
					String asignatura = def.getAsignaturas().getNombre();
					asignatura = capitalLetter.getAsString(null, null, def
							.getAsignaturas().getNombre());
					celda = new PdfPCell(new Phrase(asignatura, materia));
					table.addCell(celda);
					celda = new PdfPCell(new Phrase("JV: "
							+ def.getDefinitiva() + "", smallfont));
					table.addCell(celda);
					celda = new PdfPCell(
							new Phrase(
									getJuicioValorativo(def.getDefinitiva()),
									smallfont));
					table.addCell(celda);

					Iterator<Relacionlogrosdimensiones> it = dataListRelacionLogrosDimension
							.iterator();
					while (it.hasNext()) {
						String Logro = it.next().getLogros().getLogro();
						celda = new PdfPCell(new Phrase(Logro, smallfont));
						celda.setColspan(3);
						table.addCell(celda);
					}
				}

			}
		}

		// Hacemos la tabla de las definitivas del comportamiento
		for (Definitivas def : dataListDefinitivas) {
			if (def.getAsignaturas().getTipoasignatura() == 3) {
				String asignatura = def.getAsignaturas().getNombre();
				asignatura = capitalLetter.getAsString(null, null, def
						.getAsignaturas().getNombre());
				celda = new PdfPCell(new Phrase(asignatura, materia));
				table.addCell(celda);
				celda = new PdfPCell(new Phrase(" ", smallfont));
				celda.setBorderWidthRight(0);
				celda.setBorderColorRight(BaseColor.WHITE);
				table.addCell(celda);
				celda = new PdfPCell(new Phrase(
						getJuicioValorativo(def.getDefinitiva()), smallfont));
				celda.setBorderWidthLeft(0);
				celda.setBorderColorLeft(BaseColor.WHITE);
				table.addCell(celda);
			}
		}

		paragraphTable1.add(table);

		document.add(paragraphTable1);

		// Nueva table
		Paragraph paragraphTable2 = new Paragraph();
		paragraphTable2.setSpacingBefore(15);
		PdfPCell celdaTmp;
		PdfPTable tableTmp;

		tableTmp = new PdfPTable(2); // Code 1

		tableTmp.setWidthPercentage(100);
		tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

		// Nombre de la asignatura
		celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		celdaTmp = new PdfPCell(new Phrase(
				ObservacionesBoletin(registromatriculas), smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		paragraphTable2.add(tableTmp);

		document.add(paragraphTable2);

		// Nueva table
		Paragraph paragraphTable3 = new Paragraph();
		paragraphTable3.setSpacingBefore(15);
		PdfPCell celdaTmp2;
		PdfPTable tableTmp2;

		tableTmp2 = new PdfPTable(2); // Code 1

		tableTmp2.setWidthPercentage(100);
		tableTmp2.setWidths(new float[] { 2, 2 });

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
				+ cursoSeleccionado.getProfesor().getUsuarios().getNombres()
				+ " "
				+ cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase(
				"Directora: Rosa Josefina Muñoz Archila", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);
		celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableTmp2.addCell(celdaTmp2);
		paragraphTable3.add(tableTmp2);

		document.add(paragraphTable3);

		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.setContentType("application/pdf; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}
	
	// Método para imprimir boletin por periodo
	public void imprimirBoletinPeriodo() throws DocumentException, FileNotFoundException, BadElementException, MalformedURLException, IOException {
		if(margenInferior < 10){
			margenInferior = 10;
		}
		Document document = new Document(PageSize.LEGAL, 10, 10, 40f, margenInferior);
		String nombreFichero = registromatriculas.getEstudiantes()
				.getUsuarios().getNombres().toLowerCase().trim()
				.replaceAll(" ", "_")
				+ "_"
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toLowerCase();

		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();

		PdfContentByte cb = pdf.getDirectContent();
		//
		Image image3 = Image
				.getInstance(new URL(
						"http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png"));
		image3.setAbsolutePosition(50, 924f);
		image3.scaleAbsoluteHeight(75f);
		image3.scaleAbsoluteWidth(250f);
//		image3.scaleToFit(3000f, 75f);
//		image3.setWidthPercentage(90);
		document.add(image3);

		BaseFont bf = BaseFont.createFont();
		cb.beginText();

		// Informacion basica
		String text3 = "GRADO: " + cursoSeleccionado.getGrados().getNombre().toUpperCase();
		String text31 = "PERIODO: " + periodoSeleccionado.getNombre().toUpperCase();


		DateFormat dia = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy",new Locale("es","ES"));
		
		String text4 = dia.format(periodoSeleccionado.getFechanotas());
		
		// Información del estudiante
		String nombre = "Nombre: "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getNombres().toUpperCase()
				+ " "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toUpperCase();
		cb.setFontAndSize(bf, 15);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
		cb.setFontAndSize(bf, 13);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 460, 960f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 460, 975f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 400, 940, 0);
		

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
				+ getFallasBoletin(), 400, 875, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
				+ getRetardosBoletin(), 400, 861, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

		Paragraph paragraphTable1 = new Paragraph();
		paragraphTable1.setSpacingBefore(100);

		// boolean banderaInformacionInicial = true;

		PdfPCell celda;
		PdfPTable table;
		DecimalFormat decimalFormat = new DecimalFormat();

		cb.setFontAndSize(bf, 8);
		cb.endText();

		Font smallfont = new Font(Font.getFamily("HELVETICA"), 9, Font.NORMAL);
		Font materia = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);

		// Empezamos a recorrer la tabla de las asignaturas
		for (AsignaturasEstudiantes rm : dataListAsignturasEstudiantes) {

			if (rm.getDataListPeriodosEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().isEmpty()
					&& rm.getAsignaturas().getTipoasignatura() == 0
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().isEmpty()
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().get(0)
							.getDataListRelacionlogrosdimensiones() != null) {
				table = new PdfPTable(4); // Code 1
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 9, 2, new Float("0.5"),
						new Float("0.5") });

				// Nombre de la asignatura
				celda = new PdfPCell(new Phrase(
						rm.getAsignaturas().getNombre(), materia));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(0);
				celda.setBorderWidthTop(new Float(0.5));
				table.addCell(celda);
				// Jucio valorativo
				//Colocamos el juicio valorativo que se calculo antes
//				if(periodoSeleccionado.getCalculo() == 1){
//					List<Definitivasasignaturasperiodos> tmpList = 
//							definitivasasignaturasperiodosFacade.findByLike("SELECT D FROM Definitivasasignaturasperiodos D WHERE D.registromatricula.idregistromatriculas = " 
//							+ registromatriculas.getIdregistromatriculas() + " AND D.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " "
//							+ "AND  D.relacionasignaturasperiodos.asignaturas.idasignaturas = " + rm.getAsignaturas().getIdasignaturas());
//					if(!tmpList.isEmpty()){
//						rm.setValor(tmpList.get(0).getValor());
//					}
//					
//				}
				if (rm.getValor() > 95) {
					celda = new PdfPCell(new Phrase(
							"JV:  " + decimalFormat.format(rm.getValor())
									+ " Superior", smallfont));
					celda.setBorderWidthBottom(new Float(0.5));
					celda.setBorderWidthLeft(new Float(0.5));
					celda.setBorderWidthRight(0);
					celda.setBorderWidthTop(new Float(0.5));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);

				} else {
					if (rm.getValor() > 79) {
						celda = new PdfPCell(new Phrase(
								"JV:  " + decimalFormat.format(rm.getValor())
										+ " Alto", smallfont));
						celda.setBorderWidthBottom(new Float(0.5));
						celda.setBorderWidthLeft(new Float(0.5));
						celda.setBorderWidthRight(0);
						celda.setBorderWidthTop(new Float(0.5));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else {
						if (rm.getValor() > 64) {
							celda = new PdfPCell(new Phrase("JV:  "
									+ decimalFormat.format(rm.getValor())
									+ " Básico", smallfont));
							celda.setBorderWidthBottom(new Float(0.5));
							celda.setBorderWidthLeft(new Float(0.5));
							celda.setBorderWidthRight(0);
							celda.setBorderWidthTop(new Float(0.5));
							celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							celda = new PdfPCell(new Phrase("JV:  "
									+ decimalFormat.format(rm.getValor())
									+ " Bajo", smallfont));
							celda.setBorderWidthBottom(new Float(0.5));
							celda.setBorderWidthLeft(new Float(0.5));
							celda.setBorderWidthRight(0);
							celda.setBorderWidthTop(new Float(0.5));
							celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						}

					}
				}

				table.addCell(celda);

				// Fortaleza
				celda = new PdfPCell(new Phrase("F", smallfont));
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(0);
				celda.setBorderWidthTop(new Float(0.5));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celda);

				// Por mejorara
				celda = new PdfPCell(new Phrase("PM", smallfont));
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(new Float(0.5));
				celda.setBorderWidthTop(new Float(0.5));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celda);

				// Agregamos al párrafo la tabla
				paragraphTable1.add(table);

				// Ahora vamos a agregar cada dimensión
				for (DimensionesEstudiantes de : rm
						.getDataListPeriodosEstudiantes().get(0)
						.getDataListDimensionesEstudiantes()) {

					table = new PdfPTable(1); // Code 1
					table.getDefaultCell().setPadding(0);
					table.setWidthPercentage(100);
					table.setWidths(new float[] { 10 });

					celda = new PdfPCell(new Phrase(de.getDimensiones()
							.getNombre()));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);

					celda.setBorder(0);
					celda.setBorderWidth(0);
					// celda.setBorder(0);
					// celda.setBorderWidth(0);
					// celda.setBorderWidth(0);
					// celda.setBorderWidthLeft(new Float(0.5));
					// celda.setBorderWidthRight(new Float(0.5));

					PdfPTable tableLogro;
					tableLogro = new PdfPTable(3);
					tableLogro.setWidthPercentage(100);
					tableLogro.setWidths(new float[] { 11, new Float("0.5"),
							new Float("0.5") });

					for (Relacionlogrosdimensiones rld : de
							.getDataListRelacionlogrosdimensiones()) {

						ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
						List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

						NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
						List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

						// Relacionlogrosnotasdimension
						Long idDimension = null;
						Long idRelacionNotasDimension = null;
						double suma = 0;
						int contador = 0;
						int contador2 = 0;
						List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
										+ rld.getLogros().getIdlogros()
										+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
										+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
						for (Relacionlogrosnotasdimension rnld : tmpList) {
							// Miramos
							if (idRelacionNotasDimension == null) {

								// Sacamos el id de la dimensión que estamos
								// manejando
								idDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getIdrelaciondimensionesasignaturasano();

								// Sacamos el id del item que estamos manejando
								idRelacionNotasDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion();
								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ registromatriculas
														.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getRelacionnotaslogrosdimensionboletin()
														.getIdrelacionnotaslogrosdimensionboletin());
								if (!tmpNC.isEmpty()) {
									suma += tmpNC.get(0).getValor();
								}
								promedioActividades.setValor(suma);

								// Agregamos el porcenta
								Notascalificables notascalificables = new Notascalificables(
										new Long(0));
								// Agregamos a notas calificables una de las
								// actividades para poder acceder luego al
								// porcentaje del item
								notascalificables
										.setRelacionnotaslogrosdimensionboletin(rnld
												.getRelacionnotaslogrosdimensionboletin());
								// Agregamos notascalificables a la relación
								// con registromatricula
								promedioActividades
										.setNotascalificables(notascalificables);
								// Le agregamos a la dimensión la relación
								// entre la dimensión y el item
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension());

								// Revisamos si llegamos al final de la lista
								if (contador2 + 1 == tmpList.size()) {
									// promedioActividades.setValor(suma);
									dataListNotasEstudiantes
											.add(promedioActividades);
									// Guardamos la lista de los items
									notasDimensionesEstudiantes
											.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

									// Agregamos a la lista grande los items de
									// la dimensión
									tmpDimensionesEstudiantes
											.add(notasDimensionesEstudiantes);
								}

							} else {
								// Miramos si hay cambio del item
								if (rnld.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {

										// Terminamos
										// Si hemos llegado al final entonces lo
										// que hacemos
										suma = new BigDecimal(suma / (contador))
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Miramos si el item ha cambiado
										if (idDimension == rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

										} else {

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}

									} else {
										idRelacionNotasDimension = rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getIdrelacionnotasdimesion();
										// aScamos el promedio de las
										// actividades por item
										suma = new BigDecimal(suma / contador)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										// Agregamos este valor un objeto
										promedioActividades.setValor(suma);
										// Agregamos este objeto a una lista
										dataListNotasEstudiantes
												.add(promedioActividades);

										if (idDimension != rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
											// --Sacar valor de la dimensión

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
											// Colocamos el nuevo valor de la
											// dimensión
											idDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano();
										}

										// Reiniciamos el objeto
										promedioActividades = new ActividadesNotasEstudiantes();
										if (promedioActividades
												.getNotascalificables() == null) {
											// Agregamos el porcenta
											Notascalificables notascalificables = new Notascalificables(
													new Long(0));
											// Agregamos a notas calificables
											// una de las actividades para poder
											// acceder luego al porcentaje del
											// item
											notascalificables
													.setRelacionnotaslogrosdimensionboletin(rnld
															.getRelacionnotaslogrosdimensionboletin());
											// Agregamos notascalificables a la
											// relación con registromatricula
											promedioActividades
													.setNotascalificables(notascalificables);
										}
										// Reiniciamos la variable
										suma = 0;

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculas
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());

										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
									}
									contador = 0;

								} else {

									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									if (contador2 + 1 == tmpList.size()) {
										suma = new BigDecimal(suma
												/ (contador + 1)).setScale(0,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}
								}
							}
							contador++;
							contador2++;
						}

						double sumaTotalLogro = 0;

						double sumaDimensiones = 0;

						for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
							sumaDimensiones += nde.getRelacionnotasdimension()
									.getRelaciondimensionesasignaturasano()
									.getPorcentaje();
						}

						boolean logrodPasado = true;

						if (!tmpDimensionesEstudiantes.isEmpty()) {

							// System.out.print("Ola si hubo algo");

							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								double sumaPromedioItem = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaPromedioItem += ane
											.getNotascalificables()
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getPorcentaje();
								}

								double sumaActividades = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									try{
										sumaActividades += new BigDecimal(
												(ane.getValor() * (ane
														.getNotascalificables()
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}catch(Exception e){
										throw new ValidatorException(new FacesMessage("Hay un error con la asignatura: " +
												rm.getAsignaturas().getNombre() + " Dimension: " + nde.getRelacionnotasdimension().getRelaciondimensionesasignaturasano().getDimensiones().getNombre()));
									}
								}

								sumaTotalLogro += new BigDecimal(
										(nde.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getPorcentaje() * 100 / sumaDimensiones)
												* sumaActividades / 100)
										.setScale(0, BigDecimal.ROUND_HALF_UP)
										.doubleValue();

							}

							// Miramos si pasamos el logro o no
							if (sumaTotalLogro < 80) {
								logrodPasado = false;

								// ----CUADRAR LAS RECUPERACIONES
								List<Relacionlogrosrecuperaciones> tmpRRList = relacionlogrosrecuperacionesFacade
										.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
												+ rld.getIdrelacionlogrosdimensiones());
								if (!tmpRRList.isEmpty()) {
									List<Relacionrecuperacionregistromatriculas> relacionrecuperacionList = relacionrecuperacionregistromatriculasFacade
											.findByLike("SELECT R FROM Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
													+ tmpRRList
															.get(0)
															.getRecuperaciones()
															.getIdrecuperaciones()
													+ " AND"
													+ " R.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas());
									if (!relacionrecuperacionList.isEmpty()) {
										if (sumaTotalLogro > 0) {
											sumaTotalLogro = new BigDecimal(
													(sumaTotalLogro + relacionrecuperacionList
															.get(0).getValor()) / 2)
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											if (sumaTotalLogro > 79) {
												logrodPasado = true;
											}
										} else {
											if (relacionrecuperacionList.get(0)
													.getValor() > 79) {
												logrodPasado = true;
											}
										}
									}
								}
							}
						}

						// Agregamo la lista de los logros por dimensión
						PdfPCell celdaLogro;
						celdaLogro = new PdfPCell(new Phrase(rld.getLogros()
								.getLogro(), smallfont));
						celdaLogro.setBorder(0);
						celdaLogro.setPaddingRight(0);;
						celdaLogro.setHorizontalAlignment(Element.ALIGN_LEFT);
						celdaLogro.setLeft(10);

						
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						// }

						celdaLogro.setBorderWidthLeft(new Float(0.5));

						tableLogro.addCell(celdaLogro);

						celdaLogro = new PdfPCell(new Phrase(" "));
						if (logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						// }

						// celdaLogro.setBorderColorTop(BaseColor.WHITE);
						tableLogro.addCell(celdaLogro);

						celdaLogro = new PdfPCell(new Phrase(""));
						if (!logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						celdaLogro.setBorderWidthRight(new Float(0.5));
						// celdaLogro.setBorderWidthBottom(new Float(0.5));
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						tableLogro.addCell(celdaLogro);
					}
					celda.setPadding(0);
					// celda.setPaddingBottom(0);
					// celda.setPaddingLeft(0);
					celda.setPaddingRight(0);
					// celda.setPaddingTop(0);
					celda.addElement(tableLogro);
					table.addCell(celda);
					// Agregamos al párrafo la tabla
					paragraphTable1.add(table);
				}

			}
		}

		for (AsignaturasEstudiantes rm : dataListAsignturasEstudiantes) {
			if (rm.getDataListPeriodosEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().isEmpty()
					&& rm.getAsignaturas().getTipoasignatura() == 3
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().isEmpty()
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().get(0)
							.getDataListRelacionlogrosdimensiones() != null) {
				PdfPTable tableComportamiento = new PdfPTable(4); // Code 1
				tableComportamiento.setWidthPercentage(100);
				tableComportamiento.setWidths(new float[] { 9, 2,
						new Float("0.5"), new Float("0.5") });
				// Nombre de la asignatura
				PdfPCell celdaComportamiento = new PdfPCell(new Phrase(rm
						.getAsignaturas().getNombre(), materia));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(0);
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				tableComportamiento.addCell(celdaComportamiento);
				// Jucio valorativo
				if (rm.getValor() > 95) {
					celdaComportamiento = new PdfPCell(new Phrase("JV:  "
							+ " Excelente", smallfont));
					celdaComportamiento.setBorderWidthBottom(new Float(0.5));
					celdaComportamiento.setBorderWidthLeft(new Float(0.5));
					celdaComportamiento.setBorderWidthRight(0);
					celdaComportamiento.setBorderWidthTop(new Float(0.5));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_LEFT);

				} else {
					if (rm.getValor() > 79) {
						celdaComportamiento = new PdfPCell(new Phrase("JV:  "
								+ " Sobresaliente", smallfont));
						celdaComportamiento
								.setBorderWidthBottom(new Float(0.5));
						celdaComportamiento.setBorderWidthLeft(new Float(0.5));
						celdaComportamiento.setBorderWidthRight(0);
						celdaComportamiento.setBorderWidthTop(new Float(0.5));
						celdaComportamiento
								.setHorizontalAlignment(Element.ALIGN_CENTER);
					} else {
						if (rm.getValor() > 64) {
							celdaComportamiento = new PdfPCell(new Phrase(
									"JV:  " + " Aceptable", smallfont));
							celdaComportamiento.setBorderWidthBottom(new Float(
									0.5));
							celdaComportamiento.setBorderWidthLeft(new Float(
									0.5));
							celdaComportamiento.setBorderWidthRight(0);
							celdaComportamiento
									.setBorderWidthTop(new Float(0.5));
							celdaComportamiento
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							celdaComportamiento = new PdfPCell(new Phrase(
									"JV:  " + " Insuficiente", smallfont));
							celdaComportamiento.setBorderWidthBottom(new Float(
									0.5));
							celdaComportamiento.setBorderWidthLeft(new Float(
									0.5));
							celdaComportamiento.setBorderWidthRight(0);
							celdaComportamiento
									.setBorderWidthTop(new Float(0.5));
							celdaComportamiento
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

					}
				}
				tableComportamiento.addCell(celdaComportamiento);
				// Fortaleza
				celdaComportamiento = new PdfPCell(new Phrase("F", smallfont));
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(0);
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableComportamiento.addCell(celdaComportamiento);

				// Por mejorara
				celdaComportamiento = new PdfPCell(new Phrase("PM", smallfont));
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(new Float(0.5));
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableComportamiento.addCell(celdaComportamiento);

				// Agregamos al párrafo la tabla
				paragraphTable1.add(tableComportamiento);

				// Ahora vamos a agregar cada dimensión
				for (DimensionesEstudiantes de : rm
						.getDataListPeriodosEstudiantes().get(0)
						.getDataListDimensionesEstudiantes()) {

					tableComportamiento = new PdfPTable(1); // Code 1
					tableComportamiento.getDefaultCell().setPadding(0);
					tableComportamiento.setWidthPercentage(100);
					tableComportamiento.setWidths(new float[] { 10 });

					celdaComportamiento = new PdfPCell(new Phrase(de
							.getDimensiones().getNombre()));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_LEFT);

					celdaComportamiento.setBorder(0);
					celdaComportamiento.setBorderWidth(0);
					PdfPTable tableLogro;
					tableLogro = new PdfPTable(3);
					tableLogro.setWidthPercentage(100);
					tableLogro.setWidths(new float[] { 11, new Float("0.5"),
							new Float("0.5") });

					// notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

					for (Relacionlogrosdimensiones rld : de
							.getDataListRelacionlogrosdimensiones()) {

						ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
						List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

						NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
						List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

						// Relacionlogrosnotasdimension
						Long idDimension = null;
						Long idRelacionNotasDimension = null;
						double suma = 0;
						int contador = 0;
						int contador2 = 0;
						List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
										+ rld.getLogros().getIdlogros()
										+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
										+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
						for (Relacionlogrosnotasdimension rnld : tmpList) {
							// Miramos
							if (idRelacionNotasDimension == null) {

								// Sacamos el id de la dimensión que estamos
								// manejando
								idDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getIdrelaciondimensionesasignaturasano();

								// Sacamos el id del item que estamos manejando
								idRelacionNotasDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion();
								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ registromatriculas
														.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getRelacionnotaslogrosdimensionboletin()
														.getIdrelacionnotaslogrosdimensionboletin());
								if (!tmpNC.isEmpty()) {
									suma += tmpNC.get(0).getValor();
								}
								promedioActividades.setValor(suma);

								// Agregamos el porcenta
								Notascalificables notascalificables = new Notascalificables(
										new Long(0));
								// Agregamos a notas calificables una de las
								// actividades para poder acceder luego al
								// porcentaje del item
								notascalificables
										.setRelacionnotaslogrosdimensionboletin(rnld
												.getRelacionnotaslogrosdimensionboletin());
								// Agregamos notascalificables a la relación
								// con registromatricula
								promedioActividades
										.setNotascalificables(notascalificables);
								// Le agregamos a la dimensión la relación
								// entre la dimensión y el item
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension());
								// Revisamos si llegamos al final de la lista
								if (contador2 + 1 == tmpList.size()) {
									// promedioActividades.setValor(suma);
									dataListNotasEstudiantes
											.add(promedioActividades);
									// Guardamos la lista de los items
									notasDimensionesEstudiantes
											.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

									// Agregamos a la lista grande los items de
									// la dimensión
									tmpDimensionesEstudiantes
											.add(notasDimensionesEstudiantes);
								}

							} else {
								// Miramos si hay cambio del item
								if (rnld.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {
										// Terminamos
										// Si hemos llegado al final entonces lo
										// que hacemos
										suma = new BigDecimal(suma / (contador)).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);
										// Miramos si el item ha cambiado
										if (idDimension == rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;
											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);
											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										} else {

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}

									} else {
										idRelacionNotasDimension = rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getIdrelacionnotasdimesion();
										// aScamos el promedio de las
										// actividades por item
										suma = new BigDecimal(suma / contador)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										// Agregamos este valor un objeto
										promedioActividades.setValor(suma);
										// Agregamos este objeto a una lista
										dataListNotasEstudiantes
												.add(promedioActividades);

										if (idDimension != rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
											// --Sacar valor de la dimensión

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
											// Colocamos el nuevo valor de la
											// dimensión
											idDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano();
										}

										// Reiniciamos el objeto
										promedioActividades = new ActividadesNotasEstudiantes();
										if (promedioActividades
												.getNotascalificables() == null) {
											// Agregamos el porcenta
											Notascalificables notascalificables = new Notascalificables(
													new Long(0));
											// Agregamos a notas calificables
											// una de las actividades para poder
											// acceder luego al porcentaje del
											// item
											notascalificables
													.setRelacionnotaslogrosdimensionboletin(rnld
															.getRelacionnotaslogrosdimensionboletin());
											// Agregamos notascalificables a la
											// relación con registromatricula
											promedioActividades
													.setNotascalificables(notascalificables);
										}
										// Reiniciamos la variable
										suma = 0;

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculas
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());

										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
									}
									contador = 0;

								} else {

									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									if (contador2 + 1 == tmpList.size()) {
										suma = new BigDecimal(suma
												/ (contador + 1)).setScale(0,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}
								}
							}
							contador++;
							contador2++;
						}
						double sumaTotalLogro = 0;
						double sumaDimensiones = 0;
						for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
							sumaDimensiones += nde.getRelacionnotasdimension()
									.getRelaciondimensionesasignaturasano()
									.getPorcentaje();
						}
						boolean logrodPasado = true;
						if (!tmpDimensionesEstudiantes.isEmpty()) {
							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								double sumaPromedioItem = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaPromedioItem += ane
											.getNotascalificables()
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getPorcentaje();
								}

								double sumaActividades = 0;
								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaActividades += new BigDecimal(
											(ane.getValor() * (ane
													.getNotascalificables()
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
								}
								sumaTotalLogro += new BigDecimal(
										(nde.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getPorcentaje() * 100 / sumaDimensiones)
												* sumaActividades / 100)
										.setScale(0, BigDecimal.ROUND_HALF_UP)
										.doubleValue();

							}

							if (sumaTotalLogro < 80) {
								logrodPasado = false;
							}
						}

						// Agregamo la lista de los logros por dimensión
						PdfPCell celdaLogro;
						celdaLogro = new PdfPCell(new Phrase(rld.getLogros()
								.getLogro(), smallfont));
						celdaLogro.setBorder(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setHorizontalAlignment(Element.ALIGN_LEFT);
						celdaLogro.setLeft(10);
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						tableLogro.addCell(celdaLogro);
						celdaLogro = new PdfPCell(new Phrase(" "));
						if (logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						tableLogro.addCell(celdaLogro);

						celdaLogro = new PdfPCell(new Phrase(""));
						if (!logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						celdaLogro.setBorderWidthRight(new Float(0.5));
						// celdaLogro.setBorderWidthBottom(new Float(0.5));
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						tableLogro.addCell(celdaLogro);

					}
					celdaComportamiento.setPadding(0);
					// celda.setPaddingBottom(0);
					// celda.setPaddingLeft(0);
					celdaComportamiento.setPaddingRight(0);
					// celda.setPaddingTop(0);
					celdaComportamiento.addElement(tableLogro);
					//
					tableComportamiento.addCell(celdaComportamiento);

					// Agregamos al párrafo la tabla
					paragraphTable1.add(tableComportamiento);

					// System.out.print("Si entró");
				}

			}
		}

		// Agregamos al documento el párrafo nuevo
		document.add(paragraphTable1);

		// Nueva table
		Paragraph paragraphTable2 = new Paragraph();
		paragraphTable2.setSpacingBefore(15);
		PdfPCell celdaTmp;
		PdfPTable tableTmp;

		tableTmp = new PdfPTable(2); // Code 1

		tableTmp.setWidthPercentage(100);
		tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

		// Nombre de la asignatura
		celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		celdaTmp = new PdfPCell(
				new Phrase(getObservacionesBoletin(), smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		paragraphTable2.add(tableTmp);

		document.add(paragraphTable2);

		// Nueva table
		Paragraph paragraphTable3 = new Paragraph();
		paragraphTable3.setSpacingBefore(15);
		PdfPCell celdaTmp2;
		PdfPTable tableTmp2;

		tableTmp2 = new PdfPTable(2); // Code 1

		tableTmp2.setWidthPercentage(100);
		tableTmp2.setWidths(new float[] { 2, 2 });

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
				+ cursoSeleccionado.getProfesor().getUsuarios().getNombres()
				+ " "
				+ cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase(
				"Directora: Rosa Josefina Muñoz Archila", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableTmp2.addCell(celdaTmp2);
		paragraphTable3.add(tableTmp2);
		document.add(paragraphTable3);
		// close the PDF document
		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}
	
	
	public void imprimirBoletinAgrupado() throws DocumentException, MalformedURLException, IOException{
		if(margenInferior < 10){
			margenInferior = 10;
		}
		Document document = new Document(PageSize.LEGAL, 10, 10, 40f, margenInferior);
		String nombreFichero = registromatriculas.getEstudiantes()
				.getUsuarios().getNombres().toLowerCase().trim()
				.replaceAll(" ", "_")
				+ "_"
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toLowerCase();

		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();

		PdfContentByte cb = pdf.getDirectContent();
		//
		Image image3 = Image
				.getInstance(new URL(
						"http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png"));
		image3.setAbsolutePosition(50, 915f);
		image3.scaleAbsoluteHeight(75f);
		image3.scaleAbsoluteWidth(250f);
		document.add(image3);

		BaseFont bf = BaseFont.createFont();
		cb.beginText();

		// Informacion basica
		String text3 = "GRADO: " + cursoSeleccionado.getGrados().getNombre().toUpperCase();
		String text31 = "PERIODO: " + periodoSeleccionado.getNombre().toUpperCase();


		DateFormat dia = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy",new Locale("es","ES"));
		
		String text4 = dia.format(periodoSeleccionado.getFechanotas());
		
		// Información del estudiante
		String nombre = "Nombre: "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getNombres().toUpperCase()
				+ " "
				+ registromatriculas.getEstudiantes().getUsuarios()
						.getApellidos().toUpperCase();
		cb.setFontAndSize(bf, 15);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
		cb.setFontAndSize(bf, 13);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 460, 955f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 460, 970f, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 410, 940, 0);
		

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
				+ getFallasBoletin(), 400, 875, 0);
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
				+ getRetardosBoletin(), 400, 861, 0);

		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

		Paragraph paragraphTable1 = new Paragraph();
		paragraphTable1.setSpacingBefore(100);

		// boolean banderaInformacionInicial = true;

		PdfPCell celda;
		PdfPTable table;
		DecimalFormat decimalFormat = new DecimalFormat();

		cb.setFontAndSize(bf, 8);
		cb.endText();

		Font smallfont = new Font(Font.getFamily("HELVETICA"), 9, Font.NORMAL);
		Font materia = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);
		Anosacademicos anosacademicosCalcular = anosacademicosManual == null?getCurrentYear():anosacademicosManual;
		List<Object[]> tmp = asignaturasFacade.findo("SELECT "
				+ "a.asignatura1, "
				+ "a.asignatura2, "
				+ "aa.nombre "
				+ "from asignaturasgrupales a "
				+ "join asignaturas aa on aa.idasignaturas = a.asignatura1 "
				+ "where a.grado = " + registromatriculas.getGrados().getIdgrados() + " "
				+ "and a.idanosacademicos = " + anosacademicosCalcular.getIdanosacademicos()
				+ " order by a.asignatura1"
				);
		
		int idmateria = 0;
		int valorAsignatura = 0;
		
		
		Iterator it = tmp.iterator();
		while(it.hasNext()){
			Object[] asigagrup = (Object[]) it.next();
			//Validamos que se este buscando una nueva asignatura
			if(idmateria != Integer.parseInt(asigagrup[0].toString())){
				idmateria = Integer.parseInt(asigagrup[0].toString());
				Iterator it2 = tmp.iterator();
				int contador = 0;
				while(it2.hasNext()){
					Object[] asigagrup2 = (Object[]) it2.next();
					if(Integer.parseInt(asigagrup2[0].toString()) == idmateria){
						for (AsignaturasEstudiantes rm : dataListAsignturasEstudiantes) {
							if(Integer.parseInt(rm.asignaturas.getIdasignaturas() + "") == Integer.parseInt(asigagrup2[1].toString())){
								valorAsignatura += rm.getValor();
								contador++;
							}
						}
					}
				}
				valorAsignatura = valorAsignatura/contador;
				//Insertamos la celda del valor de la materia
				//Inicio la fila de la materia y el valor
				table = new PdfPTable(4); // Code 1
				table.setWidthPercentage(100);
				table.setWidths(new float[] { 9, 2, new Float("0.5"),
						new Float("0.5") });
	
				// Nombre de la asignatura
				celda = new PdfPCell(new Phrase(
						asigagrup[2].toString(), materia));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(0);
				celda.setBorderWidthTop(new Float(0.5));
				table.addCell(celda);
	
				// Jucio valorativo
				if (valorAsignatura > 95) {
					celda = new PdfPCell(new Phrase(
							"JV:  " + decimalFormat.format(valorAsignatura)
									+ " Superior", smallfont));
					celda.setBorderWidthBottom(new Float(0.5));
					celda.setBorderWidthLeft(new Float(0.5));
					celda.setBorderWidthRight(0);
					celda.setBorderWidthTop(new Float(0.5));
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	
				} else {
					if (valorAsignatura > 79) {
						celda = new PdfPCell(new Phrase(
								"JV:  " + decimalFormat.format(valorAsignatura)
										+ " Alto", smallfont));
						celda.setBorderWidthBottom(new Float(0.5));
						celda.setBorderWidthLeft(new Float(0.5));
						celda.setBorderWidthRight(0);
						celda.setBorderWidthTop(new Float(0.5));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else {
						if (valorAsignatura > 64) {
							celda = new PdfPCell(new Phrase("JV:  "
									+ decimalFormat.format(valorAsignatura)
									+ " Básico", smallfont));
							celda.setBorderWidthBottom(new Float(0.5));
							celda.setBorderWidthLeft(new Float(0.5));
							celda.setBorderWidthRight(0);
							celda.setBorderWidthTop(new Float(0.5));
							celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							celda = new PdfPCell(new Phrase("JV:  "
									+ decimalFormat.format(valorAsignatura)
									+ " Bajo", smallfont));
							celda.setBorderWidthBottom(new Float(0.5));
							celda.setBorderWidthLeft(new Float(0.5));
							celda.setBorderWidthRight(0);
							celda.setBorderWidthTop(new Float(0.5));
							celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
	
					}
				}
	
				table.addCell(celda);
	
				// Fortaleza
				celda = new PdfPCell(new Phrase("F", smallfont));
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(0);
				celda.setBorderWidthTop(new Float(0.5));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celda);
	
				// Por mejorara
				celda = new PdfPCell(new Phrase("PM", smallfont));
				celda.setBorderWidthBottom(new Float(0.5));
				celda.setBorderWidthLeft(new Float(0.5));
				celda.setBorderWidthRight(new Float(0.5));
				celda.setBorderWidthTop(new Float(0.5));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celda);
	
				// Agregamos al párrafo la tabla
				paragraphTable1.add(table);
				valorAsignatura = 0;
				contador = 0;
			}
			
			// Empezamos a recorrer la tabla de las asignaturas
			for (AsignaturasEstudiantes rm : dataListAsignturasEstudiantes) {
				if (rm.getDataListPeriodosEstudiantes() != null
						&& Integer.parseInt(asigagrup[1].toString()) == Integer.parseInt(rm.getAsignaturas().getIdasignaturas() + "")
						&& !rm.getDataListPeriodosEstudiantes().isEmpty()
						&& rm.getAsignaturas().getTipoasignatura() == 0
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes() != null
						&& !rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().isEmpty()
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().get(0)
								.getDataListRelacionlogrosdimensiones() != null) {
					// Ahora vamos a agregar cada dimensión
					for (DimensionesEstudiantes de : rm
							.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes()) {
	
						table = new PdfPTable(1); // Code 1
						table.getDefaultCell().setPadding(0);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 10 });
	
						celda = new PdfPCell(new Phrase(de.getDimensiones()
								.getNombre()));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	
						celda.setBorder(0);
						celda.setBorderWidth(0);
						// celda.setBorder(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidthLeft(new Float(0.5));
						// celda.setBorderWidthRight(new Float(0.5));
	
						PdfPTable tableLogro;
						tableLogro = new PdfPTable(3);
						tableLogro.setWidthPercentage(100);
						tableLogro.setWidths(new float[] { 11, new Float("0.5"),
								new Float("0.5") });
	
						// notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
	
						for (Relacionlogrosdimensiones rld : de
								.getDataListRelacionlogrosdimensiones()) {
	
							ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
							List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
	
							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
							List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
	
							// Relacionlogrosnotasdimension
							Long idDimension = null;
							Long idRelacionNotasDimension = null;
							double suma = 0;
							int contador = 0;
							int contador2 = 0;
							List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
									.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
											+ rld.getLogros().getIdlogros()
											+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
											+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
							for (Relacionlogrosnotasdimension rnld : tmpList) {
								// Miramos
								if (idRelacionNotasDimension == null) {
	
									// Sacamos el id de la dimensión que estamos
									// manejando
									idDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getRelaciondimensionesasignaturasano()
											.getIdrelaciondimensionesasignaturasano();
	
									// Sacamos el id del item que estamos manejando
									idRelacionNotasDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion();
									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									promedioActividades.setValor(suma);
	
									// Agregamos el porcenta
									Notascalificables notascalificables = new Notascalificables(
											new Long(0));
									// Agregamos a notas calificables una de las
									// actividades para poder acceder luego al
									// porcentaje del item
									notascalificables
											.setRelacionnotaslogrosdimensionboletin(rnld
													.getRelacionnotaslogrosdimensionboletin());
									// Agregamos notascalificables a la relación
									// con registromatricula
									promedioActividades
											.setNotascalificables(notascalificables);
									// Le agregamos a la dimensión la relación
									// entre la dimensión y el item
									notasDimensionesEstudiantes
											.setRelacionnotasdimension(rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension());
	
									// Revisamos si llegamos al final de la lista
									if (contador2 + 1 == tmpList.size()) {
										// promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);
										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
	
										// Agregamos a la lista grande los items de
										// la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}
	
								} else {
									// Miramos si hay cambio del item
									if (rnld.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
										// Revisamos si llegamos al final de la
										// lista
										if (contador2 + 1 == tmpList.size()) {
	
											// Terminamos
											// Si hemos llegado al final entonces lo
											// que hacemos
											suma = new BigDecimal(suma / (contador))
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);
	
											// Miramos si el item ha cambiado
											if (idDimension == rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {
	
												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al porcentaje
													// del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos notascalificables a
													// la relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;
	
												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculas
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());
	
												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0).getValor();
												}
	
												promedioActividades.setValor(suma);
	
												dataListNotasEstudiantes
														.add(promedioActividades);
	
												// Guardamos la lista de los items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
	
											} else {
	
												// Guardamos la lista de los items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
	
												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión la
												// relación entre la dimensión y
												// el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());
	
												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
	
												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al porcentaje
													// del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos notascalificables a
													// la relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;
	
												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculas
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());
	
												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0).getValor();
												}
	
												promedioActividades.setValor(suma);
	
												dataListNotasEstudiantes
														.add(promedioActividades);
	
												// Guardamos la lista de los items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
											}
	
										} else {
											idRelacionNotasDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getIdrelacionnotasdimesion();
											// aScamos el promedio de las
											// actividades por item
											suma = new BigDecimal(suma / contador)
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											// Agregamos este valor un objeto
											promedioActividades.setValor(suma);
											// Agregamos este objeto a una lista
											dataListNotasEstudiantes
													.add(promedioActividades);
	
											if (idDimension != rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {
	
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
	
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
												// --Sacar valor de la dimensión
	
												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión la
												// relación entre la dimensión y
												// el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());
	
												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
												// Colocamos el nuevo valor de la
												// dimensión
												idDimension = rnld
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getRelaciondimensionesasignaturasano()
														.getIdrelaciondimensionesasignaturasano();
											}
	
											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas calificables
												// una de las actividades para poder
												// acceder luego al porcentaje del
												// item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a la
												// relación con registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;
	
											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());
	
											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}
										}
										contador = 0;
	
									} else {
	
										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculas
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());
										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
										if (contador2 + 1 == tmpList.size()) {
											suma = new BigDecimal(suma
													/ (contador + 1)).setScale(0,
													BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);
	
											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
	
											// Agregamos a la lista grande los items
											// de la dimensión
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}
									}
								}
								contador++;
								contador2++;
							}
	
							double sumaTotalLogro = 0;
	
							double sumaDimensiones = 0;
	
							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								sumaDimensiones += nde.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getPorcentaje();
							}
	
							boolean logrodPasado = true;
	
							if (!tmpDimensionesEstudiantes.isEmpty()) {
	
								// System.out.print("Ola si hubo algo");
	
								for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
									double sumaPromedioItem = 0;
	
									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaPromedioItem += ane
												.getNotascalificables()
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getPorcentaje();
									}
	
									double sumaActividades = 0;
	
									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaActividades += new BigDecimal(
												(ane.getValor() * (ane
														.getNotascalificables()
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
												.setScale(0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}
	
									sumaTotalLogro += new BigDecimal(
											(nde.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getPorcentaje() * 100 / sumaDimensiones)
													* sumaActividades / 100)
											.setScale(0, BigDecimal.ROUND_HALF_UP)
											.doubleValue();
	
								}
	
								// Miramos si pasamos el logro o no
								if (sumaTotalLogro < 80) {
									logrodPasado = false;
	
									// ----CUADRAR LAS RECUPERACIONES
									List<Relacionlogrosrecuperaciones> tmpRRList = relacionlogrosrecuperacionesFacade
											.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
													+ rld.getIdrelacionlogrosdimensiones());
									if (!tmpRRList.isEmpty()) {
										List<Relacionrecuperacionregistromatriculas> relacionrecuperacionList = relacionrecuperacionregistromatriculasFacade
												.findByLike("SELECT R FROM Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
														+ tmpRRList
																.get(0)
																.getRecuperaciones()
																.getIdrecuperaciones()
														+ " AND"
														+ " R.registromatriculas.idregistromatriculas = "
														+ registromatriculas
																.getIdregistromatriculas());
										if (!relacionrecuperacionList.isEmpty()) {
											if (sumaTotalLogro > 0) {
												sumaTotalLogro = new BigDecimal(
														(sumaTotalLogro + relacionrecuperacionList
																.get(0).getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue();
												if (sumaTotalLogro > 79) {
													logrodPasado = true;
												}
											} else {
												if (relacionrecuperacionList.get(0)
														.getValor() > 79) {
													logrodPasado = true;
												}
											}
										}
									}
								}
							}
	
							// Agregamo la lista de los logros por dimensión
							PdfPCell celdaLogro;
							celdaLogro = new PdfPCell(new Phrase(rld.getLogros()
									.getLogro(), smallfont));
							celdaLogro.setBorder(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setHorizontalAlignment(Element.ALIGN_LEFT);
							celdaLogro.setLeft(10);
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setBorderWidthLeft(new Float(0.5));
							tableLogro.addCell(celdaLogro);
	
							celdaLogro = new PdfPCell(new Phrase(" "));
							if (logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							tableLogro.addCell(celdaLogro);
	
							celdaLogro = new PdfPCell(new Phrase(""));
							if (!logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));
							celdaLogro.setBorderWidthRight(new Float(0.5));
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }
							tableLogro.addCell(celdaLogro);
	
						}
						celda.setPadding(0);
						celda.setPaddingRight(0);
						celda.addElement(tableLogro);
						table.addCell(celda);
						// Agregamos al párrafo la tabla
						paragraphTable1.add(table);
					}
	
				}
			}
		}
		
		//Ingresamos comportamiento
		for (AsignaturasEstudiantes rm : dataListAsignturasEstudiantes) {
			if (rm.getDataListPeriodosEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().isEmpty()
					&& rm.getAsignaturas().getTipoasignatura() == 3
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes() != null
					&& !rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().isEmpty()
					&& rm.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes().get(0)
							.getDataListRelacionlogrosdimensiones() != null) {
				
				PdfPTable tableComportamiento = new PdfPTable(4); // Code 1
				tableComportamiento.setWidthPercentage(100);
				tableComportamiento.setWidths(new float[] { 9, 2,
						new Float("0.5"), new Float("0.5") });

				// Nombre de la asignatura
				PdfPCell celdaComportamiento = new PdfPCell(new Phrase(rm
						.getAsignaturas().getNombre(), materia));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(0);
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				tableComportamiento.addCell(celdaComportamiento);
				
				// Jucio valorativo

				if (rm.getValor() > 95) {
					celdaComportamiento = new PdfPCell(new Phrase("JV:  "
							+ " Excelente", smallfont));
					celdaComportamiento.setBorderWidthBottom(new Float(0.5));
					celdaComportamiento.setBorderWidthLeft(new Float(0.5));
					celdaComportamiento.setBorderWidthRight(0);
					celdaComportamiento.setBorderWidthTop(new Float(0.5));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_LEFT);

				} else {
					if (rm.getValor() > 79) {
						celdaComportamiento = new PdfPCell(new Phrase("JV:  "
								+ " Sobresaliente", smallfont));
						celdaComportamiento
								.setBorderWidthBottom(new Float(0.5));
						celdaComportamiento.setBorderWidthLeft(new Float(0.5));
						celdaComportamiento.setBorderWidthRight(0);
						celdaComportamiento.setBorderWidthTop(new Float(0.5));
						celdaComportamiento
								.setHorizontalAlignment(Element.ALIGN_CENTER);
					} else {
						if (rm.getValor() > 64) {
							celdaComportamiento = new PdfPCell(new Phrase(
									"JV:  " + " Aceptable", smallfont));
							celdaComportamiento.setBorderWidthBottom(new Float(
									0.5));
							celdaComportamiento.setBorderWidthLeft(new Float(
									0.5));
							celdaComportamiento.setBorderWidthRight(0);
							celdaComportamiento
									.setBorderWidthTop(new Float(0.5));
							celdaComportamiento
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							celdaComportamiento = new PdfPCell(new Phrase(
									"JV:  " + " Insuficiente", smallfont));
							celdaComportamiento.setBorderWidthBottom(new Float(
									0.5));
							celdaComportamiento.setBorderWidthLeft(new Float(
									0.5));
							celdaComportamiento.setBorderWidthRight(0);
							celdaComportamiento
									.setBorderWidthTop(new Float(0.5));
							celdaComportamiento
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}

					}
				}

				tableComportamiento.addCell(celdaComportamiento);

				// celda = new PdfPCell(new Phrase("JV:  " + rm.getValor() +
				// "  B", smallfont));
				// celda.setBorderWidthBottom(new Float(0.5));
				// celda.setBorderWidthLeft(new Float(0.5));
				// celda.setBorderWidthRight(0);
				// celda.setBorderWidthTop(new Float(0.5));
				// celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				// tableComportamiento.addCell(celda);

				// Fortaleza
				celdaComportamiento = new PdfPCell(new Phrase("F", smallfont));
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(0);
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableComportamiento.addCell(celdaComportamiento);

				// Por mejorara
				celdaComportamiento = new PdfPCell(new Phrase("PM", smallfont));
				celdaComportamiento.setBorderWidthBottom(new Float(0.5));
				celdaComportamiento.setBorderWidthLeft(new Float(0.5));
				celdaComportamiento.setBorderWidthRight(new Float(0.5));
				celdaComportamiento.setBorderWidthTop(new Float(0.5));
				celdaComportamiento
						.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableComportamiento.addCell(celdaComportamiento);

				// Agregamos al párrafo la tabla
				paragraphTable1.add(tableComportamiento);

				// Ahora vamos a agregar cada dimensión
				for (DimensionesEstudiantes de : rm
						.getDataListPeriodosEstudiantes().get(0)
						.getDataListDimensionesEstudiantes()) {

					tableComportamiento = new PdfPTable(1); // Code 1
					tableComportamiento.getDefaultCell().setPadding(0);
					tableComportamiento.setWidthPercentage(100);
					tableComportamiento.setWidths(new float[] { 10 });

					celdaComportamiento = new PdfPCell(new Phrase(de
							.getDimensiones().getNombre()));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_LEFT);

					celdaComportamiento.setBorder(0);
					celdaComportamiento.setBorderWidth(0);
					// celda.setBorder(0);
					// celda.setBorderWidth(0);
					// celda.setBorderWidth(0);
					// celda.setBorderWidthLeft(new Float(0.5));
					// celda.setBorderWidthRight(new Float(0.5));

					PdfPTable tableLogro;
					tableLogro = new PdfPTable(3);
					tableLogro.setWidthPercentage(100);
					tableLogro.setWidths(new float[] { 11, new Float("0.5"),
							new Float("0.5") });

					// notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

					for (Relacionlogrosdimensiones rld : de
							.getDataListRelacionlogrosdimensiones()) {

						ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
						List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

						NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
						List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

						// Relacionlogrosnotasdimension
						Long idDimension = null;
						Long idRelacionNotasDimension = null;
						double suma = 0;
						int contador = 0;
						int contador2 = 0;
						List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
										+ rld.getLogros().getIdlogros()
										+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
										+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
						for (Relacionlogrosnotasdimension rnld : tmpList) {
							// Miramos
							if (idRelacionNotasDimension == null) {

								// Sacamos el id de la dimensión que estamos
								// manejando
								idDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getIdrelaciondimensionesasignaturasano();

								// Sacamos el id del item que estamos manejando
								idRelacionNotasDimension = rnld
										.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion();
								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ registromatriculas
														.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getRelacionnotaslogrosdimensionboletin()
														.getIdrelacionnotaslogrosdimensionboletin());
								if (!tmpNC.isEmpty()) {
									suma += tmpNC.get(0).getValor();
								}
								promedioActividades.setValor(suma);

								// Agregamos el porcenta
								Notascalificables notascalificables = new Notascalificables(
										new Long(0));
								// Agregamos a notas calificables una de las
								// actividades para poder acceder luego al
								// porcentaje del item
								notascalificables
										.setRelacionnotaslogrosdimensionboletin(rnld
												.getRelacionnotaslogrosdimensionboletin());
								// Agregamos notascalificables a la relación
								// con registromatricula
								promedioActividades
										.setNotascalificables(notascalificables);
								// Le agregamos a la dimensión la relación
								// entre la dimensión y el item
								notasDimensionesEstudiantes
										.setRelacionnotasdimension(rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension());

								// Revisamos si llegamos al final de la lista
								if (contador2 + 1 == tmpList.size()) {
									// promedioActividades.setValor(suma);
									dataListNotasEstudiantes
											.add(promedioActividades);
									// Guardamos la lista de los items
									notasDimensionesEstudiantes
											.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

									// Agregamos a la lista grande los items de
									// la dimensión
									tmpDimensionesEstudiantes
											.add(notasDimensionesEstudiantes);
								}

							} else {
								// Miramos si hay cambio del item
								if (rnld.getRelacionnotaslogrosdimensionboletin()
										.getRelacionnotasdimension()
										.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {

										// Terminamos
										// Si hemos llegado al final entonces lo
										// que hacemos
										suma = new BigDecimal(suma / (contador))
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Miramos si el item ha cambiado
										if (idDimension == rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

										} else {

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculas
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}

											promedioActividades.setValor(suma);

											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
											//
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}

									} else {
										idRelacionNotasDimension = rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getIdrelacionnotasdimesion();
										// aScamos el promedio de las
										// actividades por item
										suma = new BigDecimal(suma / contador)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										// Agregamos este valor un objeto
										promedioActividades.setValor(suma);
										// Agregamos este objeto a una lista
										dataListNotasEstudiantes
												.add(promedioActividades);

										if (idDimension != rnld
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getIdrelaciondimensionesasignaturasano()) {

											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
											// --Sacar valor de la dimensión

											// Reiniciamos la otra lista
											notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
											// Le agregamos a la dimensión la
											// relación entre la dimensión y
											// el item
											notasDimensionesEstudiantes
													.setRelacionnotasdimension(rnld
															.getRelacionnotaslogrosdimensionboletin()
															.getRelacionnotasdimension());

											// Reiniciamos la lista de
											dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
											// Colocamos el nuevo valor de la
											// dimensión
											idDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano();
										}

										// Reiniciamos el objeto
										promedioActividades = new ActividadesNotasEstudiantes();
										if (promedioActividades
												.getNotascalificables() == null) {
											// Agregamos el porcenta
											Notascalificables notascalificables = new Notascalificables(
													new Long(0));
											// Agregamos a notas calificables
											// una de las actividades para poder
											// acceder luego al porcentaje del
											// item
											notascalificables
													.setRelacionnotaslogrosdimensionboletin(rnld
															.getRelacionnotaslogrosdimensionboletin());
											// Agregamos notascalificables a la
											// relación con registromatricula
											promedioActividades
													.setNotascalificables(notascalificables);
										}
										// Reiniciamos la variable
										suma = 0;

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculas
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());

										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
									}
									contador = 0;

								} else {

									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculas
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									if (contador2 + 1 == tmpList.size()) {
										suma = new BigDecimal(suma
												/ (contador + 1)).setScale(0,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue();
										promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);

										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}
								}
							}
							contador++;
							contador2++;
						}

						double sumaTotalLogro = 0;

						double sumaDimensiones = 0;

						for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
							sumaDimensiones += nde.getRelacionnotasdimension()
									.getRelaciondimensionesasignaturasano()
									.getPorcentaje();
						}

						boolean logrodPasado = true;

						if (!tmpDimensionesEstudiantes.isEmpty()) {

							// System.out.print("Ola si hubo algo");

							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								double sumaPromedioItem = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaPromedioItem += ane
											.getNotascalificables()
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getPorcentaje();
								}

								double sumaActividades = 0;

								for (ActividadesNotasEstudiantes ane : nde
										.getDataListActividadesNotasEstudiantes()) {
									sumaActividades += new BigDecimal(
											(ane.getValor() * (ane
													.getNotascalificables()
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
								}

								sumaTotalLogro += new BigDecimal(
										(nde.getRelacionnotasdimension()
												.getRelaciondimensionesasignaturasano()
												.getPorcentaje() * 100 / sumaDimensiones)
												* sumaActividades / 100)
										.setScale(0, BigDecimal.ROUND_HALF_UP)
										.doubleValue();

							}

							if (sumaTotalLogro < 80) {
								logrodPasado = false;
							}
						}

						// Agregamo la lista de los logros por dimensión
						PdfPCell celdaLogro;
						celdaLogro = new PdfPCell(new Phrase(rld.getLogros()
								.getLogro(), smallfont));

						// celdaLogro.setPadding(0);
						celdaLogro.setBorder(0);
						// celdaLogro.setBorderWidthBottom(new Float(0.5));
						// celdaLogro.setPaddingBottom(0);
						// celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						// celdaLogro.setPaddingTop(0);
						celdaLogro.setHorizontalAlignment(Element.ALIGN_LEFT);
						celdaLogro.setLeft(10);

						// if
						// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
						// - 1).getDimensiones().getIddimensiones() !=
						// de.getDimensiones().getIddimensiones()
						// ||
						// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
						// - 1).getIdrelacionlogrosdimensiones() !=
						// rld.getIdrelacionlogrosdimensiones()) {
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						// }

						celdaLogro.setBorderWidthLeft(new Float(0.5));

						tableLogro.addCell(celdaLogro);

						celdaLogro = new PdfPCell(new Phrase(" "));
						if (logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						// celdaLogro.setBorderWidthLeft(new Float(0.5));
						// celdaLogro.setBorderWidthBottom(new Float(0.5));
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));

						// if
						// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
						// - 1).getDimensiones().getIddimensiones() !=
						// de.getDimensiones().getIddimensiones()
						// ||
						// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
						// - 1).getIdrelacionlogrosdimensiones() !=
						// rld.getIdrelacionlogrosdimensiones()) {
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						// }

						// System.out.print(document.getPageNumber() +
						// " numero ");

						// if(pageNumber != document.getPageNumber()){
						// celdaLogro.setBorderWidthTop(new Float(0.5));
						// pageNumber = document.getPageNumber();
						// }

						// celdaLogro.setBorderColorTop(BaseColor.WHITE);
						tableLogro.addCell(celdaLogro);

						celdaLogro = new PdfPCell(new Phrase(""));
						if (!logrodPasado) {
							celdaLogro.setPhrase(new Phrase("x"));
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						celdaLogro.setBorder(0);
						celdaLogro.setBorderWidthLeft(new Float(0.5));
						celdaLogro.setBorderWidthRight(new Float(0.5));
						// celdaLogro.setBorderWidthBottom(new Float(0.5));
						celdaLogro.setPadding(0);
						celdaLogro.setPaddingBottom(0);
						celdaLogro.setPaddingLeft(0);
						celdaLogro.setPaddingRight(0);
						celdaLogro.setPaddingTop(0);
						// celdaLogro.setBorder(0);
						// celdaLogro.setBorderColorTop(BaseColor.WHITE);
						// if
						// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
						// - 1).getDimensiones().getIddimensiones() !=
						// de.getDimensiones().getIddimensiones()
						// ||
						// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
						// - 1).getIdrelacionlogrosdimensiones() !=
						// rld.getIdrelacionlogrosdimensiones()) {
						celdaLogro.setBorderWidthTop(new Float(0.5));
						celdaLogro.setBorderWidthBottom(new Float(0.5));
						// }
						tableLogro.addCell(celdaLogro);

					}
					celdaComportamiento.setPadding(0);
					// celda.setPaddingBottom(0);
					// celda.setPaddingLeft(0);
					celdaComportamiento.setPaddingRight(0);
					// celda.setPaddingTop(0);
					celdaComportamiento.addElement(tableLogro);
					//
					tableComportamiento.addCell(celdaComportamiento);

					// Agregamos al párrafo la tabla
					paragraphTable1.add(tableComportamiento);

					// System.out.print("Si entró");
				}

			}
		}
		

		// Agregamos al documento el párrafo nuevo
		document.add(paragraphTable1);

		// Nueva table
		Paragraph paragraphTable2 = new Paragraph();
		paragraphTable2.setSpacingBefore(15);
		PdfPCell celdaTmp;
		PdfPTable tableTmp;

		tableTmp = new PdfPTable(2); // Code 1

		tableTmp.setWidthPercentage(100);
		tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

		// Nombre de la asignatura
		celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		celdaTmp = new PdfPCell(
				new Phrase(getObservacionesBoletin(), smallfont));
		celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		celdaTmp.setBorder(0);
		celdaTmp.setBorderWidthBottom(0);
		celdaTmp.setBorderWidthLeft(0);
		celdaTmp.setBorderWidthRight(0);
		celdaTmp.setBorderWidthTop(0);
		tableTmp.addCell(celdaTmp);

		paragraphTable2.add(tableTmp);

		document.add(paragraphTable2);

		// Nueva table
		Paragraph paragraphTable3 = new Paragraph();
		paragraphTable3.setSpacingBefore(15);
		PdfPCell celdaTmp2;
		PdfPTable tableTmp2;

		tableTmp2 = new PdfPTable(2); // Code 1

		tableTmp2.setWidthPercentage(100);
		tableTmp2.setWidths(new float[] { 2, 2 });

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// Nombre de la asignatura
		celdaTmp2 = new PdfPCell(new Phrase(
				"___________________________________", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
				+ cursoSeleccionado.getProfesor().getUsuarios().getNombres()
				+ " "
				+ cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase(
				"Directora: Rosa Josefina Muñoz Archila", smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaTmp2.setBorder(0);
		celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
				smallfont));
		celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		// celdaTmp2.setBorder(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		tableTmp2.addCell(celdaTmp2);

		// celdaTmp2 = new PdfPCell(new
		// Phrase(cursoSeleccionado.getProfesor().getUsuarios().getNombres() +
		// " " + cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
		// smallfont));
		// celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
		// celdaTmp2.setBorder(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthBottom(0);
		// celdaTmp2.setBorderWidthLeft(0);
		// celdaTmp2.setPadding(0);
		// celdaTmp2.setBorderWidthRight(0);
		// celdaTmp2.setBorderWidthTop(0);
		// celdaTmp2.setPadding(0);
		// tableTmp2.addCell(celdaTmp2);

		paragraphTable3.add(tableTmp2);

		document.add(paragraphTable3);

		// document.add(new
		// Paragraph("Este es un archivo con 3 pagina: Pagina 1"));
		// document.newPage();
		// pdf.setPageEmpty(false);
		// document.newPage();
		// document.add(new Paragraph("Pagina 3"));

		// close the PDF document
		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}

	//Métoodo para el juicio valorativo
	public String getJuicioValorativo(double calificacion){
		if (calificacion > 95) {
			return "Superior";
		}
		if (calificacion > 79) {
			return "Alto";
		}
		if (calificacion > 64) {
			return "Básico";
		}
		return "Bajo";
	}
	
	//Método para los boletines en grupo
	public void imprimirBoletinGrupo() throws FileNotFoundException, BadElementException, MalformedURLException, DocumentException, IOException{
		if(periodoSeleccionado.getTipo() == 0){
			imprimirBoletinGrupoPeriodo();
		}else{
			imprimirBoletinGrupoFinal();
		}
	}
	
	//Método para imprimir el boletín final en grupo
	public void imprimirBoletinGrupoFinal() throws DocumentException, MalformedURLException, IOException{
		Document document = new Document(PageSize.LEGAL, 10, 10, 40f, 70f);
		String nombreFichero = periodoSeleccionado.getNombre()
				+ "Periodo_Grado"
				+ cursoSeleccionado.getGrados().getNombre().toLowerCase();

		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();
		
		//
		for (Registromatriculas rem : dataListRegistroMatriculas) {
			
			//Hacemos el registro matriculas global igual a cada uno de las listas 
			registromatriculas = rem;
			
			PdfContentByte cb = pdf.getDirectContent();
			//
			Image image3 = Image
					.getInstance(new URL(
							"http://localhost:8080/sistemaColegio/resources/imagenes/Escudo.png"));
			image3.setAbsolutePosition(20f, 938f);
			// image3.scaleAbsolute(10f, 10f);
			// image3.scaleAbsolute(1f, 1f);
			image3.scaleToFit(75f, 75f);
			document.add(image3);

			BaseFont bf = BaseFont.createFont();
			cb.beginText();

			// Información básica
			String text = configuracionesFacade.retornarObject("SELECT C.valor FROM Configuraciones C WHERE C.propiedad = 'nombrecolegio'").toString().toUpperCase();
			String text2 = configuracionesFacade.retornarObject("SELECT C.valor FROM Configuraciones C WHERE C.propiedad = 'resolucion'").toString();
			String text3 = "Grado: "+ cursoSeleccionado.getGrados().getNombre();
			String text31 = configuracionesFacade.retornarObject("SELECT C.valor FROM Configuraciones C WHERE C.propiedad = 'nombreboletinfinal'").toString() + periodoSeleccionado.getNombre();

			DateFormat dia = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",new Locale("es","ES"));
			
			String text4 = dia.format(periodoSeleccionado.getFechanotas());

			// Información del estudiante
			String nombre = "Nombre: "
					+ registromatriculas.getEstudiantes().getUsuarios()
							.getNombres().toUpperCase()
					+ " "
					+ registromatriculas.getEstudiantes().getUsuarios()
							.getApellidos().toUpperCase();
			cb.setFontAndSize(bf, 15);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
			cb.setFontAndSize(bf, 13);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 200, 918, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 310, 918, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 230, 904, 0);
//
//			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
//					+ getFallasBoletin(), 400, 875, 0);
//			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
//					+ getRetardosBoletin(), 400, 861, 0);

			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

			Paragraph paragraphTable1 = new Paragraph();
			paragraphTable1.setSpacingBefore(100);

			PdfPCell celda;
			PdfPTable table;

			cb.setFontAndSize(bf, 8);
			cb.endText();

			Font smallfont = new Font(Font.getFamily("HELVETICA"), 10, Font.NORMAL);
			Font materia = new Font(Font.getFamily("HELVETICA"), 12, Font.BOLD);
			
			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 7, 1, 1});
			
			celda = new PdfPCell(new Phrase("Asignatura", materia));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(celda);
			
			celda = new PdfPCell(new Phrase("Juicio Valorativo", materia));
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setColspan(2);
			table.addCell(celda);
			
			
			
			//Calculamos las definitivas de este usuario
			calcularDefinitivas();
			// Hacemos la tabla de las definitivas
			for (Definitivas def : getDataListDefinitivasasignaturasperiodos()) {
				if (def.getAsignaturas().getTipoasignatura() == 0) {
					//Hacemos una instancia del convertidor para convertir las materias
					List<Relacionlogrosdimensiones> dataListRelacionLogrosDimension =
							relacionlogrosdimensionesFacade.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1 AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = " + cursoSeleccionado.getGrados().getIdgrados() + " AND " +
									"R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.asignaturas.idasignaturas = " + def.getAsignaturas().getIdasignaturas());
					if(!dataListRelacionLogrosDimension.isEmpty()){
						//Hacemos una instancia del convertidor para convertir las materias
						String asignatura = def.getAsignaturas().getNombre();
						asignatura = capitalLetter.getAsString(null, null, def.getAsignaturas().getNombre());
						celda = new PdfPCell(new Phrase(asignatura, materia));
						table.addCell(celda);
						celda = new PdfPCell(new Phrase("JV: "+ def.getDefinitiva() + "", smallfont));
						table.addCell(celda);
						celda = new PdfPCell(new Phrase(getJuicioValorativo(def.getDefinitiva()), smallfont));
						table.addCell(celda);
		
						Iterator<Relacionlogrosdimensiones> it= dataListRelacionLogrosDimension.iterator();
						while(it.hasNext()){
							String Logro = it.next().getLogros().getLogro();
							celda = new PdfPCell(new Phrase(Logro, smallfont));
							celda.setColspan(3);
							table.addCell(celda);
						}
					}
					
				}
			}
			
			
			//Hacemos la tabla de las definitivas del comportamiento
			for(Definitivas def:dataListDefinitivas){
				if(def.getAsignaturas().getTipoasignatura() == 3){
					String asignatura = def.getAsignaturas().getNombre();
					asignatura = capitalLetter.getAsString(null, null, def.getAsignaturas().getNombre());
					celda = new PdfPCell(new Phrase(asignatura, materia));
					table.addCell(celda);
					celda = new PdfPCell(new Phrase(" ", smallfont));
					celda.setBorderWidthRight(0);
					celda.setBorderColorRight(BaseColor.WHITE);
					table.addCell(celda);
					celda = new PdfPCell(new Phrase(getJuicioValorativo(def.getDefinitiva()), smallfont));
					celda.setBorderWidthLeft(0);
					celda.setBorderColorLeft(BaseColor.WHITE);
					table.addCell(celda);
				}
			}

			paragraphTable1.add(table);

			document.add(paragraphTable1);
			
			
			//Agregamos final de los boletines
			Paragraph paragraphTable2 = new Paragraph();
			paragraphTable2.setSpacingBefore(15);
			PdfPCell celdaTmp;
			PdfPTable tableTmp;

			tableTmp = new PdfPTable(2); // Code 1

			tableTmp.setWidthPercentage(100);
			tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

			// Agregamos el tag de las observaciones
			celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
			celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
			celdaTmp.setBorder(0);
			celdaTmp.setBorderWidthBottom(0);
			celdaTmp.setBorderWidthLeft(0);
			celdaTmp.setBorderWidthRight(0);
			celdaTmp.setBorderWidthTop(0);
			tableTmp.addCell(celdaTmp);

			celdaTmp = new PdfPCell(new Phrase(ObservacionesBoletin(rem),
					smallfont));
			celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celdaTmp.setBorder(0);
			celdaTmp.setBorderWidthBottom(0);
			celdaTmp.setBorderWidthLeft(0);
			celdaTmp.setBorderWidthRight(0);
			celdaTmp.setBorderWidthTop(0);
			tableTmp.addCell(celdaTmp);

			paragraphTable2.add(tableTmp);

			document.add(paragraphTable2);

			// Nueva table
			Paragraph paragraphTable3 = new Paragraph();
			paragraphTable3.setSpacingBefore(15);
			PdfPCell celdaTmp2;
			PdfPTable tableTmp2;

			tableTmp2 = new PdfPTable(2); // Code 1

			tableTmp2.setWidthPercentage(100);
			tableTmp2.setWidths(new float[] { 2, 2 });

			// Nombre de la asignatura
			celdaTmp2 = new PdfPCell(new Phrase(
					"___________________________________", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			// Nombre de la asignatura
			celdaTmp2 = new PdfPCell(new Phrase(
					"___________________________________", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
					+ cursoSeleccionado.getProfesor().getUsuarios()
							.getNombres()
					+ " "
					+ cursoSeleccionado.getProfesor().getUsuarios()
							.getApellidos(), smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase(
					"Directora: Rosa Josefina Muñoz Archila", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
					smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			// celdaTmp2.setBorder(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			// celdaTmp2 = new PdfPCell(new
			// Phrase(cursoSeleccionado.getProfesor().getUsuarios().getNombres()
			// + " " +
			// cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
			// smallfont));
			// celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			// celdaTmp2.setBorder(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			// tableTmp2.addCell(celdaTmp2);

			paragraphTable3.add(tableTmp2);

			document.add(paragraphTable3);
			
			
			// document.add(new
			// Paragraph("Este es un archivo con 3 pagina: Pagina 1"));
			// document.newPage();
			pdf.setPageEmpty(false);
			document.newPage();
		}
		
		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}
	
	// Método para imprimir el boletin por periodo en grupo
	public void imprimirBoletinGrupoPeriodo() throws DocumentException,
			FileNotFoundException, BadElementException, MalformedURLException,
			IOException {
		String nombreFichero = periodoSeleccionado.getNombre()
				+ "Periodo_Grado"
				+ cursoSeleccionado.getGrados().getNombre().toLowerCase();

		Document document = new Document(PageSize.LEGAL, 10, 10, 40f, 70f);
		// create a PDF writer
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
				nombreFichero + ".pdf"));

		// open the PDF document
		document.open();

		for (Registromatriculas rem : dataListRegistroMatriculas) {
			dataListAsignaturas = null;

			List<AsignaturasEstudiantes> dataListAsignaturasEstudiantes = calcularBoletin(rem);

			Registromatriculas registromatriculasGrupo = rem;
			
			PdfContentByte cb = pdf.getDirectContent();
			//
			Image image3 = Image
					.getInstance(new URL(
							"http://localhost:8080/sistemaColegio/resources/imagenes/Escudo.png"));
			image3.setAbsolutePosition(20f, 938f);
			// image3.scaleAbsolute(10f, 10f);
			// image3.scaleAbsolute(1f, 1f);
			image3.scaleToFit(75f, 75f);
			document.add(image3);

			BaseFont bf = BaseFont.createFont();
			cb.beginText();

			// Información básica
			String text = "COLEGIO PEDAGÓGICO CAMPESTRE FLORIDABLANCA";
			String text2 = "Resolución No. 0241 de 9 de Junio de 2004";
			String text3 = "Grado: "
					+ cursoSeleccionado.getGrados().getNombre();
			String text31 = "Periodo: " + periodoSeleccionado.getNombre();

			DateFormat dia = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",new Locale("es","ES"));
			
			String text4 = dia.format(periodoSeleccionado.getFechanotas());
			
			String nombre = "Nombre: "
					+ registromatriculasGrupo.getEstudiantes().getUsuarios()
							.getNombres().toUpperCase()
					+ " "
					+ registromatriculasGrupo.getEstudiantes().getUsuarios()
							.getApellidos().toUpperCase();
			cb.setFontAndSize(bf, 15);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 120, 950, 0);
			cb.setFontAndSize(bf, 13);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 180, 933, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 200, 918, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text31, 310, 918, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text4, 230, 904, 0);
			;

			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fallas: "
					+ FallasBoletin(rem), 400, 875, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Retardos: "
					+ RetardosBoletin(rem), 400, 861, 0);

			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombre, 20, 875, 0);

			Paragraph paragraphTable1 = new Paragraph();
			paragraphTable1.setSpacingBefore(100);

			// boolean banderaInformacionInicial = true;

			PdfPCell celda;
			PdfPTable table;
			DecimalFormat decimalFormat = new DecimalFormat();

			cb.setFontAndSize(bf, 8);
			cb.endText();

			Font smallfont = new Font(Font.getFamily("HELVETICA"), 9,
					Font.NORMAL);
			Font materia = new Font(Font.getFamily("HELVETICA"), 9, Font.BOLD);

			// Empezamos a recorrer la tabla de las asignaturas
			for (AsignaturasEstudiantes rm : dataListAsignaturasEstudiantes) {

				if (rm.getDataListPeriodosEstudiantes() != null
						&& !rm.getDataListPeriodosEstudiantes().isEmpty()
						&& rm.getAsignaturas().getTipoasignatura() == 0
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes() != null
						&& !rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().isEmpty()
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().get(0)
								.getDataListRelacionlogrosdimensiones() != null
						&& !rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().get(0)
								.getDataListNotasDimensionesEstudiantes()
								.isEmpty()) {
					table = new PdfPTable(4); // Code 1
					table.setWidthPercentage(100);
					table.setWidths(new float[] { 9, 2, new Float("0.5"),
							new Float("0.5") });

					// Nombre de la asignatura
					celda = new PdfPCell(new Phrase(rm.getAsignaturas()
							.getNombre(), materia));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					celda.setBorderWidthBottom(new Float(0.5));
					celda.setBorderWidthLeft(new Float(0.5));
					celda.setBorderWidthRight(0);
					celda.setBorderWidthTop(new Float(0.5));
					table.addCell(celda);

					// Jucio valorativo
					if (rm.getValor() > 95) {
						celda = new PdfPCell(new Phrase("JV:  "
								+ decimalFormat.format(rm.getValor())
								+ " Superior", smallfont));
						celda.setBorderWidthBottom(new Float(0.5));
						celda.setBorderWidthLeft(new Float(0.5));
						celda.setBorderWidthRight(0);
						celda.setBorderWidthTop(new Float(0.5));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);

					} else {
						if (rm.getValor() > 79) {
							celda = new PdfPCell(new Phrase("JV:  "
									+ decimalFormat.format(rm.getValor())
									+ " Alto", smallfont));
							celda.setBorderWidthBottom(new Float(0.5));
							celda.setBorderWidthLeft(new Float(0.5));
							celda.setBorderWidthRight(0);
							celda.setBorderWidthTop(new Float(0.5));
							celda.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else {
							if (rm.getValor() > 64) {
								celda = new PdfPCell(new Phrase("JV:  "
										+ decimalFormat.format(rm.getValor())
										+ " Básico", smallfont));
								celda.setBorderWidthBottom(new Float(0.5));
								celda.setBorderWidthLeft(new Float(0.5));
								celda.setBorderWidthRight(0);
								celda.setBorderWidthTop(new Float(0.5));
								celda.setHorizontalAlignment(Element.ALIGN_LEFT);
							} else {
								celda = new PdfPCell(new Phrase("JV:  "
										+ decimalFormat.format(rm.getValor())
										+ " Bajo", smallfont));
								celda.setBorderWidthBottom(new Float(0.5));
								celda.setBorderWidthLeft(new Float(0.5));
								celda.setBorderWidthRight(0);
								celda.setBorderWidthTop(new Float(0.5));
								celda.setHorizontalAlignment(Element.ALIGN_LEFT);
							}

						}
					}

					table.addCell(celda);

					// Fortaleza
					celda = new PdfPCell(new Phrase("F", smallfont));
					celda.setBorderWidthBottom(new Float(0.5));
					celda.setBorderWidthLeft(new Float(0.5));
					celda.setBorderWidthRight(0);
					celda.setBorderWidthTop(new Float(0.5));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celda);

					// Por mejorara
					celda = new PdfPCell(new Phrase("PM", smallfont));
					celda.setBorderWidthBottom(new Float(0.5));
					celda.setBorderWidthLeft(new Float(0.5));
					celda.setBorderWidthRight(new Float(0.5));
					celda.setBorderWidthTop(new Float(0.5));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(celda);

					// Agregamos al párrafo la tabla
					paragraphTable1.add(table);

					// Ahora vamos a agregar cada dimensión
					for (DimensionesEstudiantes de : rm
							.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes()) {

						table = new PdfPTable(1); // Code 1
						table.getDefaultCell().setPadding(0);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 10 });

						celda = new PdfPCell(new Phrase(de.getDimensiones()
								.getNombre()));
						celda.setHorizontalAlignment(Element.ALIGN_LEFT);

						celda.setBorder(0);
						celda.setBorderWidth(0);
						// celda.setBorder(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidthLeft(new Float(0.5));
						// celda.setBorderWidthRight(new Float(0.5));

						PdfPTable tableLogro;
						tableLogro = new PdfPTable(3);
						tableLogro.setWidthPercentage(100);
						tableLogro.setWidths(new float[] { 11,
								new Float("0.5"), new Float("0.5") });

						// notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

						for (Relacionlogrosdimensiones rld : de
								.getDataListRelacionlogrosdimensiones()) {

							ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
							List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
							List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

							// Relacionlogrosnotasdimension
							Long idDimension = null;
							Long idRelacionNotasDimension = null;
							double suma = 0;
							int contador = 0;
							int contador2 = 0;
							List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
									.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
											+ rld.getLogros().getIdlogros()
											+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
											+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
							for (Relacionlogrosnotasdimension rnld : tmpList) {
								// Miramos
								if (idRelacionNotasDimension == null) {

									// Sacamos el id de la dimensión que
									// estamos manejando
									idDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getRelaciondimensionesasignaturasano()
											.getIdrelaciondimensionesasignaturasano();

									// Sacamos el id del item que estamos
									// manejando
									idRelacionNotasDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion();
									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculasGrupo
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									promedioActividades.setValor(suma);

									// Agregamos el porcenta
									Notascalificables notascalificables = new Notascalificables(
											new Long(0));
									// Agregamos a notas calificables una de las
									// actividades para poder acceder luego al
									// porcentaje del item
									notascalificables
											.setRelacionnotaslogrosdimensionboletin(rnld
													.getRelacionnotaslogrosdimensionboletin());
									// Agregamos notascalificables a la
									// relación con registromatricula
									promedioActividades
											.setNotascalificables(notascalificables);
									// Le agregamos a la dimensión la relación
									// entre la dimensión y el item
									notasDimensionesEstudiantes
											.setRelacionnotasdimension(rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension());

									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {
										// promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);
										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}

								} else {
									// Miramos si hay cambio del item
									if (rnld.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
										// Revisamos si llegamos al final de la
										// lista
										if (contador2 + 1 == tmpList.size()) {

											// Terminamos
											// Si hemos llegado al final
											// entonces lo que hacemos
											suma = new BigDecimal(suma
													/ (contador)).setScale(0,
													BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);

											// Miramos si el item ha cambiado
											if (idDimension == rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {

												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al
													// porcentaje del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos
													// notascalificables a la
													// relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;

												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculasGrupo
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());

												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0)
															.getValor();
												}

												promedioActividades
														.setValor(suma);

												dataListNotasEstudiantes
														.add(promedioActividades);

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);

											} else {

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);

												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión
												// la relación entre la
												// dimensión y el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());

												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al
													// porcentaje del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos
													// notascalificables a la
													// relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;

												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculasGrupo
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());

												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0)
															.getValor();
												}

												promedioActividades
														.setValor(suma);

												dataListNotasEstudiantes
														.add(promedioActividades);

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
											}

										} else {
											idRelacionNotasDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getIdrelacionnotasdimesion();
											// aScamos el promedio de las
											// actividades por item
											suma = new BigDecimal(suma
													/ contador).setScale(0,
													BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											// Agregamos este valor un objeto
											promedioActividades.setValor(suma);
											// Agregamos este objeto a una lista
											dataListNotasEstudiantes
													.add(promedioActividades);

											if (idDimension != rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {

												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
												// --Sacar valor de la
												// dimensión

												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión
												// la relación entre la
												// dimensión y el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());

												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
												// Colocamos el nuevo valor de
												// la dimensión
												idDimension = rnld
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getRelaciondimensionesasignaturasano()
														.getIdrelaciondimensionesasignaturasano();
											}

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculasGrupo
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}
										}
										contador = 0;

									} else {

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculasGrupo
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());
										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
										if (contador2 + 1 == tmpList.size()) {
											suma = new BigDecimal(suma
													/ (contador + 1))
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											// Agregamos a la lista grande los
											// items de la dimensión
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}
									}
								}
								contador++;
								contador2++;
							}

							double sumaTotalLogro = 0;

							double sumaDimensiones = 0;

							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								sumaDimensiones += nde
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getPorcentaje();
							}

							boolean logrodPasado = true;

							if (!tmpDimensionesEstudiantes.isEmpty()) {

								// System.out.print("Ola si hubo algo");

								for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
									double sumaPromedioItem = 0;

									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaPromedioItem += ane
												.getNotascalificables()
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getPorcentaje();
									}

									double sumaActividades = 0;

									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaActividades += new BigDecimal(
												(ane.getValor() * (ane
														.getNotascalificables()
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}

									sumaTotalLogro += new BigDecimal(
											(nde.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getPorcentaje() * 100 / sumaDimensiones)
													* sumaActividades / 100)
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();

								}

								// Miramos si pasamos el logro o no
								if (sumaTotalLogro < 80) {
									logrodPasado = false;

									// ----CUADRAR LAS RECUPERACIONES
									List<Relacionlogrosrecuperaciones> tmpRRList = relacionlogrosrecuperacionesFacade
											.findByLike("SELECT R FROM Relacionlogrosrecuperaciones R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = "
													+ rld.getIdrelacionlogrosdimensiones());
									if (!tmpRRList.isEmpty()) {
										List<Relacionrecuperacionregistromatriculas> relacionrecuperacionList = relacionrecuperacionregistromatriculasFacade
												.findByLike("SELECT R FROM Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
														+ tmpRRList
																.get(0)
																.getRecuperaciones()
																.getIdrecuperaciones()
														+ " AND"
														+ " R.registromatriculas.idregistromatriculas = "
														+ registromatriculasGrupo
																.getIdregistromatriculas());
										if (!relacionrecuperacionList.isEmpty()) {
											if (sumaTotalLogro > 0) {
												sumaTotalLogro = new BigDecimal(
														(sumaTotalLogro + relacionrecuperacionList
																.get(0)
																.getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue();
												if (sumaTotalLogro > 79) {
													logrodPasado = true;
												}
											} else {
												if (relacionrecuperacionList
														.get(0).getValor() > 79) {
													logrodPasado = true;
												}
											}
										}
									}
								}
							}

							// Agregamo la lista de los logros por dimensión
							PdfPCell celdaLogro;
							celdaLogro = new PdfPCell(new Phrase(rld
									.getLogros().getLogro(), smallfont));

							// celdaLogro.setPadding(0);
							celdaLogro.setBorder(0);
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							// celdaLogro.setPaddingBottom(0);
							// celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							// celdaLogro.setPaddingTop(0);
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_LEFT);
							celdaLogro.setLeft(10);

							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }

							celdaLogro.setBorderWidthLeft(new Float(0.5));

							tableLogro.addCell(celdaLogro);

							celdaLogro = new PdfPCell(new Phrase(" "));
							if (logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							// celdaLogro.setBorderWidthLeft(new Float(0.5));
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));

							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }

							// System.out.print(document.getPageNumber() +
							// " numero ");

							// if(pageNumber != document.getPageNumber()){
							// celdaLogro.setBorderWidthTop(new Float(0.5));
							// pageNumber = document.getPageNumber();
							// }

							// celdaLogro.setBorderColorTop(BaseColor.WHITE);
							tableLogro.addCell(celdaLogro);

							celdaLogro = new PdfPCell(new Phrase(""));
							if (!logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));
							celdaLogro.setBorderWidthRight(new Float(0.5));
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							// celdaLogro.setBorder(0);
							// celdaLogro.setBorderColorTop(BaseColor.WHITE);
							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }
							tableLogro.addCell(celdaLogro);

						}
						celda.setPadding(0);
						// celda.setPaddingBottom(0);
						// celda.setPaddingLeft(0);
						celda.setPaddingRight(0);
						// celda.setPaddingTop(0);
						celda.addElement(tableLogro);
						//
						table.addCell(celda);

						// Agregamos al párrafo la tabla
						paragraphTable1.add(table);
					}

				}
			}

			for (AsignaturasEstudiantes rm : dataListAsignaturasEstudiantes) {

				// System.out.print(rm.getAsignaturas().getTipoasignatura() +
				// " TIPO " + rm.getDataListPeriodosEstudiantes().size() +
				// " SIZE ");
				// System.out.print(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(0).getDimensiones().getNombre()
				// + " SIZE ");

				if (rm.getDataListPeriodosEstudiantes() != null
						&& !rm.getDataListPeriodosEstudiantes().isEmpty()
						&& rm.getAsignaturas().getTipoasignatura() == 3
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes() != null
						&& !rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().isEmpty()
						&& rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().get(0)
								.getDataListRelacionlogrosdimensiones() != null
						&& !rm.getDataListPeriodosEstudiantes().get(0)
								.getDataListDimensionesEstudiantes().get(0)
								.getDataListNotasDimensionesEstudiantes()
								.isEmpty()) {
					PdfPTable tableComportamiento = new PdfPTable(4); // Code 1
					tableComportamiento.setWidthPercentage(100);
					tableComportamiento.setWidths(new float[] { 9, 2,
							new Float("0.5"), new Float("0.5") });

					// Nombre de la asignatura
					PdfPCell celdaComportamiento = new PdfPCell(new Phrase(rm
							.getAsignaturas().getNombre(), materia));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					celdaComportamiento.setBorderWidthBottom(new Float(0.5));
					celdaComportamiento.setBorderWidthLeft(new Float(0.5));
					celdaComportamiento.setBorderWidthRight(0);
					celdaComportamiento.setBorderWidthTop(new Float(0.5));
					tableComportamiento.addCell(celdaComportamiento);

					// //Fallas
					// celda = new PdfPCell(new Phrase("Fallas: " +
					// getFallasBoletin() , smallfont));
					// celda.setBorderWidthBottom(new Float(0.5));
					// celda.setBorderWidthLeft(new Float(0.5));
					// celda.setBorderWidthRight(0);
					// celda.setBorderWidthTop(new Float(0.5));
					// celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					// tableComportamiento.addCell(celda);

					// Jucio valorativo

					if (rm.getValor() > 95) {
						celdaComportamiento = new PdfPCell(new Phrase("JV:  "
								+ " Excelente", smallfont));
						celdaComportamiento
								.setBorderWidthBottom(new Float(0.5));
						celdaComportamiento.setBorderWidthLeft(new Float(0.5));
						celdaComportamiento.setBorderWidthRight(0);
						celdaComportamiento.setBorderWidthTop(new Float(0.5));
						celdaComportamiento
								.setHorizontalAlignment(Element.ALIGN_LEFT);

					} else {
						if (rm.getValor() > 79) {
							celdaComportamiento = new PdfPCell(new Phrase(
									"JV:  " + " Sobresaliente", smallfont));
							celdaComportamiento.setBorderWidthBottom(new Float(
									0.5));
							celdaComportamiento.setBorderWidthLeft(new Float(
									0.5));
							celdaComportamiento.setBorderWidthRight(0);
							celdaComportamiento
									.setBorderWidthTop(new Float(0.5));
							celdaComportamiento
									.setHorizontalAlignment(Element.ALIGN_CENTER);
						} else {
							if (rm.getValor() > 64) {
								celdaComportamiento = new PdfPCell(new Phrase(
										"JV:  " + " Aceptable", smallfont));
								celdaComportamiento
										.setBorderWidthBottom(new Float(0.5));
								celdaComportamiento
										.setBorderWidthLeft(new Float(0.5));
								celdaComportamiento.setBorderWidthRight(0);
								celdaComportamiento
										.setBorderWidthTop(new Float(0.5));
								celdaComportamiento
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							} else {
								celdaComportamiento = new PdfPCell(new Phrase(
										"JV:  " + " Insuficiente", smallfont));
								celdaComportamiento
										.setBorderWidthBottom(new Float(0.5));
								celdaComportamiento
										.setBorderWidthLeft(new Float(0.5));
								celdaComportamiento.setBorderWidthRight(0);
								celdaComportamiento
										.setBorderWidthTop(new Float(0.5));
								celdaComportamiento
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}

						}
					}

					tableComportamiento.addCell(celdaComportamiento);

					// celda = new PdfPCell(new Phrase("JV:  " + rm.getValor() +
					// "  B", smallfont));
					// celda.setBorderWidthBottom(new Float(0.5));
					// celda.setBorderWidthLeft(new Float(0.5));
					// celda.setBorderWidthRight(0);
					// celda.setBorderWidthTop(new Float(0.5));
					// celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					// tableComportamiento.addCell(celda);

					// Fortaleza
					celdaComportamiento = new PdfPCell(new Phrase("F",
							smallfont));
					celdaComportamiento.setBorderWidthBottom(new Float(0.5));
					celdaComportamiento.setBorderWidthLeft(new Float(0.5));
					celdaComportamiento.setBorderWidthRight(0);
					celdaComportamiento.setBorderWidthTop(new Float(0.5));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableComportamiento.addCell(celdaComportamiento);

					// Por mejorara
					celdaComportamiento = new PdfPCell(new Phrase("PM",
							smallfont));
					celdaComportamiento.setBorderWidthBottom(new Float(0.5));
					celdaComportamiento.setBorderWidthLeft(new Float(0.5));
					celdaComportamiento.setBorderWidthRight(new Float(0.5));
					celdaComportamiento.setBorderWidthTop(new Float(0.5));
					celdaComportamiento
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					tableComportamiento.addCell(celdaComportamiento);

					// Agregamos al párrafo la tabla
					paragraphTable1.add(tableComportamiento);

					// Ahora vamos a agregar cada dimensión
					for (DimensionesEstudiantes de : rm
							.getDataListPeriodosEstudiantes().get(0)
							.getDataListDimensionesEstudiantes()) {

						tableComportamiento = new PdfPTable(1); // Code 1
						tableComportamiento.getDefaultCell().setPadding(0);
						tableComportamiento.setWidthPercentage(100);
						tableComportamiento.setWidths(new float[] { 10 });

						celdaComportamiento = new PdfPCell(new Phrase(de
								.getDimensiones().getNombre()));
						celdaComportamiento
								.setHorizontalAlignment(Element.ALIGN_LEFT);

						celdaComportamiento.setBorder(0);
						celdaComportamiento.setBorderWidth(0);
						// celda.setBorder(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidth(0);
						// celda.setBorderWidthLeft(new Float(0.5));
						// celda.setBorderWidthRight(new Float(0.5));

						PdfPTable tableLogro;
						tableLogro = new PdfPTable(3);
						tableLogro.setWidthPercentage(100);
						tableLogro.setWidths(new float[] { 11,
								new Float("0.5"), new Float("0.5") });

						// notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

						for (Relacionlogrosdimensiones rld : de
								.getDataListRelacionlogrosdimensiones()) {

							ActividadesNotasEstudiantes promedioActividades = new ActividadesNotasEstudiantes();
							List<ActividadesNotasEstudiantes> dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
							List<NotasDimensionesEstudiantes> tmpDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();

							// Relacionlogrosnotasdimension
							Long idDimension = null;
							Long idRelacionNotasDimension = null;
							double suma = 0;
							int contador = 0;
							int contador2 = 0;
							List<Relacionlogrosnotasdimension> tmpList = relacionlogrosnotasdimensionFacade
									.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin is not null  AND R.relacionlogrosdimension.logros.idlogros = "
											+ rld.getLogros().getIdlogros()
											+ " ORDER BY R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano, "
											+ "R.relacionnotaslogrosdimensionboletin.relacionnotasdimension.idrelacionnotasdimesion");
							for (Relacionlogrosnotasdimension rnld : tmpList) {
								// Miramos
								if (idRelacionNotasDimension == null) {

									// Sacamos el id de la dimensión que
									// estamos manejando
									idDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getRelaciondimensionesasignaturasano()
											.getIdrelaciondimensionesasignaturasano();

									// Sacamos el id del item que estamos
									// manejando
									idRelacionNotasDimension = rnld
											.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion();
									List<Notascalificables> tmpNC = notascalificablesFacade
											.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
													+ registromatriculasGrupo
															.getIdregistromatriculas()
													+ " AND "
													+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
													+ rnld.getRelacionnotaslogrosdimensionboletin()
															.getIdrelacionnotaslogrosdimensionboletin());
									if (!tmpNC.isEmpty()) {
										suma += tmpNC.get(0).getValor();
									}
									promedioActividades.setValor(suma);

									// Agregamos el porcenta
									Notascalificables notascalificables = new Notascalificables(
											new Long(0));
									// Agregamos a notas calificables una de las
									// actividades para poder acceder luego al
									// porcentaje del item
									notascalificables
											.setRelacionnotaslogrosdimensionboletin(rnld
													.getRelacionnotaslogrosdimensionboletin());
									// Agregamos notascalificables a la
									// relación con registromatricula
									promedioActividades
											.setNotascalificables(notascalificables);
									// Le agregamos a la dimensión la relación
									// entre la dimensión y el item
									notasDimensionesEstudiantes
											.setRelacionnotasdimension(rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension());

									// Revisamos si llegamos al final de la
									// lista
									if (contador2 + 1 == tmpList.size()) {
										// promedioActividades.setValor(suma);
										dataListNotasEstudiantes
												.add(promedioActividades);
										// Guardamos la lista de los items
										notasDimensionesEstudiantes
												.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

										// Agregamos a la lista grande los items
										// de la dimensión
										tmpDimensionesEstudiantes
												.add(notasDimensionesEstudiantes);
									}

								} else {
									// Miramos si hay cambio del item
									if (rnld.getRelacionnotaslogrosdimensionboletin()
											.getRelacionnotasdimension()
											.getIdrelacionnotasdimesion() != idRelacionNotasDimension) {
										// Revisamos si llegamos al final de la
										// lista
										if (contador2 + 1 == tmpList.size()) {

											// Terminamos
											// Si hemos llegado al final
											// entonces lo que hacemos
											suma = new BigDecimal(suma
													/ (contador)).setScale(0,
													BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);

											// Miramos si el item ha cambiado
											if (idDimension == rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {

												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al
													// porcentaje del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos
													// notascalificables a la
													// relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;

												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculasGrupo
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());

												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0)
															.getValor();
												}

												promedioActividades
														.setValor(suma);

												dataListNotasEstudiantes
														.add(promedioActividades);

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);

											} else {

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);

												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión
												// la relación entre la
												// dimensión y el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());

												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();

												// Reiniciamos el objeto
												promedioActividades = new ActividadesNotasEstudiantes();
												if (promedioActividades
														.getNotascalificables() == null) {
													// Agregamos el porcenta
													Notascalificables notascalificables = new Notascalificables(
															new Long(0));
													// Agregamos a notas
													// calificables una de las
													// actividades para poder
													// acceder luego al
													// porcentaje del item
													notascalificables
															.setRelacionnotaslogrosdimensionboletin(rnld
																	.getRelacionnotaslogrosdimensionboletin());
													// Agregamos
													// notascalificables a la
													// relación con
													// registromatricula
													promedioActividades
															.setNotascalificables(notascalificables);
												}
												// Reiniciamos la variable
												suma = 0;

												List<Notascalificables> tmpNC = notascalificablesFacade
														.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
																+ registromatriculasGrupo
																		.getIdregistromatriculas()
																+ " AND "
																+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
																+ rnld.getRelacionnotaslogrosdimensionboletin()
																		.getIdrelacionnotaslogrosdimensionboletin());

												if (!tmpNC.isEmpty()) {
													suma += tmpNC.get(0)
															.getValor();
												}

												promedioActividades
														.setValor(suma);

												dataListNotasEstudiantes
														.add(promedioActividades);

												// Guardamos la lista de los
												// items
												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);
												//
												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
											}

										} else {
											idRelacionNotasDimension = rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getIdrelacionnotasdimesion();
											// aScamos el promedio de las
											// actividades por item
											suma = new BigDecimal(suma
													/ contador).setScale(0,
													BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											// Agregamos este valor un objeto
											promedioActividades.setValor(suma);
											// Agregamos este objeto a una lista
											dataListNotasEstudiantes
													.add(promedioActividades);

											if (idDimension != rnld
													.getRelacionnotaslogrosdimensionboletin()
													.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getIdrelaciondimensionesasignaturasano()) {

												notasDimensionesEstudiantes
														.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

												tmpDimensionesEstudiantes
														.add(notasDimensionesEstudiantes);
												// --Sacar valor de la
												// dimensión

												// Reiniciamos la otra lista
												notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
												// Le agregamos a la dimensión
												// la relación entre la
												// dimensión y el item
												notasDimensionesEstudiantes
														.setRelacionnotasdimension(rnld
																.getRelacionnotaslogrosdimensionboletin()
																.getRelacionnotasdimension());

												// Reiniciamos la lista de
												dataListNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
												// Colocamos el nuevo valor de
												// la dimensión
												idDimension = rnld
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getRelaciondimensionesasignaturasano()
														.getIdrelaciondimensionesasignaturasano();
											}

											// Reiniciamos el objeto
											promedioActividades = new ActividadesNotasEstudiantes();
											if (promedioActividades
													.getNotascalificables() == null) {
												// Agregamos el porcenta
												Notascalificables notascalificables = new Notascalificables(
														new Long(0));
												// Agregamos a notas
												// calificables una de las
												// actividades para poder
												// acceder luego al porcentaje
												// del item
												notascalificables
														.setRelacionnotaslogrosdimensionboletin(rnld
																.getRelacionnotaslogrosdimensionboletin());
												// Agregamos notascalificables a
												// la relación con
												// registromatricula
												promedioActividades
														.setNotascalificables(notascalificables);
											}
											// Reiniciamos la variable
											suma = 0;

											List<Notascalificables> tmpNC = notascalificablesFacade
													.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
															+ registromatriculasGrupo
																	.getIdregistromatriculas()
															+ " AND "
															+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
															+ rnld.getRelacionnotaslogrosdimensionboletin()
																	.getIdrelacionnotaslogrosdimensionboletin());

											if (!tmpNC.isEmpty()) {
												suma += tmpNC.get(0).getValor();
											}
										}
										contador = 0;

									} else {

										List<Notascalificables> tmpNC = notascalificablesFacade
												.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
														+ registromatriculasGrupo
																.getIdregistromatriculas()
														+ " AND "
														+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
														+ rnld.getRelacionnotaslogrosdimensionboletin()
																.getIdrelacionnotaslogrosdimensionboletin());
										if (!tmpNC.isEmpty()) {
											suma += tmpNC.get(0).getValor();
										}
										if (contador2 + 1 == tmpList.size()) {
											suma = new BigDecimal(suma
													/ (contador + 1))
													.setScale(
															0,
															BigDecimal.ROUND_HALF_UP)
													.doubleValue();
											promedioActividades.setValor(suma);
											dataListNotasEstudiantes
													.add(promedioActividades);

											// Guardamos la lista de los items
											notasDimensionesEstudiantes
													.setDataListActividadesNotasEstudiantes(dataListNotasEstudiantes);

											// Agregamos a la lista grande los
											// items de la dimensión
											tmpDimensionesEstudiantes
													.add(notasDimensionesEstudiantes);
										}
									}
								}
								contador++;
								contador2++;
							}

							double sumaTotalLogro = 0;

							double sumaDimensiones = 0;

							for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
								sumaDimensiones += nde
										.getRelacionnotasdimension()
										.getRelaciondimensionesasignaturasano()
										.getPorcentaje();
							}

							boolean logrodPasado = true;

							if (!tmpDimensionesEstudiantes.isEmpty()) {

								// System.out.print("Ola si hubo algo");

								for (NotasDimensionesEstudiantes nde : tmpDimensionesEstudiantes) {
									double sumaPromedioItem = 0;

									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaPromedioItem += ane
												.getNotascalificables()
												.getRelacionnotaslogrosdimensionboletin()
												.getRelacionnotasdimension()
												.getPorcentaje();
									}

									double sumaActividades = 0;

									for (ActividadesNotasEstudiantes ane : nde
											.getDataListActividadesNotasEstudiantes()) {
										sumaActividades += new BigDecimal(
												(ane.getValor() * (ane
														.getNotascalificables()
														.getRelacionnotaslogrosdimensionboletin()
														.getRelacionnotasdimension()
														.getPorcentaje() * 100 / sumaPromedioItem)) * 0.01)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
												.doubleValue();
									}

									sumaTotalLogro += new BigDecimal(
											(nde.getRelacionnotasdimension()
													.getRelaciondimensionesasignaturasano()
													.getPorcentaje() * 100 / sumaDimensiones)
													* sumaActividades / 100)
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();

								}

								if (sumaTotalLogro < 80) {
									logrodPasado = false;
								}
							}

							// Agregamo la lista de los logros por dimensión
							PdfPCell celdaLogro;
							celdaLogro = new PdfPCell(new Phrase(rld
									.getLogros().getLogro(), smallfont));

							// celdaLogro.setPadding(0);
							celdaLogro.setBorder(0);
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							// celdaLogro.setPaddingBottom(0);
							// celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							// celdaLogro.setPaddingTop(0);
							celdaLogro
									.setHorizontalAlignment(Element.ALIGN_LEFT);
							celdaLogro.setLeft(10);

							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }

							celdaLogro.setBorderWidthLeft(new Float(0.5));

							tableLogro.addCell(celdaLogro);

							celdaLogro = new PdfPCell(new Phrase(" "));
							if (logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							// celdaLogro.setBorderWidthLeft(new Float(0.5));
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));

							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }

							// System.out.print(document.getPageNumber() +
							// " numero ");

							// if(pageNumber != document.getPageNumber()){
							// celdaLogro.setBorderWidthTop(new Float(0.5));
							// pageNumber = document.getPageNumber();
							// }

							// celdaLogro.setBorderColorTop(BaseColor.WHITE);
							tableLogro.addCell(celdaLogro);

							celdaLogro = new PdfPCell(new Phrase(""));
							if (!logrodPasado) {
								celdaLogro.setPhrase(new Phrase("x"));
								celdaLogro
										.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							celdaLogro.setBorder(0);
							celdaLogro.setBorderWidthLeft(new Float(0.5));
							celdaLogro.setBorderWidthRight(new Float(0.5));
							// celdaLogro.setBorderWidthBottom(new Float(0.5));
							celdaLogro.setPadding(0);
							celdaLogro.setPaddingBottom(0);
							celdaLogro.setPaddingLeft(0);
							celdaLogro.setPaddingRight(0);
							celdaLogro.setPaddingTop(0);
							// celdaLogro.setBorder(0);
							// celdaLogro.setBorderColorTop(BaseColor.WHITE);
							// if
							// (rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().get(rm.getDataListPeriodosEstudiantes().get(0).getDataListDimensionesEstudiantes().size()
							// - 1).getDimensiones().getIddimensiones() !=
							// de.getDimensiones().getIddimensiones()
							// ||
							// de.getDataListRelacionlogrosdimensiones().get(de.getDataListRelacionlogrosdimensiones().size()
							// - 1).getIdrelacionlogrosdimensiones() !=
							// rld.getIdrelacionlogrosdimensiones()) {
							celdaLogro.setBorderWidthTop(new Float(0.5));
							celdaLogro.setBorderWidthBottom(new Float(0.5));
							// }
							tableLogro.addCell(celdaLogro);

						}
						celdaComportamiento.setPadding(0);
						// celda.setPaddingBottom(0);
						// celda.setPaddingLeft(0);
						celdaComportamiento.setPaddingRight(0);
						// celda.setPaddingTop(0);
						celdaComportamiento.addElement(tableLogro);
						//
						tableComportamiento.addCell(celdaComportamiento);

						// Agregamos al párrafo la tabla
						paragraphTable1.add(tableComportamiento);

						// System.out.print("Si entró");
					}

				}
			}

			// Agregamos al documento el párrafo nuevo
			document.add(paragraphTable1);

			// Nueva table
			Paragraph paragraphTable2 = new Paragraph();
			paragraphTable2.setSpacingBefore(15);
			PdfPCell celdaTmp;
			PdfPTable tableTmp;

			tableTmp = new PdfPTable(2); // Code 1

			tableTmp.setWidthPercentage(100);
			tableTmp.setWidths(new float[] { new Float("1.5"), new Float("8.5") });

			// Nombre de la asignatura
			celdaTmp = new PdfPCell(new Phrase("Observaciones", smallfont));
			celdaTmp.setHorizontalAlignment(Element.ALIGN_LEFT);
			celdaTmp.setBorder(0);
			celdaTmp.setBorderWidthBottom(0);
			celdaTmp.setBorderWidthLeft(0);
			celdaTmp.setBorderWidthRight(0);
			celdaTmp.setBorderWidthTop(0);
			tableTmp.addCell(celdaTmp);

			celdaTmp = new PdfPCell(new Phrase(ObservacionesBoletin(rem),
					smallfont));
			celdaTmp.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			celdaTmp.setBorder(0);
			celdaTmp.setBorderWidthBottom(0);
			celdaTmp.setBorderWidthLeft(0);
			celdaTmp.setBorderWidthRight(0);
			celdaTmp.setBorderWidthTop(0);
			tableTmp.addCell(celdaTmp);

			paragraphTable2.add(tableTmp);

			document.add(paragraphTable2);

			// Nueva table
			Paragraph paragraphTable3 = new Paragraph();
			paragraphTable3.setSpacingBefore(15);
			PdfPCell celdaTmp2;
			PdfPTable tableTmp2;

			tableTmp2 = new PdfPTable(2); // Code 1

			tableTmp2.setWidthPercentage(100);
			tableTmp2.setWidths(new float[] { 2, 2 });

			// Nombre de la asignatura
			celdaTmp2 = new PdfPCell(new Phrase(
					"___________________________________", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			// Nombre de la asignatura
			celdaTmp2 = new PdfPCell(new Phrase(
					"___________________________________", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase("Director de Grupo: "
					+ cursoSeleccionado.getProfesor().getUsuarios()
							.getNombres()
					+ " "
					+ cursoSeleccionado.getProfesor().getUsuarios()
							.getApellidos(), smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);

			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase(
					"Directora: Rosa Josefina Muñoz Archila", smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaTmp2.setBorder(0);
			celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			celdaTmp2 = new PdfPCell(new Phrase("Rosa Josefina Muñoz Archila",
					smallfont));
			celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			// celdaTmp2.setBorder(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			tableTmp2.addCell(celdaTmp2);

			// celdaTmp2 = new PdfPCell(new
			// Phrase(cursoSeleccionado.getProfesor().getUsuarios().getNombres()
			// + " " +
			// cursoSeleccionado.getProfesor().getUsuarios().getApellidos(),
			// smallfont));
			// celdaTmp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			// celdaTmp2.setBorder(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthBottom(0);
			// celdaTmp2.setBorderWidthLeft(0);
			// celdaTmp2.setPadding(0);
			// celdaTmp2.setBorderWidthRight(0);
			// celdaTmp2.setBorderWidthTop(0);
			// celdaTmp2.setPadding(0);
			// tableTmp2.addCell(celdaTmp2);

			paragraphTable3.add(tableTmp2);

			document.add(paragraphTable3);

			// document.add(new
			// Paragraph("Este es un archivo con 3 pagina: Pagina 1"));
			// document.newPage();
			pdf.setPageEmpty(false);
			document.newPage();
			// document.add(new Paragraph("Pagina 3"));
		}

		calcularBoletin(registromatriculas);

		// document.add(new
		// Paragraph("Este es un archivo con 3 pagina: Pagina 1"));
		// document.newPage();
		// pdf.setPageEmpty(false);
		// document.newPage();
		// document.add(new Paragraph("Pagina 3"));

		// close the PDF document
		document.close();

		String downloadFile = nombreFichero + ".pdf";

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contentType = "application/pdf";

		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ nombreFichero + ".pdf" + "\"");
		downloadFile = nombreFichero + ".pdf";
		byte[] buf = new byte[1024];
		try {
			File file = new File(downloadFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		File fichero = new File(nombreFichero + ".pdf");
		fichero.delete();

		facesContext.responseComplete();
	}

	public void guardarNotaDefinitiva(Periodos periodos,
			Relacionasignaturaperiodos relacionasignaturaperiodos,
			Registromatriculas registromatriculas, double valor) {

		System.out.print(periodos + " p " + relacionasignaturaperiodos
				+ " rap " + registromatriculas + " rm ");

		List<Definitivasasignaturasperiodos> dataListDNE = definitivasasignaturasperiodosFacade
				.findByLike("SELECT D FROM Definitivasasignaturasperiodos D WHERE "
						+ "D.periodos.idperiodos = "
						+ periodos.getIdperiodos()
						+ " AND D.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodos
								.getIdrelacionasignaturaperiodos()
						+ " AND D.registromatricula.idregistromatriculas = "
						+ registromatriculas.getIdregistromatriculas());
		if (dataListDNE.isEmpty()) {
			Definitivasasignaturasperiodos definitivasasignaturasperiodos = new Definitivasasignaturasperiodos(
					new Long(0));
			definitivasasignaturasperiodos.setPeriodos(periodos);
			definitivasasignaturasperiodos
					.setRegistromatricula(registromatriculas);
			definitivasasignaturasperiodos
					.setRelacionasignaturasperiodos(relacionasignaturaperiodos);
			definitivasasignaturasperiodos.setValor(valor);
			definitivasasignaturasperiodosFacade
					.create(definitivasasignaturasperiodos);
		} else {
			dataListDNE.get(0).setValor(valor);
			definitivasasignaturasperiodosFacade.edit(dataListDNE.get(0));
		}
	}

	// Método para obtener los header de los periodos
	public Definitivas getDefinitivasHeader() {
		if (dataListDefinitivas != null && dataListDefinitivas.isEmpty()) {
			return dataListDefinitivas.get(0);
		}

		return definitivasHeader;
	}

	//Método para obtener la lista de las definitivas
	public List<Definitivas> getDataListDefinitivas(){
		return dataListDefinitivas;
	}
	
	public void setDataListDefinitivas(List<Definitivas> prueba){
		
	}
	
	// Metodo para obtener la lista de las definitivas del estudiante en el
	// periodo final
	public List<Definitivas> getDataListDefinitivasasignaturasperiodos() {
		if (periodoSeleccionado != null && registromatriculas != null
				&& periodoSeleccionado.getTipo() == 1) {
			// Inicializamos la lista de las definitivas
			dataListDefinitivas = new ArrayList<NotasEstudiantes.Definitivas>();
			List<Relacionasignaturaperiodos> dataListAsignturasEstudiantesTmp = new ArrayList<Relacionasignaturaperiodos>();
			
			
			if (anosacademicosManual == null) {
				dataListAsignturasEstudiantesTmp = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ registromatriculas.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+ getCurrentYear().getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
			} else {
				dataListAsignturasEstudiantesTmp = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ registromatriculas.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+ anosacademicosManual.getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
			}

			for (Relacionasignaturaperiodos r : dataListAsignturasEstudiantesTmp) {
				List<Definitivasasignaturasperiodos> dataListDefinitivasasignaturasperiodos = new ArrayList<Definitivasasignaturasperiodos>();
				// Hacemos tamben un objeto tipo Definitiva
				Definitivas definitivas = new Definitivas();
				double promedio = 0;
				for (Periodos p : getDataListPeriodos()) {
					if (p.getTipo() == 0) {
						List<Definitivasasignaturasperiodos> dataListDNE = definitivasasignaturasperiodosFacade
								.findByLike("SELECT D FROM Definitivasasignaturasperiodos D WHERE "
										+ "D.periodos.idperiodos = "
										+ p.getIdperiodos()
										+ " AND D.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
										+ r.getIdrelacionasignaturaperiodos()
										+ " AND D.registromatricula.idregistromatriculas = "
										+ registromatriculas
												.getIdregistromatriculas());

						if (!dataListDNE.isEmpty()) {
							dataListDefinitivasasignaturasperiodos
									.add(dataListDNE.get(0));
							promedio += new BigDecimal(dataListDNE.get(0)
									.getValor()*(p.getValor()*0.01)).setScale(0,
									BigDecimal.ROUND_HALF_UP).doubleValue();
						} else {
							System.out
									.print("Error tratando de establecer el valor total del periodo");
						}
					}
				}
				// Agregamos la lista
				definitivas
						.setDataListDefinitivasasignaturasperiodos(dataListDefinitivasasignaturasperiodos);
				// Agregamos la asignatura
				definitivas.setAsignaturas(r.getAsignaturas());
				// Agregamos el valor total
				definitivas.setDefinitiva(new BigDecimal(promedio).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue());
				
				//Promediamos con las recuperaciones finales
				boolean bandera = true;
				List<Relacionrecuperacionregistromatriculas> tmpRReg = new ArrayList<Relacionrecuperacionregistromatriculas>();
				for (Recuperaciones rec : getDataListRecuperaciones()) {
					List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
							.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
									+ rec.getIdrecuperaciones()
									+ " AND R.periodos.idperiodos = "
									+ periodoSeleccionado.getIdperiodos()
									+ " AND R.registromatriculas.idregistromatriculas = "
									+ registromatriculas.getIdregistromatriculas()
									+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
									+ r.getIdrelacionasignaturaperiodos());
					if (bandera && definitivas.definitiva > 0 && !tmpRM.isEmpty() && definitivas.definitiva < tmpRM.get(0).getValor() 
							&& definitivas.definitiva < 65) {
						definitivas.setDefinitiva(new BigDecimal((definitivas.getDefinitiva() + tmpRM.get(0).getValor()) / 2).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
						if (bandera && definitivas.getDefinitiva() > 64) {
							bandera = false;
						}
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
						relacionrecuperacionregistromatriculas.setValor(tmpRM.get(0).getValor());
						tmpRReg.add(relacionrecuperacionregistromatriculas);
						definitivas.setDataListRelacionrecuperacionregistromatriculas(tmpRReg);
					}else{
						Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
						relacionrecuperacionregistromatriculas.setValor(0.0);
						tmpRReg.add(relacionrecuperacionregistromatriculas);
						definitivas.setDataListRelacionrecuperacionregistromatriculas(tmpRReg);
					}
				}
//				
				// Finalmente a la lista le agregamos la definitiva
				dataListDefinitivas.add(definitivas);
			}

			return dataListDefinitivas;
		}
		return null;
	}

	// Clase para guardar las definitivas y luego mostrarlas en pantalla
	public static class Definitivas {
		private List<Definitivasasignaturasperiodos> dataListDefinitivasasignaturasperiodos;
		private List<Relacionrecuperacionregistromatriculas> dataListRelacionrecuperacionregistromatriculas;
		private int definitiva;
		private Asignaturas asignaturas;
		private Estudiantes estudiantes;
		private Registromatriculas registromatriculas;

		public List<Definitivasasignaturasperiodos> getDataListDefinitivasasignaturasperiodos() {
			return dataListDefinitivasasignaturasperiodos;
		}

		public void setDataListDefinitivasasignaturasperiodos(
				List<Definitivasasignaturasperiodos> dataListDefinitivasasignaturasperiodos) {
			this.dataListDefinitivasasignaturasperiodos = dataListDefinitivasasignaturasperiodos;
		}

		public List<Relacionrecuperacionregistromatriculas> getDataListRelacionrecuperacionregistromatriculas() {
			return dataListRelacionrecuperacionregistromatriculas;
		}

		public void setDataListRelacionrecuperacionregistromatriculas(
				List<Relacionrecuperacionregistromatriculas> dataListRelacionrecuperacionregistromatriculas) {
			this.dataListRelacionrecuperacionregistromatriculas = dataListRelacionrecuperacionregistromatriculas;
		}

		public int getDefinitiva() {
			return definitiva;
		}

		public Registromatriculas getRegistromatriculas() {
			return registromatriculas;
		}

		public void setRegistromatriculas(Registromatriculas registromatriculas) {
			this.registromatriculas = registromatriculas;
		}

		public void setDefinitiva(int definitiva) {
			this.definitiva = definitiva;
		}

		public Asignaturas getAsignaturas() {
			return asignaturas;
		}

		public void setAsignaturas(Asignaturas asignaturas) {
			this.asignaturas = asignaturas;
		}

		public Estudiantes getEstudiantes() {
			return estudiantes;
		}

		public void setEstudiantes(Estudiantes estudiantes) {
			this.estudiantes = estudiantes;
		}
		
		

	}

	// Lista que me deja ver los periodos que voy a mostrar
	public List<Periodos> getDataListPeriodosFinal() {
		if(getDataListPeriodos() != null){
			List<Periodos> tmp = new ArrayList<Periodos>();
			for (Periodos p : getDataListPeriodos()) {
				if (p.getTipo() == 0)
					tmp.add(p);
			}
			return tmp;
		}
		return null;
	}
	
	
	//Metodo para calcular el boletin de un alumno
	public List<AsignaturasEstudiantes> calcularBoletin_Imprimir(
			Registromatriculas regMat) {
		if (dataListAsignaturas == null && registromatriculas != null
				&& periodoSeleccionado != null) {

			dataListAsignturasEstudiantes = new ArrayList<AsignaturasEstudiantes>();

			if (regMat == null) {
				dataListAsignaturas = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ registromatriculas.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+   periodoSeleccionado.getAnoacademicos().getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
				regMat = registromatriculas;
			} else {
				dataListAsignaturas = relacionasignaturaperiodosFacade
						.findByLikeAll("SELECT DISTINCT(RAP.relacionasignaturasperiodos) FROM Relaciondimensionesasignaturasano RAP WHERE RAP.cursos.idcursos = "
								+ regMat.getCursos().getIdcursos()
								+ " AND RAP.relacionasignaturasperiodos.asignaturas.tipoasignatura != 1  AND RAP.relacionasignaturasperiodos.anosacademicos.idanosacademicos = "
								+ getCurrentYear().getIdanosacademicos()
								+ " ORDER BY "
								+ " RAP.relacionasignaturasperiodos.asignaturas.nombre ASC");
			}

			for (Relacionasignaturaperiodos rap : dataListAsignaturas) {
				// Lista de los periodos
				List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
				// Agregamos los periodos a la lista
				dataListPeriodosTmp.add(periodoSeleccionado);

				double promedioAsignatura = 0;

				// Creamos un nuevo objeto tipo AsignaturasEstudiantes
				AsignaturasEstudiantes asignaturasEstudiantesTmp = new AsignaturasEstudiantes();
				// Le agregamos la asignatura que estamos calculando
				asignaturasEstudiantesTmp.setAsignaturas(rap.getAsignaturas());
				// Creamos una lista nuevo para los periodos
				List<PeriodosEstudiantes> dataListPeriodosEstudiantesTmp = new ArrayList<PeriodosEstudiantes>();
				asignaturasEstudiantesTmp
						.setDataListPeriodosEstudiantes(dataListPeriodosEstudiantesTmp);

				// Validamos si no hay ningún periodo
				if (dataListPeriodosTmp.isEmpty()) {
					asignaturasEstudiantesTmp.setValor(100);
				}
				// Ahora vamos a recorrer los periodos y a agregarles la s
				// asignaturas
				for (Periodos p : dataListPeriodosTmp) {
					List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade
							.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
									+ rap.getIdrelacionasignaturaperiodos()
									+ " AND R.cursos.idcursos = "
									+ regMat.getCursos().getIdcursos()
									+ " ORDER BY R.dimensiones.iddimensiones");

					double promedioPeriodo = 0;

					PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
					// Agregamos el periodo
					periodosEstudiantes.setPeriodos(p);
					// Creamos una lista de las dimensiones que le vamos a
					// agregar a este periodo
					List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();
					// Agregamos esta lista al periodo
					periodosEstudiantes
							.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);

					// Agregamos a la lisa de los periodos esta dimensión
					dataListPeriodosEstudiantesTmp.add(periodosEstudiantes);

					if (datalistDimensionesTmp.isEmpty()) {
						periodosEstudiantes.setValor(0);
					}

					// Recorremos la lista de las dimensiones para averiguar el
					// promedio de la dimension en este periodo
					for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {
						
						// Cremos un objeto temporal tipo DimensionesEstudiantes
						DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
						// Le agregmos la dimensión
						dimensionesEstudiantesTmp.setDimensiones(rdaa
								.getDimensiones());
						// Agregamos a la lista de las dimensiones que creamos
						// antes esta nuevo dimensión
						dataListDimensionesEstudiantestmp
								.add(dimensionesEstudiantesTmp);

						// Hacemos una lista temporal de las dimensiones
						List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
						// Agregamos la lista que acabamos de crear
						dimensionesEstudiantesTmp
								.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);

						// Sacamos las notas que tiene que cada dimesion
						List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade
								.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
										+ rdaa.getIdrelaciondimensionesasignaturasano()
										+ " AND R.periodos.idperiodos = "
										+ p.getIdperiodos());
						double promedioDimension = 0;

						if (tmp.isEmpty()) {
							dimensionesEstudiantesTmp.setValor(0);
						}

						// Recogemos la lista de logros
						dimensionesEstudiantesTmp
								.setDataListRelacionlogrosdimensiones(relacionlogrosdimensionesFacade.findByLikeAll("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
										+ p.getIdperiodos()
										+ " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
										+ rdaa.getIdrelaciondimensionesasignaturasano()));

						// Recorremos las notas de esta dimensión
						for (Relacionnotasdimension rnd : tmp) {
							// Hacemos un objeto temporal tipo
							// NotasDimensionesEstudiantes
							NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
							// Le agregamos la dimensión
							notasDimensionesEstudiantes
									.setRelacionnotasdimension(rnd);
							// A la lista de las dimensiones le agregamos las
							// dimensiones
							dataListNotasDimensionesEstudiantes
									.add(notasDimensionesEstudiantes);
							// Creamos una lista temporal de actividades
							List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
							// Agregamos esta lista de actividades al objeto
							// creado anteriormente
							notasDimensionesEstudiantes
									.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);

							double promedioActividades = 0;
							List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade
									.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = "
											+ rnd.getIdrelacionnotasdimesion());

							// Recorremos la lista de las actividades
							for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {
								ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
								dataListActividadesNotasEstudiantes
										.add(actividadesNotasEstudiantes);

								List<Notascalificables> tmpNC = notascalificablesFacade
										.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = "
												+ regMat.getIdregistromatriculas()
												+ " AND "
												+ " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  "
												+ rnld.getIdrelacionnotaslogrosdimensionboletin());

								if (!tmpNC.isEmpty()) {
									actividadesNotasEstudiantes
											.setNotascalificables(tmpNC.get(0));
									actividadesNotasEstudiantes.setValor(tmpNC
											.get(0).getValor());
									promedioActividades += tmpNC.get(0)
											.getValor();
								} else {
									Notascalificables notasCalificablesTmp = new Notascalificables();
									notasCalificablesTmp
											.setRelacionnotaslogrosdimensionboletin(rnld);
									actividadesNotasEstudiantes
											.setNotascalificables(notasCalificablesTmp);
									actividadesNotasEstudiantes.setValor(0);
								}
							}

							// Validamos que la longitud de la lista no está
							// vacia
							if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
								promedioActividades = 100;
							} else {
								promedioActividades = new BigDecimal(
										promedioActividades
												/ tmpRNLDB.size())
										.setScale(0,
												BigDecimal.ROUND_HALF_UP)
										.doubleValue();
							}

							notasDimensionesEstudiantes
									.setValor(promedioActividades);

							promedioDimension = promedioDimension
									+ new BigDecimal((promedioActividades
											* rnd.getPorcentaje() / 100))
											.setScale(0,
													BigDecimal.ROUND_HALF_UP)
											.doubleValue();
							dimensionesEstudiantesTmp
									.setValor(promedioDimension);
						}

						if (rdaa.getPorcentaje() != null) {
							promedioDimension = new BigDecimal(
									promedioDimension * rdaa.getPorcentaje()
											/ 100).setScale(0,
									BigDecimal.ROUND_HALF_UP).doubleValue();
							promedioPeriodo += promedioDimension;
							periodosEstudiantes.setValor(promedioPeriodo);
						} else {
							periodosEstudiantes.setValor(100);

						}
					}
					
					
					
					List<Recuperaciones> datalistRecuperacionesTMP = recuperacionesFacade
							.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

					if (periodosEstudiantes.getValor() < 80) {
						List<Relacionrecuperacionregistromatriculas> tmpRM2 = new ArrayList<Relacionrecuperacionregistromatriculas>();
						for (Recuperaciones r : datalistRecuperacionesTMP) {
							List<Relacionrecuperacionregistromatriculas> tmpRM = relacionrecuperacionregistromatriculasFacade
									.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.periodos.idperiodos = "
											+ p.getIdperiodos()
											+ " AND R.registromatriculas.idregistromatriculas = "
											+ regMat.getIdregistromatriculas()
											+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "
											+ rap.getIdrelacionasignaturaperiodos()
											+ " AND R.recuperaciones.idrecuperaciones = "
											+ r.getIdrecuperaciones());
							if (!tmpRM.isEmpty()) {
								periodosEstudiantes
										.setDataListRelacionrecuperacionregistromatriculas(tmpRM);
								// Miramos si el estudiante es nuevo
								if (periodosEstudiantes.getValor() == 0) {
									periodosEstudiantes.setValor(tmpRM.get(0)
											.getValor());
								} else {
									if (tmpRM.get(0).getValor() > periodosEstudiantes
											.getValor()) {
										periodosEstudiantes
												.setValor(new BigDecimal(
														(periodosEstudiantes
																.getValor() + tmpRM
																.get(0)
																.getValor()) / 2)
														.setScale(
																0,
																BigDecimal.ROUND_HALF_UP)
														.doubleValue());
									}
								}
								tmpRM2.add(tmpRM.get(0));
							} else {
								Recuperaciones recuperaciones = new Recuperaciones(
										new Long(0));
								recuperaciones.setNombre("Recuperación");
								Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
								relacionrecuperacionregistromatriculas
										.setPeriodos(p);
								relacionrecuperacionregistromatriculas
										.setRelacionasignaturaperiodos(rap);
								relacionrecuperacionregistromatriculas
										.setValor(0.0);
								relacionrecuperacionregistromatriculas
										.setRecuperaciones(recuperaciones);
								tmpRM2.add(relacionrecuperacionregistromatriculas);
							}
							periodosEstudiantes
									.setDataListRelacionrecuperacionregistromatriculas(tmpRM2);
						}
					} else {
						List<Relacionrecuperacionregistromatriculas> tmpRM = new ArrayList<Relacionrecuperacionregistromatriculas>();
						for (Recuperaciones r : datalistRecuperacionesTMP) {

							Recuperaciones recuperaciones = new Recuperaciones(
									new Long(0));
							recuperaciones.setNombre("Recuperación");
							Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculas = new Relacionrecuperacionregistromatriculas();
							relacionrecuperacionregistromatriculas
									.setPeriodos(p);
							relacionrecuperacionregistromatriculas
									.setRelacionasignaturaperiodos(rap);
							relacionrecuperacionregistromatriculas
									.setValor(0.0);
							relacionrecuperacionregistromatriculas
									.setRecuperaciones(recuperaciones);
							tmpRM.add(relacionrecuperacionregistromatriculas);
						}

						periodosEstudiantes
								.setDataListRelacionrecuperacionregistromatriculas(tmpRM);

					}

					promedioAsignatura += periodosEstudiantes.getValor();

					// System.out.print(promedioAsignatura + " PROM ASIG " +
					// periodosEstudiantes.getValor() + " PERIODO " +
					// periodosEstudiantes.getPeriodos().getNombre());
					//
					// if (!dataListPeriodosEstudiantesTmp.isEmpty()) {
					// // System.out.print(promedioAsignatura /
					// dataListPeriodosEstudiantesTmp.size() +
					// " PROMEDIO ASIGNA");
					// }

				}

				promedioAsignatura = new BigDecimal(promedioAsignatura
						/ dataListPeriodosEstudiantesTmp.size()).setScale(0,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				asignaturasEstudiantesTmp.setValor(promedioAsignatura);

				dataListAsignturasEstudiantes.add(asignaturasEstudiantesTmp);

				guardarNotaDefinitiva(periodoSeleccionado, rap, regMat,
						promedioAsignatura);

			}

			if (!dataListAsignturasEstudiantes.isEmpty()) {
				dataListPeriodosEstudiantesHeader = new ArrayList<PeriodosEstudiantes>();
				for (AsignaturasEstudiantes as : dataListAsignturasEstudiantes) {
					for (PeriodosEstudiantes pe : as
							.getDataListPeriodosEstudiantes()) {
						dataListPeriodosEstudiantesHeader.add(pe);
					}
					break;
				}
			}
		}

		return dataListAsignturasEstudiantes;
	}
	
	
	private List<EstudianteAcumulativos> dataListEstudianteAcumulativos;
	
	private List<Object> teto;
	
	public void seleccionarCursoAcumulativo(Cursos cursos){
			
		
		teto = new ArrayList<Object>();
		this.dataListRegistroMatriculas = registromatriculasFacade
				.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.cursos.idcursos = "
						+ cursos.getIdcursos()
						+ " ORDER BY R.estudiantes.usuarios.apellidos");

		
		
		
		if(getSesiones().isAdministrador()){
			dataListAsignaturas = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							/*+ " AND RPA.profesores.usuarios.idusuarios = "
							+ getSesiones().getUsuarios().getIdusuarios() */
							+ " AND RPA.cursos.anosacademicos.estadoactivo = true"
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}else{
			//Validamos si es profesor
			dataListAsignaturas = relacionasignaturaperiodosFacade
					.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA "
							+ "WHERE RPA.cursos.grados.idgrados = "
							+ cursos.getGrados().getIdgrados()
							+ " AND "
							+ "(RPA.profesores.usuarios.idusuarios = "
							+ getSesiones().getUsuarios().getIdusuarios()
							+ " OR RPA.cursos.profesor.usuarios.idusuarios = " + getSesiones().getUsuarios().getIdusuarios() + " "
							+ ") AND RPA.cursos.anosacademicos.estadoactivo = true"
							+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		}
		
		
//		dataListAsignaturas = relacionasignaturaperiodosFacade
//				.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.grados.idgrados = "
//						+ cursos.getGrados().getIdgrados()
//						/*+ " AND RPA.profesores.usuarios.idusuarios = "
//						+ getSesiones().getUsuarios().getIdusuarios() */
//						+ " AND RPA.cursos.anosacademicos.estadoactivo = true"
//						+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
//		
		dataListEstudianteAcumulativos = new ArrayList<NotasEstudiantes.EstudianteAcumulativos>();
		for(Registromatriculas r:dataListRegistroMatriculas){
			List<PeriodosAcumulativos> dataListPeriodosAcumulativos = new ArrayList<NotasEstudiantes.PeriodosAcumulativos>();
//			teto.add(r.getEstudiantes().getUsuarios().getNombres());
			for(Relacionasignaturaperiodos rap:dataListAsignaturas){
				List<Definitivasasignaturasperiodos> tmp = definitivasasignaturasperiodosFacade
						.findByLike("SELECT D FROM Definitivasasignaturasperiodos D WHERE "
								+ "D.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
								+ rap.getIdrelacionasignaturaperiodos()
								+ " AND D.registromatricula.idregistromatriculas = "
								+ r.getIdregistromatriculas() + " " 
								+ "ORDER BY D.periodos.fechafin");
				double acumulado = 0;
				//Vamos hasta 4 porque son solo 4 periodos
				for(int i=0;i<4;i++){
					PeriodosAcumulativos acumulativos = new PeriodosAcumulativos();
					try{
						acumulativos.setValor(tmp.get(i).getValor());
						acumulativos.setPeriodos(tmp.get(i).getPeriodos());
						teto.add(tmp.get(i).getValor());
					}catch(Exception e){
						acumulativos.setValor(0);
						acumulativos.setPeriodos(null);
						teto.add(0);
					}
					if(acumulativos.getPeriodos()!=null){
						acumulado += acumulativos.getValor()*acumulativos.getPeriodos().getValor()*0.01;
					}
					dataListPeriodosAcumulativos.add(acumulativos);
				}
				//Colocamos el ultimo periodo que es el acumulativo
				PeriodosAcumulativos acumulativos = new PeriodosAcumulativos();
				acumulativos.setValor(new BigDecimal((acumulado)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
				dataListPeriodosAcumulativos.add(acumulativos);
			}
			EstudianteAcumulativos acumulativosEst = new EstudianteAcumulativos();
			acumulativosEst.setRegistromatriculas(r);
			acumulativosEst.setDataListPeriodosAcumulativos(dataListPeriodosAcumulativos);
			dataListEstudianteAcumulativos.add(acumulativosEst);
		}
	}
	
	
	
	
	
	public List<Object> getTeto() {
		return teto;
	}

	public void setTeto(List<Object> teto) {
		this.teto = teto;
	}

	public List<EstudianteAcumulativos> getDataListEstudianteAcumulativos() {
		return dataListEstudianteAcumulativos;
	}

	public void setDataListEstudianteAcumulativos(List<EstudianteAcumulativos> dataListEstudianteAcumulativos) {
		this.dataListEstudianteAcumulativos = dataListEstudianteAcumulativos;
	}

	public class PeriodosAcumulativos{
		Periodos periodos;
		double valor;
		public double getValor() {
			return valor;
		}
		public void setValor(double valor) {
			this.valor = valor;
		}
		public Periodos getPeriodos() {
			return periodos;
		}
		public void setPeriodos(Periodos periodos) {
			this.periodos = periodos;
		}
	}
	
	
	public class EstudianteAcumulativos{
		Registromatriculas registromatriculas;
		
		List<PeriodosAcumulativos> dataListPeriodosAcumulativos;
		
		public Registromatriculas getRegistromatriculas() {
			return registromatriculas;
		}
		public void setRegistromatriculas(Registromatriculas registromatriculas) {
			this.registromatriculas = registromatriculas;
		}
		public List<PeriodosAcumulativos> getDataListPeriodosAcumulativos() {
			return dataListPeriodosAcumulativos;
		}
		public void setDataListPeriodosAcumulativos(List<PeriodosAcumulativos> dataListPeriodosAcumulativos) {
			this.dataListPeriodosAcumulativos = dataListPeriodosAcumulativos;
		}
	}
	
	List<Integer> prueba;

	public List<Integer> prueba(Registromatriculas rm) {
		prueba = new ArrayList<Integer>();
		for(int i=0;i<85;i++){
			if(rm != null)
				prueba.add(10);
			else
				prueba.add(20);
		}
		return prueba;
	}
	
	 public static void main(String[] args) throws com.lowagie.text.DocumentException {
	        List<java.io.InputStream> list = new ArrayList<java.io.InputStream>();
	        try {
	            // Source pdfs
	            list.add(new FileInputStream(new File("f:/1.pdf")));
	            list.add(new FileInputStream(new File("f:/2.pdf")));

	            // Resulting pdf
	            java.io.FileOutputStream out = new java.io.FileOutputStream(new java.io.File("f:/result.pdf"));

	            doMerge(list, out);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	
	public static void doMerge(List<java.io.InputStream> list, java.io.FileOutputStream outputStream)
            throws DocumentException, IOException, com.lowagie.text.DocumentException {
		com.lowagie.text.Document document = new com.lowagie.text.Document();
        com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(document, outputStream);
        document.open();
        com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();
        
        for (java.io.InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                com.lowagie.text.pdf.PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        
        outputStream.flush();
        document.close();
        outputStream.close();
    }
	
}
