/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.estudiantes;

import ejb.UsuariosFacade;
import entities.Usuarios;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author juannoguera
 */
@WebServlet(name = "CargarFotosProductosServlet", urlPatterns = {"/CargarFotosProductosServlet"})
public class CargarFotosProductosServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    // Actions ------------------------------------------------------------------------------------
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
    @EJB
    private UsuariosFacade usuariosFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        } finally {
            out.close();
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
//        PrintWriter out = response.getWriter();
//        OutputStream out2 = response.getOutputStream();
        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Get ID from request.

//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
            String imageId = request.getParameter("id");

            if (imageId != null && imageId != "") {
                Usuarios usuario = usuariosFacade.find(new Long(imageId));

//            // Check if ID is supplied to the request.
                if (imageId == null || usuario == null || usuario.getFoto() == null) {
                    // Do your thing if the ID is not supplied to the request.
                    // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.

                    return;


                }


                InputStream filecontent = null;

                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

                response.reset();
                response.setBufferSize(DEFAULT_BUFFER_SIZE);
                response.setHeader("Content-Length", String.valueOf(usuario.getFoto().length));
                response.setHeader("Content-Disposition", "inline; filename=\"" + usuario.getNombres() + "\"");
                output.write(usuario.getFoto());
            }

        } finally {
            close(output);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
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
