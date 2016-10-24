/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.administracion;

import ejb.ConfiguracionesFacade;
import ejb.ProfesoresFacade;
import ejb.RelacionusuariosrolesFacade;
import entities.Configuraciones;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import jsf.usuarios.Sesiones;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class rolesManager implements Serializable {

	@EJB
	RelacionusuariosrolesFacade relacionusuariosrolesFacade;
	@EJB
	ProfesoresFacade profesoresFacade;
	@EJB
	ConfiguracionesFacade configuracionesFacade;

	/**
	 * Creates a new instance of rolesManager
	 */
	public rolesManager() {
	}

	// Funcion utilizada para devolver la sesion que estamos manejando
	public Sesiones getSesion() {
		FacesContext FCInstance = FacesContext.getCurrentInstance();
		String theBeanName = "sesiones";
		Object bean = FCInstance.getELContext().getELResolver()
				.getValue(FCInstance.getELContext(), null, theBeanName);
		Sesiones sesiones = (Sesiones) bean;
		return sesiones;
	}

	// Metodo para saber si el usuario puede o no registrar usuarios
	public boolean registrarUsuarios() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 1 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Método para saber si el usuario puede o no registrar usuarios
	public boolean registrarCirculares() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 5 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Metodo para saber si el usuario puede o no registrar usuarios
	public boolean contabilidad() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 6 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Metodo para saber si el usuario puede o no registrar usuarios
	public boolean galeria() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 7 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Metodo para saber si el usuario puede o no registrar usuarios
	public boolean votaciones() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (isEstudiante()) {
			return true;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 8 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	//
	public boolean administracionVotaciones(){
		if (!relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 1 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios()).isEmpty()) 
		{
			return true;
		}
		return false;
	}
	
	//
	public boolean inicioVotaciones() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (isEstudiante()) {
			List<Configuraciones> tmp = configuracionesFacade
					.obtenerConfiguraciones("iniciovotaciones");
			if (!tmp.isEmpty() && tmp.get(0).getValor().equals("true")) {
				return true;
			}
		}
		return false;
	}

	// Funcion para saber si puede o no registrar la matricula
	public boolean registrarMatricula() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 2 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Funcion para saber si puede o no administrar las notas
	public boolean administrarNotas() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (relacionusuariosrolesFacade
				.findByLike(
						"SELECT RU FROM Relacionusuariosroles RU WHERE RU.roles.idroles = 1 AND RU.usuarios.idusuarios = "
								+ getSesion().getUsuarios().getIdusuarios())
				.isEmpty()) {
			return false;
		}

		return true;
	}

	// Funcion para saber si el usuario es profesor
	public boolean isProfesor() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}
		if (profesoresFacade.findByLike(
				"SELECT P FROM Profesores P WHERE P.usuarios.idusuarios = "
						+ getSesion().getUsuarios().getIdusuarios()).isEmpty()) {
			return false;
		}

		return true;
	}

	// Funcion para saber si es estudiante
	public boolean isEstudiante() {
		if (getSesion().getUsuarios() == null) {
			return false;
		}

		if (profesoresFacade.findByLike(
				"SELECT P FROM Estudiantes P WHERE P.usuarios.idusuarios = "
						+ getSesion().getUsuarios().getIdusuarios()).isEmpty()) {
			return false;
		}

		return true;
	}
}
