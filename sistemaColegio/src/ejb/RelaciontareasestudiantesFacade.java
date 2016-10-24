/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Relaciontareasestudiantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author proveedor_jnoguera
 */
@Stateless
public class RelaciontareasestudiantesFacade extends AbstractFacade<Relaciontareasestudiantes> {
    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelaciontareasestudiantesFacade() {
        super(Relaciontareasestudiantes.class);
    }
    
}
