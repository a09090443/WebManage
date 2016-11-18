package com.localhost.test.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.dao.IUserDao;
import com.localhost.model.User;
import com.localhost.model.UserId;
import com.localhost.test.base.TestBase;

public class UserDaoTest extends TestBase{
	@Autowired
	private IUserDao userDao;

//	@Test
	public void testFindMaxUserId() {
		try {
			String userId = userDao.findMaxId();
			System.out.println(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void testFindUser() {
		try {
			UserId id = new UserId();
			id.setUserId("1");
			id.setAccount("account0");
			User user = new User();
			user.setId(id);
			User userTest = userDao.findUser(user);
			System.out.println(userTest);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
