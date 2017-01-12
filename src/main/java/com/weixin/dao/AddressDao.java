package com.weixin.dao;

import java.util.List;
import java.util.Map;

import com.weixin.dao.bean.Address;

public interface AddressDao {
	int save(Address addr);
	List<Address> queryByParam(Map<String, Object> param);
}
