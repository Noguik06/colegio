/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.usuarios;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ejb.AcudientesFacade;
import ejb.CiudadesFacade;
import ejb.CursosFacade;
import ejb.EstudiantesFacade;
import ejb.GradosFacade;
import ejb.ProfesoresFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionestudiantesacudientesFacade;
import ejb.UsuariosFacade;
import entities.Acudientes;
import entities.Anosacademicos;
import entities.Ciudades;
import entities.Cursos;
import entities.Estudiantes;
import entities.Grados;
import entities.Profesores;
import entities.Registromatriculas;
import entities.Relacionestudiantesacudientes;
import entities.Usuarios;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author juannoguera
 */
@ManagedBean(name = "estudiantesManager")
@ViewScoped
public class EstudiantesManager implements Serializable {

    @EJB
    private EstudiantesFacade estudiantesFacade;
    @EJB
    private AcudientesFacade acudientesFacade;
    @EJB
    private RelacionestudiantesacudientesFacade relacionestudiantesacudientesFacade;
    @EJB
    private GradosFacade gradosFacade;
    @EJB
    private CiudadesFacade ciudadesFacade;
    @EJB
    private UsuariosFacade usuariosFacade;
    @EJB
    private ProfesoresFacade profesoresFacade;
    @EJB
    private RegistromatriculasFacade registromatriculasFacade;
    @EJB
    private CursosFacade cursosFacade;
    ///##USUARIOS
    //Tipo Estudiantes
    private int tipoUsuario = 0;
    //##ESTUDIANTES
    //Estudiante que hemos escogido ya sea para crear o para editar
    private Estudiantes estudianteEscogido;
    //Lista de busqueda de los estudiante
    private List<Estudiantes> dataListEstudiantes;
    //##ACUDIENTES
    //Lista de la busqueda de los acudientes
    private List<Acudientes> dataListAcudientes;
    //Lista de los acudientes por estudiante
    private List<Relacionestudiantesacudientes> dataListAcudientesEstudiantes;
    //##GRADOS
    //Lista de los grados
    private List<Grados> datalistGrados;
    //##QUERY
    String query = "";
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of Estudiantes
     */
    public EstudiantesManager() {
    }

    //Método para saber que tipo de usuario es
    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    //Método para obtener el estudiante que hemos elegido
    public Estudiantes getEstudianteEscogido() {
        return estudianteEscogido;
    }

    //Método para editar el estudiante que hemos ele
    public void setEstudianteEscogido(Estudiantes estudianteEscogido) {
        this.estudianteEscogido = estudianteEscogido;
    }

    //Método para colocar la ciudad de nacimiento
    public void setLugarNacimiento(String ciudad) {
        this.estudianteEscogido.getUsuarios().setLugarnacimiento(ciudad);
    }

    //Método para sacar la ciudad de nacimiento
    public String getLugarNacimiento() {
        if (estudianteEscogido == null) {
            return "";
        } else {
            if (estudianteEscogido.getUsuarios().getLugarnacimiento() == null) {
                return "";
            } else {
                return estudianteEscogido.getUsuarios().getLugarnacimiento();
            }
        }
    }

    //Método para colocar la ciudad de nacimiento
    public void setTipoSangre(String gruposanguineo) {
        this.estudianteEscogido.getUsuarios().setGruposanguineo(gruposanguineo);
    }

    //Método para sacar la ciudad de nacimiento
    public String getTipoSangre() {
        if (estudianteEscogido == null) {
            return "A+";
        } else {
            if (estudianteEscogido.getUsuarios() == null) {
                return "A+";
            } else {
                return estudianteEscogido.getUsuarios().getGruposanguineo();
            }
        }
    }

    //Método para colocar la ciudad de nacimiento
    public void setCiudadIdentificacion(String ciudad) {
        this.estudianteEscogido.getUsuarios().setCiudadidentificacion(ciudad);
    }

    //Método para sacar la ciudad de nacimiento
    public String getCiudadIdentificacion() {
        if (estudianteEscogido == null) {
            return "";
        } else {
            if (estudianteEscogido.getUsuarios().getCiudadidentificacion() == null) {
                return "";
            } else {
                return estudianteEscogido.getUsuarios().getCiudadidentificacion();
            }
        }
    }

