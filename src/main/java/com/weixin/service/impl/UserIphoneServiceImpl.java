package com.weixin.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.weixin.dao.UserIphoneDao;
import com.weixin.dao.bean.UserIphone;
import com.weixin.service.UserIphoneService;

@Service
public class UserIphoneServiceImpl implements UserIphoneService {
	@Resource
	UserIphoneDao userIphoneDao;

	@Override
	public boolean save(String iphone_number, int mask) {
		UserIphone userIphone = new UserIphone();
		userIphone.setIphoneNumber(iphone_number);
		userIphone.setMask(mask);
		userIphone.setMaskDate(new Timestamp(System.currentTimeMillis()));
		
		userIphoneDao.save(userIphone);
		return true;
	}

}
