package oa.service;

import java.util.List;

public interface IService<T> {
	public T get(int id);
	public T load(int id);
	public void create(T baseBean);
	public void update(T baseBean);
	public void saveOrUpdate(T baseBean);
	public void delete(T baseBean);
	public List<T> list(String hql);		//��ѯʵ��
	public List<T> list(String hql, Object... params);
	public T uniqueResult(String hql, Object... params);    //��ѯ����ʵ��
	public int getTotalCount(String hql, Object... params);		//��ѯҳ��
	public List<T> list(String hql, int firstResult, int maxSize, 
						Object... params);		//��ҳʵ��
	public void clearSession();
	public void evict(T baseBean);				//���ĳ����Ļ���
	public List<T> findAllEntities();			//��ѯ����ʵ��
	public void deleteById(int id);
	public void executeSQL(String sql, Object...params);	//ִ��ԭ����SQL���
	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object...params);	//ִ��ԭ����SQL��ѯ
}
