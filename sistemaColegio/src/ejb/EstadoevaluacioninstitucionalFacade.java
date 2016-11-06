/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Abonosegresos;
import entities.Estadoevaluacioninstitucional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class EstadoevaluacioninstitucionalFacade extends AbstractFacade<Estadoevaluacioninstitucional> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoevaluacioninstitucionalFacade() {
        super(Estadoevaluacioninstitucional.class);
    }
    
}
