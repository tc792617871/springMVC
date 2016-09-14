package com.toncho.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.toncho.dao.Criteria;
import com.toncho.dao.entity.TUser;
import com.toncho.dao.mapper.TUserMapper;
import com.toncho.service.api.IUserService;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Resource
	private TUserMapper userMapper;

	@Override
	@Cacheable(value="userCache")
	public List<TUser> findAllUsers() {
		return userMapper.queryAllUsers();
	}
	
	@Override
	public List<TUser> findUsers(Criteria criteria){
		return userMapper.selectByExample(criteria);
	}

}
