/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.horarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import ejb.BloquesFacade;
import ejb.BloquesHorariosFacade;
import ejb.CursosFacade;
import entities.Bloques;
import entities.Bloqueshorarios;

/**
 *
 * @author ismael
 */
@ManagedBean
@ViewScoped
public class BloquesHorariosManager implements Serializable {

	@EJB
	private BloquesFacade bloquesFacade;
	@EJB
	private BloquesHorariosFacade bloquesHorariosFacade;
	@EJB
	private CursosFacade cursosFacade;
   
	//Lista de bloques
	private List<Bloques> dataListBloques;
	//bloque para editar
	private Bloques bloqueSeleccionadoEditar;
	//bloque para seleccionar y crearles las horas
	private Bloques bloqueSeleccionado;
	
	//Lista de los bloquesHorarios
	private List<Bloqueshorarios> dataListBloquesHorarios;
	//Seleecionar un bloqueHorarioEspecial
	private Bloqueshorarios bloquesHorariosSeleccionadoEditar;
	
	
    public BloquesHorariosManager() {
    }

    //Propiedades de la lista de bloques que hay
	public List<Bloques> getDataListBloques() {
		if(dataListBloques == null){
			dataListBloques = new ArrayList<Bloques>();
			dataListBloques = bloquesFacade.findByLike("SELECT B FROM Bloques B ORDER BY B.nombre");
		}
		return dataListBloques;
	}

	public void setDataListBloques(List<Bloques> dataListBloques) {
		this.dataListBloques = dataListBloques;
	}

	//Seleccionar bloque para editar o crear
	public void seleccionarBloqueEditar(Bloques bloques){
		if(bloques == null){
			bloqueSeleccionadoEditar = new Bloques(new Long(0));
		}else{
			bloqueSeleccionadoEditar = bloques;
		}
		
	}

	
	//propiedades del bloque nuevo para editar
	public Bloques getBloqueSeleccionadoEditar() {
		return bloqueSeleccionadoEditar;
	}

	public void setBloqueSeleccionadoEditar(Bloques bloqueSeleccionadoEditar) {
		this.bloqueSeleccionadoEditar = bloqueSeleccionadoEditar;
	}
	
	// Seleccionar bloque para editar o crear
	public void seleccionarBloque(Bloques bloques) {
		bloqueSeleccionado = bloques;
		dataListBloquesHorarios = bloquesHorariosFacade.findByLike("SELECT B FROM Bloqueshorarios B  "
				+ "WHERE B.bloques.idbloques = " + bloques.getIdbloques() + " ORDER BY B.horainicio");
	}
	
	//Metodo para guardar o editar o un bloque
	public void guardarBloqueSeleccionado(){
		if(bloqueSeleccionadoEditar.getIdbloques() == 0){
			bloquesFacade.create(bloqueSeleccionadoEditar);
		}else{
			bloquesFacade.edit(bloqueSeleccionadoEditar);
		}
		dataListBloques = bloquesFacade.findByLike("SELECT B FROM Bloques B ORDER BY B.nombre");
	}
	
	//###BlquesHorarios
	
	public List<Bloqueshorarios> getDataListBloquesHorarios() {
		if(dataListBloquesHorarios == null && bloqueSeleccionado != null
				&& bloqueSeleccionado.getIdbloques() != null && bloqueSeleccionado.getIdbloques() > 0){
			dataListBloquesHorarios = bloquesHorariosFacade.findByLike("SELECT B FROM Bloqueshorarios B  "
					+ "WHERE B.bloques.idbloques = " + bloqueSeleccionado.getIdbloques() + " ORDER BY B.horainicio");
		}
		return dataListBloquesHorarios;
	}

	public void setDataListBloquesHorarios(
			List<Bloqueshorarios> dataListBloquesHorarios) {
		this.dataListBloquesHorarios = dataListBloquesHorarios;
	}
	
	public void seleccionarBloquesHorariosEditar(Bloqueshorarios bloqueshorarios){
		bloquesHorariosSeleccionadoEditar = bloqueshorarios;
		if(bloqueshorarios == null){
			bloquesHorariosSeleccionadoEditar = new Bloqueshorarios(new Long(0));
			bloquesHorariosSeleccionadoEditar.setBloques(bloqueSeleccionado);
		}
	}
	
	//Propiadades del bloqueHorario
	public Bloqueshorarios getBloquesHorariosSeleccionadoEditar() {
		return bloquesHorariosSeleccionadoEditar;
	}

	public void setBloquesHorariosSeleccionadoEditar(
			Bloqueshorarios bloquesHorariosSeleccionadoEditar) {
		this.bloquesHorariosSeleccionadoEditar = bloquesHorariosSeleccionadoEditar;
	}
	
	//Metodo para guardar los bloquehorarios
	public void guardarBloquesHorariosSeleccionado(){
		if(bloquesHorariosSeleccionadoEditar.getIdbloqueshorarios() == 0){
			bloquesHorariosFacade.create(bloquesHorariosSeleccionadoEditar);
		}else{
			bloquesHorariosFacade.edit(bloquesHorariosSeleccionadoEditar);
		}
		dataListBloquesHorarios = null;
	}
	
	//Metodo para eliminar un horario de un bloque
	public void eliminarBloquesHorariosEditar(Bloqueshorarios bloques){
		bloquesHorariosFacade.remove(bloques);
		dataListBloquesHorarios = null;
		RequestContext.getCurrentInstance().update("formPrincipal:tblHorarios");
		
	}
}
