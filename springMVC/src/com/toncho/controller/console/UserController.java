package com.toncho.controller.console;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller("consoleUserController")
@RequestMapping("/console/user")
public class UserController extends AbstractController {

	@Resource
	private IUserService userService;

	// 1、处理页面跳转
	// 2、处理重定向问题
	// 3、处理ajax问题

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
		Criteria criteria = new Criteria(new Page(0L, 3L));
		List<TUser> users = userService.findUsers(criteria);
		model.addAttribute("users", users);
		return "user/userList";
	}

	/**
	 * 跳转到新增用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddUser", method = RequestMethod.GET)
	public String toAddUser(ModelMap model) {
		return "user/addUser";
	}

	/**
	 * 新增用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(TUser user, ModelMap model) {
		userService.insertUser(user);
		return "redirect:listUsers.do";
	}

	/**
	 * 获取user json数据
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getUserByID", method = RequestMethod.GET)
	@ResponseBody
	public TUser getUser(@RequestParam final Long id) {
		logger.info("获取人员信息id=" + id);
		TUser user = userService.findByID(id);
		return user;
	}

	/**
	 * 获取user json数据
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "v2/getUserByID/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TUser getUserV2(@PathVariable final Long id) {
		logger.info("获取人员信息id=" + id);
		TUser user = userService.findByID(id);
		return user;
	}

}
