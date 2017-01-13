package com.weixin.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.weixin.dao.UserIphoneDao;
import com.weixin.dao.bean.UserIphone;
import com.weixin.service.UserIphoneService;

@Service
public class UserIphoneServiceImpl implements UserIphoneService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserIphoneServiceImpl.class);
	@Resource
	UserIphoneDao userIphoneDao;

    /**
     * 将验证码信息保存到数据库中
     * @param iphone_number
     * @param mask
     * @return
     */
	@Override
	public boolean save(String iphone_number, int mask) {
		UserIphone userIphone = new UserIphone();
		userIphone.setIphoneNumber(iphone_number);
		userIphone.setMask(mask);
		userIphone.setMaskDate(new Timestamp(System.currentTimeMillis()));
		
		int id = userIphoneDao.save(userIphone);
        return id >= 0;
    }

    @Override
    public UserIphone getByPhoneNum(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            logger.debug("- the parameter phone number is need.");
            return null;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("phoneNumber", phoneNumber);
        return userIphoneDao.getByParam(paramMap);
    }

}
