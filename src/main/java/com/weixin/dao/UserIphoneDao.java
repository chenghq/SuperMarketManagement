package com.weixin.dao;

import java.util.Map;

import com.weixin.dao.bean.UserIphone;

public interface UserIphoneDao {
	int save(UserIphone userIphone);
	
	UserIphone getByParam(Map<String, Object> paramMap);
}
