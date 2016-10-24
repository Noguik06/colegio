/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Votaciones;

/**
 *
 * @author juannoguera
 */
@Stateless
public class VotacionesFacade extends AbstractFacade<Votaciones> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotacionesFacade() {
        super(Votaciones.class);
    }
    
}
