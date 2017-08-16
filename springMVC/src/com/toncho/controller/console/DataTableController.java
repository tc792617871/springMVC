package com.toncho.controller.console;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.toncho.dao.Criteria;
import com.toncho.dao.entity.TUser;
import com.toncho.service.api.IUserService;

/**
 * datatable封装
 * 
 * @author tangchao
 *
 */
@Controller("DataTableController")
@RequestMapping("/datatable")
public class DataTableController extends AbstractController {

	@Resource
	private IUserService userService;

	/**
	 * 跳转到测试datatables页面
	 */
	@RequestMapping(value = "/toTestDataTables", method = RequestMethod.GET)
	public String toTestDataTables(ModelMap model) {
		return "test/testDataTables";
	}

	@RequestMapping(value = "/getDataTableData", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDataTableData(@RequestParam String aoData) {
		Criteria criteria = createPaginationCriteria(aoData);
		List<TUser> users = userService.findUsers(criteria);
		JSONObject json = writeJson(criteria.getPage().getCount(), users);
		return json;
	}

}
