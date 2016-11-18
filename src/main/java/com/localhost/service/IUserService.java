package com.localhost.service;

import java.util.List;
import java.util.Map;

import com.localhost.model.User;

public interface IUserService {
	public List<User> findAllUser() throws Exception;
	
	public User findUserByKey(String userId, String account) throws Exception;

	public void addUser(User user) throws Exception;

	public void delUser(User user, Map<String, Object> argMap) throws Exception;

	public void updateUser(User user, Map<String, Object> argMap) throws Exception;
}
