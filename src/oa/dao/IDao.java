package oa.dao;

import java.util.List;

import org.hibernate.Query;

public interface IDao<T>{		//DAO�ӿڣ�ʹ�÷��Ͷ��壬�ɲ�������ʵ��
	public T get(Class<T> clazz, int id);	//����id����ʵ��
	public T load(Class<T> clazz, int id);
	public void create(T baseBean);		//����ʵ��
	public void saveOrUpdate(T baseBean);		//����ʵ��
	public void update(T baseBean);			//����ʵ��
	public void delete(T baseBean);		//ɾ��ʵ��
	public List<T> list(String hql, Object... params);		//��ѯʵ��,������
	public T uniqueResult(String hql, Object... params);    //��ѯ����ʵ��
	public Object uniqueResultObject(String hql, Object... params);   //��ѯ��������
	public int getTotalCount(String hql, Object... params);		//��ѯ���������Բ�����params
	public List<T> list(String hql, int firstResult, int maxResults, 
					Object... params);			//��ѯĳҳʵ��
	public Query createQuery(String hql);		//����Query����
	public void executeUpdateByHQL(String hql, Object... params);			//����HQL���������
	public void clearSession();					//������л���
	public void evict(T baseBean);				//���ĳ����Ļ���
	public void executeSQL(String sql, Object...params);	//ִ��ԭ����SQL���
	//ִ��ԭ����SQL��ѯ(����ָ���Ƿ��װ��ʵ��)
	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object...params);	
}
