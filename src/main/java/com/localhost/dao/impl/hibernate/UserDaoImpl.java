package com.localhost.dao.impl.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.localhost.base.dao.impl.hibernate.BaseHibernateDAOImpl;
import com.localhost.dao.IUserDao;
import com.localhost.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseHibernateDAOImpl<User> implements IUserDao {

	public UserDaoImpl() {
		this.setClazz(User.class);
	}

	public String findMaxId() {
		String hql = "FROM User U ORDER BY U.id.userId DESC";
		List<User> userList = this.queryHql(hql, null);
		return CollectionUtils.isEmpty(this.queryHql(hql, null)) ? "0" : userList.get(0).getId().getUserId();
	}

	public void deleteById(Long entityId) {
		// TODO Auto-generated method stub

	}

	public User findUser(User user) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", user.getId().getUserId());
		argMap.put("account", user.getId().getAccount());
		String hql = " FROM User U WHERE U.id.userId = :userId AND U.id.account = :account";
		List<User> userList = this.queryHql(hql, argMap);
		return (User) (CollectionUtils.isEmpty(userList) ? null : userList.get(0));
	}

}