    //Método par las ciudades
    public List<Ciudades> getDataListCiudades() {
        return ciudadesFacade.findByLike("SELECT C FROM Ciudades C ORDER BY C.nombre");
    }

    //Método para seleccionar el estudiante escodigo
    public void seleccionarEstudiante(Estudiantes estudiante) {
        dataListAcudientes = null;
        dataListAcudientesEstudiantes = null;
        datalistGrados = null;
        this.estudianteEscogido = estudiantesFacade.find(estudiante.getIdestudiantes());
        if (estudianteEscogido.getIdestudiantes() == 0) {
            List<Acudientes> tmp = new ArrayList<Acudientes>();
            Acudientes ac = new Acudientes(new Long(0));
        }

        if (estudianteEscogido.getGrados() == null) {
            this.estudianteEscogido.setGrados(new Grados(-10));
        }
    }

    //Método para la lista de estudiantes que hemos buscado
    public List<Estudiantes> getDataListEstudiantes() {
        if (dataListEstudiantes == null) {
            if (query.length() > 0) {
                try {
                    String[] valores;
                    Pattern p = Pattern.compile("s*");
                    valores = query.trim().toString().split(" ");
                    boolean banderaA = false;
                    Matcher m;
                    boolean b;
                    boolean h = false;
                    boolean h2 = false;
                    String queryPrueba = "";
                    for (int i = 0; i < valores.length; i++) {
                        m = p.matcher(valores[i]);
                        b = m.matches();
                        if (!b && !h2) {

                            queryPrueba = "SELECT E FROM Estudiantes E WHERE (LOWER(E.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%')";
                            h2 = true;
                        }
                        if (!b && h2) {
                            queryPrueba += " AND (LOWER(E.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%')";
                        }
                    }
                    if (h2) {
                        queryPrueba += " AND E.grados is null ORDER BY E.usuarios.apellidos";
                        dataListEstudiantes = estudiantesFacade.findByLike(queryPrueba);
                    }


                    for (int i = 0; i < valores.length; i++) {
                        m = p.matcher(valores[i]);
                        b = m.matches();
                        if (!b && !h) {

                            queryPrueba = "SELECT E FROM Estudiantes E WHERE (LOWER(E.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.grados.nombre) LIKE '%" + valores[i].toLowerCase() + "%' OR CONCAT(E.grados.numero, '') LIKE  '" + valores[i] + "')";
                            h = true;
                        }
                        if (!b && h) {
                            queryPrueba += " AND (LOWER(E.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(E.grados.nombre) LIKE '%" + valores[i].toLowerCase() + "%' OR CONCAT(E.grados.numero, '') LIKE  '" + valores[i] + "')";
                        }
                    }
                    if (h) {
                        queryPrueba += " ORDER BY  E.grados.numero, E.usuarios.apellidos";
                        if (!dataListEstudiantes.isEmpty()) {
                            dataListEstudiantes.addAll(estudiantesFacade.findByLike(queryPrueba));
                        } else {
                            dataListEstudiantes = estudiantesFacade.findByLike(queryPrueba);
                        }
                    }


                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
                return dataListEstudiantes;
            } else {
            }
            dataListEstudiantes = new ArrayList<Estudiantes>();
        }
        return dataListEstudiantes;



    }

    public int dataListEstudiantesTotales() {
        return estudiantesFacade.findAll().size();
    }

    public void setDataListEstudiantes(List<Estudiantes> dataListEstudiantes) {
        this.dataListEstudiantes = dataListEstudiantes;
    }

    //##ACUDIENTES
    //QUERY BÚSQUEDA
    public void setQuery(String query) {
        this.query = query;
        dataListEstudiantes = null;
    }

    public String getQuery() {
        return query;
    }

    //Método para obtener la lista de los acudientes que hemos buscado
    public List<Acudientes> getDataListAcudientes() {

        dataListAcudientes = new ArrayList<Acudientes>();

        String[] valores;
        Pattern p = Pattern.compile("s*");
        valores = query.trim().toString().split(" ");
        boolean banderaA = false;
        Matcher m;
        boolean b;
        boolean h = false;
        String queryPrueba = "";
        for (int i = 0; i < valores.length; i++) {
            m = p.matcher(valores[i]);
            b = m.matches();
            if (!b && !h) {

                queryPrueba = "SELECT A FROM Acudientes A WHERE (LOWER(A.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(A.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(A.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%')";
                h = true;
            }
            if (!b && h) {
                queryPrueba += " AND (LOWER(A.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(A.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(A.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%')";
            }

            if (h) {
                queryPrueba += "  ORDER BY A.apellidos";
                dataListAcudientes = acudientesFacade.findByLike(queryPrueba);
            }
        }
        return dataListAcudientes;

    }

    //Método para agregarle al estudiante más acudientes4
    public void agregarAcudienteEstudiante() {
        Relacionestudiantesacudientes relacionestudiantesacudientes = new Relacionestudiantesacudientes(new Long(0));
        Acudientes acudientes = new Acudientes(new Long(0));
        acudientes.setNumeroidentificacion("0");
        relacionestudiantesacudientes.setAcudientes(acudientes);
        relacionestudiantesacudientes.setEstudiantes(estudianteEscogido);
        relacionestudiantesacudientes.setParentesco("Padre");
        dataListAcudientesEstudiantes.add(relacionestudiantesacudientes);
    }

    //Método para crear el estudiante
    public void crearEditarAlumno() {

        //Validamos el estudiante
        if (estudianteEscogido.getGrados().getIdgrados() == -10) {
            throw new ValidatorException(new FacesMessage("##0//El grado no puede quedar en blanco"));
        }

        if (tipoUsuario == 0 && (estudianteEscogido.getColegioprocedencia() == null || estudianteEscogido.getColegioprocedencia().trim().length() == 0)) {
            throw new ValidatorException(new FacesMessage("##1//El Colegio de Procedencia no puede quedar en blanco"));
        }

        try {
            userTransaction.begin();
        } catch (Exception e) {
        }


        estudiantesFacade.edit(estudianteEscogido);

        if (registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R WHERE R.grados.idgrados = " + estudianteEscogido.getGrados().getIdgrados() + " AND R.estudiantes.idestudiantes = " + estudianteEscogido.getIdestudiantes()).isEmpty()) {
            Registromatriculas registromatriculas = new Registromatriculas(new Long(0));

            List<Cursos> tmpCursos = cursosFacade.findByLike("SELECT C FROM Cursos C WHERE C.grados.idgrados = " + estudianteEscogido.getGrados().getIdgrados());

            if (tmpCursos != null && tmpCursos.size() > 0) {
                registromatriculas.setAnosacademicos(new Anosacademicos(new Long(1)));
                registromatriculas.setCursos(tmpCursos.get(0));
                registromatriculas.setEstudiantes(estudianteEscogido);
                registromatriculas.setGrados(estudianteEscogido.getGrados());
                registromatriculasFacade.create(registromatriculas);
            }

        }

        for (Relacionestudiantesacudientes rc : dataListAcudientesEstudiantes) {

            //
            Acudientes acudientes = new Acudientes(new Long(0));
            if (rc.getAcudientes().getApellidos() != null && rc.getAcudientes().getApellidos().trim().replaceAll(" ", "").length() > 0 && rc.getAcudientes().getNombres() != null && rc.getAcudientes().getNombres().trim().replaceAll(" ", "").length() > 0) {

                acudientes.setApellidos(rc.getAcudientes().getApellidos());
                acudientes.setNombres(rc.getAcudientes().getNombres());
                if (rc.getAcudientes().getNumeroidentificacion() != null && rc.getAcudientes().getNumeroidentificacion().trim().length() > 0) {
                    acudientes.setNumeroidentificacion(rc.getAcudientes().getNumeroidentificacion());
                }
                if (rc.getAcudientes().getTelefonofijo() != null && rc.getAcudientes().getTelefonofijo().trim().length() > 0) {
                    acudientes.setTelefonofijo(rc.getAcudientes().getTelefonofijo());
                }
                if (rc.getAcudientes().getOcupacion() != null && rc.getAcudientes().getOcupacion().trim().length() > 0) {
                    acudientes.setOcupacion(rc.getAcudientes().getOcupacion());
                }

                acudientes.setIdacudientes(rc.getAcudientes().getIdacudientes());
                if (acudientes.getIdacudientes() == 0) {
                    acudientesFacade.create(acudientes);
                } else {
                    acudientesFacade.edit(acudientes);
                    relacionestudiantesacudientesFacade.edit(rc);
                }

                rc.setAcudientes(acudientes);
                rc.setEstudiantes(estudianteEscogido);


                if (rc.getIdrelacionestudiantesacudientes() == 0) {
                    try {
                        relacionestudiantesacudientesFacade.create(rc);
                    } catch (Exception e) {
                    }
                } else {
                    if (rc.getIdrelacionestudiantesacudientes() < 0) {
                        relacionestudiantesacudientesFacade.remove(rc);
                    }
                }
            }
        }

        try {
            userTransaction.commit();
            dataListEstudiantes = null;
        } catch (Exception e) {
        }
    }

//LISTA DE LOS ACUDIENTES DE CADA ESTUDIANTE
    public List<Relacionestudiantesacudientes> getDataListAcudientesEstudiantes() {
        if (estudianteEscogido == null) {
            return null;
        }
        if (dataListAcudientesEstudiantes == null) {
            if (estudianteEscogido.getIdestudiantes() == 0 || estudianteEscogido.getIdestudiantes() == null) {
                dataListAcudientesEstudiantes = new ArrayList<Relacionestudiantesacudientes>();
                Relacionestudiantesacudientes relacionestudiantesacudientes = new Relacionestudiantesacudientes(new Long(0));
                relacionestudiantesacudientes.setEstudiantes(estudianteEscogido);
                Acudientes acudientes = new Acudientes(new Long(0));
                acudientes.setNumeroidentificacion("0");
                relacionestudiantesacudientes.setAcudientes(acudientes);
                relacionestudiantesacudientes.setParentesco("Padre");
                dataListAcudientesEstudiantes.add(relacionestudiantesacudientes);
            } else {
                dataListAcudientesEstudiantes = relacionestudiantesacudientesFacade.findByLike("SELECT RE FROM Relacionestudiantesacudientes RE WHERE RE.estudiantes.idestudiantes = " + estudianteEscogido.getIdestudiantes());
            }
        }
        return dataListAcudientesEstudiantes;
    }

    public List<Grados> getDataListGrados() {
        if (datalistGrados == null) {
            datalistGrados = gradosFacade.findByLike("SELECT G FROM Grados G ORDER BY G.numero");
        }
        return datalistGrados;
    }

    //Propiedades del grado
    public int getGrado() {
        if (estudianteEscogido == null) {
            return 0;
        }
        return estudianteEscogido.getGrados().getIdgrados();
    }

    //Método para saber si se renderiza o no el grado
    public boolean renderedGrado() {
        if (estudianteEscogido.getGrados().getIdgrados() == -10) {
            return true;
        }

        if (registromatriculasFacade.findByLike("SELECT E FROM Estudiantes E WHERE E.grados.idgrados = " + estudianteEscogido.getGrados().getIdgrados() + " AND E.idestudiantes = " + estudianteEscogido.getIdestudiantes()).isEmpty()) {
            return true;
        }

        return false;
    }

    public void setGrado(int idgrados) {
        estudianteEscogido.setGrados(new Grados(idgrados));
    }

    public String getTipoIdentificacion() {
        if (estudianteEscogido == null) {
            return "";
        }
        return estudianteEscogido.getUsuarios().getTipoidentificacion();
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        estudianteEscogido.getUsuarios().setTipoidentificacion(tipoIdentificacion);
    }

    //Método para eliminar Acudientes
    public void eliminarAcudiente(Relacionestudiantesacudientes relacionestudiantesacudientes) {
        if (relacionestudiantesacudientes.getIdrelacionestudiantesacudientes() > 0) {
            relacionestudiantesacudientesFacade.remove(relacionestudiantesacudientes);
        }
        acudientesFacade.remove(relacionestudiantesacudientes.getAcudientes());
        dataListAcudientesEstudiantes = null;
    }

    //Método para cancelar la edición de un acudiente
    public void cancelarEdicion() {
        if (estudianteEscogido.getIdestudiantes() == 0) {
            estudianteEscogido = null;
            dataListAcudientesEstudiantes = null;
        } else {
            estudianteEscogido = estudiantesFacade.find(estudianteEscogido.getIdestudiantes());
        }
    }

    public void eliminarEstudiante() {
        try {
            userTransaction.begin();
        } catch (Exception e) {
        }



        registromatriculasFacade.metodo("DELETE FROM Registromatriculas R WHERE R.estudiantes.idestudiantes = " + estudianteEscogido.getIdestudiantes());

        estudianteEscogido.setGrados(null);
        estudiantesFacade.edit(estudianteEscogido);

//
//
////        if (dataListAcudientesEstudiantes != null && !dataListAcudientesEstudiantes.isEmpty()) {
////            relacionestudiantesacudientesFacade.metodo("DELETE FROM Relacionestudiantesacudientes rac WHERE rac.estudiantes.idestudiantes = " + Math.abs(estudianteEscogido.getIdestudiantes()));
////            for (Relacionestudiantesacudientes rac : dataListAcudientesEstudiantes) {
////                if (rac.getIdrelacionestudiantesacudientes() != null && rac.getIdrelacionestudiantesacudientes() > 0) {
////                    acudientesFacade.metodo("DELETE FROM Acudientes A WHERE A.idacudientes = " + rac.getAcudientes().getIdacudientes());
////                }
////            }
////        }
//
//
//        estudianteEscogido.setIdestudiantes(Math.abs(estudianteEscogido.getIdestudiantes()));
//        
//        List<Registromatriculas> tmpList = registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R WHERE R.grados.idgrados = " + estudianteEscogido.getGrados().getIdgrados() + " AND R.estudiantes.idestudiantes = " + estudianteEscogido.getIdestudiantes());
////        registromatriculasFacade.remove(null);
//        if (tmpList != null && !tmpList.isEmpty()) {
//            registromatriculasFacade.remove(tmpList.get(0));
//        }
//        
//        estudianteEscogido.setGrados(null);
//        estudiantesFacade.edit(estudianteEscogido);
        dataListAcudientesEstudiantes = null;
        estudianteEscogido.setGrados(new Grados(-11));

        try {
            userTransaction.commit();
        } catch (Exception e) {
        }
    }

    //##PARENTESCO
    public String getParentesco() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlDataTable htd = (HtmlDataTable) root.findComponent("idFormDatosEstudiantes").findComponent("idtablaRelacionAcudientesEstudiantes");
        Relacionestudiantesacudientes rac = (Relacionestudiantesacudientes) htd.getRowData();
        return rac.getParentesco();
    }

