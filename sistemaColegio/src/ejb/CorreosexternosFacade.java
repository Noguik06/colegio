/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Correosexternos;

/**
 *
 * @author juannoguera
 */
@Stateless
public class CorreosexternosFacade extends AbstractFacade<Correosexternos> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CorreosexternosFacade() {
        super(Correosexternos.class);
    }
    
}
