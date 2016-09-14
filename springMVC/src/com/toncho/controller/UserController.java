package com.toncho.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.toncho.dao.Criteria;
import com.toncho.dao.Page;
import com.toncho.dao.entity.TUser;
import com.toncho.service.api.IUserService;

/**
 * Controller - 用户管理控制器
 * 
 * @author toncho
 *
 */
@Controller("UserController")
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Resource
	private IUserService userService;

	/**
	 * 所有用户
	 */
	@RequestMapping(value = "/listAllUsers", method = RequestMethod.GET)
	public String listAllUsers(ModelMap model) {
		List<TUser> users = new ArrayList<>();
		users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "user/listAllUsers";
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		
		Criteria criteria = new Criteria(new Page(0L,3L));
		List<TUser> users = new ArrayList<>();
		users = userService.findUsers(criteria);
		model.addAttribute("users", users);
		return "user/listAllUsers";
	}
	
	

}
