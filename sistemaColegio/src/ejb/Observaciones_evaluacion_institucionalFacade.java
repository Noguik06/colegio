/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Observaciones_evaluacion_institucional;
import entities.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class Observaciones_evaluacion_institucionalFacade extends AbstractFacade<Observaciones_evaluacion_institucional> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Observaciones_evaluacion_institucionalFacade() {
        super(Observaciones_evaluacion_institucional.class);
    }
    
}
