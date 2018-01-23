/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.votaciones;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import jsf.usuarios.Sesiones;
import ejb.AnosacademicosFacade;
import ejb.ConfiguracionesFacade;
import ejb.RegistromatriculasFacade;
import ejb.VotacionesFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Registromatriculas;
import entities.Usuarios;
import entities.Votaciones;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class VotacionesManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private VotacionesFacade votacionesfacade;
	@EJB
	private ConfiguracionesFacade configuracionesFacade;	
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	//Curso seleccionado
	private Cursos cursoSeleccionado;
	//Lista de los estudiantes votaron por personero
	private List<Object[]> dataListRegistromatriculasPersonero;
	//Lista de los estudiantes votaron por contralor
	private List<Object[]> dataListRegistromatriculasContralor;
	// ##ANOSACADEMICOS
	// Anoacademico actual
	private Anosacademicos anosacademicosActual;

	public Anosacademicos getCurrentYear() {
		if (anosacademicosActual == null) {
			anosacademicosActual = anosacademicosFacade
					.findByLike(
							"SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true")
					.get(0);
		}

		return anosacademicosActual;
	}
	
	public void votarPersonero(int valor) {
		if (getSesion().getUsuarios() != null) {
			if(votacionesfacade.findByLike("SELECT V FROM Votaciones V"
					+ " WHERE V.usuarios.idusuarios = " + getSesion().getUsuarios().getIdusuarios()
					+ " AND V.tipo = 1").isEmpty()){
				if(configuracionesFacade.findByLike("SELECT C FROM Configuraciones C"
						+ " WHERE C.propiedad = 'iniciovotaciones' and C.valor = 'false'").isEmpty()){
					Votaciones votaciones = new Votaciones(new Integer(0));
					votaciones.setUsuarios(getSesion().getUsuarios());
					votaciones.setValor(valor);
					votaciones.setTipo(1);
					votacionesfacade.create(votaciones);
				}
			}
		}
	}
	
	public void votarContralor(int valor) {
		if (getSesion().getUsuarios() != null) {
			if(votacionesfacade.findByLike("SELECT V FROM Votaciones V"
					+ " WHERE V.usuarios.idusuarios = " + getSesion().getUsuarios().getIdusuarios()
					+ " AND V.tipo = 2").isEmpty()){
				if(configuracionesFacade.findByLike("SELECT C FROM Configuraciones C"
						+ " WHERE C.propiedad = 'iniciovotaciones' and C.valor = 'false'").isEmpty()){
					Votaciones votaciones = new Votaciones(new Integer(0));
					votaciones.setUsuarios(getSesion().getUsuarios());
					votaciones.setValor(valor);
					votaciones.setTipo(2);
					votacionesfacade.create(votaciones);
				}
			}
		}
	}
	
	public boolean isVotoPersonero(){
		if (getSesion().getUsuarios() != null) {
			return votacionesfacade.findByLike("SELECT V FROM Votaciones V"
					+ " WHERE V.usuarios.idusuarios = " + getSesion().getUsuarios().getIdusuarios() + " AND V.tipo = 1").isEmpty();
		}
		return false;
	}
	
	public boolean isVotoContralor(){
		if (getSesion().getUsuarios() != null) {
			return votacionesfacade.findByLike("SELECT V FROM Votaciones V"
					+ " WHERE V.usuarios.idusuarios = " + getSesion().getUsuarios().getIdusuarios() + " AND V.tipo = 2").isEmpty();
		}
		return false;
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
	
	public int totalVotosPersonero(int valor){
		if (getSesion().getUsuarios() != null) {
			return Integer.parseInt(votacionesfacade.findByObjectNative("select count(1) from votaciones " 
						+ "where valor = " + valor + " and tipo = 1 ").toString());
		}
		return 0;
	}
	
	public int totalVotosContralor(int valor){
		if (getSesion().getUsuarios() != null) {
			return Integer.parseInt(votacionesfacade.findByObjectNative("select count(1) from votaciones " 
						+ "where valor = " + valor + " and tipo = 2 ").toString());
		}
		return 0;
	}
	
	public void reiniciarVotaciones(){
		if (getSesion().getUsuarios() != null) {
			votacionesfacade.metodo("DELETE FROM Votaciones V");
		}
	}
	
	public void iniciarVotaciones(){
		if (getSesion().getUsuarios() != null) {
			votacionesfacade.metodo("UPDATE Configuraciones C SET C.valor = 'true' WHERE C.propiedad = 'iniciovotaciones'");
		}
	}
	
	public void detenerVotaciones(){
		if (getSesion().getUsuarios() != null) {
			votacionesfacade.metodo("UPDATE Configuraciones C SET C.valor = 'false' WHERE C.propiedad = 'iniciovotaciones'");
		}
	}
	
	public void generarResultadosPersonero() throws IOException, JRException{
		DecimalFormat decimalFormat = new DecimalFormat();
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<Usuarios> tmp  = new ArrayList<Usuarios>();
		Usuarios pruebita = new Usuarios();
		tmp.add(pruebita);
    	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tmp);
//    	double iva = facturaVentaSeleccionada.isIva()?facturaVentaSeleccionada.getPrecioventa()*0.1:0;
//    	String ivaPorcentaje = facturaVentaSeleccionada.isIva()?"10":"0";
//    	double subtotal  = facturaVentaSeleccionada.getPrecioventa();
//    	double total  = facturaVentaSeleccionada.getPrecioventa();
    	JasperPrint jasperPrint;
    	
    	
    	SimpleDateFormat f = new SimpleDateFormat("EEEE", new Locale("ES"));
    	String nombreDia = f.format(new Date());
    	f = new SimpleDateFormat("MMMM", new Locale("ES"));
    	String nombreMes = f.format(new Date());
    	f = new SimpleDateFormat("yyyy", new Locale("ES"));
    	String year = f.format(new Date());
    	
    	
    	Calendar cal  = Calendar.getInstance(new Locale("ES"));
    	
		cal.setTime(new Date());
		String hora = cal.get(Calendar.HOUR_OF_DAY)+":"+((cal.get(Calendar.MINUTE)+"").length() < 2 ? "0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE));
		String  dia = cal.get(Calendar.DAY_OF_MONTH) + "";
		
    	
    	parameters.put("df",dia);
    	parameters.put("mf",nombreMes);
    	parameters.put("af",year);
    	parameters.put("hf",hora);
    	parameters.put("ddf",nombreDia);
    	parameters.put("v1",totalVotosPersonero(1)+"");
    	parameters.put("v2",totalVotosPersonero(2)+"");
    	parameters.put("v3",totalVotosPersonero(3)+"");
    	
    	
//    	//Datos del cliente
//    	Clientecontado clientecontado = facturaVentaSeleccionada.getClientecontado();
//    	parameters.put("nombre",clientecontado.getNombre());
//    	parameters.put("direccion",clientecontado.getDireccion());
//    	parameters.put("codigopostal",clientecontado.getCodigopostal() + "-"+clientecontado.getCiudad());
//    	parameters.put("estado",facturaVentaSeleccionada.getClientecontado().getEstado());
    	
    	File file = new File("logo.png");
    	BufferedImage image = ImageIO.read(file);
    	parameters.put("logo", image );
//    	parameters.put("factura", new Report());
    	jasperPrint = JasperFillManager.fillReport("votacionesPersonero.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
    	HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    	httpServletResponse.addHeader("Content-disposition", "attachment; filename=votaciones.pdf");
    	ServletOutputStream outputStream = httpServletResponse.getOutputStream();
    	JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    	FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void generarResultadosContralor() throws IOException, JRException{
		DecimalFormat decimalFormat = new DecimalFormat();
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<Usuarios> tmp  = new ArrayList<Usuarios>();
		Usuarios pruebita = new Usuarios();
		tmp.add(pruebita);
    	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tmp);
//    	double iva = facturaVentaSeleccionada.isIva()?facturaVentaSeleccionada.getPrecioventa()*0.1:0;
//    	String ivaPorcentaje = facturaVentaSeleccionada.isIva()?"10":"0";
//    	double subtotal  = facturaVentaSeleccionada.getPrecioventa();
//    	double total  = facturaVentaSeleccionada.getPrecioventa();
    	JasperPrint jasperPrint;
    	
    	
    	SimpleDateFormat f = new SimpleDateFormat("EEEE", new Locale("ES"));
    	String nombreDia = f.format(new Date());
    	f = new SimpleDateFormat("MMMM", new Locale("ES"));
    	String nombreMes = f.format(new Date());
    	f = new SimpleDateFormat("yyyy", new Locale("ES"));
    	String year = f.format(new Date());
    	
    	
    	Calendar cal  = Calendar.getInstance(new Locale("ES"));
    	
		cal.setTime(new Date());
		String hora = cal.get(Calendar.HOUR_OF_DAY)+":"+((cal.get(Calendar.MINUTE)+"").length() < 2 ? "0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE));
		String  dia = cal.get(Calendar.DAY_OF_MONTH) + "";
		
    	
    	parameters.put("df",dia);
    	parameters.put("mf",nombreMes);
    	parameters.put("af",year);
    	parameters.put("hf",hora);
    	parameters.put("ddf",nombreDia);
    	parameters.put("v1",totalVotosContralor(1)+"");
    	parameters.put("v2",totalVotosContralor(2)+"");
    	parameters.put("v3",totalVotosContralor(3)+"");
    	
//    	//Datos del cliente
//    	Clientecontado clientecontado = facturaVentaSeleccionada.getClientecontado();
//    	parameters.put("nombre",clientecontado.getNombre());
//    	parameters.put("direccion",clientecontado.getDireccion());
//    	parameters.put("codigopostal",clientecontado.getCodigopostal() + "-"+clientecontado.getCiudad());
//    	parameters.put("estado",facturaVentaSeleccionada.getClientecontado().getEstado());
    	
    	File file = new File("logo.png");
    	BufferedImage image = ImageIO.read(file);
    	parameters.put("logo", image );
//    	parameters.put("factura", new Report());
    	jasperPrint = JasperFillManager.fillReport("votacionesContralor.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
    	HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    	httpServletResponse.addHeader("Content-disposition", "attachment; filename=votaciones.pdf");
    	ServletOutputStream outputStream = httpServletResponse.getOutputStream();
    	JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    	FacesContext.getCurrentInstance().responseComplete();
	}
	
	//Metodo para escoger el curso
	public void escogerCurso(Cursos curso){
		this.cursoSeleccionado = curso;
//		dataListRegistromatriculas = registromatriculasFacade.
//				findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.cursos.idcursos = " + curso.getIdcursos()
//						+ " ORDER BY R.estudiantes.usuarios.apellidos"); 
		dataListRegistromatriculasPersonero = registromatriculasFacade.findo("SELECT u.nombres, u.apellidos, v.idvotaciones "
				+ "FROM registromatriculas  reg "
				+ "join estudiantes est on est.idestudiantes = reg.estudiantes "
				+ "join usuarios u on u.idusuarios = est.usuarios "
				+ "left join votaciones v on v.idusuarios = u.idusuarios and v.tipo = 1 "
				+ "where reg.anosacademicos =  "+ getCurrentYear().getIdanosacademicos() + " and reg.cursos = " + curso.getIdcursos() +" "
				+ "and fecharetiro is null "
				+ "order by u.apellidos");
		
		dataListRegistromatriculasContralor = registromatriculasFacade.findo("SELECT u.nombres, u.apellidos, v.idvotaciones "
				+ "FROM registromatriculas  reg "
				+ "join estudiantes est on est.idestudiantes = reg.estudiantes "
				+ "join usuarios u on u.idusuarios = est.usuarios "
				+ "left join votaciones v on v.idusuarios = u.idusuarios and v.tipo = 2 "
				+ "where reg.anosacademicos =  "+ getCurrentYear().getIdanosacademicos() + " and reg.cursos = " + curso.getIdcursos() +" "
				+ "and fecharetiro is null "
				+ "order by u.apellidos");
	}

	
	//Lista 
	public List<Object[]> getDataListRegistromatriculasPersonero() {
		return dataListRegistromatriculasPersonero;
	}

	public void setDataListRegistromatriculasPersonero(
			List<Object[]> dataListRegistromatriculas) {
		this.dataListRegistromatriculasPersonero = dataListRegistromatriculas;
	}
	
	public List<Object[]> getDataListRegistromatriculasContralor() {
		return dataListRegistromatriculasContralor;
	}

	public void setDataListRegistromatriculasContralor(
			List<Object[]> dataListRegistromatriculas) {
		this.dataListRegistromatriculasContralor = dataListRegistromatriculas;
	}
}
