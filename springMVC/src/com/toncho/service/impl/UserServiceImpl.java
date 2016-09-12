package com.toncho.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.toncho.dao.entity.TUser;
import com.toncho.dao.mapper.TUserMapper;
import com.toncho.service.api.IUserService;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Resource
	private TUserMapper userMapper;

	@Override
	public List<TUser> findAllUsers() {
		return userMapper.queryAllUsers();
	}

}
