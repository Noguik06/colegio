/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.administracion;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ejb.InterfazFacade;
import ejb.ModulosFacade;
import entities.Interfaz;
import entities.Modulos;

/**
 * 
 * @author juannoguera
 */
@ManagedBean(name = "menusManager")
@ViewScoped
public class menusManager implements Serializable {

	@EJB
	private ModulosFacade modulosFacade;
	@EJB
	private InterfazFacade interfazFacade;
	
	//##MODULOS
	private List<Modulos> dataListModulos;
	//Modulo seleccionado para realizar el cambio
	private Modulos moduloSeleccionado;
	//Modulo seleccionado para realizar el cambio
	private Modulos moduloSeleccionadoEditar;
	
	//##INTERFACES
	//Lista de las interfaces que pertenecen al modulo seleccionado
	private List<Interfaz> dataListInterfaces;
	//Interfaz seleccionada
	private Interfaz interfazSeleccionada;
	//Interfaz seleccionada
	private Interfaz interfazSeleccionadaEditar;

	/**
	 * Creates a new instance of rolesManager
	 */
	public menusManager() {
		
	}

	//###MODULOS
	public List<Modulos> getDataListModulos() {
		if(dataListModulos==null){
			dataListModulos = modulosFacade.findByLike("SELECT M FROM Modulos M ORDER BY M.orden");
		}
		return dataListModulos;
	}

	public void setDataListModulos(List<Modulos> dataListModulos) {
		this.dataListModulos = dataListModulos;
	}
	
	//Metodo para seleccionar el modulo que vamos a editar
	public void seleccionarModulo(Modulos modulos){
		if(modulos == null){
			List<Object[]> prueba = modulosFacade.findByNative("select case when  r.roles is null then 0 else 10 end  from interfaz i  left join relacionrolesinterfaz r on i.idinterfaz = r.interfaz");
			moduloSeleccionado = new Modulos(0);
			System.out.println(moduloSeleccionado);
		}else{
			moduloSeleccionado = modulos;
		}
	}
	
	//Metodo para seleccionar el modulo que vamos a editar
	public void seleccionarModuloEditar(Modulos modulos) {
		if (modulos == null) {
			List<Object[]> prueba = modulosFacade
					.findByNative("select case when  r.roles is null then 0 else 10 end  from interfaz i  left join relacionrolesinterfaz r on i.idinterfaz = r.interfaz");
			moduloSeleccionadoEditar = new Modulos(0);
		} else {
			moduloSeleccionadoEditar = modulos;
		}
	}

	//Propiedades modulo seleccionado 
	public Modulos getModuloSeleccionado() {
		return moduloSeleccionado;
	}

	public void setModuloSeleccionado(Modulos moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}
	
	
	//Propiedades modulo seleccionado Editar
	public Modulos getModuloSeleccionadoEditar() {
		return moduloSeleccionadoEditar;
	}
	
	public void setModuloSeleccionadoEditar(Modulos moduloSeleccionadoEditar) {
		this.moduloSeleccionadoEditar = moduloSeleccionadoEditar;
	}
	
	public void guardarModuloSeleccionado(){
		if(moduloSeleccionadoEditar!=null && moduloSeleccionadoEditar.getModulo()!=null
				&& moduloSeleccionadoEditar.getModulo() == 0){
			moduloSeleccionadoEditar.setModulo(0);
			modulosFacade.create(moduloSeleccionadoEditar);
			dataListModulos = modulosFacade.findByLike("SELECT M FROM Modulos M ORDER BY M.orden");
		}else{
			modulosFacade.edit(moduloSeleccionadoEditar);
		}
	}
	
	//###INTERFACES
	//Metodo encargado de cargar las interfaces
	public void cargarInterfaces(Modulos modulo){
		if(modulo != null){
			moduloSeleccionado = modulo;
			dataListInterfaces = interfazFacade.findByLikeAll("SELECT I FROM Interfaz I WHERE I.modulos.modulo = " + modulo.getModulo());
		}
	}
	
	public List<Interfaz> getDataListInterfaces() {
		if(dataListInterfaces==null && moduloSeleccionado!= null && moduloSeleccionado.getModulo()!=null){
			dataListInterfaces = interfazFacade.findByLikeAll("SELECT I FROM Interfaz I WHERE I.modulos.modulo = " + moduloSeleccionado.getModulo());
		}
		return dataListInterfaces;
	}

	public void setDataListInterfaces(List<Interfaz> dataListInterfaces) {
		this.dataListInterfaces = dataListInterfaces;
	}

	//Interfaz seleccionada
	public void seleccionarInterfazEditar(Interfaz interfaz){
		if(interfaz == null){
			interfazSeleccionadaEditar = new Interfaz();
			interfazSeleccionadaEditar.setIdinterfaz(new Long(0));
			interfazSeleccionadaEditar.setModulos(moduloSeleccionado);
		}else{
			interfazSeleccionadaEditar = interfaz;
		}
	}
	
	//Interfaz seleccionada
	public void seleccionarInterfaz(Interfaz interfaz) {
		if (interfaz == null) {
			interfazSeleccionada = new Interfaz();
			interfazSeleccionada.setIdinterfaz(new Long(0));
			interfazSeleccionada.setModulos(moduloSeleccionado);
		} else {

		}
	}

	//Propiedades interfaz seleccionada
	public Interfaz getInterfazSeleccionada() {
		return interfazSeleccionada;
	}

	public void setInterfazSeleccionada(Interfaz interfazSeleccionada) {
		this.interfazSeleccionada = interfazSeleccionada;
	}


	public Interfaz getInterfazSeleccionadaEditar() {
		return interfazSeleccionadaEditar;
	}

	public void setInterfazSeleccionadaEditar(Interfaz interfazSeleccionadaEditar) {
		this.interfazSeleccionadaEditar = interfazSeleccionadaEditar;
	}

	//Metodo para guardar la edicion de la interfaz
	public void guardarEdicionInterfaz(){
		if(interfazSeleccionadaEditar!= null && interfazSeleccionadaEditar.getIdinterfaz()!=null
				&& interfazSeleccionadaEditar.getIdinterfaz()==0){
			interfazFacade.create(interfazSeleccionadaEditar);
			dataListInterfaces = interfazFacade.findByLikeAll("SELECT I FROM Interfaz I WHERE I.modulos.modulo = " + moduloSeleccionado.getModulo());
		}else{
			interfazFacade.edit(interfazSeleccionadaEditar);
		}
	}
	
	//Metodo encargado de eliminar una interfaz 
	public void eliminarInterfaz(){
		if(interfazSeleccionadaEditar!= null && interfazSeleccionadaEditar.getIdinterfaz()!=null
				&& interfazSeleccionadaEditar.getIdinterfaz() > 0){
			String queryEliminarRelaciones = "DELETE FROM Relacionrolesinterfaz R WHERE R.interfaz.idinterfaz = " + interfazSeleccionadaEditar.getIdinterfaz();
			interfazFacade.metodo(queryEliminarRelaciones);
			interfazFacade.remove(interfazSeleccionadaEditar);
			dataListInterfaces = interfazFacade.findByLikeAll("SELECT I FROM Interfaz I WHERE I.modulos.modulo = " + moduloSeleccionado.getModulo());
		}
	}
}