    public void setParentesco(String parentesco) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlDataTable htd = (HtmlDataTable) root.findComponent("idFormDatosEstudiantes").findComponent("idtablaRelacionAcudientesEstudiantes");
        Relacionestudiantesacudientes rac = (Relacionestudiantesacudientes) htd.getRowData();
        rac.setParentesco(parentesco);
    }

    //###LISTAS DE LOS ESTUDIANTES
    //Método para obtener un pdf
    public void descargarLista(Grados grados) throws IOException, DocumentException {
//        String fechaStringEspañol = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, new java.util.Locale("es", "ES")).format(vigenciasEntes.getVigencias().getInicio().getTime());

        List<Estudiantes> tmp;
        tmp = estudiantesFacade.findByLikeAll("SELECT E FROM Estudiantes E WHERE E.grados.idgrados = " + grados.getIdgrados() + "ORDER BY E.usuarios.apellidos");

        Document document = new Document(PageSize.LEGAL.rotate(), 20, 20, 20, 20);

// create a File name for the document

//create a PDF writer
        PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(tmp.get(0).getGrados().getNombre() + ".pdf"));

//open the PDF document
        document.open();

//        document.add(new Paragraph("Ente: " + vigenciasEntes.getEntes().getNombre()));

        document.add(new Paragraph("Grado: " + tmp.get(0).getGrados().getNombre().toUpperCase()));

