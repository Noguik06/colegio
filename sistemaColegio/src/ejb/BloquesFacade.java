/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Bloques;

/**
 *
 * @author juannoguera
 */
@Stateless
public class BloquesFacade extends AbstractFacade<Bloques> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BloquesFacade() {
        super(Bloques.class);
    }
    
}
