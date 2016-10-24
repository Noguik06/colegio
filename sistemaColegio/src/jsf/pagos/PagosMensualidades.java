/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.pagos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;

import clasesAyuda.Utility;
import converter.Convertermayuminus;
import ejb.AnosacademicosFacade;
import ejb.ConfiguracionesFacade;
import ejb.CursosFacade;
import ejb.PagospendientesFacade;
import ejb.PensionesFacade;
import ejb.RegistromatriculasFacade;
import ejb.UsuariosFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Pagospendientes;
import entities.Pensiones;
import entities.Registromatriculas;
import entities.Usuarios;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class PagosMensualidades implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	@EJB
	private PagospendientesFacade pagospendientesFacade;
	@EJB
	private PensionesFacade pensionesFacade;
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private UsuariosFacade usuariosFacade;
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
	// Lista de los cursos
	private List<Object[]> dataListPensiones;

	@Resource
	private UserTransaction userTransaction;

	/**
	 * Creates a new instance of ConfiguracionNotas
	 */
	public PagosMensualidades() {
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

	// ###PROPIEDADES DE LA LISTA DE CURSOS
	public List<Cursos> getDataListCursos() {
		if (dataListCursos == null || dataListCursos.isEmpty()) {
			dataListCursos = cursosFacade
					.findByLikeAll("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = 'true' ORDER BY C.grados.numero ");
		}
		return dataListCursos;
	}

	public void setDataListCursos(List<Cursos> dataListCursos) {
		this.dataListCursos = dataListCursos;
	}

	// Metodo para saber cuál es el curso seleccionado
	public boolean cursoSeleccionado(Cursos cursos) {
		if (cursoSeleccionado != null
				&& cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
			return true;
		}
		return false;
	}

	public Cursos getCursoSeleccionado() {
		return cursoSeleccionado;
	}

	public void setCursoSeleccionado(Cursos cursoSeleccionado) {
		this.cursoSeleccionado = cursoSeleccionado;
	}

	// Metodo para colocar el grado
	public void escogerCurso(Cursos cursos) {
		this.cursoSeleccionado = cursos;
		SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
		String query = "select true, "
				+ "u.apellidos || ' ' || u.nombres, "
				+ "r.idregistromatriculas, "
				+ "u.idusuarios, "
				+ "to_char(now(),'dd/mm/yyyy'), "
				+ "case when valor is not null then valor else  "
				+ "COALESCE((select case when valor is not null then valor else 0 end "
				+ "from pensiones pen where pen.idregistromatriculas = r.idregistromatriculas),0) end, "
				+ "case when p.idpagospendientes is null then 0 else p.idpagospendientes end, "
				+ "case when p.saldopendiente is null then 0 else p.saldopendiente end "
				+ "from registromatriculas r "
				+ "join anosacademicos c on c.idanosacademicos = r.anosacademicos and c.estadoactivo = true  "
				+ "join estudiantes e on e.idestudiantes = r.estudiantes "
				+ "join usuarios u on e.usuarios  = u.idusuarios "
				+ "left join pagospendientes p on p.idusuarios = u.idusuarios and  to_char(p.fecha, 'mm/YYYY') = '"
				+ format.format(new Date()).toString() + "' "
				+ "where r.fecharetiro is null AND r.cursos = "
				+ cursos.getIdcursos() + " " + "ORDER BY u.apellidos ";
		dataListPensiones = pagospendientesFacade.findByNative(query);
	}

	// Metodo encargado de consultar el pago de pension por mes
	public void consultarPesionPorMes(AjaxBehaviorEvent event)
			throws ParseException {
		String tmp = (String) ((UIOutput) event.getSource()).getValue();
		tmp = tmp.replaceAll("\\s", "");

		if (tmp != null && tmp.trim().length() > 0
				&& tmp.toString().matches("([0-9]{2})/([0-9]{4})")) {
			String query = "select true, "
					+ "u.apellidos || ' ' || u.nombres, "
					+ "r.idregistromatriculas, "
					+ "u.idusuarios, "
					+ "'01/"+ tmp+ "', "
					+ "case when valor is not null then valor else  "
					+ "COALESCE((select case when valor is not null then valor else 0 end "
					+ "from pensiones pen where pen.idregistromatriculas = r.idregistromatriculas),0) end, "
					+ "case when p.idpagospendientes is null then 0 else p.idpagospendientes end, "
					+ "case when p.saldopendiente is null then 0 else p.saldopendiente end "
					+ "from registromatriculas r "
					+ "join anosacademicos c on c.idanosacademicos = r.anosacademicos and c.estadoactivo = true  "
					+ "join estudiantes e on e.idestudiantes = r.estudiantes "
					+ "join usuarios u on e.usuarios  = u.idusuarios "
					+ "left join pagospendientes p on p.idusuarios = u.idusuarios and  to_char(p.fecha, 'mm/YYYY') = '"
					+ tmp + "' "
					+ "where r.fecharetiro is null AND r.cursos = "
					+ cursoSeleccionado.getIdcursos() + " "
					+ "ORDER BY u.apellidos ";
			dataListPensiones = pagospendientesFacade.findByNative(query);
		}
	}

	// Propiedades de los pagos de pensiones pendientes
	public List<Object[]> getDataListPensiones() {
		return dataListPensiones;
	}

	public void setDataListPensiones(List<Object[]> dataListPensiones) {
		this.dataListPensiones = dataListPensiones;
	}

	// metodo de edicion de pago
	public void edicionPagoMensual(AjaxBehaviorEvent event)
			throws ParseException {
		try {
			userTransaction.begin();
		} catch (Exception e) {

		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaPensiones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tblPensiones");
		Object[] objeto = (Object[]) tablaPensiones.getRowData();
		String queryMensualidad = "SELECT P FROM Pensiones P WHERE P.registromatriculas.idregistromatriculas = "
				+ objeto[2];
		// Validamos si ya existe el valor configurado para este estudiante
		List<Pensiones> dataListPensionesTMP = pensionesFacade
				.findByLike(queryMensualidad);
		if (dataListPensionesTMP.isEmpty()) {
			Pensiones p = new Pensiones();
			p.setIdpensiones(new Long(0));
			Registromatriculas reg = registromatriculasFacade.find(objeto[2]);
			p.setRegistromatriculas(reg);
			Double valor = new Double(objeto[5].toString());
			p.setValor(valor);
			pensionesFacade.create(p);
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("es", "CO"));
		Date date = formatter.parse(objeto[4].toString());
		cal.setTime(date);
		String mes = (cal.get(Calendar.MONTH) + 1) + "";
		if (mes.length() == 1) {
			mes = "0" + mes;
		}
		String fecha = mes + "/" + cal.get(Calendar.YEAR) + "";
		// Validamos si ya existe el pago del mes para este estudiante
		List<Object[]> dataListPagosPendientes = pagospendientesFacade
				.findByNative("SELECT P.idpagospendientes FROM Pagospendientes P "
						+ "where to_char(p.fecha,'MM/yyyy') = '"
						+ fecha
						+ "' "
						+ "AND P.idusuarios = " + objeto[3]);
		if (dataListPagosPendientes.isEmpty()) {
			Pagospendientes pagospendientes = new Pagospendientes(new Long(0));
			Usuarios usuarios = usuariosFacade.find(new Long(objeto[3]
					.toString()));
			pagospendientes.setUsuarios(usuarios);
			pagospendientes.setFecha(date);
			pagospendientes.setEstado(false);
			Double valor = new Double(objeto[5].toString());
			pagospendientes.setValor(valor);
			Double saldopendiente = new Double(objeto[7].toString());
			pagospendientes.setSaldopendiente(saldopendiente);
			pagospendientesFacade.create(pagospendientes);
			
			List<Object[]> tmp = pagospendientesFacade
					.findByNative("SELECT idpagospendientes, true "
							+ "FROM pagospendientes "
							+ "where to_char(fecha,'MM/yyyy') = '"
							+ fecha
							+ "' "
							+ "AND idusuarios = " + objeto[3]);
			Long id = Long.parseLong(tmp.get(0)[0].toString());
			dataListPensiones.get(tablaPensiones.getRowIndex())[6] = id;
		} else {
			Pagospendientes pp = pagospendientesFacade.find(new Long(objeto[6]
					.toString()));
			Double valor = new Double(objeto[5].toString());
			pp.setValor(valor);
			Double saldopendiente = new Double(objeto[7].toString());
			pp.setSaldopendiente(saldopendiente);
			pagospendientesFacade.edit(pp);
		}

		try {
			userTransaction.commit();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"OK", "Se ha editado exitososamente el pago");
			FacesContext.getCurrentInstance().addMessage("teto", fm);

//			int index = tablaPensiones.getRowIndex();
			RequestContext.getCurrentInstance().update(
					"formPrincipal:tblPensiones");
			
			

		} catch (Exception e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Ha ocurrido un error editando el pago");
			FacesContext.getCurrentInstance().addMessage("teto", fm);
		}
	}

	// ###CONFIGURACION DE LAS PENSIONES
	// Metodo para colocar el grado
	public void escogerCursoConfiguracion(Cursos cursos) {
		this.cursoSeleccionado = cursos;
		String query = "select false, "
				+ "u.apellidos || ' ' || u.nombres, "
				+ "r.idregistromatriculas, "
				+ "u.idusuarios, "
				+ "case when p.valor is not null then p.valor else 0 end,"
				+ "u.apellidos, "
				+ "u.nombres "
				+ "from registromatriculas r "
				+ "join anosacademicos c on c.idanosacademicos = r.anosacademicos and c.estadoactivo = true  "
				+ "join estudiantes e on e.idestudiantes = r.estudiantes "
				+ "join usuarios u on e.usuarios  = u.idusuarios "
				+ "left join pensiones p on p.idregistromatriculas = r.idregistromatriculas "
				+ "where r.fecharetiro is null AND r.cursos = "
				+ cursos.getIdcursos() + " " + "ORDER BY u.apellidos ";
		dataListPensiones = pagospendientesFacade.findByNative(query);
	}

	// Configuracion de las mensualidades
	public void edicionConfiguracionPagoMensual(
			AjaxBehaviorEvent event) throws ParseException {
		try {
			userTransaction.begin();
		} catch (Exception e) {

		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaPensiones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tblPensiones");
		Object[] objeto = (Object[]) tablaPensiones.getRowData();
		String queryMensualidad = "SELECT P FROM Pensiones P WHERE P.registromatriculas.idregistromatriculas = "
				+ objeto[2];
		// Validamos si ya existe el valor configurado para este estudiante
		List<Pensiones> dataListPensionesTMP = pensionesFacade
				.findByLike(queryMensualidad);
		if (dataListPensionesTMP.isEmpty()) {
			Pensiones p = new Pensiones();
			p.setIdpensiones(new Long(0));
			Registromatriculas reg = registromatriculasFacade.find(objeto[2]);
			p.setRegistromatriculas(reg);
			Double valor = new Double(objeto[4].toString());
			p.setValor(valor);
			pensionesFacade.create(p);
		} else {
			Double valor = new Double(objeto[4].toString());
			dataListPensionesTMP.get(0).setValor(valor);
			pensionesFacade.edit(dataListPensionesTMP.get(0));
		}

		try {
			userTransaction.commit();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"OK", "Se ha editado exitososamente la mensualidad");
			FacesContext.getCurrentInstance().addMessage("teto", fm);
		} catch (Exception e) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Ha ocurrido un error editando la mensualidad");
			FacesContext.getCurrentInstance().addMessage("teto", fm);
		}
	}

	//Metodo encargado de las impresiones
	public void imprimirReciboGeneral() throws JRException, IOException, ParseException{
		Iterator<Object[]> it = dataListPensiones.iterator();
		JasperPrint jasperPrintGlobal = new JasperPrint();
		int i = -1;
		int j = -1;
		File file = new File("escudoRecibo.png");
    	BufferedImage image = ImageIO.read(file);
    	Calendar calTmp = Calendar.getInstance();
		while(it.hasNext()){
			i++;
			Object[] objeto = it.next();
			if(!Boolean.valueOf(objeto[0].toString()).booleanValue()){
				continue;
			}
			j++;
			boolean tieneMas = false;
			JasperPrint jasperPrint = new JasperPrint();
			Map<String,Object> parameters = new HashMap<String,Object>();
//	    	parameters.put("logo", image );
			
			List<Object[]> tmp = new ArrayList<Object[]>();
			
			
			tmp.add(objeto);
	    	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tmp);
	    	//Ingresamos los datos del anticipo
	    	Convertermayuminus convertermayuminus = new Convertermayuminus();
	    	FacesContext facesContext = FacesContext.getCurrentInstance();
	    	String id = String.format("%05d", new Integer(objeto[6].toString()));
	    	List<Object[]> dataListVerify = pagospendientesFacade
					.findByNative("SELECT idpagospendientes, true "
							+ "FROM pagospendientes "
							+ "where to_char(fecha,'dd/mm/yyyy') = '"
							+ objeto[4].toString()
							+ "' "
							+ "AND idusuarios = " + objeto[3]);
			if(!dataListVerify.isEmpty()){
				id = dataListVerify.get(0)[0].toString();
				id = String.format("%05d", new Integer(id));
			}
	    	if(id.equals("00000")){
	    		Calendar cal = Calendar.getInstance();
//	    		DateFormat dia = new SimpleDateFormat("dd/MM/yyyy", new Locale("es",
//	    				"CO"));
	    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
	    				new Locale("es", "CO"));
	    		Date date = formatter.parse(objeto[4].toString());
	    		cal.setTime(date);
//	    		SimpleDateFormat formatterTMp = new SimpleDateFormat("MM/yy",
//	    				new Locale("es", "CO"));
	    		String mes = (cal.get(Calendar.MONTH) + 1) + "";
	    		if (mes.length() == 1) {
	    			mes = "0" + mes;
	    		}
//	    		String fecha = mes + "/" + cal.get(Calendar.YEAR) + "";
	    		Pagospendientes pagospendientes = new Pagospendientes(new Long(0));
				Usuarios usuarios = usuariosFacade.find(new Long(objeto[3]
						.toString()));
				pagospendientes.setUsuarios(usuarios);
				pagospendientes.setFecha(date);
				pagospendientes.setEstado(false);
				Double valor = new Double(objeto[5].toString());
				pagospendientes.setValor(valor);
				Double saldopendiente = new Double(objeto[7].toString());
				pagospendientes.setSaldopendiente(saldopendiente);
				pagospendientesFacade.create(pagospendientes);
				
				List<Object[]> tmp2 = pagospendientesFacade
						.findByNative("SELECT idpagospendientes, true "
								+ "FROM pagospendientes "
								+ "where to_char(fecha,'dd/mm/yyyy') = '"
								+ objeto[4].toString()
								+ "' "
								+ "AND idusuarios = " + objeto[3]);
				id = tmp2.get(0)[0].toString();
				dataListPensiones.get(i)[6] = id;
	    	}
	    	SimpleDateFormat formatterTmp = new SimpleDateFormat("dd/MM/yyyy",
    				new Locale("es", "CO"));
    		Date dateTmp = formatterTmp.parse(objeto[4].toString());
        	calTmp.setTime(dateTmp);
	    	parameters.put("id1", String.format("%05d", new Long(id)));
	    	parameters.put("nombre1",convertermayuminus.getAsString(facesContext, null, objeto[1]));
	    	parameters.put("Grado1",cursoSeleccionado.getGrados().getNombre());
	    	parameters.put("pension1",new Double(objeto[5].toString()));
	    	parameters.put("saldoanterior1",new Double(objeto[7].toString()));
	    	parameters.put("logo", image );
	    	parameters.put("mes",Utility.mes(calTmp.get(Calendar.MONTH)));
	    	if(it.hasNext()){
	    		i++;
	    		tieneMas = true;
	    		Object[] objeto2 = it.next();
	    		//Validamos si el siguiente valor esta selecciondo
	    		while(!Boolean.valueOf(objeto2[0].toString()).booleanValue()){
	    			tieneMas = false; 
	    			if(it.hasNext()){
	    				i++;
	    				objeto2 = it.next();
	    	    		if(!Boolean.valueOf(objeto2[0].toString()).booleanValue()){
	    					continue;
	    				}else{
	    					tieneMas = true;
	    					break;
	    				}
	    			}else{
	    				tieneMas = false;
	    				break;
	    			}
	    		}
	    		if(tieneMas){
		    		String id2 = String.format("%05d", new Integer(objeto2[6].toString()));
		    		dataListVerify = pagospendientesFacade
							.findByNative("SELECT idpagospendientes, true "
									+ "FROM pagospendientes "
									+ "where to_char(fecha,'dd/mm/yyyy') = '"
									+ objeto2[4].toString()
									+ "' "
									+ "AND idusuarios = " + objeto2[3]);
					if(!dataListVerify.isEmpty()){
						id2 = dataListVerify.get(0)[0].toString();
						id2 = String.format("%05d", new Integer(id2));
					}
		    		if(id2.equals("00000")){
			    		Calendar cal = Calendar.getInstance();
	//		    		DateFormat dia = new SimpleDateFormat("dd/MM/yyyy", new Locale("es",
	//		    				"CO"));
			    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
			    				new Locale("es", "CO"));
			    		Date date = formatter.parse(objeto2[4].toString());
			    		cal.setTime(date);
	//		    		SimpleDateFormat formatterTMp = new SimpleDateFormat("MM/yy",
	//		    				new Locale("es", "CO"));
			    		String mes = (cal.get(Calendar.MONTH) + 1) + "";
			    		if (mes.length() == 1) {
			    			mes = "0" + mes;
			    		}
	//		    		String fecha = mes + "/" + cal.get(Calendar.YEAR) + "";
			    		Pagospendientes pagospendientes = new Pagospendientes(new Long(0));
						Usuarios usuarios = usuariosFacade.find(new Long(objeto2[3]
								.toString()));
						pagospendientes.setUsuarios(usuarios);
						pagospendientes.setFecha(date);
						pagospendientes.setEstado(false);
						Double valor = new Double(objeto2[5].toString());
						pagospendientes.setValor(valor);
						Double saldopendiente = new Double(objeto2[7].toString());
						pagospendientes.setSaldopendiente(saldopendiente);
						pagospendientesFacade.create(pagospendientes);
						
						List<Object[]> tmp2 = pagospendientesFacade
								.findByNative("SELECT idpagospendientes, true "
										+ "FROM pagospendientes "
										+ "where to_char(fecha,'dd/mm/yyyy') = '"
										+ objeto2[4].toString()
										+ "' "
										+ "AND idusuarios = " + objeto2[3]);
						id2 = tmp2.get(0)[0].toString();
						dataListPensiones.get(i)[6] = id2;
			    	}
		    		parameters.put("id2", String.format("%05d", new Long(id2)));
			    	parameters.put("nombre2",convertermayuminus.getAsString(facesContext, null, objeto2[1]));
			    	parameters.put("Grado2",cursoSeleccionado.getGrados().getNombre());
			    	parameters.put("pension2",new Double(objeto2[5].toString()));
			    	parameters.put("mes",Utility.mes(calTmp.get(Calendar.MONTH)));
			    	parameters.put("saldoanterior2",new Double(objeto2[7].toString()));
			    	parameters.put("logo", image );
	    		}
	    	}
	    	
	    	
	    	if(j == 0){
	    		if(tieneMas){
	    			jasperPrintGlobal = JasperFillManager.fillReport("ReciboFinal.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
	    		}else{
	    			jasperPrintGlobal = JasperFillManager.fillReport("ReciboFinalSolo.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
	    		}
	    	}else{
	    		if(tieneMas){
	    			jasperPrint = JasperFillManager.fillReport("ReciboFinal.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
	    		}else{
	    			jasperPrint = JasperFillManager.fillReport("ReciboFinalSolo.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
	    		}
	    		jasperPrintGlobal.addPage(jasperPrint.getPages().get(0));
	    	}
	    	
	    	
	    	
		}
 
		if(jasperPrintGlobal!=null && !jasperPrintGlobal.getPages().isEmpty()){
	    	HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    	httpServletResponse.addHeader("Content-disposition", "inline; filename=\"PensionesMes" + Utility.mes(calTmp.get(Calendar.MONTH)) + ".pdf");
	    	ServletOutputStream outputStream = httpServletResponse.getOutputStream();
	    	JasperExportManager.exportReportToPdfStream(jasperPrintGlobal, outputStream);
	    	FacesContext.getCurrentInstance().responseComplete();
		}
		//Actualizamos la tabla
		RequestContext.getCurrentInstance().update(
				"formPrincipal:tblPensiones");
	}
	
	
	public void  imprimirRecibo() throws JRException, IOException, ParseException{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		UIData tablaPensiones = (UIData) root.findComponent("formPrincipal")
				.findComponent("tblPensiones");
		Object[] objeto = (Object[]) tablaPensiones.getRowData();
	

		JasperPrint jasperPrint = new JasperPrint();
		
		List<Object[]> tmp = new ArrayList<Object[]>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		
		File file = new File("escudoRecibo.png");
    	BufferedImage image = ImageIO.read(file);
    	parameters.put("logo", image );
		
		tmp.add(objeto);
    	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(tmp);
    	//Ingresamos los datos del anticipo
    	Convertermayuminus convertermayuminus = new Convertermayuminus();

    	String id = String.format("%05d", new Integer(objeto[6].toString()));
    	List<Object[]> dataListVerify = pagospendientesFacade
				.findByNative("SELECT idpagospendientes, true "
						+ "FROM pagospendientes "
						+ "where to_char(fecha,'dd/mm/yyyy') = '"
						+ objeto[4].toString()
						+ "' "
						+ "AND idusuarios = " + objeto[3]);
    	if(!dataListVerify.isEmpty()){
    		id = dataListVerify.get(0)[0].toString();
    		id = String.format("%05d", new Integer(id));
    	}
    	if(id.equals("00000")){
    		Calendar cal = Calendar.getInstance();
//    		DateFormat dia = new SimpleDateFormat("dd/MM/yyyy", new Locale("es",
//    				"CO"));
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
    				new Locale("es", "CO"));
    		Date date = formatter.parse(objeto[4].toString());
    		cal.setTime(date);
//    		SimpleDateFormat formatterTMp = new SimpleDateFormat("MM/yy",
//    				new Locale("es", "CO"));
    		String mes = (cal.get(Calendar.MONTH) + 1) + "";
    		if (mes.length() == 1) {
    			mes = "0" + mes;
    		}
//    		String fecha = mes + "/" + cal.get(Calendar.YEAR) + "";
    		Pagospendientes pagospendientes = new Pagospendientes(new Long(0));
			Usuarios usuarios = usuariosFacade.find(new Long(objeto[3]
					.toString()));
			pagospendientes.setUsuarios(usuarios);
			pagospendientes.setFecha(date);
			pagospendientes.setEstado(false);
			Double valor = new Double(objeto[5].toString());
			pagospendientes.setValor(valor);
			Double saldopendiente = new Double(objeto[7].toString());
			pagospendientes.setSaldopendiente(saldopendiente);
			pagospendientesFacade.create(pagospendientes);
			List<Object[]> tmp2 = pagospendientesFacade
					.findByNative("SELECT idpagospendientes, true "
							+ "FROM pagospendientes "
							+ "where to_char(fecha,'dd/mm/yyyy') = '"
							+ objeto[4].toString()
							+ "' "
							+ "AND idusuarios = " + objeto[3]);
			id = tmp2.get(0)[0].toString();
			dataListPensiones.get(tablaPensiones.getRowIndex())[6] = id;
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
				new Locale("es", "CO"));
		Date date = formatter.parse(objeto[4].toString());
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	parameters.put("id1", String.format("%05d", new Long(id)));
    	parameters.put("nombre1",convertermayuminus.getAsString(facesContext, null, objeto[1]));
    	parameters.put("Grado1",cursoSeleccionado.getGrados().getNombre());
    	parameters.put("pension1",new Double(objeto[5].toString()));
    	parameters.put("saldoanterior1",new Double(objeto[7].toString()));
    	parameters.put("mes",Utility.mes(cal.get(Calendar.MONTH)));
    	jasperPrint = JasperFillManager.fillReport("ReciboFinalSolo.jasper", (Map<String,Object>)parameters, beanCollectionDataSource);
    	HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    	httpServletResponse.addHeader("Content-disposition", "inline; filename=\"filename=report.pdf");
    	ServletOutputStream outputStream = httpServletResponse.getOutputStream();
    	JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    	FacesContext.getCurrentInstance().responseComplete();
    	
    	//Actualizamos la tabla
		RequestContext.getCurrentInstance().update(
    					"formPrincipal:tblPensiones");
	
	}
}
