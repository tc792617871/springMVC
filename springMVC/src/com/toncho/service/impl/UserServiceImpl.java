package com.toncho.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public List<TUser> findUsers(Criteria criteria){
		TUser user = new TUser();
		user.setUserid(7L);
		user.setUsername("ttt");
		user.setPassword("pas");
		user.setAge(111);
		userMapper.deleteByPrimaryKey(5L);
		userMapper.insertSelective(user);
		return userMapper.selectByExample(criteria);
	}

}
