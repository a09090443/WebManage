package com.localhost.base.dao.impl.hibernate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.base.dao.BaseHibernateDAO;

@Transactional
public abstract class BaseHibernateDAOImpl<T extends Object> implements BaseHibernateDAO<T> {
	private Class<T> clazz;
	private String entityName;

	@Autowired
	SessionFactory sessionFactory;

	public void setClazz(final Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	@SuppressWarnings("unchecked")
	public T getById(final Long id) {
		if (id != null)
			return (T) getCurrentSession().get(clazz, id);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getCurrentSession().createCriteria(clazz).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(int start, int resultSize) {
		return getCurrentSession().createCriteria(clazz).setFirstResult(start).setMaxResults(resultSize).list();
	}

	@Transactional(readOnly = false)
	public void save(final T entity) {
		// getCurrentSession().persist(entity);
		getCurrentSession().save(entity);
	}

	@Transactional(readOnly = false)
	public void update(final T entity) {
		getCurrentSession().merge(entity);
	}

	@Transactional(readOnly = false)
	public void update(final T entity, Map<String, Object> argMap) throws Exception {
		entityName = entity.getClass().getSimpleName();
		String hqlStr;
		hqlStr = "UPDATE " + entityName + " " + entityName + " SET ";
		hqlStr += this.genHql(entity, argMap, "");
		getCurrentSession().createQuery(hqlStr.toString()).setProperties(argMap).executeUpdate();
	}

	@Transactional(readOnly = false)
	public void delete(final T entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryHql(String hql, Map<String, Object> argMap) {
		return getCurrentSession().createQuery(hql).setProperties(argMap).list();
	}

	@Transactional(readOnly = false)
	public void executeHql(String hql, Map<String, Object> argMap) {
		try {
			getCurrentSession().createQuery(hql).setProperties(argMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private String genHql(Object entity, Map<String, Object> argMap, String hqlStr) throws Exception {
		String whereStr = "";
		BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
		Integer entityLen = entity.getClass().getSimpleName().length();
		Boolean compareEntityName = entity.getClass().getSimpleName().substring(entityLen - 2, entityLen).equals("Id");
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String propName = descriptors[i].getName();
			Class<?> propType = descriptors[i].getPropertyType();
			Object value = PropertyUtils.getProperty(entity, propName);
			if ((!propName.equals("class") && null != value) || propName.equals("id")) {
				if (propType.getSimpleName().equals("Integer") || propType.getSimpleName().equals("int")) {
					if (compareEntityName) {
						hqlStr += entityName + ".id." + propName + "=" + value + "";
					} else {
						hqlStr += entityName + "." + propName + "=" + value + "";
					}
				} else if (propType.getSimpleName().equals("String")) {
					if (compareEntityName) {
						hqlStr += entityName + ".id." + propName + "=" + "'" + value + "'";
					} else {
						hqlStr += entityName + "." + propName + "=" + "'" + value + "'";
					}
				} else if (propName.equals("id")) {
					if(null != value){
						hqlStr = this.genHql(value, null, hqlStr);
					}
					Class<?> c = null;
					c = Class.forName(propType.getName());
					Field[] fields = c.getDeclaredFields();
					for (Field f : fields) {
						f.setAccessible(true);
					}
					for (Field f : fields) {
						String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);
						if (null != argMap.get(field)) {
							whereStr += entityName + ".id." + field + "=:" + field + " AND ";
						}
					}
				}
				if(null != value){
					hqlStr += ",";
				}
				if (null != argMap && null != argMap.get(propName)) {
					whereStr += entityName + "." + propName + "=:" + propName + " AND ";
				}
			}
		}
		if (!whereStr.isEmpty()) {
			whereStr = " WHERE " + whereStr.substring(0, whereStr.length() - 5);
		}
		hqlStr = hqlStr.substring(0, hqlStr.length() - 1) + whereStr;
		return hqlStr;
	}
}