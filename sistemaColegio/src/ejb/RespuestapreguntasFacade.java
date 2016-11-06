/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Respuestapreguntas;
import entities.Segmentopreguntas;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RespuestapreguntasFacade extends AbstractFacade<Respuestapreguntas> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestapreguntasFacade() {
        super(Respuestapreguntas.class);
    }
    
}
