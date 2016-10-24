/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.circulares;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@SessionScoped
public class CircularesManager implements Serializable {

    private Connection con;
    private List<Circulares> dataListCirculares;
    private Circulares circularSeleccionada;

    /**
     * Creates a new instance of Circulares
     */
    public CircularesManager() {
    }
    //Metodo para conectar a la base de datos y sacar las circulares nuevas
    public String conectar() {
        abrir();
        try {
            con.close();
        } catch (Exception e) {
        }
        return "";
    }

    public void abrir() {
    	String dbUrl = "jdbc:mysql://190.90.160.66:3306/juanfno_plataforma";
//        String dbUrl = "jdbc:mysql://localhost:3306/cpc";
        String dbClass = "com.mysql.jdbc.Driver";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, "juanfno_admin", "kakanb105m");
//            con = DriverManager.getConnection(dbUrl, "admin", "admin");
            con.setAutoCommit(false);
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM colegio_content WHERE catid = 10 ORDER BY created");
            //Reiniciamos la lista de las circulares
            dataListCirculares = new ArrayList<Circulares>();
            //Recorremos el query de las circulares 
            while (rs.next()) {
                //Hacemos un objeto tipo CircularesPropiedades y le agregamos las propiedades de la circular
                Circulares circularNueva = new Circulares();
                circularNueva.setNombre(rs.getObject("title").toString());
                circularNueva.setContenido(rs.getObject("introtext").toString());
                circularNueva.setId2(Integer.parseInt(rs.getObject("asset_id").toString()));
                circularNueva.setId(Integer.parseInt(rs.getObject("id").toString()));
                circularNueva.setFecha((Date)rs.getObject("created"));
//                circularNueva.setFecha(new Date(rs.getObject("created").toString()));
                dataListCirculares.add(circularNueva);
            }

        } catch (Exception e) {
            System.out.print(e.getCause());
            System.out.print(e.getLocalizedMessage());
            System.out.print(e.getClass());
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }

    //Clase estatica para obtener las propieades de las circulares
    public static class Circulares {

        int id;
        int id2;
        String nombre;
        Date Fecha;
        String contenido;

        public Circulares() {
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Date getFecha() {
            return Fecha;
        }

        public void setFecha(Date Fecha) {
            this.Fecha = Fecha;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId2() {
            return id2;
        }

        public void setId2(int id2) {
            this.id2 = id2;
        }
    }

    //Propiedades para colocar  y recoger la lista de las circulares
    public List<Circulares> getDataListCirculares() {
        return dataListCirculares;
    }

    public void setDataListCirculares(List<Circulares> dataListCirculares) {
        this.dataListCirculares = dataListCirculares;
    }

    //Metodo para seleccionar la circular
    public void seleccionarCircular(Circulares circulares) {
        this.circularSeleccionada = circulares;
    }

    //Propiedades de la circular que estamos viendo
    public Circulares getCircularSeleccionada() {
        return circularSeleccionada;
    }

    public void setCircularSeleccionada(Circulares circularSeleccionada) {
        this.circularSeleccionada = circularSeleccionada;
    }

    //Propiedades para recoger el contenido de lo que va a llevar la circular
    public String getContenido() {
        if (circularSeleccionada == null || circularSeleccionada.getContenido() == null) {
            return "";

        }
        return circularSeleccionada.getContenido();
    }

    public void setContenido(String contenido) {
        circularSeleccionada.setContenido(contenido);
    }

    //Metodo para colocar una circular Nueva
    public void nuevaCircular() {
        circularSeleccionada = new Circulares();
        circularSeleccionada.setFecha(new Date());
        circularSeleccionada.setId(0);
        circularSeleccionada.setNombre("");
        circularSeleccionada.setContenido("");
    }

    //Metodo para guardar la circular
    public String guardarCircular() throws SQLException {

        abrir();

        String clean_string = circularSeleccionada.getContenido();
//        clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");       
//        clean_string = clean_string.replaceAll("\\r", "\\\\r");
//        clean_string = clean_string.replaceAll("\\t", "\\\\t");
//        clean_string = clean_string.replaceAll("\\00", "\\\\0");
//        clean_string = clean_string.replaceAll("'", "\\\\'");
//        clean_string = clean_string.replaceAll("\\\"", "\\\\\"");
        circularSeleccionada.setContenido(clean_string);


        if (circularSeleccionada == null) {
        }
        if (circularSeleccionada.getNombre() == null || circularSeleccionada.getNombre().trim().replaceAll(" ", "").length() == 0) {
            FacesMessage msg = new FacesMessage("La circular no tiene titulo", null);
            msg.setSeverity(msg.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        if (circularSeleccionada.getContenido() == null || circularSeleccionada.getContenido().trim().replaceAll(" ", "").length() == 0) {
            FacesMessage msg = new FacesMessage("La circular no tiene contendio", null);
            msg.setSeverity(msg.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        int idMaximoAssets = 0;
        int idMaximoArticles = 0;

        if (circularSeleccionada != null && circularSeleccionada.getId() > 0) {
            String query = "UPDATE colegio_content C set C.title = '" + circularSeleccionada.getNombre() + "', C.introtext = '" + circularSeleccionada.getContenido() + "' WHERE C.id = " + circularSeleccionada.getId();

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            con.commit();

            FacesMessage msg = new FacesMessage("La circular fue editada exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            if (circularSeleccionada != null && circularSeleccionada.getId() == 0) {
                try {
                    java.sql.Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT MAX(id) as maximo FROM colegio_assets");
                    while (rs.next()) {
                        idMaximoAssets = Integer.parseInt(rs.getObject("maximo").toString());
                    }
                    rs = stmt.executeQuery("SELECT MAX(id) as maximo FROM colegio_content");
                    while (rs.next()) {
                        idMaximoArticles = Integer.parseInt(rs.getObject("maximo").toString());
                    }
                    //Creamos lo del asset_colegio
                    String query = " insert into colegio_assets (id, parent_id, lft, rgt, level, name, title, rules)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?)";
                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    //Colocamos las propiedades
                    preparedStmt.setInt(1, idMaximoAssets + 1);
                    preparedStmt.setInt(2, 83);
                    preparedStmt.setInt(3, 43);
                    preparedStmt.setInt(4, 44);
                    preparedStmt.setInt(5, 3);
                    preparedStmt.setString(6, "com_content.article." + (idMaximoArticles + 1));
                    preparedStmt.setString(7, circularSeleccionada.getNombre());
                    preparedStmt.setString(8, "{\"core.delete\":{\"6\":1},\"core.edit\":{\"6\":1,\"4\":1},\"core.edit.state\":{\"6\":1,\"5\":1}}");
                    preparedStmt.execute();
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    System.out.print(e.getStackTrace());
                    System.out.print("Este es el error");
                }
//                    
                try {
                    String query = "insert into colegio_content (id, asset_id, title, alias, introtext, xreference, " +
                    		"state, catid, created, created_by, created_by_alias, modified, modified_by, checked_out,  " +
                    		"checked_out_time, publish_up, publish_down, images, urls, attribs, version, ordering, " +
                    		"metakey, metadesc, access, hits, metadata, featured, language, `fulltext`)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
//
//                    
//                    
                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    //id
                    preparedStmt.setInt(1, idMaximoArticles + 1);
                    //asset_id
                    preparedStmt.setInt(2, idMaximoAssets + 1);
                    //title
                    preparedStmt.setString(3, circularSeleccionada.getNombre());
                    //alias
                    preparedStmt.setString(4, circularSeleccionada.getNombre().toLowerCase().replaceAll(" ", "-"));
                    //Introtext
                    preparedStmt.setString(5, circularSeleccionada.getContenido());
                    //Fulltext
                    preparedStmt.setString(6, "");
                    //State
                    preparedStmt.setInt(7, 1);
                    //catid
                    preparedStmt.setInt(8, 10);
                    //created
                    java.util.Date momentoDate = new java.util.Date();
                    java.sql.Date date = new java.sql.Date(momentoDate.getTime());
                    preparedStmt.setString(9, "2014-02-22 15:33:12");
                    //Created by
                    preparedStmt.setInt(10, 448);
                    //created by alias
                    preparedStmt.setString(11,"");
                    //modified
                    java.util.Date modifiedDate = new java.util.Date();
                    java.sql.Date modifiedDateDate = new java.sql.Date(modifiedDate.getTime());
                    preparedStmt.setDate(12, modifiedDateDate);
                    //modified_by
                    preparedStmt.setInt(13, 0);
                    //checked_out
                    preparedStmt.setInt(14, 0);
                    //checked_out_time
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MONTH, 0);
                    cal.set(Calendar.YEAR, 0);
                    cal.set(Calendar.DAY_OF_YEAR, 0);
                    java.util.Date momentoDate2 = cal.getTime();
                    preparedStmt.setString(15, "0000-00-00 00:00:00");
                    //publish_up
                    preparedStmt.setDate(16, date);
                    //publish_down
                    preparedStmt.setString(17, "0000-00-00 00:00:00");
                    //images
                    preparedStmt.setString(18, "");
                    //urls
                    preparedStmt.setString(19, "");
                    //Attribs
                    preparedStmt.setString(20, "{\"show_title\":\"0\",\"link_titles\":\"0\",\"show_intro\":\"0\",\"show_category\":\"0\",\"link_category\":\"0\",\"show_parent_category\":\"0\",\"link_parent_category\":\"0\",\"show_author\":\"0\",\"link_author\":\"0\",\"show_create_date\":\"0\",\"show_modify_date\":\"0\",\"show_publish_date\":\"0\",\"show_item_navigation\":\"0\",\"show_icons\":\"0\",\"show_print_icon\":\"0\",\"show_email_icon\":\"0\",\"show_vote\":\"0\",\"show_hits\":\"0\",\"show_noauth\":\"0\",\"alternative_readmore\":\"\",\"article_layout\":\"\"}");
                    //version
                    preparedStmt.setInt(21, 1);
                    //ordering
                    preparedStmt.setInt(22, 0);
                    //Metakey
                    preparedStmt.setString(23, "");
                    //metadesc
                    preparedStmt.setString(24, "");                    
                    //Access
                    preparedStmt.setInt(25, 1);
                    //Hits
                    preparedStmt.setInt(26, 0);
                    //metadata
                    preparedStmt.setString(27, "{\"robots\":\"\",\"author\":\"\",\"rights\":\"\",\"xreference\":\"\"}");
                    //Featured
                    preparedStmt.setInt(28, 0);
                    //language
                    preparedStmt.setString(29, "*");
                    //Fulltext
                    preparedStmt.setString(30, "");
                    
                    // execute the preparedstatement
                    preparedStmt.execute();
                    con.commit();
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    System.out.print(e.getStackTrace());
                    System.out.print("Error en la segunda parte");
                }
            }


            FacesMessage msg = new FacesMessage("La circular fue subida exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

//        con.close();
//        conectar();

//
        try {
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM colegio_content WHERE catid = 10 ORDER BY created");
            //Reiniciamos la lista de las circulares
            dataListCirculares = new ArrayList<Circulares>();
            //Recorremos el query de las circulares 
            while (rs.next()) {
                //Hacemos un objeto tipo CircularesPropiedades y le agregamos las propiedades de la circular
                Circulares circularNueva = new Circulares();
                circularNueva.setNombre(rs.getObject("title").toString());
                circularNueva.setContenido(rs.getObject("introtext").toString());
                circularNueva.setId(Integer.parseInt(rs.getObject("id").toString()));
                circularNueva.setFecha((Date)rs.getObject("created"));
                dataListCirculares.add(circularNueva);
            }

        } catch (Exception e) {
            System.out.print("error");
        }

        circularSeleccionada = null;

        con.close();



        return "";
    }

    //Metodo para eliminar una circular
    public String eliminarCircular(Circulares circulares) {
        try {
            abrir();
            String query = "delete from colegio_content where id = " + circulares.getId();
            PreparedStatement preparedStmt = con.prepareStatement(query);
            // execute the preparedstatement
            preparedStmt.execute();

            query = "delete from colegio_assets where id = " + circulares.getId2();
            preparedStmt = con.prepareStatement(query);
            // execute the preparedstatement
            preparedStmt.execute();

            con.commit();
            
            dataListCirculares.remove(circulares);

            FacesMessage msg = new FacesMessage("La circular fue eliminada exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Hubo un error tratando de eliminar la circular", null);
            msg.setSeverity(msg.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }

        try {
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM colegio_content WHERE catid = 10 ORDER BY created");
            //Reiniciamos la lista de las circulares
            dataListCirculares = new ArrayList<Circulares>();
            //Recorremos el query de las circulares 
            while (rs.next()) {
                //Hacemos un objeto tipo CircularesPropiedades y le agregamos las propiedades de la circular
                Circulares circularNueva = new Circulares();
                circularNueva.setNombre(rs.getObject("title").toString());
                circularNueva.setContenido(rs.getObject("introtext").toString());
                circularNueva.setId(Integer.parseInt(rs.getObject("id").toString()));
                circularNueva.setFecha((Date)rs.getObject("created"));
                dataListCirculares.add(circularNueva);
            }

        } catch (Exception e) {
            System.out.print("error");
        }

        try {
            con.close();
        } catch (Exception e) {
        }

        return "";
    }
}
