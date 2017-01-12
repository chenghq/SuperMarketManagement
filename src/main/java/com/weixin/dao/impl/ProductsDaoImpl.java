package com.weixin.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.common.mydb.SqlClient;
import com.weixin.dao.ProductsDao;
import com.weixin.dao.bean.Products;

@Repository
public class ProductsDaoImpl implements ProductsDao {
	private static final String SQL_NAMESPACE = "products.";
	@Resource
	SqlClient sqlClient;

	@Override
	public int save(Products products) {
		return sqlClient.persist(products).intValue();
	}
	
	@Override
	public int update(Products products) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Products> queryByParam(Map<String, Object> paramMap) {
		return sqlClient.queryForList(SQL_NAMESPACE + "query_by_param", paramMap, Products.class);
	}
	
	@Override
	public Map<String, Object> queryCountByParam(Map<String, Object> paramMap) {
		return sqlClient.queryForMap(SQL_NAMESPACE + "query_count_by_param", paramMap);
	}

}
