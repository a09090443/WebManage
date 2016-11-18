package com.localhost.dao;

import com.localhost.base.dao.BaseHibernateDAO;
import com.localhost.model.User;

public interface IUserDao extends BaseHibernateDAO<User> {
	public String findMaxId();
	
	public User findUser(User user);
}
