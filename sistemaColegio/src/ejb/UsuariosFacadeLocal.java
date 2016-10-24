/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;

import entities.Usuarios;

/**
 *
 * @author juannoguera
 */
@Local
public interface UsuariosFacadeLocal {

    void create(Usuarios usuarios);

    void edit(Usuarios usuario);

    void remove(Usuarios usuario);

    Usuarios find(Object id);

    List<Usuarios> findAll();

    List<Usuarios> findRange(int[] range);

    int count();

	EntityManager getEntityManager();
    
}
