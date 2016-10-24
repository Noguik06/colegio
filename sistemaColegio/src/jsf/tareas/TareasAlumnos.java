/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.tareas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsf.usuarios.Sesiones;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ejb.AnosacademicosFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.LogrosFacade;
import ejb.NotascalificablesFacade;
import ejb.PeriodosFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionlogrosdimensionesFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import ejb.RelaciontareasestudiantesFacade;
import ejb.TareasFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Relacionprofesoresasignaturaperiodo;
import entities.Relaciontareasestudiantes;
import entities.Tareas;

import java.util.Date;

import javax.faces.application.FacesMessage;

import org.primefaces.model.UploadedFile;

import common.StaticVariables;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class TareasAlumnos implements Serializable {

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
    private LogrosFacade logrosFacade;
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
    private RelaciontareasestudiantesFacade relaciontareasestudiantesFacade;
    //##ANOSACADEMICOS
    //Anoacademico actual
    private Anosacademicos anosacademicosActual;
    //###CURSOS
    //Lista de los cursos
    private List<Cursos> dataListCursos;
    //Curso seleccionado
    private Cursos cursoSeleccionado;
    //Lista de las asignaturasPorCurso
    private List<Relacionprofesoresasignaturaperiodo> dataListRelacionprofesoresasignaturaperiodos;
    //Asignatura escogida
    private Relacionprofesoresasignaturaperiodo relacionprofesorasignaturaperiodosAsignado;
    //###TAREAS
    //Variable para guardar la tarea
    private Tareas tareas;
    //Lista de las tareas
    private List<Tareas> dataListTareas;
    //Lista de las tareas
    private List<Tareas> dataListTareasOnline;
    //Numero de las tareas
    private int numeroTareas = 0;
    //Archivo de la tarea   
    private UploadedFile file;
    //Tarea que sube el estudiante
    private Relaciontareasestudiantes relaciontareasestudiantes;
	//Relacion escogida para verificar la nota del estudiante subida por el profesor
	private Relaciontareasestudiantes relaciontareasEstudiantesVN;
    //
//    private StaticVariables staticVariables = new StaticVariables();

//    StreamedContent file2;
    /**
     * Creates a new instance of taresProfesores
     */
    public TareasAlumnos() {
    }

    public Anosacademicos getCurrentYear() {
        if (anosacademicosActual == null) {
            anosacademicosActual = anosacademicosFacade.findByLike("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true").get(0);
        }

        return anosacademicosActual;
    }

    public Sesiones getSesiones() {
        FacesContext FCInstance = FacesContext.getCurrentInstance();
        String theBeanName = "sesiones";
        Object bean = FCInstance.getELContext().getELResolver().getValue(
                FCInstance.getELContext(), null, theBeanName);
        Sesiones sesiones = (Sesiones) bean;
        if (sesiones != null && sesiones.getUsuarios() != null) {
            return sesiones;
        }

        return null;
    }

    //###PROPIEDADES DE LA LISTA DE CURSOS
//    public List<Cursos> getDataListCursos() {
//    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        if ((StaticVariables.dataListCursosTotales == null || StaticVariables.dataListCursosTotales.isEmpty())) {
//        	System.out.print("ola");
//            dataListCursos = cursosFacade.findByLike("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R "
//                    + " WHERE R.cursos.anosacademicos.estadoactivo = true"
//                    + " ORDER BY R.cursos.grados.numero");
//            StaticVariables.dataListCursosTotales = dataListCursos;
//        }
//        return StaticVariables.dataListCursosTotales;
//    }

    public void setDataListCursos(List<Cursos> dataListCursos) {
        this.dataListCursos = dataListCursos;
    }

    //Metodo para saber cual es el curso seleccionado
    public boolean cursoSeleccionado(Cursos cursos) {
        if (cursoSeleccionado != null && cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
            return true;
        }
        return false;
    }

    //Metodo para colocar el grado
    public void escogerCurso(Cursos cursos) {
        this.cursoSeleccionado = cursos;

//        dataListRelacionasignaturaperiodos = relacionasignaturaperiodosFacade.findByLike("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.grados.idgrados = " + cursos.getGrados().getIdgrados() + " AND RPA.profesores.usuarios.idusuarios = " + sesiones.getUsuarios().getIdusuarios() + " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
        dataListRelacionprofesoresasignaturaperiodos = relacionprofesoresasignaturaperiodoFacade
                .findByLikeAll("SELECT RPA FROM Relacionprofesoresasignaturaperiodo RPA WHERE "
                + " RPA.cursos.grados.idgrados = "
                + cursos.getGrados().getIdgrados()
                + " AND RPA.cursos.anosacademicos.estadoactivo = true"
                + " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");

        relacionprofesorasignaturaperiodosAsignado = null;

        dataListTareas = null;

    }

    //###ASIGNATURAS
    //PROPIEDADES DE LAS ASIGNATURAS
    public List<Relacionprofesoresasignaturaperiodo> getDataListRelacionasignaturaperiodos() {
        if (dataListRelacionprofesoresasignaturaperiodos == null
        		&& cursoSeleccionado!=null) {
            dataListRelacionprofesoresasignaturaperiodos = relacionprofesoresasignaturaperiodoFacade
                    .findByLikeAll("SELECT RPA FROM Relacionprofesoresasignaturaperiodo RPA WHERE "
                    + " RPA.cursos.grados.idgrados = "
                    + 	cursoSeleccionado.getGrados().getIdgrados()
                    + " AND RPA.cursos.anosacademicos.estadoactivo = true"
                    + " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
        }
        return dataListRelacionprofesoresasignaturaperiodos;
    }
    
    
    public List<Relacionprofesoresasignaturaperiodo> getDataListRelacionasignaturaperiodosOnline() {
        if (dataListRelacionprofesoresasignaturaperiodos == null
                && getSesiones() != null) {
            dataListRelacionprofesoresasignaturaperiodos = relacionprofesoresasignaturaperiodoFacade
                    .findByLikeAll("SELECT T.relacionprofesoresasignaturaperiodo FROM Tareas T WHERE "
                    + " T.relacionprofesoresasignaturaperiodo.cursos.grados.idgrados = "
                    + getSesiones().getRegistromatriculas().getCursos().getGrados().getIdgrados() + " "
                    + "AND T.relacionprofesoresasignaturaperiodo.cursos.anosacademicos.estadoactivo = true "
                    + "AND T.online = true "
                    + "ORDER BY T.relacionprofesoresasignaturaperiodo.relacionasignaturaperiodos.asignaturas.nombre ");
        }
        return dataListRelacionprofesoresasignaturaperiodos;
    }

    public void setDataListRelacionasignaturaperiodos(List<Relacionprofesoresasignaturaperiodo> dataListRelacionasignaturaperiodos) {
        this.dataListRelacionprofesoresasignaturaperiodos = dataListRelacionasignaturaperiodos;
    }

    //Escoger la asignatura
    public void escogerAsignatura(Relacionprofesoresasignaturaperiodo asignatura) {
        this.relacionprofesorasignaturaperiodosAsignado = asignatura;
        tareas = null;
        dataListTareasOnline = null;
        dataListTareas = null;
    }

    //
    public boolean asignaturaSeleccionada(Relacionprofesoresasignaturaperiodo asignatura) {
        if (relacionprofesorasignaturaperiodosAsignado != null && this.relacionprofesorasignaturaperiodosAsignado.getIdrelacionprofesoresasignaturaperiodo() == asignatura.getIdrelacionprofesoresasignaturaperiodo()) {
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

    //TAREAS
    //Metodo para guardar el archivo de la tarea
    //Propiedades para guardar una tarea
    public Tareas getTareas() {
        if (tareas == null) {
            tareas = new Tareas(new Long(0));
        }
        return tareas;
    }

    public void setTareas(Tareas tareas) {
        this.tareas = tareas;
    }

    //Lista de las tareas
    public List<Tareas> getDataListTareas() {
        if (relacionprofesorasignaturaperiodosAsignado != null && dataListTareas == null) {
            dataListTareas = tareasFacade.findByLike("SELECT T FROM Tareas T WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = " + relacionprofesorasignaturaperiodosAsignado.getIdrelacionprofesoresasignaturaperiodo() + " ORDER BY T.fecha DESC");
        }
        return dataListTareas;
    }

    public void setDataListTareas(List<Tareas> dataListTareas) {
        this.dataListTareas = dataListTareas;
    }

    public StreamedContent getFileDownload() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setCharacterEncoding("UTF-8");
        UIViewRoot root = facesContext.getViewRoot();
        UIData tablaTareas = (UIData) root.findComponent("formTareas").findComponent("tablaTareas");
        StreamedContent file2;
        Tareas tareas = (Tareas) tablaTareas.getRowData();
        InputStream file3;
        file3 = new ByteArrayInputStream(tareas.getArchivo());
        file2 = new DefaultStreamedContent(file3, tareas.getTipo(), tareas.getNombre());
        return file2;
    }
    
    public void prueba() throws IOException{
   	 FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tablaTareas = (UIData) root.findComponent("formTareas").findComponent("tablaTareas");
        StreamedContent file2;
        Tareas tareas = (Tareas) tablaTareas.getRowData();
        Object[] object = tareasFacade.findByNative("SELECT archivo, nombre, tipo from tareas where idtareas = " + tareas.getIdtareas()).get(0);
        InputStream fis;
        String fileName =  object[1].toString();
        String contentType = object[2].toString();
        fis = new ByteArrayInputStream((byte[]) object[0]);
//        file2 = new DefaultStreamedContent(file3, object[2].toString(), object[1].toString());
//   	File ficheroXLS = new File(strPathXLS);
   	FacesContext ctx = FacesContext.getCurrentInstance();
//   	FileInputStream fis = new FileInputStream(ficheroXLS);
   	byte[] bytes = new byte[1000];
   	int read = 0;

   	if (!ctx.getResponseComplete()) {
//   	   String fileName = ficheroXLS.getName();
//   	   String contentType = "application/vnd.ms-excel";
   	   //String contentType = "application/pdf";
   	   HttpServletResponse response =(HttpServletResponse) ctx.getExternalContext().getResponse();
   	   response.setContentType(contentType);
   	   response.setCharacterEncoding("UTF-8");
   	   response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
   	   ServletOutputStream out = response.getOutputStream();

   	   while ((read = fis.read(bytes)) != -1) {
   	        out.write(bytes, 0, read);
   	   }

   	   out.flush();
   	   out.close();
   	   System.out.println("\nDescargado\n");
   	   ctx.responseComplete();
   	}
   }

    public int numeroTareasCurso(Relacionprofesoresasignaturaperiodo rap) {
        if (rap != null) {
            numeroTareas = tareasFacade.retornarValorInteger("SELECT COUNT(T) FROM Tareas T WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = " + rap.getIdrelacionprofesoresasignaturaperiodo());
        }
        return numeroTareas;
    }

    public int getNumeroTareas() {
        return numeroTareas;
    }

    //###TAREAS PARA DEVOLVER
    public List<Tareas> getDataListTareasOnline() {
        if (relacionprofesorasignaturaperiodosAsignado != null && dataListTareasOnline == null) {
            Date date = new Date();
            dataListTareasOnline = tareasFacade.findByLikeAll("SELECT T FROM Tareas T "
                    + "WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = "
                    + relacionprofesorasignaturaperiodosAsignado.getIdrelacionprofesoresasignaturaperiodo() + " "
                    + "AND t.online = true AND T.fin is not null ORDER BY T.fecha DESC");
        }
        return dataListTareasOnline;
    }

    public int numeroTareasOnline(Relacionprofesoresasignaturaperiodo rap) {
        if (rap != null) {
            return numeroTareas = tareasFacade.retornarValorInteger("SELECT COUNT(T) FROM Tareas T"
                    + " WHERE T.relacionprofesoresasignaturaperiodo.idrelacionprofesoresasignaturaperiodo = " + rap.getIdrelacionprofesoresasignaturaperiodo()
                    + " AND T.fin >= CURRENT_DATE AND T.fin is not null");
        }


        return 0;
    }

    //Metodo para guardar el archivo de la tarea
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    //Metodo para guardar una tarea
    public String upload() throws IOException {
    	try{
    		if(tareas.getFin().compareTo(new Date()) > 0){
    			FacesMessage fm = new FacesMessage(
    					FacesMessage.SEVERITY_ERROR, "Error",
    					"La fecha de la tarea se ha vencido");
    			FacesContext.getCurrentInstance()
    					.addMessage("teto", fm);
    		}else{
		    	List<Relaciontareasestudiantes> dataListRelacionTareasEstudiantes = 
						relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareas.getIdtareas() + 
								" AND R.registromatriculas.idregistromatriculas = " + getSesiones().getRegistromatriculas().getIdregistromatriculas());
		        if(dataListRelacionTareasEstudiantes.isEmpty()){
		        	relaciontareasestudiantes.setTareas(tareas);
			        if(file != null &&  file.getInputstream()!= null){
			        	byte[] bytes = IOUtils.toByteArray(file.getInputstream());
			            relaciontareasestudiantes.setArchivo(bytes);
			            relaciontareasestudiantes.setTipo(file.getContentType());
			            relaciontareasestudiantes.setNombre(file.getFileName());
			        }
			        relaciontareasestudiantes.setRegistromatriculas(getSesiones().getRegistromatriculas());
			        relaciontareasestudiantes.setCalificacion(new Short("0"));
			        relaciontareasestudiantesFacade.create(relaciontareasestudiantes);
			        FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Exitoso",
							"La tarea fue subida exitosamente ");
					FacesContext.getCurrentInstance()
							.addMessage("teto", fm);
		        }else{
			        if(file != null &&  file.getInputstream()!= null){
			        	byte[] bytes = IOUtils.toByteArray(file.getInputstream());
			        	dataListRelacionTareasEstudiantes.get(0).setArchivo(bytes);
			        	dataListRelacionTareasEstudiantes.get(0).setTipo(file.getContentType());
			        	dataListRelacionTareasEstudiantes.get(0).setNombre(file.getFileName());
			        }
			        dataListRelacionTareasEstudiantes.get(0).setCalificacion(dataListRelacionTareasEstudiantes.get(0).getCalificacion());
			        relaciontareasestudiantesFacade.edit(dataListRelacionTareasEstudiantes.get(0));
			        FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Exitoso",
							"La tarea fue subida exitosamente ");
					FacesContext.getCurrentInstance()
							.addMessage("teto", fm);
		        }
    		}
    	}catch(Exception e){
    		FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"Ha ocurrido un error registrando la nota ");
			FacesContext.getCurrentInstance()
					.addMessage("teto", fm);
    	}
        return "";
    }

    //
    public boolean validarEliminarSubirTarea(Tareas tareas){
    	if(tareas == null || tareas.getFin().compareTo(new Date()) == -1){
    		return false;
    	}
    	return true;
    }
    
    
    //Metodo para asignar la tarea que vamos a subir
    public void asignarTarea(Tareas tareas){
        this.tareas = new Tareas();
        this.tareas = tareas;
        relaciontareasestudiantes = new Relaciontareasestudiantes(new Long(0)); 
    }
    
    //Metodo para validar si la tarea ya habia sido subida
    public boolean validarTareaSubida(Tareas tareas){
        if(relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R" + 
                " WHERE R.tareas.idtareas = " + tareas.getIdtareas() + " AND R.registromatriculas.idregistromatriculas = " + getSesiones().getRegistromatriculas().getIdregistromatriculas()).isEmpty()){
            return true;
        }
        return false;
    }
    
    //Metodo para eliminar una tarea que ya ha sido subida
    public void eliminarTarea(Tareas tareas){
        relaciontareasestudiantesFacade.metodo("DELETE FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareas.getIdtareas()
                + "  AND R.registromatriculas.idregistromatriculas = " + getSesiones().getRegistromatriculas().getIdregistromatriculas());
    }

    //propiedades de las tareas del estudiante
	public Relaciontareasestudiantes getRelaciontareasestudiantes() {
		return relaciontareasestudiantes;
	}

	public void setRelaciontareasestudiantes(
			Relaciontareasestudiantes relaciontareasestudiantes) {
		this.relaciontareasestudiantes = relaciontareasestudiantes;
	}
	
	
	//Metodo para mostrar la nota
	public int notaCalificable(Tareas tareas){
		if(tareas == null){
			return 0;
		}
		List<Relaciontareasestudiantes> dataListRelacionTareasEstudiantes = 
				relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareas.getIdtareas() + 
						" AND R.registromatriculas.idregistromatriculas = " + getSesiones().getRegistromatriculas().getIdregistromatriculas());
		if(dataListRelacionTareasEstudiantes == null || dataListRelacionTareasEstudiantes.isEmpty()
				|| dataListRelacionTareasEstudiantes.get(0).getCalificacion() == null){
			return 0;
		}
		return dataListRelacionTareasEstudiantes.get(0).getCalificacion();
	}
	

	
	//Metodo para seleccionar la tarea para verificar la nota y los camentarios del profesor
	public void seleccionarVerificarNota(Tareas tareas){
		List<Relaciontareasestudiantes> dataListRelacionTareasEstudiantes = 
				relaciontareasestudiantesFacade.findByLike("SELECT R FROM Relaciontareasestudiantes R WHERE R.tareas.idtareas = " + tareas.getIdtareas() + 
						" AND R.registromatriculas.idregistromatriculas = " + getSesiones().getRegistromatriculas().getIdregistromatriculas());
		
		if(dataListRelacionTareasEstudiantes == null || dataListRelacionTareasEstudiantes.isEmpty()
				|| dataListRelacionTareasEstudiantes.get(0).getCalificacion() == null){
			relaciontareasEstudiantesVN = new Relaciontareasestudiantes(new Long(0));
			relaciontareasEstudiantesVN.setComentariosprofesor("No hay comentarios del profesor");
			relaciontareasEstudiantesVN.setCalificacion(new Short("0"));
		}else{
			if(dataListRelacionTareasEstudiantes.get(0).getComentariosprofesor() == null
					|| dataListRelacionTareasEstudiantes.get(0).getComentariosprofesor().trim().length() == 0){
				dataListRelacionTareasEstudiantes.get(0).setComentariosprofesor("No hay comentarios del profesor");
			}
			relaciontareasEstudiantesVN = dataListRelacionTareasEstudiantes.get(0);
		}
	}
	
	//Propiedades de la relaciontarea escogida
	public Relaciontareasestudiantes getRelaciontareasEstudiantesVN() {
		return relaciontareasEstudiantesVN;
	}

	public void setRelaciontareasEstudiantesVN(
			Relaciontareasestudiantes relaciontareasEstudiantesVN) {
		this.relaciontareasEstudiantesVN = relaciontareasEstudiantesVN;
	}
    
}
