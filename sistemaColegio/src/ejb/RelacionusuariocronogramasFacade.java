/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Relacionusuariocronogramas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RelacionusuariocronogramasFacade extends AbstractFacade<Relacionusuariocronogramas> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelacionusuariocronogramasFacade() {
        super(Relacionusuariocronogramas.class);
    }
    
}
