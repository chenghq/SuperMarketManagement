package com.weixin.dao;

import java.util.List;
import java.util.Map;

import com.weixin.dao.bean.Products;

public interface ProductsDao {
	int save(Products products);
	
	int update(Products products);
	
	List<Products> queryByParam(Map<String, Object> param);
	
	Map<String, Object> queryCountByParam(Map<String, Object> paramMap);
}
