/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Relacionrolesinterfaz;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RelacionrolesinterfazFacade extends AbstractFacade<Relacionrolesinterfaz> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelacionrolesinterfazFacade() {
        super(Relacionrolesinterfaz.class);
    }
    
}
