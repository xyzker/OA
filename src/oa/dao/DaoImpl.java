package oa.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImpl<T>	implements IDao<T> {

	@Resource
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	public void create(T baseBean) {
		getCurrentSession().save(baseBean);
		getCurrentSession().flush();
	}

	public Query createQuery(String hql) {
		return getCurrentSession().createQuery(hql);
	}

	public void delete(T baseBean) {
		getCurrentSession().delete(baseBean);
		getCurrentSession().flush();
	}

	@SuppressWarnings("unchecked")
	public T get(Class<T> clazz, int id) {
		return (T)getCurrentSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public T load(Class<T> clazz, int id) {
		return (T)getCurrentSession().load(clazz, id);
	}

	public int getTotalCount(String hql, Object... params) {		//查询总数
		List<T> list = this.list(hql, params);
		return list.size();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(String hql, Object... params){
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		List<T> list = (List<T>)query.list();
		return list;
	}
	
	public void executeUpdateByHQL(String hql, Object... params){
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public T uniqueResult(String hql, Object... params){
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		T result = (T) query.uniqueResult();
		return result;
	}
	
	public Object uniqueResultObject(String hql, Object... params){
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		Object result = query.uniqueResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String hql, int firstResult, int maxResults,
			Object... params) {
		Query query = getCurrentSession().createQuery(hql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		List<T> list = (List<T>)query.setFirstResult(firstResult).
						setMaxResults(maxResults).list();
		return list;
	}

	public void saveOrUpdate(T baseBean) {
		getCurrentSession().saveOrUpdate(baseBean);
		getCurrentSession().flush();
	}
	
	public void update(T baseBean) {
		getCurrentSession().update(baseBean);
		getCurrentSession().flush();
	}

	public void clearSession() {
		getCurrentSession().clear();
	}

	public void evict(T baseBean) {
		getCurrentSession().evict(baseBean);
	}

	public void executeSQL(String sql, Object... params) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object... params) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		//添加实体类
		if(clazz != null){
			query.addEntity(clazz);
		}
		for(int i=0; i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

}
