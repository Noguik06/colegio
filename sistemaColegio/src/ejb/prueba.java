package ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Usuarios;

@Stateless
@Local
public class prueba implements UsuariosFacadeLocal{

    @PersistenceContext(unitName = "sistemaColegioPU")
    private EntityManager em;
    
	public List listCurrencies() {
		if (em == null)
			System.out.println("em is null!!!");

		Query q = em.createQuery("SELECT crt FROM Usuarios crt");

		List currList = q.getResultList();

		return currList;
	}
	
	@Override
	public void create(Usuarios usuarios) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Usuarios usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Usuarios usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuarios find(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuarios> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuarios> findRange(int[] range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
