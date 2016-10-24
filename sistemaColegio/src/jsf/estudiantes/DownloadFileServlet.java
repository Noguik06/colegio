package jsf.estudiantes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJB;
//import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ejb.TareasFacade;
import entities.Tareas;

//@WebServlet("/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096; 
	
    @EJB
    TareasFacade tareasFacade;
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		try{
			String paramName = "idtarea";
			String paramValue = request.getParameter(paramName);
			String query = "select  nombre, archivo, descripcion, fecha, fin, "
					+ "case when archivo is not null "
					+ "then true else false end adjunto, idtareas from tareas "
					+ "where idtareas = " + paramValue;
			
			Tareas tareas = tareasFacade.find(new Long(paramValue));
			
			if(tareas == null || tareas.getArchivo() == null){
				response.setContentType("text/html");
//				response.setCharacterEncoding("");
			    PrintWriter out = response.getWriter();
			    out.println("<html>");
			    out.println("<head>");
			    out.println("<title>Hola</title>");
			    out.println("</head>");
			    out.println("<body bgcolor=\"white\">");
			    out.println("<p>Esta tarea no existe o no tiene nigun adjunto </p>");
			    out.println("</body>");
			    out.println("</html>");
			}else{
				String fileName = URLDecoder.decode(tareas.getNombre(), "UTF-8");
				InputStream inputStream = new ByteArrayInputStream((byte[]) tareas.getArchivo());
				int fileLength = inputStream.available();
		        System.out.println("fileLength = " + fileLength);
		        ServletContext context = getServletContext();
		        // sets MIME type for the file download
		        String mimeType = context.getMimeType(fileName);
		        if (mimeType == null) {        
		            mimeType = "application/octet-stream";
		        }              
		        // set content properties and header attributes for the response
		        response.setContentType(mimeType);
		        response.setCharacterEncoding("UTF-8");
		        response.setContentLength(fileLength);
//		        String headerKey = "Content-Disposition";
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//		        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
//		        response.setHeader(headerKey, headerValue);
		        // writes the file to the client
		        OutputStream outStream = response.getOutputStream();
		        byte[] buffer = new byte[BUFFER_SIZE];
		        int bytesRead = -1;
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        inputStream.close();
		        outStream.close();
		        response.setContentType("text/html");
			    PrintWriter out = response.getWriter();
			    out.println("<html>");
			    out.println("<head>");
			    out.println("<title>Hola</title>");
			    out.println("</head>");
			    out.println("<body bgcolor=\"white\">");
			    out.println("<p>Teto </p>");
			    out.println("</body>");
			    out.println("</html>");
			}
		}catch(Exception e){
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    out.println("<html>");
		    out.println("<head>");
		    out.println("<title>Hola</title>");
		    out.println("</head>");
		    out.println("<body bgcolor=\"white\">");
		    out.println("<p>Error descargando la tarea </p>");
		    out.println("</body>");
		    out.println("</html>");
		}
		
		
		
    }
}
