/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesAyuda;

import java.io.IOException;

import javax.enterprise.inject.spi.Bean;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsf.usuarios.Sesiones;

/**
 *
 * @author juannoguera
 */
public class ManejoSesiones implements Filter {

	private FilterConfig filterConfig;
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         String url = ((HttpServletRequest)request).getRequestURL().toString();
//         System.out.print(url);
//         String queryString = ((HttpServletRequest)request).getQueryString();
//         System.out.print(queryString);
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//    	String url = ((HttpServletRequest)request).getRequestURI();
    	RequestDispatcher view = request.getRequestDispatcher(url);
    	
//    	view.forward(request, response);
    	
    	if(!url.equals("/sistemaColegio/faces/interfaces/tareas/tareasAlumnos/tareasAlumnos.xhtml")){
	        if (((HttpServletRequest) request).getSession().getAttribute("USUARIO") == null) {
	            //HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	            //String contextPath = ((HttpServletRequest) request).getContextPath();
	            ((HttpServletResponse) response).sendRedirect("/sistemaColegio/faces/index.xhtml");
	            
//	            String toReplace = requestURI.substring(requestURI.indexOf("/Dir_My_App"), requestURI.lastIndexOf("/") + 1);
//	            String newURI = requestURI.replace(toReplace, "?Contact_Id=");
	            request.getRequestDispatcher("/sistemaColegio/faces/index.xhtml");	            
	        }else{
	        	Object bean =  (Object) ((HttpServletRequest) request).getSession().getAttribute("sesiones");
	        	Sesiones sesssion = (Sesiones) bean;
	        	boolean banderaPermisos = false;
	        	for(Object[] o:sesssion.getDataListPermisos()){
	        		if(o[1].toString().equals(url)){
	        			banderaPermisos = true;
	        			break;
	        		}
	        	}
	        	if(!banderaPermisos){
	        		request.getRequestDispatcher("/sistemaColegio/faces/interfaces/usuarios/miUsuario/miUsuario.xhtml");
//	        		((HttpServletResponse) response).sendRedirect("/sistemaColegio/faces/interfaces/usuarios/miUsuario/miUsuario.xhtml");
	        	}
	        	
	        	if(!sesssion.isEstadoEvalucionInsttitucional()
	        			&& !url.equals("/sistemaColegio/faces/interfaces/usuarios/estudiantes/evaluacionInstitucional.xhtml")
	        			&& url.contains("xhtml")){
	        		((HttpServletResponse) response).sendRedirect("/sistemaColegio/faces/interfaces/usuarios/estudiantes/evaluacionInstitucional.xhtml");
	        		request.getRequestDispatcher("/sistemaColegio/faces/interfaces/usuarios/estudiantes/evaluacionInstitucional.xhtml");
	        	}
	        }
    	}
    	chain.doFilter(request, response);
    	
    	 /*HttpServletRequest req = (HttpServletRequest) request;
         HttpServletResponse res = (HttpServletResponse) response;

         if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
             res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
             res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
             res.setDateHeader("Expires", 0); // Proxies.
         }

         chain.doFilter(request, response);*/
    }

    @Override
    public void destroy() {
    	filterConfig = null;
    }

}
