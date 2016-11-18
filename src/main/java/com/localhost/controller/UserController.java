package com.localhost.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.localhost.model.User;
import com.localhost.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String userList(ModelMap model) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<User> userList = userService.findAllUser();
		User user = new User();
		model.addAttribute("userEditForm", user);
		model.addAttribute("userAddForm", user);
		model.addAttribute("users", mapper.writeValueAsString(userList));
		return "userlist";
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String userEdit(@ModelAttribute("userEditForm") User user) throws Exception {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", user.getId().getUserId());
		argMap.put("account", user.getId().getAccount());
		userService.updateUser(user, argMap);
		return "redirect:list";
	}
	
	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String userAdd(@ModelAttribute("userAddForm") User user) throws Exception {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", user.getId().getUserId());
		argMap.put("account", user.getId().getAccount());
		userService.addUser(user);
		return "redirect:list";
	}
	
	/**
	 * @param delList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/del" }, method = RequestMethod.POST)
	public String userDel(@RequestParam("delInfo") List<String> delList) throws Exception {
		if(CollectionUtils.isNotEmpty(delList)){
			String[] delInfoSpilt;
			String userId;
			String account;
			User user = new User();
			Map<String, Object> argMap = new HashMap<String, Object>();
			for(String detail:delList){
				delInfoSpilt = detail.split("-");
				userId = delInfoSpilt[0];
				account = delInfoSpilt[1];
				user.setStatus("0");
				argMap.put("userId", userId);
				argMap.put("account", account);
				userService.delUser(user, argMap);
				argMap.clear();
			}
		}
		return "redirect:list";
	}
}
