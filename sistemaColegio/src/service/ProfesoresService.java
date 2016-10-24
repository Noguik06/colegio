package service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ejb.ProfesoresFacade;
import entities.Profesores;
 
 
@ManagedBean(name="profesoresService", eager = true)
@ApplicationScoped
public class ProfesoresService {
     
	@EJB
	ProfesoresFacade profesoresFacade;
	
    private List<Profesores> themes;
     
    @PostConstruct
    public void init() {
    	themes = profesoresFacade.findAll();
        
    }
     
    public List<Profesores> getThemes() {
    	themes = profesoresFacade.findAll();
        return themes;
    } 
}