//        document.add(new Paragraph("Vigencia " + fechaStringEspañol));

        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4); // Code 1


        PdfPCell cell;
        cell = new PdfPCell(new Phrase(tmp.get(0).getGrados().getNombre() + ".pdf"));
        float[] columnWidths = new float[]{20f, 20f, 15f, 25f};
        table.setWidths(columnWidths);

        // Code 2
        table.setWidthPercentage(100);
        table.addCell("Nombre");
        table.addCell("Dirección");
        table.addCell("Teléfono:");
        table.addCell("Acudientes");
        for (Estudiantes e : tmp) {
            table.addCell("" + e.getUsuarios().getApellidos().toUpperCase(Locale.US) + " " + e.getUsuarios().getNombres().toUpperCase());
//            table.addCell("" + e.getUsuarios().getDireccion().to);/
            table.addCell("" + e.getUsuarios().getDireccion().toUpperCase());
            table.addCell("" + e.getUsuarios().getTelefono());

            List<Relacionestudiantesacudientes> tmp2 = relacionestudiantesacudientesFacade.findByLike("SELECT R FROM Relacionestudiantesacudientes R WHERE R.estudiantes.idestudiantes = " + e.getIdestudiantes());
            String acudientes = "";
            for (Relacionestudiantesacudientes r : tmp2) {
                acudientes += r.getParentesco().toUpperCase() + ": " + r.getAcudientes().getNombres().toUpperCase() + " " + r.getAcudientes().getApellidos().toUpperCase() + "\n";

                if (r.getAcudientes().getTelefonofijo() != null) {
                    acudientes += " / " + r.getAcudientes().getTelefonofijo();
                }

                if (r.getAcudientes().getTelefonomovil() != null) {
                    acudientes += " - " + r.getAcudientes().getTelefonomovil() + "\n";
                }

                if (r.getAcudientes().getTelefonomovil() == null && r.getAcudientes().getTelefonofijo() != null) {
                    acudientes += "\n";
                }

//                        + " / " + r.getAcudientes().getTelefonofijo() + " " + r.getAcudientes().getTelefonomovil() + " \n";
            }

            table.addCell(acudientes);
        }




