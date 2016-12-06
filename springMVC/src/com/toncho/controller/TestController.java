package com.toncho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试类控制器
 * 
 * @author tangchao
 *
 */
@Controller("UserController")
@RequestMapping("/user")
public class TestController {

	/**
	 * 跳转到测试datatables页面
	 */
	@RequestMapping(value = "/toTestDataTables", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		return "test/testDataTables";
	}
}
