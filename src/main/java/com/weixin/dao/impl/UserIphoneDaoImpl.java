package com.weixin.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.common.mydb.SqlClient;
import com.weixin.dao.UserIphoneDao;
import com.weixin.dao.bean.UserIphone;

@Repository
public class UserIphoneDaoImpl implements UserIphoneDao {
	private static final String SQL_NAMESPACE = "user_iphone.";
	@Resource
	SqlClient sqlClient;

	@Override
	public int save(UserIphone userIphone) {
		return sqlClient.persist(userIphone).intValue();
	}

	@Override
	public UserIphone getByParam(Map<String, Object> paramMap) {
		return sqlClient.queryForObject(SQL_NAMESPACE + "get_by_param", paramMap, UserIphone.class);
	}

}
