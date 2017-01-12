package com.weixin.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.common.mydb.SqlClient;
import com.weixin.dao.AddressDao;
import com.weixin.dao.bean.Address;

@Repository
public class AddressDaoImpl implements AddressDao {
	private static final String SQL_NAMESPACE = "address.";
	@Resource
	SqlClient sqlClient;

	@Override
	public int save(Address addr) {
		return sqlClient.persist(addr).intValue();
	}

	@Override
	public List<Address> queryByParam(Map<String, Object> paramMap) {
		return sqlClient.queryForList(SQL_NAMESPACE + "query_by_param", paramMap, Address.class);
	}

}
