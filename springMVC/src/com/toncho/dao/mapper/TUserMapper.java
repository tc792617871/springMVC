package com.toncho.dao.mapper;

import java.util.List;

import com.toncho.dao.entity.TUser;

/**
 * @ClassName: TUserMapper
 * @Description: t_user表对应的dao操作Mapper映射类
 */
public interface TUserMapper {

	List<TUser> queryAllUsers();

}