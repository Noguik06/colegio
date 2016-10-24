/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.tareas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import jsf.usuarios.Sesiones;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.sun.research.ws.wadl.Request;

import ejb.AnosacademicosFacade;
import ejb.CursosFacade;
import ejb.NotascalificablesFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelacionlogrosdimensionesFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import ejb.RelaciontareasestudiantesFacade;
import ejb.TareasFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Registromatriculas;
import entities.Relacionprofesoresasignaturaperiodo;
import entities.Relaciontareasestudiantes;
import entities.Tareas;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class tareasProfesores implements Serializable {

	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
	@EJB
	private RelacionlogrosdimensionesFacade relacionlogrosdimensionesFacade;
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	@EJB
	private RelacionnotaslogrosdimensionboletinFacade relacionnotaslogrosdimensionboletinFacade;
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private NotascalificablesFacade notascalificablesFacade;
	@EJB
	private TareasFacade tareasFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private RelaciontareasestudiantesFacade relaciontareasestudiantesFacade;
	// ##ANOSACADEMICOS
	// Anoacademico actual
	private Anosacademicos anosacademicosActual;
	// ###CURSOS
	// Lista de los cursos
	private List<Cursos> dataListCursos;
	// Curso seleccionado
	private Cursos cursoSeleccionado;
	// Lista de las asignaturasPorCurso
	private List<Relacionprofesoresasignaturaperiodo> dataListRelacionprofesoresasignaturaperiodos;
	// Asignatura escogida
	private Relacionprofesoresasignaturaperiodo relacionprofesorasignaturaperiodosAsignado;
	// ###TAREAS
	// Archivo de la tarea
	private UploadedFile file;
	// Variable para guardar la tarea
	private Tareas tareas;
	// Lista de las tareas
	private List<Tareas> dataListTareas;
	StreamedContent file2;
	// Lista de las tareas de los estudiantes
	private List<Object[]> dataListTareasEstudiantes;

	/**
	 * Creates a new instance of taresProfesores
	 */
	public tareasProfesores() {
	}

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
		if ((dataListCursos == null || dataListCursos.isEmpty())
				&& sesiones != null && sesiones.getUsuarios() != null) {
			Relacionprofesoresasignaturaperiodo tmp;
			dataListCursos = cursosFacade
					.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R "
							+ " WHERE R.profesores.usuarios.idusuarios = "
							+ sesiones.getUsuarios().getIdusuarios()
							+ "  "
							+ " AND  R.cursos.anosacademicos.estadoactivo = true "
							+ " ORDER BY R.cursos.grados.numero");
		}
		return dataListCursos;
	}

	public void setDataListCursos(List<Cursos> dataListCursos) {
		this.dataListCursos = dataListCursos;
	}

	// Metodo para saber cual es el curso seleccionado
	public boolean cursoSeleccionado(Cursos cursos) {
		if (cursoSeleccionado != null
				&& cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
			return true;
		}
		return false;
	}

	// Método para colocar el grado
	public void escogerCurso(Cursos cursos) {
		Sesiones sesiones = getSesiones();
		this.cursoSeleccionado = cursos;

		// dataListRelacionasignaturaperiodos =
		// relacionasignaturaperiodosFacade.findByLike("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.grados.idgrados = "
		// + cursos.getGrados().getIdgrados() +
		// " AND RPA.profesores.usuarios.idusuarios = " +
		// sesiones.getUsuarios().getIdusuarios() +
		// " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
		dataListRelacionprofesoresasignaturaperiodos = relacionprofesoresasignaturaperiodoFacade
				.findByLike("SELECT RPA FROM Relacionprofesoresasignaturaperiodo RPA"
						+ " WHERE RPA.cursos.grados.idgrados = "
						+ cursos.getGrados().getIdgrados()
						+ " "
						+ " AND RPA.profesores.usuarios.idusuarios = "
						+ sesiones.getUsuarios().getIdusuarios()
						+ " "
						+ " AND  RPA.cursos.anosacademicos.estadoactivo = true "
						+ " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");

		relacionprofesorasignaturaperiodosAsignado = null;

		dataListTareas = null;

	}

	// ###ASIGNATURAS
	// PROPIEDADES DE LAS ASIGNATURAS
	public List<Relacionprofesoresasignaturaperiodo> getDataListRelacionasignaturaperiodos() {
		return dataListRelacionprofesoresasignaturaperiodos;
	}

	public void setDataListRelacionasignaturaperiodos(
			List<Relacionprofesoresasignaturaperiodo> dataListRelacionasignaturaperiodos) {
		this.dataListRelacionprofesoresasignaturaperiodos = dataListRelacionasignaturaperiodos;
	}

	// Escoger la asignatura
	public void escogerAsignatura(Relacionprofesoresasignaturaperiodo asignatura) {
		this.relacionprofesorasignaturaperiodosAsignado = asignatura;
		tareas = null;
		file = null;
		dataListTareas = null;
		dataListTareasEstudiantes = new ArrayList<Object[]>();
	}

	//
	public boolean asignaturaSeleccionada(
			Relacionprofesoresasignaturaperiodo asignatura) {
		if (relacionprofesorasignaturaperiodosAsignado != null
				&& this.relacionprofesorasignaturaperiodosAsignado
						.getIdrelacionprofesoresasignaturaperiodo() == asignatura
						.getIdrelacionprofesoresasignaturaperiodo()) {
			return true;
		}
		return false;
	}

	public boolean isAsignaturaSeleccionada() {
		if (relacionprofesorasignaturaperiodosAsignado != null) {
			return true;
		}

		return false;
	}

	public void prueba() {
		System.out.print("Teto12");
	}

	// TAREAS
	// Metodo para guardar el archivo de la tarea
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// Metodo para guardar una tarea
	public String upload() throws UnsupportedEncodingException {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        myRequest.setCharacterEncoding("UTF-8");
		System.out.print(tareas.getNombre());
		if (tareas.getNombre() == null
				|| tareas.getNombre().replaceAll(" ", "").length() == 0) {

			FacesMessage msg = new FacesMessage("Error",
					"Es necesario el nombre de la tarea");
			msg.setSeverity(msg.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			if (tareas.getFin() == null) {
				FacesMessage msg = new FacesMessage("Error",
						"La fecha final de la tarea es necesaria");
				msg.setSeverity(msg.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				if (tareas.getDescripcion() == null
						|| tareas.getDescripcion().replaceAll(" ", "").length() == 0) {

					FacesMessage msg = new FacesMessage("Error",
							"Es necesaria la descripcion de la tarea");
					msg.setSeverity(msg.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					if (file != null && file.getFileName() != null
							&& file.getFileName().length() > 0
							&& file.getSize() > 0) {
						if (file.getSize() > 4710720) {
							FacesMessage msg = new FacesMessage("Error",
									"El tamaño del archivo es mayor que lo permitido");
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							FacesContext.getCurrentInstance().addMessage(null,
									msg);
							return "";
						}
						tareas.setTipo(file.getContentType());
//						InputStream is;
						try {
							byte[] bytes = IOUtils.toByteArray(file.getInputstream());
							tareas.setArchivo(bytes);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String ext = file.getFileName();
						ext = ext.substring(ext.lastIndexOf("."), ext.length());
						tareas.setNombre(tareas.getNombre() + ext);
					}

					tareas.setRelacionprofesoresasignaturaperiodo(relacionprofesorasignaturaperiodosAsignado);
					tareas.setFecha(new Date());
					tareasFacade.create(tareas);

					
					try {
					    String mensaje = "2,1";
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						mensaje+=","+tareas.getIdtareas();
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("nombre", tareas.getNombre());
						jsonObject.put("descripcion", tareas.getDescripcion());
						jsonObject.put("fechaini", sdf.format(tareas.getFecha()));
						jsonObject.put("fechafin", sdf.format(tareas.getFin()));
						jsonObject.put("adjunto", tareas.getArchivo()!=null?true:false);
						jsonObject.put("idtarea", tareas.getIdtareas());
						jsonObject.put("idmateria", tareas.getRelacionprofesoresasignaturaperiodo().getIdrelacionprofesoresasignaturaperiodo());
						mensaje += ","+jsonObject;
					    JSONObject jGcmData = new JSONObject();
					    JSONObject jData = new JSONObject();
					    jData.put("message", mensaje);
					    jGcmData.put("to", "/topics/global");
					    jGcmData.put("data", jData);

//					    URL url = new URL("https://android.googleapis.com/gcm/send");
//					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					    conn.setRequestProperty("Authorization", "key=AIzaSyDKO6wQvqRpy4CAhApIlTWqMqJKVg8zso0");
//					    conn.setRequestProperty("Content-Type", "application/json");
//					    conn.setRequestMethod("POST");
//					    conn.setDoOutput(true);
//
//					    OutputStream outputStream = conn.getOutputStream();
//					    outputStream.write(jGcmData.toString().getBytes());
//
//					    InputStream inputStream = conn.getInputStream();
//					    
//					    System.out.println(mensaje + " prueba");
//					    InputStream inputStream = conn.getInputStream();
//					    String resp = IOUtils.toString(inputStream);
//					    System.out.println(resp);
//					    System.out.println("Check your device/emulator for notification or logcat for " +
//					            "confirmation of the receipt of the GCM message.");
					} catch (Exception e) {
					    System.out.println("Unable to send GCM message.");
					    System.out.println("Please ensure that API_KEY has been replaced by the server " +
					            "API key, and that the device's registration token is correct (if specified).");
					    e.printStackTrace();
					}


					
					
					tareas = null;
					file = null;

					dataListTareas = null;

					FacesMessage msg = new FacesMessage(" ",
							"La tarea ha sido cargada");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		}

		return "";
	}

	// Propiedades para guardar una tarea
	public Tareas getTareas() {
		if (tareas == null) {
			tareas = new Tareas(new Long(0));
		}
		return tareas;
	}

	public void setTareas(Tareas tareas) {
		this.tareas = tareas;
	}

	public void setNombreTarea(String nombre)
			throws UnsupportedEncodingException {
		System.out.print(nombre);
		String nombreTarea = new String(nombre.getBytes("ISO-8859-1"),
				"ISO-8859-1");
		System.out.print(nombreTarea);
		getTareas().setNombre(nombreTarea);
	}

	public String getNombreTarea() {
		return "";
	}

	public void setDescripcionTarea(String nombre)
			throws UnsupportedEncodingException {
		String nombreTarea = new String(nombre.getBytes("ISO-8859-1"),
				"ISO-8859-1");
		getTareas().setDescripcion(nombreTarea);
	}

	public String getDescripcionTarea() {
		return "";
	}

	// Lista de las tareas
	public List<Tareas> getDataListTareas() {
		if (relacionprofesorasignaturaperiodosAsignado != null
				&& dataListTareas == null) {
			dataListTareas = tareasFacade
					.findByLike("SELECT T FROM Tareas T WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = "
							+ relacionprofesorasignaturaperiodosAsignado
									.getIdrelacionprofesoresasignaturaperiodo()
							+ " "
							+ " AND T.relacionprofesoresasignaturaperiodo.cursos.anosacademicos.estadoactivo = true "
							+ " ORDER BY T.fecha DESC");
		}
		return dataListTareas;
	}

	// Metodo para obtener la lista de las tareas que se tienen que entregar
	// online
	public List<Tareas> getDataListTareasOnline() {
		if (relacionprofesorasignaturaperiodosAsignado != null
				&& dataListTareas == null) {
			dataListTareas = tareasFacade
					.findByLike("SELECT T FROM Tareas T WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = "
							+ relacionprofesorasignaturaperiodosAsignado
									.getIdrelacionprofesoresasignaturaperiodo()
							+ " "
							+ " AND T.online = true AND T.relacionprofesoresasignaturaperiodo.cursos.anosacademicos.estadoactivo = true "
							+ " ORDER BY T.fecha DESC");
		}
		return dataListTareas;
	}

	// Metodo para obtener el numero de estudiantes que han subido la tarea
	public List<Tareas> getMumeroEstudiantesCumplidos() {
		if (relacionprofesorasignaturaperiodosAsignado != null
				&& dataListTareas == null) {
			dataListTareas = tareasFacade
					.findByLike("SELECT T FROM Tareas T WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = "
							+ relacionprofesorasignaturaperiodosAsignado
									.getIdrelacionprofesoresasignaturaperiodo()
							+ " "
							+ " AND T.online = true AND T.relacionprofesoresasignaturaperiodo.cursos.anosacademicos.estadoactivo = true "
							+ " ORDER BY T.fecha DESC");

		}
		return dataListTareas;
	}

	// Metodo para saber el numero de los estudiantes que han subido la tarea
	public int numeroEstudiantesCumplidos(Tareas tareas) {
		if (tareas != null) {
			return tareasFacade
					.retornarValorInteger("SELECT COUNT(R) FROM Relaciontareasestudiantes R"
							+ " WHERE R.tareas.idtareas = "
							+ tareas.getIdtareas());
		}

		return 0;
	}

	// Metodo para asignar la tarea que se va a subir
	public void asignarTarea(Tareas tareas) {
		this.tareas = new Tareas();
		this.tareas = tareas;
		dataListTareasEstudiantes = null;
		String query = "select u.nombres,  "
				+ "u.apellidos,  "
				+ "rm.idregistromatriculas, "
				+ "e.idestudiantes, "
				+ "rte.idrelaciontareasestudiantes, "
				+ "rte.nombre, "
				+ "rte.archivo, "
				+ "rte.tipo, "
				+ "rte.comentarios, "
				+ tareas.getIdtareas() + ", "
				+ "case when rte.calificacion is null then 0 else rte.calificacion end, "
				+ "rte.comentariosprofesor "
				+ "from registromatriculas  rm "
				+ "join estudiantes e on rm.estudiantes = e.idestudiantes "
				+ "join usuarios u on e.usuarios = u.idusuarios and u.estado_activo = true "
				+ "left join relaciontareasestudiantes rte on rte.idregistromatriculas = rm.idregistromatriculas "
				+ "where rm.cursos  =  " + cursoSeleccionado.getIdcursos() + " "
				+ "and rm.fecharetiro is null order by 2, 1";
		dataListTareasEstudiantes = tareasFacade.findByNative(query);
	}

	// Metodo para traer los estudiantes que han subido la tarea
	public List<Object[]> getDataListTareasEstudiantes() {
		if (tareas != null && dataListTareasEstudiantes == null) {
			dataListTareasEstudiantes = new ArrayList<Object[]>();
		}
		return dataListTareasEstudiantes;
	}

	public void setDataListTareas(List<Tareas> dataListTareas) {
		this.dataListTareas = dataListTareas;
	}

	public StreamedContent getFileDownload() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaTareas = (UIData) root
				.findComponent("formListaEstudiantes").findComponent(
						"tableListaEstudiantes");
		
		System.out.print(tablaTareas + " tabla tareas");
		StreamedContent file2;
		Object[] tarea = (Object[]) tablaTareas.getRowData();
		InputStream file3;
		file3 = new ByteArrayInputStream((byte[]) tarea[6]);
		file2 = new DefaultStreamedContent(file3,
				tarea[7].toString(),
				tarea[5].toString());
		return file2;
	}

	public void uploadp() {
		if (file != null) {
			FacesMessage msg = new FacesMessage("Succesful", file.getFileName()
					+ " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			file = null;
		}
	}

	// ###METODO PARA ELIMINAR UNA TAREA
	public void eliminarTarea(Tareas tarea) {
		tareasFacade
				.metodo("DELETE FROM Relaciontareasestudiantes R where R.tareas.idtareas = "
						+ tarea.getIdtareas());
		tareasFacade.remove(tarea);
		
		try {
		    String mensaje = "2,3";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			mensaje+=","+tarea.getIdtareas();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("nombre", tarea.getNombre());
			jsonObject.put("descripcion", tarea.getDescripcion());
			jsonObject.put("fechaini", sdf.format(tarea.getFecha()));
			jsonObject.put("fechafin", sdf.format(tarea.getFin()));
			jsonObject.put("adjunto", tarea.getArchivo()!=null?true:false);
			jsonObject.put("idtarea", tarea.getIdtareas());
			jsonObject.put("idmateria", tarea.getRelacionprofesoresasignaturaperiodo().getIdrelacionprofesoresasignaturaperiodo());
			mensaje += ","+jsonObject;
		    JSONObject jGcmData = new JSONObject();
		    JSONObject jData = new JSONObject();
		    jData.put("message", mensaje);
		    jGcmData.put("to", "/topics/global");
		    jGcmData.put("data", jData);

		    URL url = new URL("https://android.googleapis.com/gcm/send");
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestProperty("Authorization", "key=AIzaSyDKO6wQvqRpy4CAhApIlTWqMqJKVg8zso0");
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setRequestMethod("POST");
		    conn.setDoOutput(true);

		    OutputStream outputStream = conn.getOutputStream();
		    outputStream.write(jGcmData.toString().getBytes());

		    InputStream inputStream = conn.getInputStream();
		    
		    System.out.println(mensaje + " prueba");
//		    InputStream inputStream = conn.getInputStream();
//		    String resp = IOUtils.toString(inputStream);
//		    System.out.println(resp);
//		    System.out.println("Check your device/emulator for notification or logcat for " +
//		            "confirmation of the receipt of the GCM message.");
		} catch (Exception e) {
		    System.out.println("Unable to send GCM message.");
		    System.out.println("Please ensure that API_KEY has been replaced by the server " +
		            "API key, and that the device's registration token is correct (if specified).");
		    e.printStackTrace();
		}
		
		dataListTareas = null;
	}
	
	//
	private Object[] tareaCalificableEstudiante;
	private int index = 0;
	
	//Metodo para seleccionar el estudiante para calificar
	public void seleccionarEstudianteACalificar(AjaxBehaviorEvent event){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaTareas = (UIData) root
				.findComponent("formListaEstudiantes").findComponent(
						"tableListaEstudiantes");
		//Seleccionamos la tarea
		tareaCalificableEstudiante = (Object[]) tablaTareas.getRowData();
		index = tablaTareas.getRowIndex();
	}

	//Propiedades para la nota calificable del estudiante
	public Object[] getTareaCalificableEstudiante() {
		return tareaCalificableEstudiante;
	}
	public void setTareaCalificableEstudiante(Object[] tareaCalificableEstudiante) {
		this.tareaCalificableEstudiante = tareaCalificableEstudiante;
	}
	
	//Metodo para guardar la nota del estudiante
	public void guardarNotaCalificableEstudiante(){
		
		if(tareaCalificableEstudiante[10]== null || !(tareaCalificableEstudiante[10] instanceof BigDecimal) ||  Short.parseShort(tareaCalificableEstudiante[10].toString()) < 0 || Short.parseShort(tareaCalificableEstudiante[10].toString()) > 100){
			RequestContext.getCurrentInstance().update("formListaEstudiantes:tableListaEstudiantes:" + index + ":notaEstudiante");
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"La nota introducida debe ser numerica y debe estar entre 0 y 100 ");
			FacesContext.getCurrentInstance()
					.addMessage("teto", fm);
			List<Relaciontareasestudiantes> dataListRelacionTareasEstudiantes = 
					relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareaCalificableEstudiante[9] + 
							" AND R.registromatriculas.idregistromatriculas = " + tareaCalificableEstudiante[2]);
			if(dataListRelacionTareasEstudiantes == null
					|| dataListRelacionTareasEstudiantes.isEmpty()){
				dataListTareasEstudiantes.get(index)[10] = 0;
			}else{
				dataListTareasEstudiantes.get(index)[10] = dataListRelacionTareasEstudiantes.get(0).getCalificacion();
			}
			RequestContext.getCurrentInstance().update("formListaEstudiantes:tableListaEstudiantes:" + index + ":notaEstudiante");
		}else{
			try{
				List<Relaciontareasestudiantes> dataListRelacionTareasEstudiantes = 
						relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareaCalificableEstudiante[9] + 
								" AND R.registromatriculas.idregistromatriculas = " + tareaCalificableEstudiante[2]);
				if(dataListRelacionTareasEstudiantes.isEmpty()){
					Relaciontareasestudiantes relaciontareasestudiantes = new Relaciontareasestudiantes(new Long(0));
					relaciontareasestudiantes.setCalificacion(new Short(tareaCalificableEstudiante[10].toString()));
					if(tareaCalificableEstudiante[11] == null || tareaCalificableEstudiante[11].toString().trim().length() == 0)
						relaciontareasestudiantes.setComentariosprofesor("");
					else
						relaciontareasestudiantes.setComentariosprofesor(tareaCalificableEstudiante[11].toString());
					Registromatriculas registromatriculas = registromatriculasFacade.find(new Long(tareaCalificableEstudiante[2].toString()));
					relaciontareasestudiantes.setRegistromatriculas(registromatriculas);
					Tareas tareas =tareasFacade.find(new Long(tareaCalificableEstudiante[9].toString()));  
					relaciontareasestudiantes.setTareas(tareas);
					relaciontareasestudiantesFacade.create(relaciontareasestudiantes);
					dataListTareasEstudiantes.get(index)[10] = tareaCalificableEstudiante[10];
				}else{
					dataListRelacionTareasEstudiantes.get(0).setCalificacion(new Short(tareaCalificableEstudiante[10].toString()));
					dataListRelacionTareasEstudiantes.get(0).setComentariosprofesor(tareaCalificableEstudiante[11].toString());
					relaciontareasestudiantesFacade.edit(dataListRelacionTareasEstudiantes.get(0));
					dataListTareasEstudiantes.get(index)[10] = tareaCalificableEstudiante[10];
				}
				RequestContext.getCurrentInstance().update("formListaEstudiantes:tableListaEstudiantes:" + index + ":notaEstudiante");
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "OK",
						"Se ha introducido exitosamente la nota ");
				FacesContext.getCurrentInstance()
						.addMessage("teto", fm);
//				log.log(Level.FINE,"Se ha ocurrido exitosamente la tarea");
			}catch(Exception e){
				System.out.println(e);
//				log.log(Level.SEVERE,"Ha ocurrido un error cargando la nota de la tareas");
			}
		}
	}
}
