package oa.dao;

import java.util.List;

import org.hibernate.Query;

public interface IDao<T>{		//DAO接口，使用泛型定义，可操作所有实体
	public T get(Class<T> clazz, int id);	//根据id查找实体
	public T load(Class<T> clazz, int id);
	public void create(T baseBean);		//创建实体
	public void saveOrUpdate(T baseBean);		//保存实体
	public void update(T baseBean);			//更新实体
	public void delete(T baseBean);		//删除实体
	public List<T> list(String hql, Object... params);		//查询实体,带参数
	public T uniqueResult(String hql, Object... params);    //查询单个实体
	public Object uniqueResultObject(String hql, Object... params);   //查询单个对象
	public int getTotalCount(String hql, Object... params);		//查询总数，可以不传入params
	public List<T> list(String hql, int firstResult, int maxResults, 
					Object... params);			//查询某页实体
	public Query createQuery(String hql);		//创建Query对象
	public void executeUpdateByHQL(String hql, Object... params);			//依据HQL语句作更新
	public void clearSession();					//清除所有缓存
	public void evict(T baseBean);				//清除某对象的缓存
	public void executeSQL(String sql, Object...params);	//执行原生的SQL语句
	//执行原生的SQL查询(可以指定是否封装成实体)
	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object...params);	
}
