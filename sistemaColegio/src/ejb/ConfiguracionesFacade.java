package ejb;

import java.util.List;

import entities.Configuraciones;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class ConfiguracionesFacade extends AbstractFacade<Configuraciones> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;
    
    private static final String queryPropiedad= "SELECT C FROM Configuraciones C where C.propiedad = ";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionesFacade() {
        super(Configuraciones.class);
    }
    
    public List<Configuraciones> obtenerConfiguraciones(String propiedad){
    	return getEntityManager().createQuery(queryPropiedad + "'"+propiedad+"'").setMaxResults(20).getResultList();
    }
    
}
