package com.toncho.controller.rest;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toncho.dao.entity.TUser;
import com.toncho.service.api.IUserService;

@Controller("apiUserController")
@RequestMapping("/api/user")
public class UserController {

	@Resource
	private IUserService userService;

	/**
	 * ajax新增用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addUser(@RequestBody TUser user) {
		Boolean flag = userService.insertUser(user);
		return flag;
	}

}
