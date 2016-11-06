/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.estudiantes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import ejb.EstadoevaluacioninstitucionalFacade;
import ejb.PreguntasFacade;
import ejb.ProfesoresFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import ejb.RespuestapreguntasFacade;
import ejb.SegmentopreguntasFacade;
import entities.Estadoevaluacioninstitucional;
import entities.Preguntas;
import entities.Profesores;
import entities.Registromatriculas;
import entities.Respuestapreguntas;
import entities.Segmentopreguntas;
import excepciones.EvaluacionInstitucionalException;
import jsf.usuarios.Sesiones;

/**
 * 
 * @author juannoguera
 */
@ManagedBean(name = "evaluacionInstitucional")
@ViewScoped
@SuppressWarnings(value = { "all" })
public class EvaluacionInstitucional implements Serializable {

	/**
	 * Creates a new instance of NewJSFManagedBean
	 */
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	
	@EJB
	private ProfesoresFacade profesoresFacade;
	
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	
	@EJB
	private SegmentopreguntasFacade segmentopreguntasFacade;
	
	@EJB
	private PreguntasFacade preguntasFacade;
	
	@EJB
	private RespuestapreguntasFacade respuestapreguntasFacade;
	
	@EJB
	private EstadoevaluacioninstitucionalFacade estadoevaluacioninstitucionalFacade;
	
	//Lista de los profesores
	private List<Profesores> dataListProfesores;
	
	//
	private List<Segmentopreguntas> dataListSegmentoPreguntas;
	
	private List<PreguntasEvaluacion> dataListRespuestaPreguntas;
	
	//Profesor seleccionado para realizar la encuesta
	private Profesores profesorSeleccionado;
	
	
	@Resource
    private UserTransaction userTransaction;
	
	//Metodo para traer la sesion actual del usuario
	public Sesiones getSesion() {
		FacesContext FCInstance = FacesContext.getCurrentInstance();
		String theBeanName = "sesiones";
		Object bean = FCInstance.getELContext().getELResolver()
				.getValue(FCInstance.getELContext(), null, theBeanName);
		Sesiones sesiones = (Sesiones) bean;

		if (sesiones.getUsuarios() == null) {
			return null;
		}

		return sesiones;
	}

