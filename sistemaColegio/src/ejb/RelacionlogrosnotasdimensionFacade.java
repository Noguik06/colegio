/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Relacionlogrosnotasdimension;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RelacionlogrosnotasdimensionFacade extends AbstractFacade<Relacionlogrosnotasdimension> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelacionlogrosnotasdimensionFacade() {
        super(Relacionlogrosnotasdimension.class);
    }
    
}
