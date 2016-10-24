/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Relacionprofesoresasignaturaperiodo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juannoguera
 */
@Stateless
public class RelacionprofesoresasignaturaperiodoFacade extends AbstractFacade<Relacionprofesoresasignaturaperiodo> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelacionprofesoresasignaturaperiodoFacade() {
        super(Relacionprofesoresasignaturaperiodo.class);
    }
    
}
