package com.localhost.test.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.localhost.model.User;
import com.localhost.model.UserId;
import com.localhost.service.IUserService;
import com.localhost.test.base.TestBase;

public class UserServiceTest extends TestBase {

	@Autowired
	private IUserService userService;

	@Test
	public void testAddUser() {
		try {
			System.out.println("test");
			UserId id = new UserId();
			id.setAccount("useraaa");
			User user = new User();
			user.setPassword("testsss");
			user.setUserName("ttaa5");
			user.setEmail("usertest@localhost.com");
			user.setId(id);
			this.userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

//	@Test
	public void testDelUser() {
		try {
			User user = new User();
			UserId id = new UserId();
			id.setAccount("tseg7");
			id.setUserId("00044");
			user.setId(id);
//			this.userService.delUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

//	@Test
	public void testUpdateUser() {
		try {
			UserId id = new UserId();
			id.setAccount("test");
			id.setUserId("00001");
			User user = new User();
			user.setId(id);
			user.setEmail("ggggg@localhost.com");
			
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("userId", "00001");
			conditionMap.put("account", "tseg1");

			this.userService.updateUser(user, conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
//	@Test
	public void testFindUser() {
		try {
			List<User> userList = userService.findAllUser();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
