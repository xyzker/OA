package oa.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import oa.dao.IDao;
import oa.service.IService;
import oa.util.StringUtil;

@Transactional
public abstract class ServiceImpl<T> implements IService<T>{
	
	@Resource
	protected IDao<T> dao;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public ServiceImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public IDao<T> getDao() {
		return dao;
	}

	public void setDao(IDao<T> dao) {
		this.dao = dao;
	}

	public void create(T baseBean) {
		dao.create(baseBean);
	}
	

	public void delete(T baseBean) {
		dao.delete(baseBean);
	}

	public T get(int id) {
		return dao.get(clazz, id);
	}
	
	public T load(int id) {
		return dao.load(clazz, id);
	}

	public int getTotalCount(String hql, Object... params) {
		return dao.getTotalCount(hql, params);
	}
	
	public List<T> list(String hql, Object... params){
		return dao.list(hql, params);
	}
	
	public T uniqueResult(String hql, Object... params){		//查询单个实体
		return dao.uniqueResult(hql, params);
	}

	public List<T> list(String hql, int firstResult, int maxSize,
			Object... params) {
		return dao.list(hql, firstResult, maxSize, params);
	}

	public List<T> list(String hql) {
		return dao.list(hql);
	}
	
	@SuppressWarnings("all")
	public List<T> getByIds(Integer[] ids){
		if(ids == null || ids.length == 0){
			return null;
		} else {
			return dao.list("from " + clazz.getSimpleName() + " where id in (" + StringUtil.arr2Str(ids) +")");
		}
	}

	public void saveOrUpdate(T baseBean) {
		dao.saveOrUpdate(baseBean);
	}
	
	public void update(T baseBean) {
		dao.update(baseBean);
	}
	
	public void clearSession(){
		dao.clearSession();
	}
	
	public void evict(T baseBean){
		dao.evict(baseBean);
	}
	
	public List<T> findAllEntities(){
		String hql = "from " + clazz.getSimpleName();
		return dao.list(hql);
	}
	
	public void deleteById(int id){
		String hql = "delete from " + clazz.getSimpleName() + " where id = :id";
		dao.createQuery(hql).setParameter("id", id).executeUpdate();
	}
	
	public void executeSQL(String sql, Object...params){
		dao.executeSQL(sql, params);
	}
	
	@SuppressWarnings("unchecked")
	public List executeSQLQuery(Class clazz, String sql, Object...params){
		return dao.executeSQLQuery(clazz, sql, params);
	}
}
