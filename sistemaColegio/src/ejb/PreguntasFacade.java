/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Preguntas;

/**
 *
 * @author juannoguera
 */
@Stateless
public class PreguntasFacade extends AbstractFacade<Preguntas> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntasFacade() {
        super(Preguntas.class);
    }
    
}