//        for (int i = 0; i < dataF1.size(); i++) {
//            // Code 3
////            table.addCell(dataF1.get(i).getCodigocontable() + "");
////            table.addCell(dataF1.get(i).getNombredelacuenta());
////            table.addCell(dataF1.get(i).getSaldoanterior() + "");
////            table.addCell(dataF1.get(i).getDebito() + "");
////            table.addCell(dataF1.get(i).getCredito() + "");
////            table.addCell(dataF1.get(i).getSaldoanterior() + "");
////            table.addCell(dataF1.get(i).getSaldonocorriente() + "");
//        }
        // Code 4
//        table.addCell("5");
//        table.addCell("6");

        // Code 5
        document.add(table);



        //close the PDF document
        document.close();

        String downloadFile = tmp.get(0).getGrados().getNombre() + ".pdf";

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String contentType = "application/pdf";

        response.setContentType(contentType);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + tmp.get(0).getGrados().getNombre() + ".pdf" + "\"");
        downloadFile = tmp.get(0).getGrados().getNombre() + ".pdf";
        byte[] buf = new byte[1024];
        try {
            File file = new File(downloadFile);
            long length = file.length();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream out = response.getOutputStream();
            response.setContentLength((int) length);
            while ((in != null) && ((length = in.read(buf)) != -1)) {
                out.write(buf, 0, (int) length);
            }
            in.close();
            out.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        facesContext.responseComplete();
    }

    public List<Grados> getDataListCursos() {
        return gradosFacade.findByLike("SELECT G FROM Grados G ORDER BY G.nombre");
    }
}
