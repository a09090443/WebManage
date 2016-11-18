package com.localhost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.dao.IUserDao;
import com.localhost.model.User;
import com.localhost.model.UserId;
import com.localhost.service.IUserService;
import com.usefulness.utils.date.DateUtils;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {
	private Integer maxId;

	@Autowired
	private IUserDao userDao;

	/* (non-Javadoc)
	 * @see com.localhost.service.IUserService#addUser(com.localhost.model.User)
	 */
	public void addUser(User user) throws Exception {
		Integer newId = 0;
		String getTime;
		if (null == maxId) {
			String userId = userDao.findMaxId();
			if(userId.equals("0")){
				maxId = 0;
			}else{
				maxId = Integer.valueOf(userId);
			}
		}
		newId = maxId + 1;
		getTime = DateUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss");
		String formatStr = "%05d";
		String newIdStr = String.format(formatStr, newId);
		user.getId().setUserId(newIdStr);
		user.setRegisterTime(getTime);
		user.setLastActiveTime(getTime);
		try {
			userDao.save(user);
			maxId += 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.localhost.service.IUserService#delUser(com.localhost.model.User, java.util.Map)
	 */
	public void delUser(User user, Map<String, Object> argMap) throws Exception {
		try {
			userDao.update(user, argMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.localhost.service.IUserService#updateUser(com.localhost.model.User, java.util.Map)
	 */
	public void updateUser(User user, Map<String, Object> argMap) throws Exception {
		try {
			userDao.update(user, argMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.localhost.service.IUserService#findAllUser()
	 */
	public List<User> findAllUser() throws Exception {
		List<User> allUsers = userDao.findAll();
		return allUsers;

	}

	/* (non-Javadoc)
	 * @see com.localhost.service.IUserService#findUserByKey(java.lang.String, java.lang.String)
	 */
	public User findUserByKey(String userId, String account) throws Exception {
		User user = new User();
		UserId id = new UserId();
		id.setUserId(userId);
		id.setAccount(account);
		user.setId(id);
		return userDao.findUser(user);
	}
}