	//Lista de los profesores del estudiante
	public List<Profesores> getDataListProfesores() {
		if(getSesion() != null && (dataListProfesores == null || dataListProfesores.isEmpty())){
			List<Registromatriculas> tmp = registromatriculasFacade
					.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.estudiantes.usuarios.idusuarios = "
							+ getSesion().getUsuarios().getIdusuarios() + " AND R.fecharetiro is null");
			if(tmp!=null & !tmp.isEmpty()){
				dataListProfesores = profesoresFacade.findByLikeAll("SELECT DISTINCT R.profesores FROM Relacionprofesoresasignaturaperiodo R "
						+ "WHERE R.cursos.idcursos = " 
						+ tmp.get(0).getCursos().getIdcursos() + " " 
						+ "AND R.profesores.idprofesores not in "
						+ "(SELECT E.profesor.idprofesores  FROM Estadoevaluacioninstitucional E "
						+ "WHERE E.profesor.idprofesores = R.profesores.idprofesores AND E.registromatriculas.idregistromatriculas = "+ 
						getSesion().getRegistromatriculas().getIdregistromatriculas()+ ") ");
			}
			
		}
		return dataListProfesores;
	}

	public void setDataListProfesores(List<Profesores> dataListProfesores) {
		this.dataListProfesores = dataListProfesores;
	}

	//Lista de las preguntas por profesor
	public List<PreguntasEvaluacion> getDataListSegmentoPreguntas() {
		if(profesorSeleccionado != null && (dataListSegmentoPreguntas==null || dataListSegmentoPreguntas.isEmpty())){
			dataListRespuestaPreguntas = new ArrayList<PreguntasEvaluacion>();
			dataListSegmentoPreguntas = segmentopreguntasFacade.findByLikeAll("SELECT S FROM Segmentopreguntas S order by S.idsegmentopreguntas");
			for(Segmentopreguntas s:dataListSegmentoPreguntas){
				PreguntasEvaluacion preguntasEvaluacion = new PreguntasEvaluacion();
				preguntasEvaluacion.setSegmentopreguntas(s);
				List<Respuestapreguntas> dataListPreguntasRespuestas = new ArrayList<Respuestapreguntas>();
				List<Preguntas> dataListPreguntas = preguntasFacade.findByLikeAll("SELECT P FROM Preguntas P WHERE P.segmentopreguntas.idsegmentopreguntas = "
						+ s.getIdsegmentopreguntas());
				for(Preguntas preguntas : dataListPreguntas){
					Respuestapreguntas respuestapreguntas = new Respuestapreguntas();
					respuestapreguntas.setIdrespuestapreguntas(new Long(0));
					respuestapreguntas.setPreguntas(preguntas);
					respuestapreguntas.setRegistromatriculas(getSesion().getRegistromatriculas());
					respuestapreguntas.setValor(null);
					dataListPreguntasRespuestas.add(respuestapreguntas);
				}
				preguntasEvaluacion.setDataLisRespuestaPreguntas(dataListPreguntasRespuestas);
				dataListRespuestaPreguntas.add(preguntasEvaluacion);
			}
		}
		return dataListRespuestaPreguntas;
	}

	public void setDataListSegmentoPreguntas(List<PreguntasEvaluacion> dataListRespuestaPreguntas) {
		this.dataListRespuestaPreguntas = dataListRespuestaPreguntas;
	}
	
	//Clase estatica para
	public static class PreguntasEvaluacion{
		private Segmentopreguntas segmentopreguntas;
		private List<Respuestapreguntas> dataLisRespuestaPreguntas;
		public Segmentopreguntas getSegmentopreguntas() {
			return segmentopreguntas;
		}
		public void setSegmentopreguntas(Segmentopreguntas segmentopreguntas) {
			this.segmentopreguntas = segmentopreguntas;
		}
		public List<Respuestapreguntas> getDataLisRespuestaPreguntas() {
			return dataLisRespuestaPreguntas;
		}
		public void setDataLisRespuestaPreguntas(List<Respuestapreguntas> dataLisRespuestaPreguntas) {
			this.dataLisRespuestaPreguntas = dataLisRespuestaPreguntas;
		}
	}
	
	//Metodo para seleccionar un profesor
	public void seleccionarDocente(Profesores profesores){
		if(profesorSeleccionado == null || profesores.getIdprofesores() != profesorSeleccionado.getIdprofesores()){
			this.profesorSeleccionado = profesores;
			dataListSegmentoPreguntas = null;
		}
	}
	
	//
	public void finalizarEncuesta() throws SystemException{
		
		try{
			userTransaction.begin();
			
			boolean banderaError = false;
			
			//Validamos que el usuario este logueado
			if(getSesion()!=null){
				//Sacamos las preguntas con sus valores
				for(PreguntasEvaluacion p:dataListRespuestaPreguntas){
					for(Respuestapreguntas r:p.getDataLisRespuestaPreguntas()){
						if(r.getValor() == null || r.getValor()<1 || r.getValor() > 10){
							banderaError = true;
							FacesMessage msg = new FacesMessage("Error","Debes llenar todas las respuestas con valores mayores que 0 y menores que 11");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
							throw new EvaluacionInstitucionalException("Valores en nulos o en limites no permitidos");
						}
						r.setProfesor(profesorSeleccionado);
						respuestapreguntasFacade.create(r);
						
					}
					if(banderaError){
						break;
					}
				}
			}
			userTransaction.commit();
			
			Estadoevaluacioninstitucional estadoevaluacioninstitucional  = new Estadoevaluacioninstitucional();
			estadoevaluacioninstitucional.setIdestadoevaluacioninstitucional(new Long(0));
			estadoevaluacioninstitucional.setRegistromatriculas(getSesion().getRegistromatriculas());
			estadoevaluacioninstitucional.setProfesor(profesorSeleccionado);
			estadoevaluacioninstitucionalFacade.create(estadoevaluacioninstitucional);
			FacesMessage msg = new FacesMessage("OK", "La encuesta ha sido completada exitosamente");
            msg.setSeverity(msg.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            dataListProfesores.remove(profesorSeleccionado);
			profesorSeleccionado = null;
			dataListSegmentoPreguntas = null;
			dataListRespuestaPreguntas = null;
			
		}catch(Exception e){
			System.out.println("Error guardando las preguntas");
		}finally{
		}
	}
	
	
	
}

