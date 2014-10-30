package oa.service;

import java.util.List;

public interface IService<T> {
	public T get(int id);
	public T load(int id);
	public void create(T baseBean);
	public void update(T baseBean);
	public void saveOrUpdate(T baseBean);
	public void delete(T baseBean);
	public List<T> list(String hql);		//查询实体
	public List<T> list(String hql, Object... params);
	public List<T> getByIds(Integer[] ids);
	public T uniqueResult(String hql, Object... params);    //查询单个实体
	public int getTotalCount(String hql, Object... params);		//查询页数
	public List<T> list(String hql, int firstResult, int maxSize, 
						Object... params);		//分页实体
	public void clearSession();
	public void evict(T baseBean);				//清除某对象的缓存
	public List<T> findAllEntities();			//查询所有实体
	public void deleteById(int id);
	public void executeSQL(String sql, Object...params);	//执行原生的SQL语句
	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object...params);	//执行原生的SQL查询
}
