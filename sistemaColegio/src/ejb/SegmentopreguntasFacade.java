/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Segmentopreguntas;

/**
 *
 * @author juannoguera
 */
@Stateless
public class SegmentopreguntasFacade extends AbstractFacade<Segmentopreguntas> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegmentopreguntasFacade() {
        super(Segmentopreguntas.class);
    }
    
}
