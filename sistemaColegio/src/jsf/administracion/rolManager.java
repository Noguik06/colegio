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
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import ejb.InterfazFacade;
import ejb.ModulosFacade;
import ejb.RelacionrolesinterfazFacade;
//import ejb.RelacionrolesinterfazFacade;
import ejb.RolesFacade;
import entities.Interfaz;
import entities.Modulos;
import entities.Relacionrolesinterfaz;
import entities.Roles;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class rolManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ModulosFacade modulosFacade;
	@EJB
	private InterfazFacade interfazFacade;
	@EJB
	private RolesFacade rolesFacade;
	@EJB
	private RelacionrolesinterfazFacade relacionrolesinterfazFacade;
	
	//##ROLES
	private List<Roles> dataListRoles;
	private Roles rolSeleccionado;
	private Roles rolSeleccionadoEditar;
	private List<Object[]> dataListRelacionRolInterfaz;
	
	//##MODULOS
	private List<Modulos> dataListModulos;
	//Modulo seleccionado para realizar el cambio
	private Modulos moduloSeleccionado;
	
	//##INTERFACES
	//Lista de las interfaces que pertenecen al modulo seleccionado
	private List<Interfaz> dataListInterfaces;
	//Interfaz seleccionada
	private Interfaz interfazSeleccionada;

	/**
	 * Creates a new instance of rolesManager
	 */
	public rolManager() {
		
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
			moduloSeleccionado = new Modulos(0);
		}else{
			moduloSeleccionado = modulos;
		}
	}

	public Modulos getModuloSeleccionado() {
		return moduloSeleccionado;
	}

	public void setModuloSeleccionado(Modulos moduloSeleccionado) {
		this.moduloSeleccionado = moduloSeleccionado;
	}
	
	public void guardarModuloSeleccionado(){
		System.out.println(moduloSeleccionado + " modulo ");
		if(moduloSeleccionado!=null && moduloSeleccionado.getModulo()!=null
				&& moduloSeleccionado.getModulo() == 0){
			moduloSeleccionado.setModulo(0);
			modulosFacade.create(moduloSeleccionado);
			dataListModulos = modulosFacade.findByLike("SELECT M FROM Modulos M ORDER BY M.orden");
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


	public Interfaz getInterfazSeleccionada() {
		return interfazSeleccionada;
	}

	public void setInterfazSeleccionada(Interfaz interfazSeleccionada) {
		this.interfazSeleccionada = interfazSeleccionada;
	}

	
	//##ROLES
	//Metodo para retornar la lista de ROLES que hay
	public List<Roles> getDataListRoles() {
		if(dataListRoles == null){
			dataListRoles = rolesFacade.findByLikeAll("SELECT R FROM Roles R ORDER BY R.nombre");
		}
		return dataListRoles;
	}

	public void setDataListRoles(List<Roles> dataListRoles) {
		this.dataListRoles = dataListRoles;
	}
	
	//Metodo para seleccionar un rol
	public void seleccionarRol(Roles roles){
		if(roles == null){
			rolSeleccionado = new Roles(new Long(0));
		}else{
			rolSeleccionado = roles;
			String query = "select i.idinterfaz, i.descripcion, "
					+ "case  when r.roles is null then 0 else r.roles end "
					+ "from interfaz i  "
					+ "left join relacionrolesinterfaz r on i.idinterfaz = r.interfaz and r.roles = " + rolSeleccionado.getIdroles() + " "
					+ "order by 1";
			dataListRelacionRolInterfaz = rolesFacade.findByNative(query);
		}
	}	
	
	//Metodo para seleccinar un rol para editar o para crear un rol nuevo
	public void seleccionarEditarCrearRol(Roles roles){
		if(roles == null){
			rolSeleccionadoEditar = new Roles(new Long(0));
		}else{
			rolSeleccionadoEditar = roles;
		}
	}
	
	//Metodo encargado de guardar los nuevos roles
	public void guardarRolSeleccionado(){
		if(rolSeleccionado!=null){
			if(rolSeleccionado.getIdroles() != null && rolSeleccionado.getIdroles() == 0){
				rolesFacade.create(rolSeleccionado);
				dataListRoles.add(rolSeleccionado);
			}else{
				rolesFacade.edit(rolSeleccionado);
			}
		}
	}

	//Metodos de las listas de la relacion entre los roles y las interfaces
	public List<Object[]> getDataListRelacionRolInterfaz() {
		if(dataListRelacionRolInterfaz == null){
			if(rolSeleccionado!=null
					&& rolSeleccionado.getIdroles()!=null
					&& rolSeleccionado.getIdroles()!=0){
				dataListRoles = rolesFacade.findByLikeAll("SELECT R FROM Roles R ORDER BY R.nombre");
				return dataListRelacionRolInterfaz;
			}
		}return dataListRelacionRolInterfaz;
	}

	public void setDataListRelacionRolInterfaz(
			List<Object[]> dataListRelacionRolInterfaz) {
		this.dataListRelacionRolInterfaz = dataListRelacionRolInterfaz;
	}	
	

	//Método para obtener los permisos del usuario
	public boolean getActivo(){
		if(rolSeleccionado == null
				|| rolSeleccionado.getIdroles() == null
				|| rolSeleccionado.getIdroles() == 0){
			return false;
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData inputQuery = (UIData) root.findComponent("formPrincipal").findComponent("tblRelacionRolInterfaces");
        Object[]  r = (Object[]) inputQuery.getRowData();
        if(!r[2].toString().equals("0")){
        	return true;
        }
        return false;
	}
    
	
	//Método para setear permisos al usuario
	public void setActivo(boolean permiso){
		FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData inputQuery = (UIData) root.findComponent("formPrincipal").findComponent("tblRelacionRolInterfaces");
        Object[]  object = (Object[]) inputQuery.getRowData();
        String query = "SELECT r.interfaz from relacionrolesinterfaz r where r.interfaz = " + object[0].toString() + " and r.roles = " + rolSeleccionado.getIdroles();
        Object resultado = rolesFacade.findByObjectNative(query);
		if(permiso){
			if(resultado ==null){
				Relacionrolesinterfaz relacionrolesinterfaz = new Relacionrolesinterfaz();
				relacionrolesinterfaz.setIdrelacionrolesinterfaz(new Long(0));
				Interfaz interfaz = new Interfaz();
				interfaz.setIdinterfaz(new Long(object[0].toString()));
				relacionrolesinterfaz.setInterfaz(interfaz);
				relacionrolesinterfaz.setRoles(rolSeleccionado);
				relacionrolesinterfazFacade.create(relacionrolesinterfaz);
			}
		}else{
			if(resultado != null){
				String queryDelete = "DELETE FROM Relacionrolesinterfaz R "
						+ "WHERE R.interfaz.idinterfaz = " + object[0].toString() + " AND R.roles.idroles =  " + rolSeleccionado.getIdroles();
				relacionrolesinterfazFacade.metodo(queryDelete);
			}
		}
	}

	//Propiedades del rolseleccionado
	public Roles getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(Roles rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}
	
	//Propiedades del rolseleccionado Editar
	public Roles getRolSeleccionadoEditar() {
		return rolSeleccionadoEditar;
	}

	public void setRolSeleccionadoEditar(Roles rolSeleccionado) {
		this.rolSeleccionadoEditar = rolSeleccionado;
	}
}
