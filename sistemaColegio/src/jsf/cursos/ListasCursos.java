/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.cursos;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ejb.AnosacademicosFacade;
import ejb.CursosFacade;
import ejb.EstudiantesFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Estudiantes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class ListasCursos implements Serializable {

	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private EstudiantesFacade estudiantesFacade;
	@EJB
	private AnosacademicosFacade anosacademicosFacade;
	private List<Cursos> dataListCursos;
	// Curso seleccionado
	private Cursos cursoSeleccionado;
	// Lista de ni��os de este grupo
	private List<Estudiantes> dataListEstudiantes;

	// /
	private List<Object[]> dataListEstudiantesMatriculados;
	private List<Object[]> dataListEstudiantesMatriculadosSeleccionados;

	/**
	 * Creates a new instance of ListasCursos
	 */
	public ListasCursos() {
	}
	
	@PostConstruct
    public void init() {
//        cars = service.createCars(10);
        list = Arrays.asList(true, true, true, true, true, true, true, true, true, true);
    }

	// Metodo para obtener los cursos
	public List<Cursos> getDataListCursos() {
		if (dataListCursos == null) {
			dataListCursos = new ArrayList<Cursos>();
			dataListCursos = cursosFacade
					.findByLike("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = true ORDER BY C.grados.numero");
		}

		return dataListCursos;
	}

	public void seleccionarCurso(Cursos cursos) {
		this.cursoSeleccionado = cursos;
		dataListEstudiantes = null;
	}

	public List<Estudiantes> getDataListEstudiantes() {
		if (dataListEstudiantes == null && cursoSeleccionado != null) {
			dataListEstudiantes = estudiantesFacade
					.findByLikeAll("SELECT R.estudiantes FROM Registromatriculas R WHERE R.fecharetiro is null and R.grados.idgrados = "
							+ cursoSeleccionado.getGrados().getIdgrados()
							+ " ORDER BY R.estudiantes.usuarios.apellidos, R.estudiantes.usuarios.nombres");
		}
		return dataListEstudiantes;
	}

//	// Metodo para imprimir el curso
//	public void imprimirCurso() throws DocumentException,
//			FileNotFoundException, BadElementException, MalformedURLException,
//			IOException {
//
//		Document document = new Document(PageSize.LEGAL.rotate(), 20, 20, 20,
//				20);
//		// create a PDF writer
//		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(
//				cursoSeleccionado.getGrados().getNombre() + ".pdf"));
//
//		// open the PDF document
//		document.open();
//
//		PdfContentByte cb = pdf.getDirectContent();
//		//
//		Image image3 = Image
//				.getInstance(new URL(
//						"http://localhost:8080/sistemaColegio/resources/imagenes/Escudo.png"));
//		image3.setAbsolutePosition(30f, 490f);
//		document.add(image3);
//
//		BaseFont bf = BaseFont.createFont();
//		cb.beginText();
//
//		String text = "COLEGIO PEDAGOGICO CAMPESTRE FLORIDABLANCA";
//		String text2 = "Resolucion No. 0241 de 9 de Junio de 2004";
//		String text3 = "Grado: " + cursoSeleccionado.getGrados().getNombre();
//		cb.setFontAndSize(bf, 25);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 200, 540, 0);
//		cb.setFontAndSize(bf, 15);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text2, 360, 525, 0);
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text3, 460, 510, 0);
//		cb.endText();
//
//		Paragraph paragraphTable1 = new Paragraph();
//		paragraphTable1.setSpacingBefore(120);
//
//		PdfPTable table = new PdfPTable(32); // Code 1
//		table.setWidthPercentage(90);
//
//		float[] columnWidths = new float[32];
//		columnWidths[0] = 2f;
//		columnWidths[1] = 38f;
//		for (int i = 0; i < 30; i++) {
//			columnWidths[i + 2] = 2f;
//		}
//
//		table.setWidths(columnWidths);
//
//		table.addCell("No");
//		table.addCell("Nombre");
//
//		for (int i = 0; i < 30; i++) {
//			table.addCell(new Phrase((i + 1) + "", FontFactory.getFont(
//					FontFactory.HELVETICA, 8)));
//		}
//
//
//		int contador = 0;
//		for (Estudiantes e : dataListEstudiantes) {
//			contador++;
//			table.addCell(contador + "");
//			// String apellidos =
//			// e.getUsuarios().getApellidos().toLowerCase().substring(0,
//			// 1).toUpperCase() +
//			// e.getUsuarios().getApellidos().substring(1,e.getUsuarios().getApellidos().length()).toLowerCase();
//			//
//			//
//			// String nombres =
//			// e.getUsuarios().getNombres().toLowerCase().substring(0,
//			// 1).toUpperCase() +
//			// e.getUsuarios().getNombres().substring(1,e.getUsuarios().getNombres().length()).toLowerCase();
//			table.addCell(e.getUsuarios().getApellidos().toUpperCase() + " "
//					+ e.getUsuarios().getNombres().toUpperCase());
//			for (int i = 0; i < 30; i++) {
//				table.addCell(" ");
//			}
//		}
//
//		paragraphTable1.add(table);
//
//		document.add(paragraphTable1);
//		//
//		//
//		// PdfPCell cell;
//		// cell = new PdfPCell(new Phrase(tmp.get(0).getGrados().getNombre() +
//		// ".pdf"));
//		// float[] columnWidths = new float[]{20f, 20f, 15f, 25f};
//		// table.setWidths(columnWidths);
//		//
//		// // Code 2
//
//		// table.addCell("Nombre");
//		// table.addCell("Direcci��n");
//		// table.addCell("Tel��fono:");
//		// table.addCell("Acudientes");
//		// for (Estudiantes e : tmp) {
//		// table.addCell("" +
//		// e.getUsuarios().getApellidos().toUpperCase(Locale.US) + " " +
//		// e.getUsuarios().getNombres().toUpperCase());
//		// // table.addCell("" + e.getUsuarios().getDireccion().to);/
//		// table.addCell("" + e.getUsuarios().getDireccion().toUpperCase());
//		// table.addCell("" + e.getUsuarios().getTelefono());
//		//
//		// List<Relacionestudiantesacudientes> tmp2 =
//		// relacionestudiantesacudientesFacade.findByLike("SELECT R FROM Relacionestudiantesacudientes R WHERE R.estudiantes.idestudiantes = "
//		// + e.getIdestudiantes());
//		// String acudientes = "";
//		// for (Relacionestudiantesacudientes r : tmp2) {
//		// acudientes += r.getParentesco().toUpperCase() + ": " +
//		// r.getAcudientes().getNombres().toUpperCase() + " " +
//		// r.getAcudientes().getApellidos().toUpperCase() + "\n";
//		//
//		// if (r.getAcudientes().getTelefonofijo() != null) {
//		// acudientes += " / " + r.getAcudientes().getTelefonofijo();
//		// }
//		//
//		// if (r.getAcudientes().getTelefonomovil() != null) {
//		// acudientes += " - " + r.getAcudientes().getTelefonomovil() + "\n";
//		// }
//		//
//		// if (r.getAcudientes().getTelefonomovil() == null &&
//		// r.getAcudientes().getTelefonofijo() != null) {
//		// acudientes += "\n";
//		// }
//		//
//		// // + " / " + r.getAcudientes().getTelefonofijo() + " " +
//		// r.getAcudientes().getTelefonomovil() + " \n";
//		// }
//		//
//		// table.addCell(acudientes);
//		// }
//		// ;
//		//
//		// // Code 5
//		// document.add(table);
//		//
//
//		// close the PDF document
//		document.close();
//
//		String downloadFile = cursoSeleccionado.getGrados().getNombre()
//				+ ".pdf";
//
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = facesContext.getExternalContext();
//		HttpServletResponse response = (HttpServletResponse) externalContext
//				.getResponse();
//
//		String contentType = "application/pdf";
//
//		response.setContentType(contentType);
//		response.addHeader("Content-Disposition", "attachment; filename=\""
//				+ cursoSeleccionado.getGrados().getNombre() + ".pdf" + "\"");
//		downloadFile = cursoSeleccionado.getGrados().getNombre() + ".pdf";
//		byte[] buf = new byte[1024];
//		try {
//			File file = new File(downloadFile);
//			long length = file.length();
//			BufferedInputStream in = new BufferedInputStream(
//					new FileInputStream(file));
//			ServletOutputStream out = response.getOutputStream();
//			response.setContentLength((int) length);
//			while ((in != null) && ((length = in.read(buf)) != -1)) {
//				out.write(buf, 0, (int) length);
//			}
//			in.close();
//			out.close();
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}
//
//		File fichero = new File(cursoSeleccionado.getGrados().getNombre()
//				+ ".pdf");
//		fichero.delete();
//
//		facesContext.responseComplete();
//	}

	public List<Object[]> getDataListEstudiantesMatriculados() {
		if (dataListEstudiantesMatriculados == null) {
			String query = "select trim(u.nombres), trim(u.apellidos) nombre, "
					+ "fr.nombre, "
					+ "tipoidentificacion, "
					+ "u.numeroidentificacion,  "
					+ "u.telefono, "
					+ "u.fechanacimiento, "
					+ "u.sexo, "
					+ "u.direccion, "
					+ "u.eps "
					+ "from "
					+ "registromatriculas reg "
					+ "join grados fr on fr.idgrados = reg.grados "
					+ "join estudiantes e on e.idestudiantes = reg.estudiantes  "
					+ "join usuarios u on u.idusuarios = e.usuarios where fecharetiro  is null"
					+ " and reg.anosacademicos = " + getCurrentYear().getIdanosacademicos();
			dataListEstudiantesMatriculados = estudiantesFacade.findoAll(query);
		}
		return dataListEstudiantesMatriculados;
	}
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


	public void setDataListEstudiantesMatriculados(
			List<Object[]> dataListEstudiantesMatriculados) {
		this.dataListEstudiantesMatriculados = dataListEstudiantesMatriculados;
	}

	// Filtro por proveedor
	public boolean filterByNombre(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		if (value == null) {
			return false;
		}
		return value.toString().toLowerCase()
				.contains(((filterText).toString().toLowerCase()));
	}

	public List<Object[]> getDataListEstudiantesMatriculadosSeleccionados() {
		return dataListEstudiantesMatriculadosSeleccionados;
	}

	public void setDataListEstudiantesMatriculadosSeleccionados(
			List<Object[]> dataListEstudiantesMatriculadosSeleccionados) {
		this.dataListEstudiantesMatriculadosSeleccionados = dataListEstudiantesMatriculadosSeleccionados;
	}
	
	private List<Boolean> list;

    public void setList(List<Boolean> list) {
		this.list = list;
	}

	public List<Boolean> getList() {
        return list;
    }
	
	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() ==  Visibility.VISIBLE);
	}
}
