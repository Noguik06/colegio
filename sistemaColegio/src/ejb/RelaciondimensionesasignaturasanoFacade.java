/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Relaciondimensionesasignaturasano;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RelaciondimensionesasignaturasanoFacade extends AbstractFacade<Relaciondimensionesasignaturasano> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelaciondimensionesasignaturasanoFacade() {
        super(Relaciondimensionesasignaturasano.class);
    }
    
}
