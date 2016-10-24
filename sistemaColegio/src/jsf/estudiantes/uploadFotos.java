/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.estudiantes;

import ejb.EstudiantesFacade;
import ejb.UsuariosFacade;
import entities.Estudiantes;
import entities.Usuarios;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author juannoguera
 */
@WebServlet(name = "uploadFotos", urlPatterns = {"/uploadFotos"})
@MultipartConfig
public class uploadFotos extends HttpServlet {

    @EJB
    private UsuariosFacade usuariosFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String id2 = request.getParameter("id2");


        if (id.equals("0") && id2 != null) {
            id = id2;
        }



        final Part filePart = request.getPart("file");
        try{
        if ((id2 != null || id != null) && (!id2.equals("0") || !id2.equals("0")) && filePart.getSize() > 0) {
            BufferedOutputStream output = null;
            InputStream filecontent = null;
            try {
                if (filePart.getSize() > 0) {
                    System.out.print(id + " id del producto");
                    filecontent = filePart.getInputStream();
                    long size = filePart.getSize(); //... el tama√±o
                    byte[] buffer = new byte[(int) size];
                    filecontent.read(buffer); //.. leo el 

                    Usuarios usuarios = usuariosFacade.find(new Long(id));
                    usuarios.setFoto(buffer);
                    usuariosFacade.edit(usuarios);
                    out.write("La foto se ha guardado satisfactoriamente");
                } else {
//                out.write("No se ha guardado ninguna imagen");
                }
            } catch (FileNotFoundException fne) {
                out.write("La imagen no se pudo guardar");
            } finally {
                out.close();
            }

        }}
        catch(Exception e){
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
