package com.toncho.service.api;

import java.util.List;

import com.toncho.dao.entity.TUser;

public interface IUserService {

	List<TUser> findAllUsers();
}
