/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author juannoguera
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    private String entityName;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.entityName = entityClass.getName();
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
 
    
    
    public List<T> findByParamater(String query, String parametro, String valor) {
        return getEntityManager().createNamedQuery(query).setParameter(parametro, valor).getResultList();
    }

    public List<T> findByParamater2(String query, String[] parametro, String[] valor) {
        return getEntityManager().createNamedQuery(query).setParameter(parametro[0], valor[0]).setParameter(parametro[1], valor[1]).getResultList();
    }

    public List<T> findByParamater1(String query, String parametro, Integer valor) {
        return getEntityManager().createNamedQuery(query).setParameter(parametro, valor).getResultList();
    }

    //Query para buscar parametros con con long
    public List<T> findByParamaterLong(String query, String parametro, Long valor) {
        getEntityManager().flush();
        return getEntityManager().createNamedQuery(query).setParameter(parametro, valor).getResultList();
    }

    public List<T> findLike(String query, String tabla, String id) {
        return getEntityManager().createQuery("select o from " + tabla + " o where o." + id + " like '%" + query + "%'").getResultList();
    }

    public List<T> findByLike(String query) {
        return getEntityManager().createQuery(query).setMaxResults(20).getResultList();
    }

    public void metodo(String busqueda) {
        Query query = getEntityManager().createQuery(busqueda);
        query.executeUpdate();
    }

    public List<T> findByLikeAll(String query) {
//        _refresh = true;
//        doRefresh();
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<T> findByLikeAll2(String query) {
        _refresh = true;
        doRefresh();
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Object[]> findo(String query) {
        List<Object[]> lista = getEntityManager().createNativeQuery(query).setMaxResults(100).getResultList();
        return lista;
    }
    
    public List<Object[]> findoAll(String query) {
        List<Object[]> lista = getEntityManager().createNativeQuery(query).getResultList();
        return lista;

    }

    public List<Object[]> findoBusquedaVenta(String query) {
        List<Object[]> lista = getEntityManager().createNativeQuery(query).getResultList();
        return lista;

    }
    
    //Metodo para retornar varios objetos de la busqueda
    public List<Object[]> findLista(String query) {
        List<Object[]> lista = getEntityManager().createQuery(query).getResultList();
        return lista;

    }

    //Query para buscar los proveedores en la entidad Pv_Tiene
    public List<T> buscarEnPvTiene(Integer var) {
        return getEntityManager().createNamedQuery("pvTieneBuscaProveedor").setParameter("id", var).getResultList();
    }

    //Query general para buscar un atributo y devolver una lista de objetos: INTEGERS
    public List<T> buscarGeneralQuery(String query, String parametro, Integer valor) {
        return getEntityManager().createNamedQuery(query).setParameter(parametro, valor).getResultList();
    }

    //Queries PedidoProveedor
    //Query general para buscar un atributo y devolver una lista de objetos: INTEGERS
    public List<T> buscarGeneralQuery(String entidad, String parametro, Long valor) {
        return getEntityManager().createNamedQuery(entidad).setParameter(parametro, valor).getResultList();
    }

    //Query general para buscar un atributo y devolver una lista de objetos: STRINGS
    public List<T> buscarGeneralQueryString(String entidad, String parametro, String valor) {
        return getEntityManager().createNamedQuery(entidad).setParameter(parametro, valor).getResultList();
    }

    public List<T> queryGeneralReturnVoid(String entidad) {
        return getEntityManager().createNamedQuery(entidad).getResultList();
    }

    public Long buscaMaxId(String entidad) {
        return (Long) getEntityManager().createNamedQuery(entidad).getSingleResult();
    }

    public List<Object[]> findByNative(String query) {
        return getEntityManager().createNativeQuery(query).getResultList();
    }

    public List<Object[]> findByNativeProductoProveedor(String query) {
        List<Object[]> lista = getEntityManager().createNativeQuery(query).setMaxResults(10).getResultList();
        return lista;
    }

    public List<T> findByNativePrueba(String query) {
        List<T> lista = getEntityManager().createNativeQuery(query).getResultList();
        return lista;
    }

    public List<T> getQueryRelacionPP(String query) {
        return getEntityManager().createQuery(query).setMaxResults(20).getResultList();
    }

    //Metodo para buscar el maximo id de una entidad y que devuelva el objeto como resultado
    public T findByMaxTupla(String query) {
        return (T) getEntityManager().createNamedQuery(query).getSingleResult();
    }

    //Metodo que pasa dos parametros de busqueda al query y retorna un objeto
    public List<T> findByTwoParamet(String nameQuery, String parametroProductoPedido, Long idProdcutoPedido, String parametroRelacionProveedorProducto, Long idRelacionProveedorProducto) {
        return getEntityManager().createNamedQuery(nameQuery).setParameter(parametroProductoPedido, idProdcutoPedido).setParameter(parametroRelacionProveedorProducto, idRelacionProveedorProducto).getResultList();
    }

    public List<T> findByQueryLista(String nameQuery) {
        return getEntityManager().createNamedQuery(nameQuery).getResultList();
    }

    public void prueba(T entity) {
        getEntityManager().refresh(entity);
    }

    //Retornar valor tipo double
    public Double retornarValor(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(getEntityManager().createQuery(query).getSingleResult().toString());
    }
    
    public Object retornarValorObject(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return 0.0;
        }
        
        if(getEntityManager().createQuery(query).getSingleResult() == null){
            return 0.0;
        }
        
        return getEntityManager().createQuery(query).getSingleResult();
    }
    
    public Object retornarObject(String query) {
    	return getEntityManager().createQuery(query).getSingleResult();
    }
    
    
    
    
    public Object retornarValorObjectNativo(String query) {
        Object tmp = getEntityManager().createNativeQuery(query).getSingleResult();
        return tmp != null ? tmp : new Long(0);
    }

    public Integer retornarValorInteger(String query) {
        if (getEntityManager().createQuery(query).getSingleResult() == null) {
            return 0;
        }
        return Integer.parseInt(getEntityManager().createQuery(query).getSingleResult().toString());
    }

    public Integer retornarValorIntegerNuevo(String query) {
        if (getEntityManager().createQuery(query).getSingleResult() == null || (Integer) getEntityManager().createQuery(query).getSingleResult() == 0) {
            return 0;
        }
        return Integer.parseInt(getEntityManager().createQuery(query).getSingleResult().toString());
    }

    public List<T> buscarByLongLongInt(String nameQuery, String parametro1, Integer valor1, String parametro2, Long valor2, String parametro3, Long valor3) {
        return getEntityManager().createNamedQuery(nameQuery).setParameter(parametro1, valor1).setParameter(parametro2, valor2).setParameter(parametro3, valor3).getResultList();
    }

    public List<T> buscarBYLong_Int(String nameQuery, String parametro1, Long valor1, String parametro2, Integer valor2) {
        return getEntityManager().createNamedQuery(nameQuery).setParameter(parametro1, valor1).setParameter(parametro2, valor2).getResultList();
    }

    public Long buscarByLongReturnLong(String nameQuery, String parametro, Long valor) {
        return (Long) getEntityManager().createNamedQuery(nameQuery).setParameter(parametro, valor).getSingleResult();
    }

    public List<T> buscarGeneralQueryMAxUno(String entidad, String parametro, Long valor) {
        return getEntityManager().createNamedQuery(entidad).setParameter(parametro, valor).setMaxResults(1).getResultList();
    }

    public Long buscarByNameQueryReturnLong(String nameQuery) {
        return (Long) getEntityManager().createNamedQuery(nameQuery).getSingleResult();
    }

    public List<T> burcarByQueryTotal(String query) {
        return getEntityManager().createQuery(query).getResultList();
    }

     public Long buScarByQueryTotalReturnLong(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return new Long(0);
        }
        return (Long) getEntityManager().createQuery(query).getSingleResult();
    }

    public List<T> buscarByQueryTotalMaxResult(String query, int maxResult) {
        return getEntityManager().createQuery(query).setMaxResults(maxResult).getResultList();
    }

    public Double buscarByQueryTotalReturnDouble(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return null;
        }
        return (Double) getEntityManager().createQuery(query).getSingleResult();
    }

    public List<Object[]> buscarByQueryReturnObject(String query) {
        List<Object[]> lista = getEntityManager().createQuery(query).getResultList();
        return lista;
    }
    
    public List<Object> buscarByQueryListObject(String query) {
        List<Object> lista = getEntityManager().createQuery(query).getResultList();
        return lista;
    }

    public List<Integer> findByNativeReturnInt(String query) {
        return getEntityManager().createNativeQuery(query).getResultList();
    }

    public Integer buScarByQueryTotalReturnInteger(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return 0;
        }
        return (Integer) getEntityManager().createQuery(query).getSingleResult();
    }

    public Date buscarByQueryTotalReturnDate(String query) {
        if (getEntityManager().createQuery(query).getResultList().isEmpty()) {
            return null;
        }
        return (Date) getEntityManager().createQuery(query).getSingleResult();
    }
    
    private static boolean _refresh = true;
    
    public static void refresh(){ 
        _refresh = true;
    }
    
    public Object findByObjectNative(String query) {
    	if (getEntityManager().createNativeQuery(query).getResultList().isEmpty()) {
    		return null;
    	}
        return getEntityManager().createNativeQuery(query).getSingleResult();
    }
    
    private void doRefresh(){
        if(_refresh){
            EntityManager em = getEntityManager();
            em.getEntityManagerFactory().getCache().evict(entityClass);
            em.flush();
//            
//            for(javax.persistence.metamodel.EntityType<?> entity : em.getMetamodel().getEntities()){
//                if(entity.getName().contains(entityName)){
//                    try{
//                        em.refresh(entity);
//                    }catch(IllegalArgumentException e){
//                        System.out.print("Prueba");
//                    }
//                }
//            }
            _refresh = false;
        }
    }
}
