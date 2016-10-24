/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Asignaturas;
import entities.Asignaturasgrupales;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class AsignaturasgrupalesFacade extends AbstractFacade<Asignaturasgrupales> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturasgrupalesFacade() {
        super(Asignaturasgrupales.class);
    }
    
}
